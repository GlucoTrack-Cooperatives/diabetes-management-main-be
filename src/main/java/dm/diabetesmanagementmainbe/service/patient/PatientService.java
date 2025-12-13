package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.dtos.DashboardDTO;
import dm.diabetesmanagementmainbe.dtos.KeyMetricsDTO;
import dm.diabetesmanagementmainbe.dao.model.logging.FoodLog;
import dm.diabetesmanagementmainbe.dao.model.tracker.GlucoseReading;
import dm.diabetesmanagementmainbe.dao.model.logging.InsulinDose;
import dm.diabetesmanagementmainbe.dao.repository.logging.FoodLogRepository;
import dm.diabetesmanagementmainbe.dao.repository.tracker.GlucoseReadingRepository;
import dm.diabetesmanagementmainbe.dao.repository.logging.InsulinDoseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final GlucoseReadingRepository glucoseReadingRepository;
    private final FoodLogRepository foodLogRepository;
    private final InsulinDoseRepository insulinDoseRepository;

    @Override
    public DashboardDTO getDashboardData() {
        // Mock data for now
        var realTimeGlucose = new GlucoseReading();
        realTimeGlucose.setValue(145);
        realTimeGlucose.setTrend("up");

        var glucoseReadings = List.of(
                new GlucoseReading(),
                new GlucoseReading()
        );

        var keyMetrics = KeyMetricsDTO.builder()
                .timeInRange(72)
                .timeBelowRange(4)
                .averageGlucose(165)
                .build();

        return DashboardDTO.builder()
                .realTimeGlucose(realTimeGlucose)
                .glucoseReadings(glucoseReadings)
                .keyMetrics(keyMetrics)
                .build();
    }

    @Override
    public FoodLog logMeal(FoodLog foodLog) {
        return foodLogRepository.save(foodLog);
    }

    @Override
    public InsulinDose logInsulin(InsulinDose insulinDose) {
        return insulinDoseRepository.save(insulinDose);
    }
}
