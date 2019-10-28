package io;

import exceptions.TaskException;
import exceptions.TooManyIncompleteTasksException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SavableTest {
    private TaskList taskList;
    private TaskList taskList1;
    private TaskList taskList3;
    private TaskList taskList4;
    private TaskListHashMap taskListHashMap;
    private TaskListHashMap taskListHashMap1;
    private Savable savable;

    @BeforeEach
    void runBefore() {
        taskList = new TaskList("a");
        taskList1 = new TaskList("b");
        taskListHashMap = new TaskListHashMap();
        taskListHashMap1 = new TaskListHashMap();

        try {
            new IncompleteTask(taskList,"task I", (LocalDate.of(2020, 2, 3)),
                    "high");
            new IncompleteTask(taskList,"task III", (LocalDate.of(2020, 4, 3)),
                    "high");

            new CompletedTask(taskList,"task IV", (LocalDate.of(2019, 10, 4)), "past due.");
            new CompletedTask(taskList1,"task VII", (LocalDate.of(2019, 4, 5)), "4/5");

            new ImportantTask(taskList1,"task V", (LocalDate.of(2019, 12, 2)),
                    "mid", "High Importance");
            new ImportantTask(taskList1,"task VI", (LocalDate.of(2020, 10, 3)),
                    "mid", "High Importance");

        } catch (TaskException e) {
            fail();
        }

        savable = new SaveAndLoad();
        taskListHashMap.storeTaskList(taskList);
        taskListHashMap.storeTaskList(taskList1);
    }

    @Test
    void saveTest() throws IOException {

        savable.save(taskListHashMap, "saveTest.txt");
        try {
            ((SaveAndLoad) savable).load(taskListHashMap1, "saveTest.txt");
        } catch (TaskException e) {
            fail();
        }

        assertEquals(taskListHashMap.getTaskList("a").getTask(1).getContent(),"task I");
        assertEquals(((IncompleteTask)(taskListHashMap.getTaskList("a").getTask(1))).getUrgency(),"high");
        assertEquals(taskListHashMap.getTaskList("a").getTask(1).getDueDateObj(), LocalDate.of(2020,2,3));

        assertEquals(taskListHashMap.getTaskList("a").getTask(2).getContent(),"task III");
        assertEquals(((IncompleteTask)(taskListHashMap.getTaskList("a").getTask(2))).getUrgency(),"high");
        assertEquals(taskListHashMap.getTaskList("a").getTask(2).getDueDateObj(), LocalDate.of(2020,4,3));

        assertEquals(taskListHashMap.getTaskList("a").getTask(3).getContent(), "task IV");
        assertEquals(taskListHashMap.getTaskList("a").getTask(3).getDueDateObj(), LocalDate.of(2019,10,4));
        assertEquals(((CompletedTask) taskListHashMap.getTaskList("a").getTask(3)).getCompletionStatus(), "past due.");

        assertEquals(taskListHashMap.getTaskList("b").getTask(1).getContent(), "task VII");
        assertEquals(taskListHashMap.getTaskList("b").getTask(1).getDueDateObj(), LocalDate.of(2019,4,5));
        assertEquals(((CompletedTask) taskListHashMap.getTaskList("b").getTask(1)).getCompletionStatus(), "4/5");

        assertEquals(taskListHashMap.getTaskList("b").getTask(2).getContent(), "task V");
        assertEquals(taskListHashMap.getTaskList("b").getTask(2).getDueDateObj(), LocalDate.of(2019,12,2));
        assertEquals(((ImportantTask) taskListHashMap.getTaskList("b").getTask(2)).getUrgency(), "mid");
        assertEquals(((ImportantTask) taskListHashMap.getTaskList("b").getTask(2)).getImportance(), "High Importance");

        assertEquals(taskListHashMap.getTaskList("b").getTask(3).getContent(), "task VI");
        assertEquals(taskListHashMap.getTaskList("b").getTask(3).getDueDateObj(), LocalDate.of(2020,10,3));
        assertEquals(((ImportantTask) taskListHashMap.getTaskList("b").getTask(3)).getUrgency(), "mid");
        assertEquals(((ImportantTask) taskListHashMap.getTaskList("b").getTask(3)).getImportance(), "High Importance");
    }
}
