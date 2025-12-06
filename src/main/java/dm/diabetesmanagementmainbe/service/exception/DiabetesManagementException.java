package dm.diabetesmanagementmainbe.service.exception;

import lombok.Getter;

@Getter
public abstract class DiabetesManagementException extends RuntimeException {

    private String message;

    public DiabetesManagementException(String message){
        this.message = message;
    }

    public abstract ExceptionErrorCode code();
}

