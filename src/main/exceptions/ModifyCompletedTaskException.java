package exceptions;

public class ModifyCompletedTaskException extends Exception {
    public ModifyCompletedTaskException() {
        super("Error! Completed tasks can not be modified.");
    }
}
