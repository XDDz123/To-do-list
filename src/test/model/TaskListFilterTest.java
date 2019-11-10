package model;

import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskListFilterTest extends TaskListTestSetup {
    private ArrayList<Task> list;

    @BeforeEach
    void runBefore() {
        setUp();
        list = new ArrayList<>();
    }

    @Test
    void testGetTaskByUrgencyHigh() {
        list.add(task1);
        list.add(task3);
        assertEquals(taskList.getTaskByUrgency("high"), list);
    }

    @Test
    void testGetTaskByUrgencyMid() {
        list.add(task2);
        assertEquals(taskList.getTaskByUrgency("mid"), list);
    }

    @Test
    void testGetTaskByUrgencyLow() {
        list.add(task4);
        assertEquals(taskList.getTaskByUrgency("low"), list);
    }
}
