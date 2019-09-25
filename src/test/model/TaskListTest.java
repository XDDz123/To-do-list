package model;

import model.Task;
import model.TaskList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList taskList;
    private TaskList taskList1;
    private TaskList taskList2;
    private TaskList taskList3;
    private Task task;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;


    @BeforeEach
    public void runBefore() {
        taskList = new TaskList();
        task = new Task();
        taskList.storeTask(task);
    }

    @Test
    public void storeTaskTest() {
        assertEquals(taskList.getTask(1), task);
        assertEquals(taskList.getTaskListSize(), 1);
    }

    @Test
    public void getTaskListTest() {
        assertEquals(taskList.getTaskListSize(), 1);
        taskList.storeTask(task);
        assertEquals(taskList.getTaskListSize(), 2);
        taskList.storeTask(task);
        assertEquals(taskList.getTaskListSize(), 3);
    }

    @Test
    public void getTaskTest() {
        assertEquals(taskList.getTask(1), task);
    }

    @Test
    public void deleteTaskTest() {
        taskList.deleteTask(1);
        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    public void clearTaskListTest() {
        taskList.clearTaskList();
        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    public void isTaskListEmptyTest() {
        assertFalse(taskList.isTaskListEmpty());
        taskList.clearTaskList();
        assertTrue(taskList.isTaskListEmpty());
    }

    @Test
    public void getTaskListSizeTest() {
        assertEquals(taskList.getTaskListSize(), 1);
    }

    public void runBeforeGetTaskByAndSort() {
        task.setUrgency("high");
        task1 = new Task();
        task2 = new Task();
        task3 = new Task();
        task4 = new Task();
        task1.setUrgency("high");
        task2.setUrgency("mid");
        taskList.storeTask(task1);
        taskList.storeTask(task2);
        taskList.storeTask(task3);
        taskList.storeTask(task4);
        taskList1 = new TaskList();
        taskList2 = new TaskList();
        taskList3 = new TaskList();
        MonthDay monthDay;
        monthDay = MonthDay.of(1,2);
        task.setDueDate(monthDay);
        monthDay = MonthDay.of(3,4);
        task1.setDueDate(monthDay);
        monthDay = MonthDay.of(2,3);
        task2.setDueDate(monthDay);
        monthDay = MonthDay.of(6,7);
        task3.setDueDate(monthDay);
        monthDay = MonthDay.of(5,6);
        task4.setDueDate(monthDay);
    }

    @Test
    public void getTaskByUrgencyTestHigh() {
        runBeforeGetTaskByAndSort();
        taskList1.storeTask(task);
        taskList1.storeTask(task1);
        assertEquals(taskList.getTaskByUrgency("high").getTaskList(), taskList1.getTaskList());
    }

    @Test
    public void getTaskByUrgencyTestMid() {
        runBeforeGetTaskByAndSort();
        taskList2.storeTask(task2);
        assertEquals(taskList.getTaskByUrgency("mid").getTaskList(), taskList2.getTaskList());
    }

    @Test
    public void getTaskByUrgencyTestLow() {
        runBeforeGetTaskByAndSort();
        assertEquals(taskList.getTaskByUrgency("low").getTaskList(), taskList3.getTaskList());
    }

    @Test
    public void sortByDueDateTest() {
        runBeforeGetTaskByAndSort();
        TaskList taskList5 = new TaskList();
        taskList5.storeTask(task);
        taskList5.storeTask(task2);
        taskList5.storeTask(task1);
        taskList5.storeTask(task4);
        taskList5.storeTask(task3);
        taskList.sortByDueDate();
        assertEquals(taskList.getTaskList(), taskList5.getTaskList());
    }
}
