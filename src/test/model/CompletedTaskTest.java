package model;

import exceptions.TaskException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.MonthDay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CompletedTaskTest {
    private CompletedTask completedTask;
    private LocalDate taskDueDate = LocalDate.now();

    @BeforeEach
    void runBefore() {
        TaskList taskList = new TaskList("");
        try {
            completedTask = new CompletedTask(taskList,"empty task", taskDueDate, "tbd");
        } catch (TaskException e) {
            fail();
        }
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
