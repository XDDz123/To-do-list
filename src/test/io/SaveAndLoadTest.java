package io;

import model.CompletedTask;
import model.ImportantTask;
import model.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Period;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaveAndLoadTest {

    private SaveAndLoad saveAndLoad;
    private TaskList taskList;

    @BeforeEach
    public void runBefore() {
        saveAndLoad = new SaveAndLoad();
        taskList = new TaskList();
    }

    @Test
    public void clearSaveTest() throws IOException {
        File file = new File("clearTest.txt");
        saveAndLoad.clearSave("clearTest.txt", taskList);
        assertEquals(file.length(), 0);
        assertTrue(taskList.isTaskListEmpty());
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
    public void createPastDueFromImportantTest() {
        saveAndLoad.createPastDueFromLoad(taskList);
        assertEquals(((CompletedTask) taskList.getTask(1)).getCompletionStatus(), "past due.");
    }

    @Test
    public void checkImportantTaskLoadPastDueTest() {
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add("*");
        partsOfLine.add("unassigned");
        partsOfLine.add(String.valueOf(MonthDay.now().getMonthValue() - 1));
        partsOfLine.add(String.valueOf(MonthDay.now().getDayOfMonth() - 1));
        partsOfLine.add("2019");
        partsOfLine.add("high");
        partsOfLine.add("High Importance");
        saveAndLoad.setGeneralTaskField(partsOfLine);
        TaskList taskList = new TaskList();
        saveAndLoad.createTaskSetYearFromLoad(partsOfLine, taskList, "*");
        assertEquals(taskList.getTask(1).getContent(), "unassigned");
        assertEquals(taskList.getTask(1).getDueDate(), (MonthDay.now().getMonthValue() - 1)
        + "/" + (MonthDay.now().getDayOfMonth() - 1));
        assertEquals(((CompletedTask) taskList.getTask(1)).getCompletionStatus(), "past due.");
        assertTrue(taskList.getTask(1) instanceof CompletedTask);
    }

    @Test
    public void checkImportantTaskNextYear() {
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add("*");
        partsOfLine.add("unassigned");
        partsOfLine.add(String.valueOf(MonthDay.now().getMonthValue() - 1));
        partsOfLine.add(String.valueOf(MonthDay.now().getDayOfMonth() - 1));
        partsOfLine.add("2020");
        partsOfLine.add("high");
        partsOfLine.add("High Importance");

        saveAndLoad.setGeneralTaskField(partsOfLine);
        TaskList taskList = new TaskList();
        saveAndLoad.createTaskSetYearFromLoad(partsOfLine, taskList, "*");
        Period difference = Period.between((taskList.getTask(1)).getDueDateObj(), LocalDate.now());

        assertEquals(((ImportantTask) taskList.getTask(1)).getTimeLeft(),
                Math.abs(difference.getMonths()) + " months " + Math.abs(difference.getDays()) + " days");
    }

    @Test
    public void checkImportantTaskCurrentYear() {
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add("*");
        partsOfLine.add("unassigned");
        partsOfLine.add(String.valueOf(MonthDay.now().getMonthValue() + 1));
        partsOfLine.add(String.valueOf(MonthDay.now().getDayOfMonth() + 1));
        partsOfLine.add("2019");
        partsOfLine.add("high");
        partsOfLine.add("High Importance");

        saveAndLoad.setGeneralTaskField(partsOfLine);
        TaskList taskList = new TaskList();
        saveAndLoad.createTaskSetYearFromLoad(partsOfLine, taskList, "*");
        assertEquals(((ImportantTask) taskList.getTask(1)).getTimeLeft(), "1 months 1 days");
    }
}
