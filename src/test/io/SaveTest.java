package io;

import exceptions.TaskException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SaveTest {

    private TaskListHashMap taskListHashMap;
    private TaskListHashMap taskListHashMap1;
    private Save save;

    @BeforeEach
    void runBefore() {
        TaskList taskList = new TaskList("a");
        TaskList taskList1 = new TaskList("b");
        taskListHashMap = new TaskListHashMap();
        taskListHashMap1 = new TaskListHashMap();

        try {
            new IncompleteTask(taskList,"task I", (LocalDate.of(2020, 2, 3)),
                    "high", true);
            new IncompleteTask(taskList,"task III", (LocalDate.of(2020, 4, 3)),
                    "high", false);

            new CompletedTask(taskList,"task IV", (LocalDate.of(2019, 10, 4)), "past due");
            new CompletedTask(taskList1,"task VII", (LocalDate.of(2019, 4, 5)), "4/5");

            new IncompleteTask(taskList1,"task V", (LocalDate.of(2019, 5, 2)),
                    "mid", true);

            new IncompleteTask(taskList1,"task VI", (LocalDate.of(2020, 10, 3)),
                    "mid", false);

        } catch (TaskException e) {
            fail();
        }

        save = new Save();
        taskListHashMap.storeTaskList(taskList);
        taskListHashMap.storeTaskList(taskList1);
    }

    @Test
    void saveTest() throws IOException {

        save.save(taskListHashMap, "saveTest.txt", "keyList");
        try {
            (new Load()).load(taskListHashMap1, "saveTest.txt", "keyList");
        } catch (TaskException | ClassNotFoundException e) {
            fail();
        }

        assertEquals(taskListHashMap.getTaskList("a").getTask(1).getContent(),"task I");
        assertEquals(((IncompleteTask)(taskListHashMap.getTaskList("a").getTask(1))).getUrgency(),"high");
        assertEquals(taskListHashMap.getTaskList("a").getTask(1).getDueDateObj(), LocalDate.of(2020,2,3));
        assertEquals(((IncompleteTask) taskListHashMap.getTaskList("a").getTask(1)).getStarred(), true);

        assertEquals(taskListHashMap.getTaskList("a").getTask(2).getContent(),"task III");
        assertEquals(((IncompleteTask)(taskListHashMap.getTaskList("a").getTask(2))).getUrgency(),"high");
        assertEquals(taskListHashMap.getTaskList("a").getTask(2).getDueDateObj(), LocalDate.of(2020,4,3));
        assertEquals(((IncompleteTask) taskListHashMap.getTaskList("a").getTask(2)).getStarred(), false);

        assertEquals(taskListHashMap.getTaskList("a").getTask(3).getContent(), "task IV");
        assertEquals(taskListHashMap.getTaskList("a").getTask(3).getDueDateObj(), LocalDate.of(2019,10,4));
        assertEquals(((CompletedTask) taskListHashMap.getTaskList("a").getTask(3)).getCompletionStatus(), "past due");

        assertEquals(taskListHashMap.getTaskList("b").getTask(1).getContent(), "task VII");
        assertEquals(taskListHashMap.getTaskList("b").getTask(1).getDueDateObj(), LocalDate.of(2019,4,5));
        assertEquals(((CompletedTask) taskListHashMap.getTaskList("b").getTask(1)).getCompletionStatus(), "4/5");

        assertEquals(taskListHashMap.getTaskList("b").getTask(2).getContent(), "task V");
        assertEquals(taskListHashMap.getTaskList("b").getTask(2).getDueDateObj(), LocalDate.of(2019,5,2));
        assertEquals(((IncompleteTask) taskListHashMap.getTaskList("b").getTask(2)).getUrgency(), "mid");
        assertEquals(((IncompleteTask) taskListHashMap.getTaskList("b").getTask(2)).getStarred(), true);

        assertEquals(taskListHashMap.getTaskList("b").getTask(3).getContent(), "task VI");
        assertEquals(taskListHashMap.getTaskList("b").getTask(3).getDueDateObj(), LocalDate.of(2020,10,3));
        assertEquals(((IncompleteTask) taskListHashMap.getTaskList("b").getTask(3)).getUrgency(), "mid");
        assertEquals(((IncompleteTask) taskListHashMap.getTaskList("b").getTask(3)).getStarred(), false);
    }
}
