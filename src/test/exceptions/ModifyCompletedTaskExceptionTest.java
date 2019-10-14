package exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModifyCompletedTaskExceptionTest {
    @Test
    void ModifyCompletedTaskExceptionConstructorTest() {
        Exception exception = new ModifyCompletedTaskException();
        assertEquals(exception.getMessage(), "Error! Completed tasks can not be modified.");
    }
}
