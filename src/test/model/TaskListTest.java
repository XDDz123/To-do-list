package model;

import exceptions.TaskDoesNotExistException;
import exceptions.TooManyIncompleteTasksException;
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
        try {
            taskList.storeTask(task);
        } catch (TooManyIncompleteTasksException e) {
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
            taskList.storeTask(task);
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }
        assertEquals(taskList.getTaskListSize(), 2);
        try {
            taskList.storeTask(task);
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }
        assertEquals(taskList.getTaskListSize(), 3);
    }

    @Test
    void getTaskTest() {
        assertEquals(taskList.getTask(1), task);
    }

    @Test
    void deleteTaskTest() {
        try {
            taskList.deleteTask(1);
        } catch (TaskDoesNotExistException e) {
            fail();
        }

        assertTrue(taskList.getTaskList().isEmpty());
    }

    @Test
    void deleteTaskExceptionTest() {
        try {
            taskList.deleteTask(10);
        } catch (TaskDoesNotExistException e) {
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
        task1 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task2 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task3 = new RegularTask(taskContent, taskDueDate, taskUrgency);
        task4 = new RegularTask(taskContent, taskDueDate, taskUrgency);

        task.setUrgency("high");
        task1.setUrgency("high");
        task2.setUrgency("mid");


        try {
            taskList.storeTask(task1);
            taskList.storeTask(task2);
            taskList.storeTask(task3);
            taskList.storeTask(task4);
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }

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
        try {
            taskList1.storeTask(task);
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }
        try {
            taskList1.storeTask(task1);
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }
        try {
            assertEquals(taskList.getTaskByUrgency("high").getTaskList(), taskList1.getTaskList());
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }
    }

    @Test
    void getTaskByUrgencyTestMid() {
        runBeforeGetTaskByAndSortAndPrint();
        try {
            taskList2.storeTask(task2);
            assertEquals(taskList.getTaskByUrgency("mid").getTaskList(), taskList2.getTaskList());
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }
    }

    @Test
    void getTaskByUrgencyTestLow() {
        runBeforeGetTaskByAndSortAndPrint();
        try {
            assertEquals(taskList.getTaskByUrgency("low").getTaskList(), taskList3.getTaskList());
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }
    }

    @Test
    void sortByDueDateTest() {
        runBeforeGetTaskByAndSortAndPrint();
        CompletedTask task5 = new CompletedTask("empty task", LocalDate.now(), "tbd");

        ImportantTask task6 = new ImportantTask(
                "empty task",
                LocalDate.of(2019,3,4),
                "tbd",
                "tbd");

        ImportantTask task7 = new ImportantTask(
                "empty task",
                LocalDate.of(2019,3,5),
                "tbd",
                "tbd");


        try {
            taskList.storeTask(task5);
            taskList.storeTask(task6);
            taskList.storeTask(task7);
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }

        TaskList taskList5 = new TaskList();
        try {
            taskList5.storeTask(task);
            taskList5.storeTask(task2);
            taskList5.storeTask(task1);
            taskList5.storeTask(task6);
            taskList5.storeTask(task7);
            taskList5.storeTask(task4);
            taskList5.storeTask(task3);
            taskList5.storeTask(task5);
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }

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
        try {
            taskList.storeTask(task5);
        } catch (TooManyIncompleteTasksException e) {
            fail();
        }
        System.out.println(taskList.printIncompleteTasks());
        assertEquals(taskList.printIncompleteTasks(),
                "1 : empty task  Due: 1/2  Urgency: high  Time left: tbd\n" +
                        "2 : empty task  Due: 3/4  Urgency: high  Time left: tbd\n" +
                        "3 : empty task  Due: 2/3  Urgency: mid  Time left: tbd\n" +
                        "4 : empty task  Due: 6/7  Urgency: unassigned  Time left: tbd\n" +
                        "5 : empty task  Due: 5/6  Urgency: unassigned  Time left: tbd");
    }

    @Test
    void overLoadInsertExceptionTest() {
        try {
            for (int i = 0; i < 10; i++) {
                task = new RegularTask(taskContent, taskDueDate, taskUrgency);
                taskList.storeTask(task);
            }
        } catch (TooManyIncompleteTasksException e) {
            assertEquals(e.getMessage(), "Too many incomplete tasks.");
        }
    }
}

