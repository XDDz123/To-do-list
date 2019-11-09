package io;

import exceptions.TaskException;
import model.*;
import model.task.CompletedTask;
import model.task.IncompleteTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


class LoadTest {

    private Load loadable;
    private TaskListHashMap taskListHashMap;

    @BeforeEach
    void runBefore() {
        loadable = new Load();
        taskListHashMap = new TaskListHashMap();
    }

    @Test
    void loadTest() throws IOException {
        try {
            loadable.load(taskListHashMap, "saveTest.txt");
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
        assertEquals(((CompletedTask) taskListHashMap.getTaskList("b").getTask(2)).getCompletionStatus(), "past due");

        assertEquals(taskListHashMap.getTaskList("b").getTask(3).getContent(), "task VI");
        assertEquals(taskListHashMap.getTaskList("b").getTask(3).getDueDateObj(), LocalDate.of(2020,10,3));
        assertEquals(((IncompleteTask) taskListHashMap.getTaskList("b").getTask(3)).getUrgency(), "mid");
        assertEquals(((IncompleteTask) taskListHashMap.getTaskList("b").getTask(3)).getStarred(), false);
    }

}
