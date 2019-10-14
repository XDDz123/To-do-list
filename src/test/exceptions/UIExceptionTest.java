package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UIExceptionTest {

    @Test
    void UIExceptionConstructorTest() {
        Exception exception = new UIException("msg");
        assertEquals(exception.getMessage(), "msg");
    }
}
