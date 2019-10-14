package exceptions;

public class ModifyCompletedTaskException extends UIException {
    public ModifyCompletedTaskException() {
        super("Error! Completed tasks can not be modified.");
    }
}
