package model;

import exceptions.TaskException;
//import model.task.IncompleteTask;
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
        TaskList taskList = new TaskList("");
        try {
            task = new Task(taskList,"empty task", taskDueDate, Urgency.UNASSIGNED, false);
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
        assertEquals(task.printTask(), "empty task"  + "  " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "  " + "Urgency: " + "unassigned" + "  " + "Time left: due today"
                + "  " + "Starred: false");
    }

    @Test
    void testSetTimeLeft() {
        //task.setTimeLeft();
        assertEquals(task.getTimeLeft(), "due today");
    }

    @Test
    void testGetTimeLeft() {
        assertEquals(task.getTimeLeft(), "due today");
        //task.setTimeLeft();
        assertEquals(task.getTimeLeft(), "due today");
    }

    @Test
    void testComputeTimeLeftDueToday() {
        //task.setTimeLeft();
        assertEquals(task.getTimeLeft(),"due today");
    }

    @Test
    void testComputeTimeLeftInDays() {
        task.setDueDate(LocalDate.of(2019, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth() + 1));
        //task.setTimeLeft();
        assertEquals(task.getTimeLeft(),1 + " days");
    }

    @Test
    void testComputeTimeLeftInMonths() {
        task.setDueDate(LocalDate.of(2019,LocalDate.now().getMonthValue() + 1, LocalDate.now().getDayOfMonth()));
        //task.setTimeLeft();
        assertEquals(task.getTimeLeft(),1 + " months " + 0 + " days");
    }

    @Test
    void testComputeTimeLeftInDaysAndMonths() {
        task.setDueDate(LocalDate.of(2019,LocalDate.now().getMonthValue() + 1,
                LocalDate.now().getDayOfMonth() + 1));
        //task.setTimeLeft();
        assertEquals(task.getTimeLeft(),1 + " months " + 1 + " days");
    }

    @Test
    void tesGetTaskDueDate() {
        assertEquals(task.getTaskDueDate(), LocalDate.now());
    }
}
