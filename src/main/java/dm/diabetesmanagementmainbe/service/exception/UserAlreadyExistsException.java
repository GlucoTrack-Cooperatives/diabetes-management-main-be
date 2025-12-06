package dm.diabetesmanagementmainbe.service.exception;


public class UserAlreadyExistsException extends DiabetesManagementException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public ExceptionErrorCode code() {
        return ExceptionErrorCode.USER_ALREADY_EXISTS;
    }
}
