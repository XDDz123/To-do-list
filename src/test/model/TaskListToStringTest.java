package model;

import exceptions.TaskException;
import model.task.CompletedTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TaskListToStringTest extends TaskListTestSetup {

    @BeforeEach
    void runBefore() {
        setUp();
    }

    @Test
    void testPrintTaskList() {
        assertEquals(taskList.printTaskList(),
                "1 : a  Due: 1/2  Urgency: unassigned  Time left: 10 months 8 days  Starred: false\n" +
                        "2 : b  Due: 3/4  Urgency: high  Time left: 8 months 6 days  Starred: false\n" +
                        "3 : c  Due: 2/3  Urgency: mid  Time left: 9 months 7 days  Starred: false\n" +
                        "4 : d  Due: 6/7  Urgency: high  Time left: 5 months 3 days  Starred: false\n" +
                        "5 : e  Due: 5/6  Urgency: low  Time left: 6 months 4 days  Starred: false");
    }

    @Test
    void testPrintTaskListEmpty() {
        taskList.clearTaskList();
        assertEquals(taskList.printTaskList(), "No tasks found.");
    }

    @Test
    void testPrintIncompleteTasks() {

        try {
            new CompletedTask(taskList,"empty task", LocalDate.now(), "tbd");
        } catch (TaskException e) {
            fail();
        }

        System.out.println(taskList.printIncompleteTasks());
        assertEquals(taskList.printIncompleteTasks(),
                "1 : a  Due: 1/2  Urgency: unassigned  Time left: 10 months 8 days  Starred: false\n" +
                        "2 : b  Due: 3/4  Urgency: high  Time left: 8 months 6 days  Starred: false\n" +
                        "3 : c  Due: 2/3  Urgency: mid  Time left: 9 months 7 days  Starred: false\n" +
                        "4 : d  Due: 6/7  Urgency: high  Time left: 5 months 3 days  Starred: false\n" +
                        "5 : e  Due: 5/6  Urgency: low  Time left: 6 months 4 days  Starred: false");

        taskList.clearTaskList();
        assertEquals(taskList.printIncompleteTasks(), "No tasks found.");
    }
}
