package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.MonthDay;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletedTaskTest {
    private CompletedTask completedTask;
    private LocalDate taskDueDate = LocalDate.now();

    @BeforeEach
    void runBefore() {
        completedTask = new CompletedTask("empty task", taskDueDate, "tbd");
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
