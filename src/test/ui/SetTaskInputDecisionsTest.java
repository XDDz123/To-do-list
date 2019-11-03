package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SetTaskInputDecisionsTest {
    private TaskInputDecisions taskBehavior;
    private LocalDate localDate;

    @BeforeEach
    void runBefore() {
        taskBehavior = new TaskInputDecisions();
        localDate = LocalDate.of(2019, MonthDay.now().getMonthValue(), MonthDay.now().getDayOfMonth());
    }


    @Test
    void checkMonthTest() {
        assertTrue(taskBehavior.checkMonth(12));
        assertTrue(taskBehavior.checkMonth(2));
        assertFalse(taskBehavior.checkMonth(0));
        assertFalse(taskBehavior.checkMonth(-2));
        assertFalse(taskBehavior.checkMonth(13));
    }

    @Test
    void checkDayJanTest() {
        localDate = LocalDate.of(2019,1,1);
        assertTrue(taskBehavior.checkDay(31, localDate));
        assertFalse(taskBehavior.checkDay(32, localDate));
    }

    @Test
    void checkFebDayTest() {
        localDate = LocalDate.of(2019,2,1);
        assertFalse(taskBehavior.checkDay(29, localDate));
        assertTrue(taskBehavior.checkDay(28, localDate));
    }

    @Test
    void checkDayMarTest() {
        localDate = LocalDate.of(2019,3,1);
        assertTrue(taskBehavior.checkDay(31, localDate));
        assertFalse(taskBehavior.checkDay(32, localDate));
    }

    @Test
    void checkDayTestApr() {
        localDate = LocalDate.of(2019,4,1);
        assertTrue(taskBehavior.checkDay(30, localDate));
        assertFalse(taskBehavior.checkDay(31, localDate));
    }

    @Test
    void checkDayMayTest() {
        localDate = LocalDate.of(2019,5,1);
        assertTrue(taskBehavior.checkDay(31, localDate));
        assertFalse(taskBehavior.checkDay(32, localDate));
    }

    @Test
    void checkDayJunTest() {
        localDate = LocalDate.of(2019,6,1);
        assertTrue(taskBehavior.checkDay(30, localDate));
        assertFalse(taskBehavior.checkDay(31, localDate));
    }

    @Test
    void checkDayTestJul() {
        localDate = LocalDate.of(2019,7,1);
        assertTrue(taskBehavior.checkDay(31, localDate));
        assertFalse(taskBehavior.checkDay(32, localDate));
    }

    @Test
    void checkDayAugTest() {
        localDate = LocalDate.of(2019,8,1);
        assertTrue(taskBehavior.checkDay(31, localDate));
        assertFalse(taskBehavior.checkDay(32, localDate));
    }

    @Test
    void checkDayTestSep() {
        localDate = LocalDate.of(2019,9,1);
        assertTrue(taskBehavior.checkDay(30, localDate));
        assertFalse(taskBehavior.checkDay(31, localDate));
    }

    @Test
    void checkDayOctTest() {
        localDate = LocalDate.of(2019,10,1);
        assertTrue(taskBehavior.checkDay(31, localDate));
        assertFalse(taskBehavior.checkDay(32, localDate));
    }

    @Test
    void checkDayNovTest() {
        localDate = LocalDate.of(2019,11,1);
        assertTrue(taskBehavior.checkDay(30, localDate));
        assertFalse(taskBehavior.checkDay(31, localDate));
    }

    @Test
    void checkDayDecTest() {
        localDate = LocalDate.of(2019,12,1);
        assertTrue(taskBehavior.checkDay(31, localDate));
        assertFalse(taskBehavior.checkDay(32, localDate));
    }
}
