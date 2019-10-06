package io;

import model.CompletedTask;
import model.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.MonthDay;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaveAndLoadTest {

    private SaveAndLoad saveAndLoad;

    @BeforeEach
    public void runBefore() {
        saveAndLoad = new SaveAndLoad();
    }

    @Test
    public void clearSaveTest() throws IOException {
        File file = new File("clearTest.txt");
        saveAndLoad.clearSave("clearTest.txt");
        assertEquals(file.length(), 0);
    }

    @Test
    public void separateOnTildeTest() {
        String line = "task I~high~2~3~false";
        assertEquals(saveAndLoad.separateOnTilde(line).get(0), "task I");
        assertEquals(saveAndLoad.separateOnTilde(line).get(1), "high");
        assertEquals(saveAndLoad.separateOnTilde(line).get(2), "2");
        assertEquals(saveAndLoad.separateOnTilde(line).get(3), "3");
        assertEquals(saveAndLoad.separateOnTilde(line).get(4), "false");
    }

    @Test
    public void checkFirstElementTest() {
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add("*");
        assertTrue(saveAndLoad.checkFirstElement(partsOfLine, "*"));
    }

    @Test
    public void createPastDueFromImportantTest() {
        TaskList taskList = new TaskList();
        saveAndLoad.createPastDueFromImportant(taskList);
        assertEquals(((CompletedTask) taskList.getTask(1)).getCompletionStatus(), "past due.");
    }

    @Test
    public void checkImportantTaskLoadPastDueTest() {
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add("*");
        partsOfLine.add("unassigned");
        partsOfLine.add("high");
        partsOfLine.add(String.valueOf(MonthDay.now().getMonthValue() - 1));
        partsOfLine.add(String.valueOf(MonthDay.now().getDayOfMonth() - 1));
        partsOfLine.add("High Importance");
        partsOfLine.add("2019");
        saveAndLoad.setIncompleteTaskField(partsOfLine);
        TaskList taskList = new TaskList();
        saveAndLoad.checkImportantTaskLoad(partsOfLine, taskList);
        assertEquals(taskList.getTask(1).getContent(), "unassigned");
        assertEquals(taskList.getTask(1).getDueDate(), (MonthDay.now().getMonthValue() - 1)
        + "/" + (MonthDay.now().getDayOfMonth() - 1));
        assertEquals(((CompletedTask) taskList.getTask(1)).getCompletionStatus(), "past due.");
        assertTrue(taskList.getTask(1) instanceof CompletedTask);
    }
}
