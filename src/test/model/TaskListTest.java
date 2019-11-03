package model;

import exceptions.TaskDoesNotExistException;
import exceptions.TaskException;
import exceptions.TooManyIncompleteTasksException;
import exceptions.UIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    private TaskList taskList;
    private TaskList taskList1;
    private TaskList taskList2;
    private TaskList taskList3;
    private IncompleteTask task;
    private IncompleteTask task1;
    private IncompleteTask task2;
    private IncompleteTask task3;
    private IncompleteTask task4;

    private String taskContent = "empty task";
    private LocalDate taskDueDate = LocalDate.now();
    private String taskUrgency = "unassigned";

    @BeforeEach
    void runBefore() {
        taskList = new TaskList("");
        try {
            task = new IncompleteTask(taskList, taskContent, taskDueDate, taskUrgency);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void storeTaskTest() {
        assertEquals(taskList.getTask(1), task);
        assertEquals(taskList.getTaskListSize(), 1);
    }

    @Test
    void getTaskListTest() {
        assertEquals(taskList.getTaskListSize(), 1);

        try {
            task1 = new IncompleteTask(taskList, "a", taskDueDate, taskUrgency);
            assertEquals(taskList.getTaskListSize(), 2);
            task2 = new IncompleteTask(taskList, "b", taskDueDate, taskUrgency);
        } catch (TaskException e) {
            fail();
        }
        assertEquals(taskList.getTaskListSize(), 3);
    }

    @Test
    void duplicateTaskTest() {
        try {
            new IncompleteTask(taskList, taskContent, taskDueDate, taskUrgency);
            //fail();
        } catch (TooManyIncompleteTasksException e) {
            fail();
        } catch (TaskException e) {
            fail();
            //System.out.println("Test passed.");
        }
    }

    @Test
    void getTaskTest() {
        assertEquals(taskList.getTask(1), task);
    }

    @Test
    void deleteTaskTest() {
        try {
            taskList.deleteTask(1);
        } catch (TaskDoesNotExistException | TaskException e) {
            fail();
        }

        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    void deleteTaskExceptionTest() {
        try {
            taskList.deleteTask(10);
            fail();
        } catch (TaskDoesNotExistException | TaskException e) {
            assertEquals(e.getMessage(), "Selection out of bounds!");
        }
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

        try {
            task1 = new IncompleteTask(taskList, "a", taskDueDate, taskUrgency);
            task2 = new IncompleteTask(taskList, "b", taskDueDate, taskUrgency);
            task3 = new IncompleteTask(taskList, "c", taskDueDate, taskUrgency);
            task4 = new IncompleteTask(taskList, "d", taskDueDate, taskUrgency);
        } catch (TaskException e) {
            fail();
        }

        task.setUrgency("high");
        task1.setUrgency("high");
        task2.setUrgency("mid");


        taskList1 = new TaskList("");
        taskList2 = new TaskList("");
        taskList3 = new TaskList("");


        task.setDueDate(LocalDate.of(2019,1,2));
        task1.setDueDate(LocalDate.of(2019,3,4));
        task2.setDueDate(LocalDate.of(2019,2,3));
        task3.setDueDate(LocalDate.of(2019,6,7));
        task4.setDueDate(LocalDate.of(2019,5,6));
    }

    @Test
    void getTaskByUrgencyTestHigh() {
        runBeforeGetTaskByAndSortAndPrint();
        ArrayList<Task> list = new ArrayList<>();

        try {
            list.add(task);
            list.add(task1);
            assertEquals(taskList.getTaskByUrgency("high").getTaskList(), list);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void getTaskByUrgencyTestMid() {
        runBeforeGetTaskByAndSortAndPrint();
        ArrayList<Task> list = new ArrayList<>();

        try {
            list.add(task2);
            assertEquals(taskList.getTaskByUrgency("mid").getTaskList(), list);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void getTaskByUrgencyTestLow() {
        runBeforeGetTaskByAndSortAndPrint();
        try {
            assertEquals(taskList.getTaskByUrgency("low").getTaskList(), taskList3.getTaskList());
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void sortByDueDateTest() {
        runBeforeGetTaskByAndSortAndPrint();
        CompletedTask task5;
        ImportantTask task6;
        ImportantTask task7;
        CompletedTask task8;
        CompletedTask task9;
        ArrayList<Task> list1;
        ArrayList<Task> list;

        try {
            task5= new CompletedTask(taskList,"empty task 5", LocalDate.now(), "tbd");
            task6 = new ImportantTask(
                    taskList,
                    "empty task 6",
                    LocalDate.of(2019,3,4),
                    "tbd",
                    "tbd");
            task7 = new ImportantTask(
                    taskList,
                    "empty task 7",
                    LocalDate.of(2019,3,5),
                    "tbd",
                    "tbd");
            task8 = new CompletedTask(taskList,"empty task 8", LocalDate.now(), "tbd");
            task9 = new CompletedTask(taskList,"empty task 9", LocalDate.now(), "tbd");

            list1 = new ArrayList<>();

            list1.add(task);
            list1.add(task2);
            list1.add(task1);
            list1.add(task6);
            list1.add(task7);
            list1.add(task4);
            list1.add(task3);
            list1.add(task5);
            list1.add(task8);
            list1.add(task9);

            taskList.sortByDueDate();
            list = new ArrayList<>(taskList.getTaskList());

            assertEquals(list, list1);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void sortByDueDateAlt() {
        try {
            new CompletedTask(taskList,"empty task 11", LocalDate.now(), "tbd");
            new ImportantTask(taskList,
                    "empty task 10",
                    LocalDate.of(2019,3,4),
                    "tbd",
                    "tbd");
        } catch (TaskException e) {
            fail();
        }

        taskList.sortByDueDate();
        assertTrue(taskList.getTask(taskList.getTaskListSize()) instanceof CompletedTask);
    }

    @Test
    void printTaskListTest() {
        runBeforeGetTaskByAndSortAndPrint();
        assertEquals(taskList.printTaskList(),
                "1 : empty task  Due: 1/2  Urgency: high  Time left: tbd\n" +
                "2 : a  Due: 3/4  Urgency: high  Time left: tbd\n" +
                "3 : b  Due: 2/3  Urgency: mid  Time left: tbd\n" +
                "4 : c  Due: 6/7  Urgency: unassigned  Time left: tbd\n" +
                "5 : d  Due: 5/6  Urgency: unassigned  Time left: tbd");
    }

    @Test
    void printTaskListTestEmpty() {
        taskList.clearTaskList();
        assertEquals(taskList.printTaskList(), "No tasks found.");
    }

    @Test
    void printIncompleteTasksTest() {
        runBeforeGetTaskByAndSortAndPrint();

        try {
            new CompletedTask(taskList,"empty task", LocalDate.now(), "tbd");
        } catch (TaskException e) {
            fail();
        }

        System.out.println(taskList.printIncompleteTasks());
        assertEquals(taskList.printIncompleteTasks(),
                "1 : empty task  Due: 1/2  Urgency: high  Time left: tbd\n" +
                        "2 : a  Due: 3/4  Urgency: high  Time left: tbd\n" +
                        "3 : b  Due: 2/3  Urgency: mid  Time left: tbd\n" +
                        "4 : c  Due: 6/7  Urgency: unassigned  Time left: tbd\n" +
                        "5 : d  Due: 5/6  Urgency: unassigned  Time left: tbd");

        taskList.clearTaskList();
        assertEquals(taskList.printIncompleteTasks(), "No tasks found.");
    }

    @Test
    void overLoadInsertExceptionTest() {
        try {
            for (int i = 0; i <= TaskList.maxSize; i++) {
                task = new IncompleteTask(taskList, Integer.toString(i), taskDueDate, taskUrgency);
            }
        } catch (TaskException e) {
            assertEquals(e.getMessage(), "Too many incomplete tasks!");
        }

        try {
            CompletedTask completedTask = new CompletedTask(taskList, taskContent, taskDueDate, "tbd");
            assertEquals(taskList.getTask(taskList.getTaskListSize()), completedTask);
        } catch (TaskException e) {
            fail();
        }
    }
}

