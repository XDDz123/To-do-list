package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportantTaskTest {

    private ImportantTask importantTask;
    private String taskContent = "empty task";
    private LocalDate taskDueDate = LocalDate.now();
    private String taskUrgency = "unassigned";
    private String taskImportance = "unassigned";

    @BeforeEach
    void runBefore() {
        importantTask = new ImportantTask(taskContent, taskDueDate, taskUrgency, taskImportance);
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
