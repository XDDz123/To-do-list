package exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ModifyCompletedTaskExceptionTest {
    @Test
    void testModifyCompletedTaskExceptionConstructor() {
        Exception exception = new ModifyCompletedTaskException();
        assertEquals(exception.getMessage(), "Error! Completed tasks can not be modified.");
    }
}
