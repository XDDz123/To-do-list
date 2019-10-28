package exceptions;

public class DuplicateTaskException extends TaskException {
    public DuplicateTaskException() {
        super("Error! Duplicate task.");
    }
}
