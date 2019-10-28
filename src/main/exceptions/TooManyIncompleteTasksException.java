package exceptions;

public class TooManyIncompleteTasksException extends TaskException {
    public TooManyIncompleteTasksException() {
        super("Too many incomplete tasks!");
    }
}
