package io;

import exceptions.TaskException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Period;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaveAndLoadTest {

    private SaveAndLoad saveAndLoad;
    private ArrayList<Task> taskList;

    @BeforeEach
    void runBefore() {
        saveAndLoad = new SaveAndLoad();
        taskList = new ArrayList<>();
    }

/*    @Test
    void clearSaveTest() throws IOException {
        File file = new File("clearTest.txt");
        saveAndLoad.clearSave("clearTest.txt", taskList);
        assertEquals(file.length(), 0);
        assertTrue(taskList.isTaskListEmpty());
    }*/

    @Test
    void separateOnTildeTest() {
        String line = "task I~high~2~3~false";
        assertEquals(saveAndLoad.separateLine(line).get(0), "task I");
        assertEquals(saveAndLoad.separateLine(line).get(1), "high");
        assertEquals(saveAndLoad.separateLine(line).get(2), "2");
        assertEquals(saveAndLoad.separateLine(line).get(3), "3");
        assertEquals(saveAndLoad.separateLine(line).get(4), "false");
    }

    @Test
    void createPastDueFromImportantTest() {
        TaskReconstructor taskReconstructor = new TaskReconstructor();
        try {
            taskReconstructor.createPastDueTask(taskList);
        } catch (TaskException e) {
            fail();
        }
        assertEquals(((CompletedTask) taskList.get(0)).getCompletionStatus(), CompletedTask.pastDue);
    }

    @Test
    void checkIncompleteTaskLoadPastDueTest() {
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add(TaskReconstructor.incompleteTaskIdentifier);
        partsOfLine.add("unassigned");
        partsOfLine.add("list");
        partsOfLine.add(String.valueOf(LocalDate.now().getMonth().minus(1).getValue()));
        partsOfLine.add(String.valueOf(LocalDate.now().getDayOfMonth()));
        partsOfLine.add("2019");
        partsOfLine.add("high");
        partsOfLine.add("High Importance");
        TaskReconstructor taskReconstructor = new TaskReconstructor();

        taskReconstructor.setGeneralTaskField(partsOfLine);

        try {
            taskReconstructor.createTaskSetYear(partsOfLine, taskList, TaskReconstructor.incompleteTaskIdentifier);
        } catch (TaskException e) {
            fail();
        }
        assertEquals(taskList.get(0).getContent(), "unassigned");
        assertEquals(taskList.get(0).getDueDate(), (LocalDate.now().getMonth().minus(1).getValue())
        + "/" + (MonthDay.now().getDayOfMonth()));
        assertEquals(((CompletedTask) taskList.get(0)).getCompletionStatus(), CompletedTask.pastDue);
        assertTrue(taskList.get(0) instanceof CompletedTask);
    }

    @Test
    void checkIncompleteTaskNextYear() {
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add(TaskReconstructor.incompleteTaskIdentifier);
        partsOfLine.add("unassigned");
        partsOfLine.add("list");
        partsOfLine.add(String.valueOf(LocalDate.now().getMonth().minus(1).getValue()));
        partsOfLine.add(String.valueOf(MonthDay.now().getDayOfMonth()));
        partsOfLine.add("2020");
        partsOfLine.add("high");
        partsOfLine.add("High Importance");

        TaskReconstructor taskReconstructor = new TaskReconstructor();
        taskReconstructor.setGeneralTaskField(partsOfLine);

        try {
            taskReconstructor.createTaskSetYear(partsOfLine, taskList, TaskReconstructor.incompleteTaskIdentifier);
        } catch (TaskException e) {
            fail();
        }

        Period difference = Period.between((taskList.get(0)).getDueDateObj(), LocalDate.now());

        assertEquals(((IncompleteTask) taskList.get(0)).getTimeLeft(),
                Math.abs(difference.getMonths()) + " months " + Math.abs(difference.getDays()) + " days");
    }

    @Test
    void checkIncompleteTaskCurrentYear() {
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add(TaskReconstructor.incompleteTaskIdentifier);
        partsOfLine.add("unassigned");
        partsOfLine.add("list");
        partsOfLine.add(String.valueOf(MonthDay.now().getMonthValue() + 1));
        partsOfLine.add(String.valueOf(MonthDay.now().getDayOfMonth() + 1));
        partsOfLine.add("2019");
        partsOfLine.add("high");
        partsOfLine.add("High Importance");

        TaskReconstructor taskReconstructor = new TaskReconstructor();

        taskReconstructor.setGeneralTaskField(partsOfLine);

        try {
            taskReconstructor.createTaskSetYear(partsOfLine, taskList, TaskReconstructor.incompleteTaskIdentifier);
        } catch (TaskException e) {
            fail();
        }
        assertEquals(((IncompleteTask) taskList.get(0)).getTimeLeft(), "1 months 1 days");
    }

    @Test
    void loadInToHashMapTest() {
        TaskListHashMap taskListHashMap = new TaskListHashMap();
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add("a");
        partsOfLine.add("a");
        HashMapReconstructor hashMapReconstructor = new HashMapReconstructor();
        try {
            taskList.add(new IncompleteTask(null,"", LocalDate.now(), "", false));
            taskList.add(new IncompleteTask(null,"", LocalDate.now(), "", false));
            hashMapReconstructor.loadIntoHashMap(taskListHashMap, taskList, partsOfLine);
        } catch (TaskException e) {
            fail();
        }
    }
}
