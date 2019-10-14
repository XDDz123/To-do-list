package exceptions;

public class TaskDoesNotExistException extends Exception {
    public TaskDoesNotExistException() {
        super("Selection out of bounds!");
    }
}
