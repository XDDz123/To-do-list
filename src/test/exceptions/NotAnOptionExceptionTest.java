package exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NotAnOptionExceptionTest {
    @Test
    void testNotAnOptionExceptionConstructor() {
        UIException exception = new NotAnOptionException();
        assertEquals(exception.getMessage(), "Error! Not an option.");
    }
}
