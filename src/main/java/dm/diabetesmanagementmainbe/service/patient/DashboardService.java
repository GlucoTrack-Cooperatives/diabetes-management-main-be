package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.DashboardStatsDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.LatestGlucoseDTO;
import dm.diabetesmanagementmainbe.dao.model.tracker.GlucoseReading;
import dm.diabetesmanagementmainbe.dao.repository.stats.DailyPatientStatRepository;
import dm.diabetesmanagementmainbe.dao.repository.tracker.GlucoseReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final GlucoseReadingRepository glucoseReadingRepository;
    private final DailyPatientStatRepository dailyPatientStatRepository;

    // Constants for glucose ranges (mg/dL)
    private static final int TARGET_RANGE_LOW = 70;
    private static final int TARGET_RANGE_HIGH = 180;

    public LatestGlucoseDTO findLatestGlucose(UUID patientId) {
        return glucoseReadingRepository.findFirstByPatientIdOrderByTimestampDesc(patientId)
                .map(reading -> LatestGlucoseDTO.builder()
                        .value(reading.getValue())
                        .trend(reading.getTrend())
                        .timestamp(reading.getTimestamp())
                        .build())
                .orElse(null);
    }

    public List<LatestGlucoseDTO> findGlucoseHistory(UUID patientId, Integer hours) {
        Instant end = Instant.now();
        Instant start = end.minus(hours, ChronoUnit.HOURS);
        return glucoseReadingRepository.findByPatientIdAndTimestampBetween(patientId, start, end)
                .stream()
                .map(reading -> LatestGlucoseDTO.builder()
                        .value(reading.getValue())
                        .trend(reading.getTrend())
                        .timestamp(reading.getTimestamp())
                        .build())
                .toList();
    }

    public DashboardStatsDTO findDashboardStats(UUID patientId) {
        // Calculate stats based on the last 24 hours of data
        Instant end = Instant.now();
        Instant start = end.minus(24, ChronoUnit.HOURS);
        
        List<GlucoseReading> readings = glucoseReadingRepository.findByPatientIdAndTimestampBetween(patientId, start, end);

        if (readings.isEmpty()) {
            return new DashboardStatsDTO(0, 0, 0);
        }

        double totalGlucose = 0;
        int inRangeCount = 0;
        int belowRangeCount = 0;

        for (GlucoseReading reading : readings) {
            int value = reading.getValue();
            totalGlucose += value;

            if (value >= TARGET_RANGE_LOW && value <= TARGET_RANGE_HIGH) {
                inRangeCount++;
            } else if (value < TARGET_RANGE_LOW) {
                belowRangeCount++;
            }
        }

        double averageGlucose = totalGlucose / readings.size();
        double timeInRange = (double) inRangeCount / readings.size() * 100;
        double timeBelowRange = (double) belowRangeCount / readings.size() * 100;

        return DashboardStatsDTO.builder()
                .averageGlucose(averageGlucose)
                .timeInRange(timeInRange)
                .timeBelowRange(timeBelowRange)
                .build();
    }
}
