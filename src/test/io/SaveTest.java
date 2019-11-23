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

import static org.junit.jupiter.api.Assertions.*;

class SaveTest {

    private TaskListHashMap taskListHashMap;

    @BeforeEach
    void runBefore() {
        TaskList taskList = new TaskList(new Name("a"));
        TaskList taskList1 = new TaskList(new Name("b"));
        taskListHashMap = new TaskListHashMap();
        TaskListHashMap taskListHashMap1 = new TaskListHashMap();

        try {
            new Task(taskList,"task I", (LocalDate.of(2020, 2, 3)),
                    Urgency.HIGH, true, false);
            new Task(taskList,"task III", (LocalDate.of(2020, 4, 3)),
                    Urgency.LOW, false, false);

/*            new CompletedTask(taskList,"task IV", (LocalDate.of(2019, 10, 4)), "past due");
            new CompletedTask(taskList1,"task VII", (LocalDate.of(2019, 4, 5)), "4/5");*/

            new Task(taskList, "task IV", (LocalDate.of(2019, 10, 4)), Urgency.LOW,false, true);
            new Task(taskList1, "task VII", (LocalDate.of(2019, 4, 5)), Urgency.LOW,false, true);

            new Task(taskList1,"task V", (LocalDate.of(2019, 5, 2)),
                    Urgency.MID, true, false);
            new Task(taskList1,"task VI", (LocalDate.of(2020, 10, 3)),
                    Urgency.MID, false, false);
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
        Name name = new Name("a");
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getContent(),"task I");
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getUrgency().getString(),"high");
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getDueDateObj(), LocalDate.of(2020,2,3));
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).isStarred(), true);
    }

    @Test
    void testSaveTaskTwo() {
        Name name = new Name("a");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getContent(),"task III");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getUrgency().getString(),"low");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getDueDateObj(), LocalDate.of(2020,4,3));
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).isStarred(), false);
    }

    @Test
    void testSaveTaskThree() {
        Name name = new Name("a");
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getContent(), "task IV");
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getDueDateObj(), LocalDate.of(2019,10,4));
        assertTrue(taskListHashMap.getTaskList(name).getTask(3).isCompleted());
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getTimeLeft(), "Completed");
    }

    @Test
    void testSaveTaskFour() {
        Name name = new Name("b");
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getContent(), "task VII");
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getDueDateObj(), LocalDate.of(2019,4,5));
        assertTrue(taskListHashMap.getTaskList(name).getTask(1).isCompleted());
        assertEquals(taskListHashMap.getTaskList(name).getTask(1).getTimeLeft(), "Completed");
    }

    @Test
    void testSaveTaskFive() {
        Name name = new Name("b");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getContent(), "task V");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getDueDateObj(), LocalDate.of(2019,5,2));
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).getUrgency().getString(), "mid");
        assertEquals(taskListHashMap.getTaskList(name).getTask(2).isStarred(), true);
    }

    @Test
    void testSaveTaskSix() {
        Name name = new Name("b");
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getContent(), "task VI");
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getDueDateObj(), LocalDate.of(2020,10,3));
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).getUrgency().getString(), "mid");
        assertEquals(taskListHashMap.getTaskList(name).getTask(3).isStarred(), false);
    }
}
