package model;

import model.observer.ObserverState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ObserverStateTest {

    @Test
    void testOneStateConstructor() {
        ObserverState observerState = new ObserverState<>("state");
        assertEquals(observerState.getStateOne(), "state");
    }
}
