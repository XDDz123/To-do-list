package model;

import exceptions.TaskException;
import model.task.CompletedTask;
import model.task.IncompleteTask;
import model.task.Task;
import model.task.Urgency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskListSorterTest extends TaskListTestSetup{

    private CompletedTask task5;
    private IncompleteTask task6;
    private IncompleteTask task7;
    private CompletedTask task8;
    private CompletedTask task9;
    private Urgency taskUrgency = Urgency.UNASSIGNED;

    @BeforeEach
    void runBefore() {
        setUp();
        try {
            task5= new CompletedTask(taskList,"f", LocalDate.now(), "tbd");
            task6 = new IncompleteTask(taskList, "g", LocalDate.of(2019,3,4), taskUrgency, false);
            task7 = new IncompleteTask(taskList, "h", LocalDate.of(2019,3,5), taskUrgency, false);
            task8 = new CompletedTask(taskList,"i", LocalDate.now(), "tbd");
            task9 = new CompletedTask(taskList,"j", LocalDate.now(), "tbd");
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testSortByDueDate() {
        ArrayList<Task> list;
        ArrayList<Task> list1;

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
    }

    @Test
    void testSortByDueDateAlt() {
        try {
            new CompletedTask(taskList,"k", LocalDate.now(), "tbd");
            new IncompleteTask(taskList, "l", LocalDate.of(2019,3,4), Urgency.UNASSIGNED, false);
        } catch (TaskException e) {
            fail();
        }

        taskList.sortByDueDate();
        assertTrue(taskList.getTask(taskList.getTaskListSize()) instanceof CompletedTask);
    }
}
