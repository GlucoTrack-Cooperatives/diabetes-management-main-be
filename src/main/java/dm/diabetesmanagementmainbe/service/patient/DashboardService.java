package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.DashboardStatsDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.LatestGlucoseDTO;
import dm.diabetesmanagementmainbe.dao.repository.stats.DailyPatientStatRepository;
import dm.diabetesmanagementmainbe.dao.repository.tracker.GlucoseReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final GlucoseReadingRepository glucoseReadingRepository;
    private final DailyPatientStatRepository dailyPatientStatRepository;

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
        // This is a simplified implementation. A real implementation would
        // calculate these stats based on the glucose readings over a period.
        return dailyPatientStatRepository.findByPatientIdAndDate(patientId, LocalDate.now())
                .map(stat -> DashboardStatsDTO.builder()
                        .timeInRange(stat.getTimeInRange())
                        .timeBelowRange(stat.getTimeBelowRange())
                        .averageGlucose(stat.getAverageGlucose())
                        .build())
                .orElse(new DashboardStatsDTO(0, 0, 0));
    }
}
