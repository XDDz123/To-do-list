package model;

import exceptions.TaskException;
import model.task.IncompleteTask;
import model.task.Urgency;
import model.tasklist.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class IncompleteTaskTest {

    private IncompleteTask incompleteTask;
    private LocalDate taskDueDate = LocalDate.now();

    @BeforeEach
    void runBefore() {
        TaskList taskList = new TaskList("");
        try {
            incompleteTask = new IncompleteTask(taskList,"empty task", taskDueDate, Urgency.UNASSIGNED, false);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testGetStarred() {
        assertFalse(incompleteTask.getStarred());
    }

    @Test
    void testSetStarred() {
        incompleteTask.setStarred(true);
        assertTrue(incompleteTask.getStarred());
    }

    @Test
    void testSetUrgency() {
        incompleteTask.setUrgency(Urgency.HIGH);
        assertEquals(incompleteTask.getUrgency().getString(), "high");
    }

    @Test
    void testGetUrgency() {
        assertEquals(incompleteTask.getUrgency(), Urgency.UNASSIGNED);
    }

    @Test
    void testPrintTask() {
        assertEquals(incompleteTask.printTask(), "empty task"  + "  " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "  " + "Urgency: " + "unassigned" + "  " + "Time left: due today"
                + "  " + "Starred: false");
    }

    @Test
    void testSetTimeLeft() {
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(), "due today");
    }

    @Test
    void testGetTimeLeft() {
        assertEquals(incompleteTask.getTimeLeft(), "due today");
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(), "due today");
    }

    @Test
    void testComputeTimeLeftDueToday() {
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(),"due today");
    }

    @Test
    void testComputeTimeLeftInDays() {
        incompleteTask.setDueDate(LocalDate.of(2019, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth() + 1));
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(),1 + " days");
    }

    @Test
    void testComputeTimeLeftInMonths() {
        incompleteTask.setDueDate(LocalDate.of(2019,LocalDate.now().getMonthValue() + 1, LocalDate.now().getDayOfMonth()));
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(),1 + " months " + 0 + " days");
    }

    @Test
    void testComputeTimeLeftInDaysAndMonths() {
        incompleteTask.setDueDate(LocalDate.of(2019,LocalDate.now().getMonthValue() + 1,
                LocalDate.now().getDayOfMonth() + 1));
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(),1 + " months " + 1 + " days");
    }

    @Test
    void tesGetTaskDueDate() {
        assertEquals(incompleteTask.getTaskDueDate(), LocalDate.now());
    }
}
