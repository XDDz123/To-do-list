package model;

import model.task.Task;
import model.tasklist.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void testRemap() {
        taskListHashMap.storeTaskList(new TaskList(new Name("a")));
        taskListHashMap.storeTaskList(new TaskList(new Name("b")));
        taskListHashMap.storeTaskList(new TaskList(new Name("c")));

        taskListHashMap.remap(new Name("b"), new Name("d"));

        TaskListHashMap taskListHashMap1 = new TaskListHashMap();
        taskListHashMap1.storeTaskList(new TaskList(new Name("a")));
        taskListHashMap1.storeTaskList(new TaskList(new Name("d")));
        taskListHashMap1.storeTaskList(new TaskList(new Name("c")));

        assertEquals(taskListHashMap1.getTaskListMap().keySet(), taskListHashMap.getTaskListMap().keySet());
    }
}
