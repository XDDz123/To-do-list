package io;

import exceptions.TaskException;
import model.*;
//import model.task.CompletedTask;
//import model.task.IncompleteTask;
import model.task.Task;
import model.task.Urgency;
import model.tasklist.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SaveTest {

    private TaskListHashMap taskListHashMap;

    @BeforeEach
    void runBefore() {
        TaskList taskList = new TaskList("a");
        TaskList taskList1 = new TaskList("b");
        taskListHashMap = new TaskListHashMap();
        TaskListHashMap taskListHashMap1 = new TaskListHashMap();

        try {
            new Task(taskList,"task I", (LocalDate.of(2020, 2, 3)),
                    Urgency.HIGH, true);
            new Task(taskList,"task III", (LocalDate.of(2020, 4, 3)),
                    Urgency.LOW, false);

/*            new CompletedTask(taskList,"task IV", (LocalDate.of(2019, 10, 4)), "past due");
            new CompletedTask(taskList1,"task VII", (LocalDate.of(2019, 4, 5)), "4/5");*/

            new Task(taskList1,"task V", (LocalDate.of(2019, 5, 2)),
                    Urgency.MID, true);
            new Task(taskList1,"task VI", (LocalDate.of(2020, 10, 3)),
                    Urgency.MID, false);
        } catch (TaskException e) {
            fail();
        }

        taskListHashMap.storeTaskList(taskList);
        taskListHashMap.storeTaskList(taskList1);

        try {
            (new Save()).save(taskListHashMap, "ioTest.txt");
            (new Load()).load(taskListHashMap1, "ioTest.txt");
        } catch (TaskException | ClassNotFoundException | IOException e) {
            fail();
        }
    }

    @Test
    void testSaveTaskOne() {
        assertEquals(taskListHashMap.getTaskList("a").getTask(1).getContent(),"task I");
        assertEquals(taskListHashMap.getTaskList("a").getTask(1).getUrgency().getString(),"high");
        assertEquals(taskListHashMap.getTaskList("a").getTask(1).getDueDateObj(), LocalDate.of(2020,2,3));
        assertEquals(taskListHashMap.getTaskList("a").getTask(1).isStarred(), true);
    }

    @Test
    void testSaveTaskTwo() {
        assertEquals(taskListHashMap.getTaskList("a").getTask(2).getContent(),"task III");
        assertEquals(taskListHashMap.getTaskList("a").getTask(2).getUrgency().getString(),"low");
        assertEquals(taskListHashMap.getTaskList("a").getTask(2).getDueDateObj(), LocalDate.of(2020,4,3));
        assertEquals(taskListHashMap.getTaskList("a").getTask(2).isStarred(), false);
    }

/*    @Test
    void testSaveTaskThree() {
        assertEquals(taskListHashMap.getTaskList("a").getTask(3).getContent(), "task IV");
        assertEquals(taskListHashMap.getTaskList("a").getTask(3).getDueDateObj(), LocalDate.of(2019,10,4));
        assertEquals(((CompletedTask) taskListHashMap.getTaskList("a").getTask(3)).getCompletionStatus(), "past due");
    }

    @Test
    void testSaveTaskFour() {
        assertEquals(taskListHashMap.getTaskList("b").getTask(1).getContent(), "task VII");
        assertEquals(taskListHashMap.getTaskList("b").getTask(1).getDueDateObj(), LocalDate.of(2019,4,5));
        assertEquals(((CompletedTask) taskListHashMap.getTaskList("b").getTask(1)).getCompletionStatus(), "4/5");
    }*/

    @Test
    void testSaveTaskFive() {
        assertEquals(taskListHashMap.getTaskList("b").getTask(1).getContent(), "task V");
        assertEquals(taskListHashMap.getTaskList("b").getTask(1).getDueDateObj(), LocalDate.of(2019,5,2));
        assertEquals(taskListHashMap.getTaskList("b").getTask(1).getUrgency().getString(), "mid");
        assertEquals(taskListHashMap.getTaskList("b").getTask(1).isStarred(), true);
    }

    @Test
    void testSaveTaskSix() {
        assertEquals(taskListHashMap.getTaskList("b").getTask(2).getContent(), "task VI");
        assertEquals(taskListHashMap.getTaskList("b").getTask(2).getDueDateObj(), LocalDate.of(2020,10,3));
        assertEquals(taskListHashMap.getTaskList("b").getTask(2).getUrgency().getString(), "mid");
        assertEquals(taskListHashMap.getTaskList("b").getTask(2).isStarred(), false);
    }
}
