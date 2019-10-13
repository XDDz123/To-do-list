package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList taskList;
    private TaskList taskList1;
    private TaskList taskList2;
    private TaskList taskList3;
    private RegularTask task;
    private RegularTask task1;
    private RegularTask task2;
    private RegularTask task3;
    private RegularTask task4;

    private String taskContent = "empty task";
    private LocalDate taskDueDate = LocalDate.now();
    private String taskUrgency = "unassigned";

    @BeforeEach
    public void runBefore() {
        taskList = new TaskList();
        task = new RegularTask(taskContent, taskDueDate, taskUrgency);
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

    public void runBeforeGetTaskByAndSortAndPrint() {
        task.setUrgency("high");
        task1 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task2 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task3 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task4 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task1.setUrgency("high");
        task2.setUrgency("mid");
        taskList.storeTask(task1);
        taskList.storeTask(task2);
        taskList.storeTask(task3);
        taskList.storeTask(task4);
        taskList1 = new TaskList();
        taskList2 = new TaskList();
        taskList3 = new TaskList();
        LocalDate localDate;
        localDate = LocalDate.of(2019,1,2);
        task.setDueDate(localDate);
        localDate = LocalDate.of(2019,3,4);
        task1.setDueDate(localDate);
        localDate = LocalDate.of(2019,2,3);
        task2.setDueDate(localDate);
        localDate = LocalDate.of(2019,6,7);
        task3.setDueDate(localDate);
        localDate = LocalDate.of(2019,5,6);
        task4.setDueDate(localDate);
    }

    @Test
    public void getTaskByUrgencyTestHigh() {
        runBeforeGetTaskByAndSortAndPrint();
        taskList1.storeTask(task);
        taskList1.storeTask(task1);
        assertEquals(taskList.getTaskByUrgency("high").getTaskList(), taskList1.getTaskList());
    }

    @Test
    public void getTaskByUrgencyTestMid() {
        runBeforeGetTaskByAndSortAndPrint();
        taskList2.storeTask(task2);
        assertEquals(taskList.getTaskByUrgency("mid").getTaskList(), taskList2.getTaskList());
    }

    @Test
    public void getTaskByUrgencyTestLow() {
        runBeforeGetTaskByAndSortAndPrint();
        assertEquals(taskList.getTaskByUrgency("low").getTaskList(), taskList3.getTaskList());
    }

/*    @Test
    public void sortByDueDateTest() {
        runBeforeGetTaskByAndSortAndPrint();
        TaskList taskList5 = new TaskList();
        taskList5.storeTask(task);
        taskList5.storeTask(task2);
        taskList5.storeTask(task1);
        taskList5.storeTask(task4);
        taskList5.storeTask(task3);
        taskList.sortByDueDate();
        assertEquals(taskList.getTaskList(), taskList5.getTaskList());
    }*/

    @Test
    public void printTaskListTest() {
        runBeforeGetTaskByAndSortAndPrint();
        assertEquals(taskList.printTaskList(),
                "1 : empty task  Due: 1/2  Urgency: high  Time left: tbd\n" +
                "2 : empty task  Due: 3/4  Urgency: high  Time left: tbd\n" +
                "3 : empty task  Due: 2/3  Urgency: mid  Time left: tbd\n" +
                "4 : empty task  Due: 6/7  Urgency: unassigned  Time left: tbd\n" +
                "5 : empty task  Due: 5/6  Urgency: unassigned  Time left: tbd");
    }

    @Test
    public void printTaskListTestEmpty() {
        taskList.clearTaskList();
        assertEquals(taskList.printTaskList(), "No tasks found.");
    }
}

