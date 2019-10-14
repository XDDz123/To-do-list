package exceptions;

public class TooManyIncompleteTasksException extends Exception {
    public TooManyIncompleteTasksException() {
        super("Too many incomplete tasks!");
    }
}
