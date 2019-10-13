package io;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavableTest {
    private TaskList taskList;
    private TaskList taskList1;
    private RegularTask task1;
    private RegularTask task2;
    private CompletedTask task3;
    private CompletedTask task4;
    private ImportantTask task5;
    private ImportantTask task6;
    private Savable savable;

    @BeforeEach
    public void runBefore() {
        taskList = new TaskList();
        taskList1 = new TaskList();
        task1 = new RegularTask("task I", (LocalDate.of(2020,2,3)),
                "high");
        task2 = new RegularTask("task III", (LocalDate.of(2020,4,3)),
                "high");

        task3 = new CompletedTask("task IV", (LocalDate.of(2019,10,4)), "past due.");
        task4 = new CompletedTask("task VII", (LocalDate.of(2019,4,5)), "4/5");

        task5 = new ImportantTask("task V", (LocalDate.of(2019,12,2)),
                "mid", "High Importance");
        task6 = new ImportantTask("task VI", (LocalDate.of(2020,10,3)),
                "mid", "High Importance");

        taskList.storeTask(task1);
        taskList.storeTask(task2);
        taskList.storeTask(task3);
        taskList.storeTask(task4);
        taskList.storeTask(task5);
        taskList.storeTask(task6);
        savable = new SaveAndLoad();
    }

    @Test
    public void saveTest() throws IOException {

        savable.save(taskList, "saveTest.txt");
        ((SaveAndLoad) savable).load(taskList1, "saveTest.txt");

        assertEquals(taskList1.getTask(1).getContent(),"task I");
        assertEquals(((RegularTask)(taskList1.getTask(1))).getUrgency(),"high");
        assertEquals(taskList1.getTask(1).getDueDateObj(), LocalDate.of(2020,2,3));

        assertEquals(taskList1.getTask(2).getContent(),"task III");
        assertEquals(((RegularTask)(taskList1.getTask(2))).getUrgency(),"high");
        assertEquals(taskList1.getTask(2).getDueDateObj(), LocalDate.of(2020,4,3));

        assertEquals(taskList1.getTask(3).getContent(), "task IV");
        assertEquals(taskList1.getTask(3).getDueDateObj(), LocalDate.of(2019,10,4));
        assertEquals(((CompletedTask) taskList1.getTask(3)).getCompletionStatus(), "past due.");

        assertEquals(taskList1.getTask(4).getContent(), "task VII");
        assertEquals(taskList1.getTask(4).getDueDateObj(), LocalDate.of(2019,4,5));
        assertEquals(((CompletedTask) taskList1.getTask(4)).getCompletionStatus(), "4/5");

        assertEquals(taskList1.getTask(5).getContent(), "task V");
        assertEquals(taskList1.getTask(5).getDueDateObj(), LocalDate.of(2019,12,2));
        assertEquals(((ImportantTask) taskList1.getTask(5)).getUrgency(), "mid");
        assertEquals(((ImportantTask) taskList1.getTask(5)).getImportance(), "High Importance");

        assertEquals(taskList1.getTask(6).getContent(), "task VI");
        assertEquals(taskList1.getTask(6).getDueDateObj(), LocalDate.of(2020,10,3));
        assertEquals(((ImportantTask) taskList1.getTask(6)).getUrgency(), "mid");
        assertEquals(((ImportantTask) taskList1.getTask(6)).getImportance(), "High Importance");
    }
}
