package exceptions;

public class NoListsFoundException extends Exception {
    public NoListsFoundException() {
        super("No lists found.");
    }
}
