package jp.co.axa.apidemo.exceptions;

public class EmployeeNotExistException extends RuntimeException {
    public EmployeeNotExistException() {
        super();
    }

    public EmployeeNotExistException(String message) {
        super(message);
    }

    public EmployeeNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeNotExistException(Throwable cause) {
        super(cause);
    }

    protected EmployeeNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
