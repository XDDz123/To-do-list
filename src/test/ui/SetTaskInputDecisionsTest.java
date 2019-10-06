package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.MonthDay;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SetTaskInputDecisionsTest {
    private SetTaskInputDecisions taskBehavior;
    private MonthDay monthDay;

    @BeforeEach
    public void runBefore() {
        taskBehavior = new SetTaskInputDecisions();
        MonthDay monthDay;
        monthDay = MonthDay.of(1,1);
        monthDay.atYear(Year.now().getValue());
    }


    @Test
    public void checkMonthTest() {
        assertTrue(taskBehavior.checkMonth(12));
        assertTrue(taskBehavior.checkMonth(2));
        assertFalse(taskBehavior.checkMonth(0));
        assertFalse(taskBehavior.checkMonth(-2));
        assertFalse(taskBehavior.checkMonth(13));
    }

    @Test
    public void checkDayJanTest() {
        monthDay = MonthDay.of(1,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
    }

    @Test
    public void checkFebDayTest() {
        monthDay = MonthDay.of(2,1);
        assertFalse(taskBehavior.checkDay(29, monthDay));
        assertTrue(taskBehavior.checkDay(28, monthDay));
    }

    @Test
    public void checkDayMarTest() {
        monthDay = MonthDay.of(3,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
    }

    @Test
    public void checkDayTestApr() {
        monthDay = MonthDay.of(4,1);
        assertTrue(taskBehavior.checkDay(30, monthDay));
        assertFalse(taskBehavior.checkDay(31, monthDay));
    }

    @Test
    public void checkDayMayTest() {
        monthDay = MonthDay.of(5,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
    }

    @Test
    public void checkDayJunTest() {
        monthDay = MonthDay.of(6,1);
        assertTrue(taskBehavior.checkDay(30, monthDay));
        assertFalse(taskBehavior.checkDay(31, monthDay));
    }

    @Test
    public void checkDayTestJul() {
        monthDay = MonthDay.of(7,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
    }

    @Test
    public void checkDayAugTest() {
        monthDay = MonthDay.of(8,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
    }

    @Test
    public void checkDayTestSep() {
        monthDay = MonthDay.of(9,1);
        assertTrue(taskBehavior.checkDay(30, monthDay));
        assertFalse(taskBehavior.checkDay(31, monthDay));
    }

    @Test
    public void checkDayOctTest() {
        monthDay = MonthDay.of(10,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
    }

    @Test
    public void checkDayNovTest() {
        monthDay = MonthDay.of(11,1);
        assertTrue(taskBehavior.checkDay(30, monthDay));
        assertFalse(taskBehavior.checkDay(31, monthDay));
    }

    @Test
    public void checkDayDecTest() {
        monthDay = MonthDay.of(12,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
    }
}
