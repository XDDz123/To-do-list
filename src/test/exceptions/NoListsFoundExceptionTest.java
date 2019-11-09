package exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NoListsFoundExceptionTest {
    @Test
    void noListsFoundExceptionTest() {
        Exception exception = new NoListsFoundException();
        assertEquals(exception.getMessage(), "No lists found.");
    }
}
