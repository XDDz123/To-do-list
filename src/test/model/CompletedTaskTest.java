package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.MonthDay;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletedTaskTest {
    private CompletedTask completedTask;
    private String taskContent = "empty task";
    private LocalDate taskDueDate = LocalDate.now();
    private String completionStatus = "tbd";

    @BeforeEach
    void runBefore() {
        completedTask = new CompletedTask(taskContent, taskDueDate, completionStatus);
    }

    @Test
    void getCompletionStatus() {
        assertEquals(completedTask.getCompletionStatus(), "tbd");
    }

    @Test
    void printTaskTest() {
        assertEquals(completedTask.printTask(), "empty task"  + "  " + "Due: " + MonthDay.now().getMonthValue()
                + "/" + MonthDay.now().getDayOfMonth() + "  " + "Completed on: " + "tbd");
    }
}
