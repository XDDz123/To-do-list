/*
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

    @BeforeEach
    public void runBefore() {
        taskList = new TaskList();
        taskList1 = new TaskList();
        task1 = new RegularTask();
        task2 = new RegularTask();
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
        //task1.setStatus(true);

        savable.save(taskList, "saveTest.txt");
        ((SaveAndLoad)savable).load(taskList1, "saveTest.txt");

        assertEquals(taskList1.getTask(1).getContent(),"task I");
        //assertEquals(taskList1.getTask(1).getUrgency(),"high");
        assertEquals(taskList1.getTask(1).getDueDateObj(),MonthDay.of(2,3));
        //assertTrue(taskList1.getTask(1).getStatus());

        assertEquals(taskList1.getTask(2).getContent(),"task III");
        //assertEquals(taskList1.getTask(2).getUrgency(),"high");
        assertEquals(taskList1.getTask(2).getDueDateObj(),MonthDay.of(4,3));
        //assertFalse(taskList1.getTask(2).getStatus());
    }
}
*/
