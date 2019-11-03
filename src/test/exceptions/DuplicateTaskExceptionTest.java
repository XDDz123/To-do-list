package exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DuplicateTaskExceptionTest {
    @Test
    void DuplicateTaskExceptionConstructorTest() {
        Exception exception = new DuplicateTaskException();
        assertEquals(exception.getMessage(), "Error! Duplicate task.");
    }
}
