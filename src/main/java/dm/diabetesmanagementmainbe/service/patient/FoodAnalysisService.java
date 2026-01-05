package dm.diabetesmanagementmainbe.service.patient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dm.diabetesmanagementmainbe.dtos.FoodAnalysisResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodAnalysisService {

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    private final String geminiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FoodAnalysisResponseDTO analyzeFoodImage(MultipartFile file) {
        try {
            log.info("Starting analysis for file: {}", file.getOriginalFilename());

            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

            // --- THE FIX STARTS HERE ---
            // 1. Get the original filename (e.g., "pizza.jpg")
            String fileName = file.getOriginalFilename();
            String mimeType = "image/jpeg"; // Default fallback

            // 2. Check extension to set correct MIME type for Gemini
            if (fileName != null) {
                String lowerName = fileName.toLowerCase();
                if (lowerName.endsWith(".png")) {
                    mimeType = "image/png";
                } else if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) {
                    mimeType = "image/jpeg";
                } else if (lowerName.endsWith(".webp")) {
                    mimeType = "image/webp";
                }
            }
            log.info("Fixed MIME type to: {}", mimeType);
            // --- THE FIX ENDS HERE ---

            String jsonBody = """
                {
                  "contents": [{
                    "parts": [
                      {"text": "Analyze this image. Return a raw JSON object with these fields: 'description' (string), 'calories' (int), 'carbs' (int). Do not use Markdown formatting. Limit the description to 10 words. Focus on the food not on the surroundings."},
                      {"inline_data": {
                        "mime_type": "%s",
                        "data": "%s"
                      }}
                    ]
                  }]
                }
            """.formatted(mimeType, base64Image);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(geminiUrl + geminiApiKey, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return parseGeminiResponse(response.getBody());
            }

        } catch (HttpClientErrorException e) {
            log.error("Google API Error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Internal Error", e);
        }
        return new FoodAnalysisResponseDTO("Analysis Error", "0", "0");
    }

    private FoodAnalysisResponseDTO parseGeminiResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);

            if (!root.has("candidates") || root.path("candidates").isEmpty()) {
                return new FoodAnalysisResponseDTO("No Data", "0", "0");
            }

            String aiText = root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();

            log.info("AI Response: {}", aiText); // Log successful response

            aiText = aiText.replaceAll("```json|```", "").trim();
            JsonNode data = objectMapper.readTree(aiText);

            return new FoodAnalysisResponseDTO(
                    data.path("description").asText("Unknown"),
                    String.valueOf(data.path("calories").asInt(0)),
                    String.valueOf(data.path("carbs").asInt(0))
            );
        } catch (Exception e) {
            log.error("Parsing Failed", e);
            return new FoodAnalysisResponseDTO("Parsing Error", "0", "0");
        }
    }
}