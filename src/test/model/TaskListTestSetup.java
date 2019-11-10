package model;

import exceptions.TaskException;
import model.task.IncompleteTask;
import model.task.Urgency;
import model.tasklist.TaskList;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;

class TaskListTestSetup {

    TaskList taskList;
    IncompleteTask task;
    IncompleteTask task1;
    IncompleteTask task2;
    IncompleteTask task3;
    IncompleteTask task4;

    void setUp() {
        taskList = new TaskList("");

        try {
            task = new IncompleteTask(taskList, "a", LocalDate.of(2019,1,2), Urgency.UNASSIGNED, false);
            task1 = new IncompleteTask(taskList, "b", LocalDate.of(2019,3,4), Urgency.HIGH, false);
            task2 = new IncompleteTask(taskList, "c", LocalDate.of(2019,2,3), Urgency.MID, false);
            task3 = new IncompleteTask(taskList, "d", LocalDate.of(2019,6,7), Urgency.HIGH, false);
            task4 = new IncompleteTask(taskList, "e", LocalDate.of(2019,5,6), Urgency.LOW, false);
        } catch (TaskException e) {
            fail();
        }
    }
}
