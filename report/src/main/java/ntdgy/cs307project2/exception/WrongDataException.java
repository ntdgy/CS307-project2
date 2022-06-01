package ntdgy.cs307project2.exception;

public class WrongDataException extends Exception {

    public WrongDataException() {
        super();
    }

    public WrongDataException(String message) {
        super(message);
    }

    public WrongDataException(Throwable cause) {
        super(cause);
    }

    public WrongDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
