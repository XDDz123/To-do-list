package io;

import exceptions.TaskException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;


class LoadableTest {

    private Loadable loadable;
    private TaskListHashMap taskListHashMap;

    @BeforeEach
    void runBefore() {
        loadable = new SaveAndLoad();
        taskListHashMap = new TaskListHashMap();
    }

    @Test
    void loadTest() throws IOException {
        try {
            loadable.load(taskListHashMap, "loadTest.txt");
        } catch (TaskException e) {
            assertEquals(e.getMessage(), "Too many incomplete tasks.");
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
