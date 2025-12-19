package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.DashboardStatsDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.LatestGlucoseDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.log.LogEntryDTO;
import dm.diabetesmanagementmainbe.service.patient.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients/{patientId}/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/glucose/latest")
    public ResponseEntity<LatestGlucoseDTO> getLatestGlucose(@PathVariable UUID patientId) {
        return ResponseEntity.ok(dashboardService.findLatestGlucose(patientId));
    }

    @GetMapping("/glucose/history")
    public ResponseEntity<List<LatestGlucoseDTO>> getGlucoseHistory(@PathVariable UUID patientId, @RequestParam(defaultValue = "24") Integer hours) {
        return ResponseEntity.ok(dashboardService.findGlucoseHistory(patientId, hours));
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats(@PathVariable UUID patientId) {
        return ResponseEntity.ok(dashboardService.findDashboardStats(patientId));
    }
}
