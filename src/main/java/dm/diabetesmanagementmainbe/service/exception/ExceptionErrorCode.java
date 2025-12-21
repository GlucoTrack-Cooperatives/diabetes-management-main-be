package dm.diabetesmanagementmainbe.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionErrorCode {

    USER_ALREADY_EXISTS("Invalid input", Category.BUSINESS),
    UNAUTHORIZED_ACCESS("unauthorized-access", Category.BUSINESS),
    RESOURCES_NOT_FOUND("resources-not-found", Category.BUSINESS),
    RESOURCES_EXISTS("resources-already-exists", Category.BUSINESS),
    SOMETHING_WENT_WRONG("something-went-wrong", Category.TECHNICAL),
    INVALID_CREDENTIALS("invalid-credentials", Category.BUSINESS),
    JWT_EXCEPTION("jwt-exception", Category.TECHNICAL),
    EMAIL_NOT_FOUND("email_not_found",Category.BUSINESS),
    INTEGRATION_EXCEPTION("integration_exception", Category.TECHNICAL),
    DEXCOM_AUTHENTICATION_FAILED("dexcom-authentication-failed", Category.BUSINESS);


    private final String shortName;
    private final Category category;

    @Getter
    @RequiredArgsConstructor
    public enum Category {

        BUSINESS("business"),
        TECHNICAL("technical");

        private final String shortName;

    }
}
