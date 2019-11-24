/*
package model;

import model.observer.ListSizeObserver;
import model.observer.ObserverState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Observable;

import static org.junit.jupiter.api.Assertions.*;

class ListSizeObserverTest {
    private ListSizeObserver listSizeObserver;

    @BeforeEach
    void runBefore() {
        listSizeObserver = new ListSizeObserver();
    }

    @Test
    void testGetSize() {
        assertEquals(listSizeObserver.getSize(), 0);
        listSizeObserver.update(new Observable(), new ObserverState<>(1, "test"));
        assertEquals(listSizeObserver.getSize(), 1);
    }

    @Test
    void testClearSize() {
        listSizeObserver.update(new Observable(), new ObserverState<>(1, "test"));
        listSizeObserver.clearSize();
        assertEquals(listSizeObserver.getSize(), 0);
    }
}
*/
