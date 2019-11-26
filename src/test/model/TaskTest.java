package model;

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

class TaskTest {
    private Task task;
    private TaskList taskList;
    private LocalDate taskDueDate = LocalDate.now();

    @BeforeEach
    void runBefore() {
        try {
            taskList = new TaskList(new Name(""));
            task = new Task(taskList,"empty task", taskDueDate, Urgency.UNASSIGNED, false, false);
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
        assertEquals(task.getDueDate(), localDate.getMonthValue() + "/" + localDate.getDayOfMonth() + "/" + localDate.getYear());
    }

    @Test
    void testPrintTaskContentAndDate() {
        assertEquals(task.printTaskContentAndDate(), "empty task"  + "   " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getYear() + "  ");
    }

    @Test
    void testGetTaskList() {
        assertEquals(task.getTaskList(), taskList);
    }
    @Test

    void testSetTaskList() {
        TaskList taskList = new TaskList(new Name("list"));
        try {
            task.setTaskList(taskList);
        } catch (TaskException e) {
            fail();
        }
        assertEquals(task.getTaskList(), taskList);
        assertEquals(taskList.getTask(1), task);

        TaskList taskList1 = new TaskList(new Name("list1"));
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
        TaskList taskList = new TaskList(new Name("list"));
        try {
            new Task(taskList,"empty task", taskDueDate, Urgency.UNASSIGNED, false, false);
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
        TaskList list;
        Task task3 = null;
        TaskList taskList = null;

        list = new TaskList(new Name("list"));
        assertNotEquals(task, list);
        assertNotEquals(task, taskList);
    }

   @Test
    void testEqualsList() {
        TaskList taskList = new TaskList(new Name(""));
        try {
            Task task1 = new Task(taskList,"empty task", taskDueDate, Urgency.UNASSIGNED, false, false);
            TaskList task2 = new TaskList(new Name("list"));
            assertNotEquals(task1, task2);
        } catch (TaskException e) {
            fail();
        }
    }


    @Test
    void testHashCode() {
        String taskContent = "empty task";

        int result = task.getTaskList() != null ? task.getTaskList().hashCode() : 0;
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
        try {
            assertEquals((new Task(null, taskContent, taskDueDate, Urgency.UNASSIGNED, false, false)).hashCode(), result);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testHashCodeNullContent() {
        TaskList taskList = new TaskList(new Name(""));
        String taskContent = null;

        Task task1 = null;
        try {
            task1 = new Task(taskList,null, taskDueDate, Urgency.UNASSIGNED, false, false);
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
        TaskList taskList = new TaskList(new Name(""));
        String taskContent = "empty task";
        taskDueDate = null;

        Task task1 = null;
        try {
            task1 = new Task(taskList,"empty task", null, Urgency.UNASSIGNED, false, false);
        } catch (TaskException e) {
            fail();
        }

        int result = taskList != null ? taskList.hashCode() : 0;
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);

        assertEquals(task1.hashCode(), result);
    }
}
