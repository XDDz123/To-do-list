package model;

import exceptions.TaskException;
import model.task.Task;
import model.task.Urgency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TaskListSorterTest extends TaskListTestSetup{

    private Task task5;
    private Task task6;
    private Task task7;
    private Task task8;
    private Task task9;
    private Urgency taskUrgency = Urgency.UNASSIGNED;

    @BeforeEach
    void runBefore() {
        setUp();
        try {
            task5 = new Task(taskList,"f", LocalDate.now(), taskUrgency, false,true);
            task6 = new Task(taskList, "g", LocalDate.of(2019,3,4), taskUrgency, false, false);
            task7 = new Task(taskList, "h", LocalDate.of(2019,3,5), taskUrgency, false, false);
            task8 = new Task(taskList,"i", LocalDate.now(), taskUrgency, false,true);
            task9 = new Task(taskList,"j", LocalDate.now(), taskUrgency, false,true);
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testSortByDueDate() {
        ArrayList<Task> list;
        ArrayList<Task> list1;

        list1 = new ArrayList<>(Arrays.asList(task, task2, task1, task6, task7, task4, task3, task5, task8, task9));

        taskList.sortByDueDate();
        list = new ArrayList<>(taskList.getTaskList());

        assertEquals(list, list1);
    }

    @Test
    void testSortByDueDateAlt() {
        try {
            new Task(taskList, "k", LocalDate.now(), Urgency.UNASSIGNED, false, true);
            new Task(taskList, "l", LocalDate.of(2019,3,4), Urgency.UNASSIGNED, false, false);
        } catch (TaskException e) {
            fail();
        }

        taskList.sortByDueDate();
        assertTrue(taskList.getTask(taskList.getTaskListSize()).isCompleted());
    }

    @Test
    void testSortByDueDateInCompletedBeforeCompleted() {
        try {
            new Task(taskList, "k", LocalDate.now(), Urgency.UNASSIGNED, false, false);
            new Task(taskList, "l", LocalDate.of(2019,3,4), Urgency.UNASSIGNED, false, true);
        } catch (TaskException e) {
            fail();
        }

        taskList.sortByDueDate();
        assertTrue(taskList.getTask(taskList.getTaskListSize()).isCompleted());
    }
}
