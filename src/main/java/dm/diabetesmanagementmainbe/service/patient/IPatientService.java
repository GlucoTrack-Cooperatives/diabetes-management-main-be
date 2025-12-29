package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.dtos.DashboardDTO;
import dm.diabetesmanagementmainbe.dao.model.logging.FoodLog;
import dm.diabetesmanagementmainbe.dao.model.logging.InsulinDose;
import dm.diabetesmanagementmainbe.dtos.PatientDTO;

import java.util.UUID;

public interface IPatientService {
    DashboardDTO getDashboardData();
    FoodLog logMeal(FoodLog foodLog);
    InsulinDose logInsulin(InsulinDose insulinDose);
    PatientDTO getPatient(UUID patientId);
}
