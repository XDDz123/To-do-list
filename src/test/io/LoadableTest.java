package io;

import model.CompletedTask;
import model.ImportantTask;
import model.RegularTask;
import model.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LoadableTest {

    private TaskList taskList;
    private Loadable loadable;

    @BeforeEach
    public void runBefore() {
        taskList = new TaskList();
        loadable = new SaveAndLoad();
    }

    @Test
    public void loadTest() throws IOException {
        loadable.load(taskList, "loadTest.txt");

        assertEquals(taskList.getTask(1).getContent(),"task I");
        assertEquals(((RegularTask)(taskList.getTask(1))).getUrgency(),"high");
        assertEquals(taskList.getTask(1).getDueDateObj(), MonthDay.of(2,3));

        assertEquals(taskList.getTask(2).getContent(),"task III");
        assertEquals(((RegularTask)(taskList.getTask(2))).getUrgency(),"high");
        assertEquals(taskList.getTask(2).getDueDateObj(), MonthDay.of(4,3));

        assertEquals(taskList.getTask(3).getContent(), "task IV");
        assertEquals(taskList.getTask(3).getDueDateObj(), MonthDay.of(10, 4));
        assertEquals(((CompletedTask) taskList.getTask(3)).getCompletionStatus(), "past due.");

        assertEquals(taskList.getTask(4).getContent(), "task VII");
        assertEquals(taskList.getTask(4).getDueDateObj(), MonthDay.of(4, 5));
        assertEquals(((CompletedTask) taskList.getTask(4)).getCompletionStatus(), "4/5");

        assertEquals(taskList.getTask(5).getContent(), "task V");
        assertEquals(taskList.getTask(5).getDueDateObj(), MonthDay.of(12, 2));
        assertEquals(((ImportantTask) taskList.getTask(5)).getUrgency(), "mid");
        assertEquals(((ImportantTask) taskList.getTask(5)).getImportance(), "High Importance");

        assertEquals(taskList.getTask(6).getContent(), "task VI");
        assertEquals(taskList.getTask(6).getDueDateObj(), MonthDay.of(10, 3));
        assertEquals(((ImportantTask) taskList.getTask(6)).getUrgency(), "mid");
        assertEquals(((ImportantTask) taskList.getTask(6)).getImportance(), "High Importance");

        assertEquals(taskList.getTask(7).getContent(), "task VII");
        assertEquals(taskList.getTask(7).getDueDateObj(), MonthDay.of(10, 3));
        assertEquals(((CompletedTask) taskList.getTask(7)).getCompletionStatus(), "past due.");
    }
}
