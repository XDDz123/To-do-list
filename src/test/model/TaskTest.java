package model;

import exceptions.TaskException;
import model.task.CompletedTask;
import model.task.IncompleteTask;
import model.task.Task;
import model.task.Urgency;
import model.tasklist.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;
    private LocalDate taskDueDate = LocalDate.now();

    @BeforeEach
    void runBefore() {
        try {
            task = new IncompleteTask(null,"empty task", taskDueDate, Urgency.UNASSIGNED, false);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testSetContent() {
        task.setContent("this is a test!");
        assertEquals(task.getContent(), "this is a test!");

    }

    @Test
    void testSetContentEmpty() {
        task.setContent("");
        assertEquals(task.getContent(), "");
    }

    @Test
    void testGetContent() {
        assertEquals(task.getContent(), "empty task");
    }

    @Test
    void testSetDueDate() {
        LocalDate localDate = LocalDate.of(2019,1,2);
        task.setDueDate(localDate);
        assertEquals(task.getDueDateObj(), localDate);
    }

    @Test
    void testSetGetDueDateObj() {
        assertEquals(task.getDueDateObj(), LocalDate.now());
    }

    @Test
    void testGetDueDate() {
        LocalDate localDate = LocalDate.now();
        assertEquals(task.getDueDate(), localDate.getMonthValue() + "/" + localDate.getDayOfMonth());
    }

    @Test
    void testPrintTaskContentAndDate() {
        assertEquals(task.printTaskContentAndDate(), "empty task"  + "  " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "  ");
    }

    @Test
    void testGetTaskList() {
        assertNull(task.getTaskList());
    }

    @Test
    void testSetTaskList() {
        TaskList taskList = new TaskList("list");
        try {
            task.setTaskList(taskList);
        } catch (TaskException e) {
            fail();
        }
        assertEquals(task.getTaskList(), taskList);
        assertEquals(taskList.getTask(1), task);

        TaskList taskList1 = new TaskList("list1");
        try {
            task.setTaskList(taskList1);
        } catch (TaskException e) {
            fail();
        }
        assertTrue(taskList1.getTaskList().contains(task));
        assertFalse(taskList.getTaskList().contains(task));
        assertEquals(taskList1, task.getTaskList());
    }

    @Test
    void testSetTaskListDuplicate() {
        TaskList taskList = new TaskList("list");
        try {
            new IncompleteTask(taskList,"empty task", taskDueDate, Urgency.UNASSIGNED, false);
        } catch (TaskException e) {
            fail();
        }

        try {
            task.setTaskList(taskList);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testEqualsNull() {
        CompletedTask task2;
        Task task3 = null;
        CompletedTask task4 = null;
        TaskList taskList = null;
        try {
            task2 = new CompletedTask(null,"empty task", taskDueDate, "unassigned");
            assertNotEquals(task, task2);
        } catch (TaskException e) {
            fail();
        }

        assertNotEquals(task, task3);
        assertNotEquals(task, task4);
        assertNotEquals(task, taskList);
    }

    @Test
    void testEqualsList() {
        TaskList taskList = new TaskList("");
        try {
            Task task1 = new IncompleteTask(taskList,"empty task", taskDueDate, Urgency.UNASSIGNED, false);
            Task task2 = new CompletedTask(null,"empty task", taskDueDate, "unassigned");
            assertNotEquals(task1, task2);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testHashCode() {
        TaskList taskList = null;
        String taskContent = "empty task";

        int result = taskList != null ? taskList.hashCode() : 0;
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);

        assertEquals(task.hashCode(), result);
    }

    @Test
    void testHashCodeNullList() {
        TaskList taskList = null;
        String taskContent = "empty task";

        int result = taskList != null ? taskList.hashCode() : 0;
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);

        assertEquals(task.hashCode(), result);
    }

    @Test
    void testHashCodeNullContent() {
        TaskList taskList = new TaskList("");
        String taskContent = null;

        Task task1 = null;
        try {
            task1 = new IncompleteTask(taskList,null, taskDueDate, Urgency.UNASSIGNED, false);
        } catch (TaskException e) {
            fail();
        }

        int result = taskList != null ? taskList.hashCode() : 0;
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);

        assertEquals(task1.hashCode(), result);
    }

    @Test
    void testHashCodeNullDueDate() {
        TaskList taskList = new TaskList("");
        String taskContent = "empty task";
        taskDueDate = null;

        Task task1 = null;
        try {
            task1 = new IncompleteTask(taskList,"empty task", null, Urgency.UNASSIGNED, false);
        } catch (TaskException e) {
            fail();
        }

        int result = taskList != null ? taskList.hashCode() : 0;
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);

        assertEquals(task1.hashCode(), result);
    }
}
