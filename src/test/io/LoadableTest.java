package io;

import model.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

        assertEquals(taskList.getTask(1).getContent(), "task I");
        assertEquals(taskList.getTask(2).getContent(), "task III");
        assertEquals(taskList.getTask(3).getContent(), "task IV");
        assertEquals(taskList.getTask(4).getContent(), "task II");
/*        assertEquals(taskList.getTask(1).getUrgency(), "high");
        assertEquals(taskList.getTask(2).getUrgency(), "high");
        assertEquals(taskList.getTask(3).getUrgency(), "low");
        assertEquals(taskList.getTask(4).getUrgency(), "low");*/
        assertEquals(taskList.getTask(1).getDueDate(), "2/3");
        assertEquals(taskList.getTask(2).getDueDate(), "4/3");
        assertEquals(taskList.getTask(3).getDueDate(), "6/7");
        assertEquals(taskList.getTask(4).getDueDate(), "9/27");

/*      assertFalse(taskList.getTask(1).getStatus());
        assertFalse(taskList.getTask(2).getStatus());
        assertTrue(taskList.getTask(3).getStatus());
        assertFalse(taskList.getTask(4).getStatus());*/
    }
}
