package model;

import exceptions.TaskDoesNotExistException;
import exceptions.TaskException;
//import model.task.CompletedTask;
//import model.task.IncompleteTask;
import model.task.Task;
import model.task.Urgency;
import model.tasklist.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    private TaskList taskList;
    private Task task;
    private String taskContent = "empty task";
    private LocalDate taskDueDate = LocalDate.now();
    private Urgency taskUrgency = Urgency.UNASSIGNED;
    private Boolean starred = false;

    @BeforeEach
    void runBefore() {
        taskList = new TaskList("");
        try {
            task = new Task(taskList, taskContent, taskDueDate, taskUrgency, starred);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testStoreTask() {
        assertEquals(taskList.getTask(1), task);
        assertEquals(taskList.getTaskListSize(), 1);
    }

    @Test
    void testGetTaskList() {
        assertEquals(taskList.getTaskListSize(), 1);

        try {
            new Task(taskList, "a", taskDueDate, taskUrgency, starred);
            assertEquals(taskList.getTaskListSize(), 2);
            new Task(taskList, "b", taskDueDate, taskUrgency, starred);
        } catch (TaskException e) {
            fail();
        }
        assertEquals(taskList.getTaskListSize(), 3);
    }

    @Test
    void testDuplicateTask() {
        try {
            new Task(taskList, taskContent, taskDueDate, taskUrgency, starred);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testGetTask() {
        assertEquals(taskList.getTask(1), task);
    }

    @Test
    void testDeleteTask() {
        try {
            taskList.deleteTask(1);
        } catch (TaskDoesNotExistException | TaskException e) {
            fail();
        }

        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    void testDeleteTaskException() {
        try {
            taskList.deleteTask(10);
            fail();
        } catch (TaskDoesNotExistException | TaskException e) {
            assertEquals(e.getMessage(), "Selection out of bounds!");
        }
    }

    @Test
    void testClearTaskList() {
        taskList.clearTaskList();
        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    void testIsTaskListEmpty() {
        assertFalse(taskList.isTaskListEmpty());
        taskList.clearTaskList();
        assertTrue(taskList.isTaskListEmpty());
    }

    @Test
    void testGetTaskListSize() {
        assertEquals(taskList.getTaskListSize(), 1);
    }

    @Test
    void testTooManyTasks() {
        try {
            for (int i = 0; i <=  TaskList.maxSize; i++) {
                task = new Task(taskList, Integer.toString(i), taskDueDate, taskUrgency, starred);
            }
        } catch (TaskException e) {
            assertEquals(e.getMessage(), "Too many incomplete tasks!");
        }

/*        try {
            CompletedTask completedTask = new CompletedTask(taskList, taskContent, taskDueDate, "tbd");
            assertEquals(taskList.getTask(taskList.getTaskListSize()), completedTask);
        } catch (TaskException e) {
            fail();
        }*/
    }
}

