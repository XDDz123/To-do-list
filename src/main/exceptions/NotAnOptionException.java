package exceptions;

public class NotAnOptionException extends UIException {
    public NotAnOptionException() {
        super("Error! Not an option.");
    }
}
