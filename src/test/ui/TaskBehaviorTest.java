package ui;

import ui.TaskBehavior;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TaskBehaviorTest {
    private TaskBehavior taskBehavior;

    @BeforeEach
    public void runBefore() {
        taskBehavior = new TaskBehavior();
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
    public void checkDayTest() {
        MonthDay monthDay;
        monthDay = MonthDay.of(1,1);
        monthDay.atYear(2019);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
        monthDay = MonthDay.of(2,1);
        assertFalse(taskBehavior.checkDay(29, monthDay));
        assertTrue(taskBehavior.checkDay(28, monthDay));
        monthDay = MonthDay.of(3,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
        monthDay = MonthDay.of(4,1);
        assertTrue(taskBehavior.checkDay(30, monthDay));
        assertFalse(taskBehavior.checkDay(31, monthDay));
        monthDay = MonthDay.of(5,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
        monthDay = MonthDay.of(6,1);
        assertTrue(taskBehavior.checkDay(30, monthDay));
        assertFalse(taskBehavior.checkDay(31, monthDay));
        monthDay = MonthDay.of(7,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
        monthDay = MonthDay.of(8,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
        monthDay = MonthDay.of(9,1);
        assertTrue(taskBehavior.checkDay(30, monthDay));
        assertFalse(taskBehavior.checkDay(31, monthDay));
        monthDay = MonthDay.of(10,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
        monthDay = MonthDay.of(11,1);
        assertTrue(taskBehavior.checkDay(30, monthDay));
        assertFalse(taskBehavior.checkDay(31, monthDay));
        monthDay = MonthDay.of(12,1);
        assertTrue(taskBehavior.checkDay(31, monthDay));
        assertFalse(taskBehavior.checkDay(32, monthDay));
    }

}
