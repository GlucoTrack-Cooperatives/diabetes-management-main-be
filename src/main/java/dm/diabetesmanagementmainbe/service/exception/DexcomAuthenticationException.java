package dm.diabetesmanagementmainbe.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class DexcomAuthenticationException extends DiabetesManagementException {

    private final HttpStatusCode httpStatus;

    public DexcomAuthenticationException(String message, HttpStatusCode httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    @Override
    public ExceptionErrorCode code() {
        return ExceptionErrorCode.DEXCOM_AUTHENTICATION_FAILED;
    }
}
