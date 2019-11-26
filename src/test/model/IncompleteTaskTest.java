package model;

import exceptions.TaskException;
import model.task.Task;
import model.task.Urgency;
import model.tasklist.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class IncompleteTaskTest {

    private Task task;
    private LocalDate taskDueDate = LocalDate.now();

    @BeforeEach
    void runBefore() {
        TaskList taskList = new TaskList(new Name(""));
        try {
            task = new Task(taskList,"empty task", taskDueDate, Urgency.UNASSIGNED, false, false);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testGetStarred() {
        assertFalse(task.isStarred());
    }

    @Test
    void testSetStarred() {
        task.setStarred(true);
        assertTrue(task.isStarred());
    }

    @Test
    void testSetUrgency() {
        task.setUrgency(Urgency.HIGH);
        assertEquals(task.getUrgency().getString(), "high");
    }

    @Test
    void testGetUrgency() {
        assertEquals(task.getUrgency(), Urgency.UNASSIGNED);
    }

    @Test
    void testPrintTask() {
        assertEquals(task.toString(), "empty task"  + "   " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth()  + "/" + LocalDate.now().getYear() + "   " +
                "Urgency: " + "unassigned" + "   " + "Time left: due today");
    }

    @Test
    void testSetTimeLeft() {
        assertEquals(task.getTimeLeft(), "due today");
    }

    @Test
    void testGetTimeLeft() {
        assertEquals(task.getTimeLeft(), "due today");
        assertEquals(task.getTimeLeft(), "due today");
    }

    @Test
    void testComputeTimeLeftDueToday() {
        assertEquals(task.getTimeLeft(),"due today");
    }

    @Test
    void testComputeTimeLeftInDays() {
        task.setDueDate(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth() + 1));
        assertEquals(task.getTimeLeft(),1 + " day(s)");
    }

    @Test
    void testComputeTimeLeftInMonths() {
        task.setDueDate(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue() + 1, LocalDate.now().getDayOfMonth()));
        assertEquals(task.getTimeLeft(),1 + " month(s) " + 0 + " day(s)");
    }

    @Test
    void testComputeTimeLeftInDaysAndMonths() {
        task.setDueDate(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue() + 1,
                LocalDate.now().getDayOfMonth() + 1));
        assertEquals(task.getTimeLeft(),1 + " month(s) " + 1 + " day(s)");
    }

    @Test
    void testComputeTimeLeftInDaysAndMonthsAndYears() {
        task.setDueDate(LocalDate.of(LocalDate.now().getYear() + 1,LocalDate.now().getMonthValue() + 1,
                LocalDate.now().getDayOfMonth() + 1));
        assertEquals(task.getTimeLeft(),1 + " year(s) " + 1 + " month(s) " + 1 + " day(s)");
    }

    @Test
    void tesGetTaskDueDate() {
        assertEquals(task.getTaskDueDate(), LocalDate.now());
    }
}
