package io;

import model.RegularTask;
import model.TaskList;
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
    private Savable savable;

    private String taskContent = "empty RegularTask";
    private MonthDay taskDueDate = MonthDay.now();
    private String taskUrgency = "unassigned";
    private String taskImportance = "important";


    @BeforeEach
    public void runBefore() {
        taskList = new TaskList();
        taskList1 = new TaskList();
        task1 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task2 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        taskList.storeTask(task1);
        taskList.storeTask(task2);
        savable = new SaveAndLoad();
    }

    @Test
    public void saveTest() throws IOException {
        task1.setContent("task I");
        task2.setContent("task III");
        task1.setUrgency("high");
        task2.setUrgency("high");
        task1.setDueDate(MonthDay.of(2,3));
        task2.setDueDate(MonthDay.of(4,3));

        savable.save(taskList, "saveTest.txt");
        ((SaveAndLoad)savable).load(taskList1, "saveTest.txt");

        assertEquals(taskList1.getTask(1).getContent(),"task I");
        assertEquals(((RegularTask)(taskList1.getTask(1))).getUrgency(),"high");
        assertEquals(taskList1.getTask(1).getDueDateObj(),MonthDay.of(2,3));

        assertEquals(taskList1.getTask(2).getContent(),"task III");
        assertEquals(((RegularTask)(taskList1.getTask(2))).getUrgency(),"high");
        assertEquals(taskList1.getTask(2).getDueDateObj(),MonthDay.of(4,3));
    }
}
