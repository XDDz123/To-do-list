package model;

import model.tasklist.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListHashMapTest {
    private TaskListHashMap taskListHashMap;

    @BeforeEach
    void runBefore() {
        taskListHashMap = new TaskListHashMap();
    }

    @Test
    void removeTaskListTest() {
        taskListHashMap.storeTaskList(new TaskList("key"));
        taskListHashMap.removeTaskList("key");
        assertTrue(taskListHashMap.getTaskListMap().isEmpty());
    }

    @Test
    void getTaskListMapTest() {
        taskListHashMap.storeTaskList(new TaskList("key"));
        assertTrue(taskListHashMap.getTaskList("key").isTaskListEmpty());
    }
}
