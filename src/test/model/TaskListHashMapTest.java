package model;

import model.tasklist.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskListHashMapTest {
    private TaskListHashMap taskListHashMap;

    @BeforeEach
    void runBefore() {
        taskListHashMap = new TaskListHashMap();
    }

    @Test
    void testRemoveTaskList() {
        taskListHashMap.storeTaskList(new TaskList(new Name("key")));
        taskListHashMap.removeTaskList(new Name("key"));
        assertTrue(taskListHashMap.getTaskListMap().isEmpty());
    }

    @Test
    void testGetTaskListMap() {
        taskListHashMap.storeTaskList(new TaskList(new Name("key")));
        assertTrue(taskListHashMap.getTaskList(new Name("key")).isTaskListEmpty());
    }
}
