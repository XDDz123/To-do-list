package model;

import exceptions.TaskException;
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
            incompleteTask = new IncompleteTask(taskList,"empty task", taskDueDate, "unassigned", false);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void getStarredTest() {
        assertFalse(incompleteTask.getStarred());
    }

    @Test
    void setStarredTest() {
        incompleteTask.setStarred(true);
        assertTrue(incompleteTask.getStarred());
    }

    @Test
    void setUrgencyTest() {
        incompleteTask.setUrgency("high");
        assertEquals(incompleteTask.getUrgency(), "high");
    }

    @Test
    void getUrgencyTest() {
        assertEquals(incompleteTask.getUrgency(), "unassigned");
    }

    @Test
    void printTaskTest() {
        assertEquals(incompleteTask.printTask(), "empty task"  + "  " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "  " + "Urgency: " + "unassigned" + "  " + "Time left: tbd"
                + "  " + "Starred: false");
    }


    @Test
    void setTimeLeftTest() {
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(), "due today");
    }

    @Test
    void getTimeLeftTest() {
        assertEquals(incompleteTask.getTimeLeft(), "tbd");
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(), "due today");
    }

    @Test
    void changeTimeLeftTest() {
        incompleteTask.changeTimeLeft("1 day");
        assertEquals(incompleteTask.getTimeLeft(), "1 day");
    }

    @Test
    void changeTimeLeftAltTest() {
        incompleteTask.changeTimeLeft("2 days");
        assertEquals(incompleteTask.getTimeLeft(), "2 days");
    }


    @Test
    void computeTimeLeftDueTodayTest() {
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(),"due today");
    }

    @Test
    void computeTimeLeftInDaysTest() {
        incompleteTask.setDueDate(LocalDate.of(2019, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth() + 1));
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(),1 + " days");
    }

    @Test
    void computeTimeLeftInMonthsTest() {
        incompleteTask.setDueDate(LocalDate.of(2019,LocalDate.now().getMonthValue() + 1, LocalDate.now().getDayOfMonth()));
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(),1 + " months " + 0 + " days");
    }

    @Test
    void computeTimeLeftInDaysAndMonthsTest() {
        incompleteTask.setDueDate(LocalDate.of(2019,LocalDate.now().getMonthValue() + 1,
                LocalDate.now().getDayOfMonth() + 1));
        incompleteTask.setTimeLeft();
        assertEquals(incompleteTask.getTimeLeft(),1 + " months " + 1 + " days");
    }

    @Test
    void getTaskDueDateTest() {
        assertEquals(incompleteTask.getTaskDueDate(), LocalDate.now());
    }
}
