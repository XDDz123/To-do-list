package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularTaskTest {

    private RegularTask regularTask;
    private String taskContent = "empty task";
    private MonthDay taskDueDate = MonthDay.now();
    private String taskUrgency = "unassigned";

    @BeforeEach
    public void runBefore() {
        regularTask = new RegularTask(taskContent, taskDueDate, taskUrgency);
    }

    @Test
    public void setUrgencyTest() {
        regularTask.setUrgency("high");
        assertEquals(regularTask.getUrgency(), "high");
    }

    @Test
    public void getUrgencyTest() {
        assertEquals(regularTask.getUrgency(), "unassigned");
    }

    @Test
    public void printTaskTest() {
        assertEquals(regularTask.printTask(), "empty task"  + "  " + "Due: " + MonthDay.now().getMonthValue()
                + "/" + MonthDay.now().getDayOfMonth() + "  " + "Urgency: " + "unassigned");
    }

}
