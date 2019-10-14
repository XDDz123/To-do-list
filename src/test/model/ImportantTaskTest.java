package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportantTaskTest {

    private ImportantTask importantTask;
    private LocalDate taskDueDate = LocalDate.now();


    @BeforeEach
    void runBefore() {
        importantTask = new ImportantTask(
                "empty task",
                taskDueDate,
                "unassigned",
                "unassigned");
    }

    @Test
    void setImportanceTest() {
        importantTask.setImportance("high importance");
        assertEquals(importantTask.getImportance(), "high importance");
    }

    @Test
    void getImportanceTest() {
        assertEquals(importantTask.getImportance(), "unassigned");
    }

    @Test
    void printTaskTest() {
        assertEquals("empty task"  + "  " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "  " + "Urgency: " + "unassigned" + "  " + "Time left: "
                + "tbd" + "  " + "*" + "unassigned" + "*", importantTask.printTask());
    }
}
