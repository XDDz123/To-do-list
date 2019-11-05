package model;

import exceptions.TaskException;
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
            task = new IncompleteTask(null,"empty task", taskDueDate, "unassigned", false);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void setContentTest() {
        task.setContent("this is a test!");
        assertEquals(task.getContent(), "this is a test!");

    }

    @Test
    void setContentTestEmpty() {
        task.setContent("");
        assertEquals(task.getContent(), "");
    }

    @Test
    void getContentTest() {
        assertEquals(task.getContent(), "empty task");
    }

    @Test
    void setDueDateTest() {
        LocalDate localDate = LocalDate.of(2019,1,2);
        task.setDueDate(localDate);
        assertEquals(task.getDueDateObj(), localDate);
    }

    @Test
    void getDueDateObjTest() {
        assertEquals(task.getDueDateObj(), LocalDate.now());
    }

    @Test
    void getDueDateTest() {
        LocalDate localDate = LocalDate.now();
        assertEquals(task.getDueDate(), localDate.getMonthValue() + "/" + localDate.getDayOfMonth());
    }

    @Test
    void printTaskContentAndDateTest() {
        assertEquals(task.printTaskContentAndDate(), "empty task"  + "  " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "  ");
    }

    @Test
    void getTaskListTest() {
        assertNull(task.getTaskList());
    }

    @Test
    void setTaskListTest() {
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
    void setTaskListDuplicateTest() {
        TaskList taskList = new TaskList("list");
        try {
            new IncompleteTask(taskList,"empty task", taskDueDate, "unassigned", false);
        } catch (TaskException e) {
            fail();
        }

        try {
            task.setTaskList(taskList);
            //fail();
        } catch (TaskException e) {
            fail();
            //System.out.println("Test passed!");
        }
    }

    @Test
    void equalsNull() {
        CompletedTask task2;
        Task task3 = null;
        CompletedTask task4 = null;
        TaskList taskList = null;
        try {
            task2 = new CompletedTask(null,"empty task", taskDueDate, "unassigned");
            assertFalse(task.equals(task2));
        } catch (TaskException e) {
            fail();
        }

        assertFalse(task.equals(task3));
        assertFalse(task.equals(task4));
        assertFalse(task.equals(taskList));
    }

    @Test
    void equalsList() {
        TaskList taskList = new TaskList("");
        try {
            Task task1 = new IncompleteTask(taskList,"empty task", taskDueDate, "unassigned", false);
            Task task2 = new CompletedTask(null,"empty task", taskDueDate, "unassigned");
            assertFalse(task1.equals(task2));
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void hashCodeTest() {
        TaskList taskList = null;
        String taskContent = "empty task";

        int result = taskList != null ? taskList.hashCode() : 0;
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);

        assertEquals(task.hashCode(), result);
    }

    @Test
    void hashCodeTestNullList() {
        TaskList taskList = null;
        String taskContent = "empty task";

        int result = taskList != null ? taskList.hashCode() : 0;
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);

        assertEquals(task.hashCode(), result);
    }

    @Test
    void hashCodeTestNullContent() {
        TaskList taskList = new TaskList("");
        String taskContent = null;

        Task task1 = null;
        try {
            task1 = new IncompleteTask(taskList,null, taskDueDate, "unassigned", false);
        } catch (TaskException e) {
            fail();
        }

        int result = taskList != null ? taskList.hashCode() : 0;
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);

        assertEquals(task1.hashCode(), result);
    }

    @Test
    void hashCodeTestNullDueDate() {
        TaskList taskList = new TaskList("");
        String taskContent = "empty task";
        taskDueDate = null;

        Task task1 = null;
        try {
            task1 = new IncompleteTask(taskList,"empty task", null, "unassigned", false);
        } catch (TaskException e) {
            fail();
        }

        int result = taskList != null ? taskList.hashCode() : 0;
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);

        assertEquals(task1.hashCode(), result);
    }
}
