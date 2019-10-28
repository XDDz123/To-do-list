package exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoListsFoundExceptionTest {
    @Test
    void NoListsFoundExceptionTest() {
        Exception exception = new NoListsFoundException();
        assertEquals(exception.getMessage(), "No lists found.");
    }
}
