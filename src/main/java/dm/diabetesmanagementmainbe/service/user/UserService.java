package dm.diabetesmanagementmainbe.service.user;

import dm.diabetesmanagementmainbe.controller.patient.dto.PatientSignUpRequest;
import dm.diabetesmanagementmainbe.controller.physician.dto.PhysicianSignUpRequest;
import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import dm.diabetesmanagementmainbe.dao.model.user.Physician;
import dm.diabetesmanagementmainbe.dao.model.user.User;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.PhysicianRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.UserRepository;
import dm.diabetesmanagementmainbe.service.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PhysicianRepository physicianRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUpPatient(PatientSignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        var patient = new Patient();
        patient.setEmail(request.getEmail());
        patient.setFullName(request.getFullName());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setRole(User.Role.PATIENT.name());
        patient.setIsActive(true);
        patient.setPhoneNumbers(request.getPhoneNumbers());
        patient.setDob(request.getDob());
        patient.setDiagnosisDate(request.getDiagnosisDate());
        patient.setEmergencyContactPhone(request.getEmergencyContactPhone());

        patientRepository.save(patient);
        log.info("Patient registered successfully with email: {}", request.getEmail());
    }

    public void signUpPhysician(PhysicianSignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        var physician = new Physician();
        physician.setEmail(request.getEmail());
        physician.setFullName(request.getFullName());
        physician.setPassword(passwordEncoder.encode(request.getPassword()));
        physician.setRole(User.Role.PHYSICIAN.name());
        physician.setIsActive(true);
        physician.setSpecialty(request.getSpecialty());
        physician.setLicenseNumber(request.getLicenseNumber());
        physician.setClinicName(request.getClinicName());

        physicianRepository.save(physician);
        log.info("Physician registered successfully with email: {}", request.getEmail());
    }
}
