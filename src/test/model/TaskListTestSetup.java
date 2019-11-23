package model;

import exceptions.TaskException;
//import model.task.IncompleteTask;
import model.task.Task;
import model.task.Urgency;
import model.tasklist.TaskList;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;

class TaskListTestSetup {

    TaskList taskList;
    Task task;
    Task task1;
    Task task2;
    Task task3;
    Task task4;

    void setUp() {
        taskList = new TaskList("");

        try {
            task = new Task(taskList, "a", LocalDate.of(2019,1,2), Urgency.UNASSIGNED, false);
            task1 = new Task(taskList, "b", LocalDate.of(2019,3,4), Urgency.HIGH, false);
            task2 = new Task(taskList, "c", LocalDate.of(2019,2,3), Urgency.MID, false);
            task3 = new Task(taskList, "d", LocalDate.of(2019,6,7), Urgency.HIGH, false);
            task4 = new Task(taskList, "e", LocalDate.of(2019,5,6), Urgency.LOW, false);
        } catch (TaskException e) {
            fail();
        }
    }
}
