package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompletedTaskTest {
    private CompletedTask completedTask;
    private String taskContent = "empty task";
    private MonthDay taskDueDate = MonthDay.now();
    private String completionStatus = "tbd";

    @BeforeEach
    public void runBefore() {
        completedTask = new CompletedTask(taskContent, taskDueDate, completionStatus);
    }

    @Test
    public void getCompletionStatus() {
        assertEquals(completedTask.getCompletionStatus(), "tbd");
    }

    @Test
    public void printTaskTest() {
        assertEquals(completedTask.printTask(), "empty task"  + "  " + "Due: " + MonthDay.now().getMonthValue()
                + "/" + MonthDay.now().getDayOfMonth() + "  " + "Completed on: " + "tbd");
    }
}
