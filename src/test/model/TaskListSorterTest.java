package model;

import exceptions.TaskException;
//import model.task.CompletedTask;
//import model.task.IncompleteTask;
import model.task.Task;
import model.task.Urgency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TaskListSorterTest extends TaskListTestSetup{

    //private CompletedTask task5;
    private Task task6;
    private Task task7;
    //private CompletedTask task8;
    //private CompletedTask task9;
    private Urgency taskUrgency = Urgency.UNASSIGNED;

    @BeforeEach
    void runBefore() {
        setUp();
        try {
            //task5= new CompletedTask(taskList,"f", LocalDate.now(), "tbd");
            task6 = new Task(taskList, "g", LocalDate.of(2019,3,4), taskUrgency, false);
            task7 = new Task(taskList, "h", LocalDate.of(2019,3,5), taskUrgency, false);
            //task8 = new CompletedTask(taskList,"i", LocalDate.now(), "tbd");
            //task9 = new CompletedTask(taskList,"j", LocalDate.now(), "tbd");
        } catch (TaskException e) {
            fail();
        }
    }

    @Test
    void testSortByDueDate() {
        ArrayList<Task> list;
        ArrayList<Task> list1;

        //list1 = new ArrayList<>(Arrays.asList(task, task2, task1, task6, task7, task4, task3, task5, task8, task9));

        taskList.sortByDueDate();
        list = new ArrayList<>(taskList.getTaskList());

        //assertEquals(list, list1);
    }

    @Test
    void testSortByDueDateAlt() {
        try {
            new Task(taskList, "l", LocalDate.of(2019,3,4), Urgency.UNASSIGNED, false);
            //new CompletedTask(taskList,"k", LocalDate.now(), "tbd");
        } catch (TaskException e) {
            fail();
        }

        taskList.sortByDueDate();
        //assertTrue(taskList.getTask(taskList.getTaskListSize()) instanceof CompletedTask);
    }
}
