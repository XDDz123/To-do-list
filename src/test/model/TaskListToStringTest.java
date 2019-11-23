package model;

import exceptions.TaskException;
//import model.task.CompletedTask;
import model.task.Task;
import model.task.Urgency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TaskListToStringTest extends TaskListTestSetup {

    @BeforeEach
    void runBefore() {
        setUp();
        taskList.getTaskList().forEach(task -> task.setDueDate(LocalDate.now()));
    }

    @Test
    void testPrintTaskList() {
        assertEquals(taskList.printTaskList(),
                        "1 : a  Due: " + taskList.getTask(1).getDueDate() + "  Urgency: unassigned  Time left: due today  Starred: false\n" +
                        "2 : b  Due: " + taskList.getTask(2).getDueDate() + "  Urgency: high  Time left: due today  Starred: false\n" +
                        "3 : c  Due: " + taskList.getTask(3).getDueDate() + "  Urgency: mid  Time left: due today  Starred: false\n" +
                        "4 : d  Due: " + taskList.getTask(4).getDueDate() + "  Urgency: high  Time left: due today  Starred: false\n" +
                        "5 : e  Due: " + taskList.getTask(5).getDueDate() + "  Urgency: low  Time left: due today  Starred: false");
    }

    @Test
    void testPrintTaskListEmpty() {
        taskList.clearTaskList();
        assertEquals(taskList.printTaskList(), "No tasks found.");
    }

    @Test
    void testPrintIncompleteTasks() {

        try {
            Task testTask = new Task(taskList,"task", LocalDate.now(), Urgency.UNASSIGNED, false, false);
            testTask.setCompleted(true);
        } catch (TaskException e) {
            fail();
        }

        //fix time left to be dynamic or static...
        //System.out.println(taskList.printIncompleteTasks());
        assertEquals(taskList.printIncompleteTasks(),
                "1 : a  Due: " + taskList.getTask(1).getDueDate() + "  Urgency: unassigned  Time left: due today  Starred: false\n" +
                        "2 : b  Due: " + taskList.getTask(2).getDueDate() + "  Urgency: high  Time left: due today  Starred: false\n" +
                        "3 : c  Due: " + taskList.getTask(3).getDueDate() + "  Urgency: mid  Time left: due today  Starred: false\n" +
                        "4 : d  Due: " + taskList.getTask(4).getDueDate() + "  Urgency: high  Time left: due today  Starred: false\n" +
                        "5 : e  Due: " + taskList.getTask(5).getDueDate() + "  Urgency: low  Time left: due today  Starred: false");

        taskList.clearTaskList();
        assertEquals(taskList.printIncompleteTasks(), "No tasks found.");
    }
}
