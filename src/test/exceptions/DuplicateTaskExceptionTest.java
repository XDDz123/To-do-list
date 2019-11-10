package exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DuplicateTaskExceptionTest {
    @Test
    void testDuplicateTaskExceptionConstructor() {
        Exception exception = new DuplicateTaskException();
        assertEquals(exception.getMessage(), "Error! Duplicate task.");
    }
}
