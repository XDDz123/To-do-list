package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UIExceptionTest {

    @Test
    void testUIExceptionConstructor() {
        Exception exception = new UIException("msg");
        assertEquals(exception.getMessage(), "msg");
    }
}
