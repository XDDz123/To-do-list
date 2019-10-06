package io;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SavableTest {
    private TaskList taskList;
    private TaskList taskList1;
    private RegularTask task1;
    private RegularTask task2;
    private CompletedTask task3;
    private ImportantTask task4;
    private Savable savable;

    private String taskContent = "empty task";
    private MonthDay taskDueDate = MonthDay.now();
    private String taskUrgency = "unassigned";
    private String taskImportance = "important";


    @BeforeEach
    public void runBefore() {
        taskList = new TaskList();
        taskList1 = new TaskList();
        task1 = new RegularTask("task I", (MonthDay.of(2,3)),
                "high");
        task2 = new RegularTask("task III", (MonthDay.of(4,3)),
                "high");
        task3 = new CompletedTask("task IV", (MonthDay.of(10,4)),
                "past due.");
        task4 = new ImportantTask("task V", (MonthDay.of(12,2)),
                "mid", "High Importance");
        taskList.storeTask(task1);
        taskList.storeTask(task2);
        taskList.storeTask(task3);
        taskList.storeTask(task4);
        savable = new SaveAndLoad();
    }

    @Test
    public void saveTest() throws IOException {

        savable.save(taskList, "saveTest.txt");
        ((SaveAndLoad)savable).load(taskList1, "saveTest.txt");

        assertEquals(taskList1.getTask(1).getContent(),"task I");
        assertEquals(((RegularTask)(taskList1.getTask(1))).getUrgency(),"high");
        assertEquals(taskList1.getTask(1).getDueDateObj(), MonthDay.of(2,3));

        assertEquals(taskList1.getTask(2).getContent(),"task III");
        assertEquals(((RegularTask)(taskList1.getTask(2))).getUrgency(),"high");
        assertEquals(taskList1.getTask(2).getDueDateObj(), MonthDay.of(4,3));

        assertEquals(taskList1.getTask(3).getContent(), "task IV");
        assertEquals(taskList1.getTask(3).getDueDateObj(), MonthDay.of(10, 4));
        assertEquals(((CompletedTask) taskList1.getTask(3)).getCompletionStatus(), "past due.");

        assertEquals(taskList1.getTask(4).getContent(), "task V");
        assertEquals(taskList1.getTask(4).getDueDateObj(), MonthDay.of(12, 2));
        assertEquals(((ImportantTask) taskList1.getTask(4)).getUrgency(), "mid");
        assertEquals(((ImportantTask) taskList1.getTask(4)).getImportance(), "High Importance");
    }
}
