package dm.diabetesmanagementmainbe.dao.model.user;

import lombok.Getter;

import java.time.Duration;

@Getter
public enum AppointmentType {
    EYE_EXAM("Eye Exam", Duration.ofDays(365)),
    CHECK_UP("Check Up", Duration.ofDays(180)), // 6 months
    A1C_TEST("A1C Test", Duration.ofDays(90)),   // 3 months
    KIDNEY_FUNCTION("Kidney Function", Duration.ofDays(365)),
    LIVER_FUNCTION("Liver Function", Duration.ofDays(365)),
    LIPID_PROFILE("Lipid Profile", Duration.ofDays(365));

    private final String label;
    private final Duration frequency;

    AppointmentType(String label, Duration frequency) {
        this.label = label;
        this.frequency = frequency;
    }

    public static AppointmentType fromLabel(String label) {
        for (AppointmentType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        return CHECK_UP; // Default
    }
}
