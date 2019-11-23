package io;

import exceptions.TaskException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


class LoadTest {

    private TaskListHashMap taskListHashMap;

    @BeforeEach
    void runBefore() {
        Load loadable = new Load();
        taskListHashMap = new TaskListHashMap();

        try {
            loadable.load(taskListHashMap, "ioTest.txt");
        } catch (TaskException | ClassNotFoundException | IOException e) {
            fail();
        }
    }

    @Test
    void testLoadTaskOne() {
        Name name = new Name("a");
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getContent(),"task I");
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getUrgency().getString(),"high");
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getDueDateObj(), LocalDate.of(2020,2,3));
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).isStarred(), true);
    }

    @Test
     void testLoadTaskTwo() {
        Name name = new Name("a");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getContent(),"task III");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getUrgency().getString(),"low");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getDueDateObj(), LocalDate.of(2020,4,3));
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).isStarred(), false);
    }

    @Test
    void testSaveTaskThree() {
        Name name = new Name("a");
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getContent(), "task IV");
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getDueDateObj(), LocalDate.of(2019,10,4));
        assertTrue(taskListHashMap.getTaskList(name).getTask(3).isCompleted());
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getTimeLeft(), "Completed");
    }

    @Test
    void testSaveTaskFour() {
        Name name = new Name("b");
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getContent(), "task VII");
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getDueDateObj(), LocalDate.of(2019, 4, 5));
        assertTrue(taskListHashMap.getTaskList(name).getTask(1).isCompleted());
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getTimeLeft(), "Completed");
    }

    @Test
    void testLoadTaskFive() {
        Name name = new Name("b");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getContent(), "task V");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getDueDateObj(), LocalDate.of(2019,5,2));
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getTimeLeft(), "past due");
    }

    @Test
    void testLoadTaskSix() {
        Name name = new Name("b");
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getContent(), "task VI");
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getDueDateObj(), LocalDate.of(2020,10,3));
        assertEquals((taskListHashMap.getTaskList(name).getTask(3)).getUrgency().getString(), "mid");
        assertEquals((taskListHashMap.getTaskList(name).getTask(3)).isStarred(), false);
    }
}
