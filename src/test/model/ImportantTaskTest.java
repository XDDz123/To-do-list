package model;

import exceptions.TaskException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ImportantTaskTest {

    private ImportantTask importantTask;
    private LocalDate taskDueDate = LocalDate.now();


    @BeforeEach
    void runBefore() {
        TaskList taskList = new TaskList("");
        try {
            importantTask = new ImportantTask(
                    taskList,
                    "empty task",
                    taskDueDate,
                    "unassigned",
                    "unassigned");
        } catch (TaskException e) {
            fail();
        }
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
