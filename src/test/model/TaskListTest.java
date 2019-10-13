package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
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
    void runBefore() {
        taskList = new TaskList();
        task = new RegularTask(taskContent, taskDueDate, taskUrgency);
        taskList.storeTask(task);
    }

    @Test
    void storeTaskTest() {
        assertEquals(taskList.getTask(1), task);
        assertEquals(taskList.getTaskListSize(), 1);
    }

    @Test
    void getTaskListTest() {
        assertEquals(taskList.getTaskListSize(), 1);
        taskList.storeTask(task);
        assertEquals(taskList.getTaskListSize(), 2);
        taskList.storeTask(task);
        assertEquals(taskList.getTaskListSize(), 3);
    }

    @Test
    void getTaskTest() {
        assertEquals(taskList.getTask(1), task);
    }

    @Test
    void deleteTaskTest() {
        taskList.deleteTask(1);
        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    void clearTaskListTest() {
        taskList.clearTaskList();
        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    void isTaskListEmptyTest() {
        assertFalse(taskList.isTaskListEmpty());
        taskList.clearTaskList();
        assertTrue(taskList.isTaskListEmpty());
    }

    @Test
    void getTaskListSizeTest() {
        assertEquals(taskList.getTaskListSize(), 1);
    }

    void runBeforeGetTaskByAndSortAndPrint() {
        task1 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task2 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task3 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task4 = new RegularTask(taskContent, taskDueDate, taskUrgency);

        task.setUrgency("high");
        task1.setUrgency("high");
        task2.setUrgency("mid");

        taskList.storeTask(task1);
        taskList.storeTask(task2);
        taskList.storeTask(task3);
        taskList.storeTask(task4);

        taskList1 = new TaskList();
        taskList2 = new TaskList();
        taskList3 = new TaskList();


        task.setDueDate(LocalDate.of(2019,1,2));
        task1.setDueDate(LocalDate.of(2019,3,4));
        task2.setDueDate(LocalDate.of(2019,2,3));
        task3.setDueDate(LocalDate.of(2019,6,7));
        task4.setDueDate(LocalDate.of(2019,5,6));
    }

    @Test
    void getTaskByUrgencyTestHigh() {
        runBeforeGetTaskByAndSortAndPrint();
        taskList1.storeTask(task);
        taskList1.storeTask(task1);
        assertEquals(taskList.getTaskByUrgency("high").getTaskList(), taskList1.getTaskList());
    }

    @Test
    void getTaskByUrgencyTestMid() {
        runBeforeGetTaskByAndSortAndPrint();
        taskList2.storeTask(task2);
        assertEquals(taskList.getTaskByUrgency("mid").getTaskList(), taskList2.getTaskList());
    }

    @Test
    void getTaskByUrgencyTestLow() {
        runBeforeGetTaskByAndSortAndPrint();
        assertEquals(taskList.getTaskByUrgency("low").getTaskList(), taskList3.getTaskList());
    }

    @Test
    void sortByDueDateTest() {
        runBeforeGetTaskByAndSortAndPrint();
        CompletedTask task5 = new CompletedTask("empty task", LocalDate.now(), "tbd");
        taskList.storeTask(task5);
        ImportantTask task6 = new ImportantTask(
                "empty task",
                LocalDate.of(2019,3,4),
                "tbd",
                "tbd");
        taskList.storeTask(task6);


        TaskList taskList5 = new TaskList();
        taskList5.storeTask(task);
        taskList5.storeTask(task2);
        taskList5.storeTask(task1);
        taskList5.storeTask(task6);
        taskList5.storeTask(task4);
        taskList5.storeTask(task3);
        taskList5.storeTask(task5);
        taskList.sortByDueDate();
        assertEquals(taskList.getTaskList(), taskList5.getTaskList());
    }

    @Test
    void printTaskListTest() {
        runBeforeGetTaskByAndSortAndPrint();
        assertEquals(taskList.printTaskList(),
                "1 : empty task  Due: 1/2  Urgency: high  Time left: tbd\n" +
                "2 : empty task  Due: 3/4  Urgency: high  Time left: tbd\n" +
                "3 : empty task  Due: 2/3  Urgency: mid  Time left: tbd\n" +
                "4 : empty task  Due: 6/7  Urgency: unassigned  Time left: tbd\n" +
                "5 : empty task  Due: 5/6  Urgency: unassigned  Time left: tbd");
    }

    @Test
    void printTaskListTestEmpty() {
        taskList.clearTaskList();
        assertEquals(taskList.printTaskList(), "No tasks found.");
    }

    @Test
    void printIncompleteTasksTest() {
        runBeforeGetTaskByAndSortAndPrint();
        CompletedTask task5 = new CompletedTask("empty task", LocalDate.now(), "tbd");
        taskList.storeTask(task5);
        System.out.println(taskList.printIncompleteTasks());
        assertEquals(taskList.printIncompleteTasks(),
                "1 : empty task  Due: 1/2  Urgency: high  Time left: tbd\n" +
                        "2 : empty task  Due: 3/4  Urgency: high  Time left: tbd\n" +
                        "3 : empty task  Due: 2/3  Urgency: mid  Time left: tbd\n" +
                        "4 : empty task  Due: 6/7  Urgency: unassigned  Time left: tbd\n" +
                        "5 : empty task  Due: 5/6  Urgency: unassigned  Time left: tbd");
    }
}

