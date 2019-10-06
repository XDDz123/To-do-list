package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImportantTaskTest {

    private ImportantTask importantTask;
    private String taskContent = "empty task";
    private MonthDay taskDueDate = MonthDay.now();
    private String taskUrgency = "unassigned";
    private String taskImportance = "unassigned";

    @BeforeEach
    public void runBefore() {
        importantTask = new ImportantTask(taskContent, taskDueDate, taskUrgency, taskImportance);
    }

    @Test
    public void setImportanceTest() {
        importantTask.setImportance("high importance");
        assertEquals(importantTask.getImportance(), "high importance");
    }

    @Test
    public void getImportanceTest() {
        assertEquals(importantTask.getImportance(), "unassigned");
    }

    @Test
    public void setTimeLeft() {
        importantTask.setTimeLeft();
        assertEquals(importantTask.getTimeLeft(), "due today");
    }

    @Test
    public void getTimeLeft() {
        assertEquals(importantTask.getTimeLeft(), "tbd");
        importantTask.setTimeLeft();
        assertEquals(importantTask.getTimeLeft(), "due today");
    }

    @Test
    public void printTaskTest() {
        assertEquals(importantTask.printTask(), "empty task"  + "  " + "Due: " + MonthDay.now().getMonthValue()
                + "/" + MonthDay.now().getDayOfMonth() + "  " + "Urgency: " + "unassigned" + "  " + "Time left: "
                + "tbd" + "  " + "*" + "unassigned" + "*");
    }

    @Test
    public void setCurrentDateAndDueDateTest() {
        importantTask.setCurrentDateAndDueDate();
        assertEquals(importantTask.getCurrentDate(), MonthDay.now());
        assertEquals(importantTask.getDueDateTemp(), MonthDay.now());
    }

    @Test
    public void computeTimeLeftDueTodayTest() {
        assertEquals(importantTask.computeTimeLeft(),"due today");
    }

    @Test
    public void computeTimeLeftInDaysTest() {
        importantTask.setDueDate(MonthDay.of(MonthDay.now().getMonthValue(), MonthDay.now().getDayOfMonth() + 1));
        assertEquals(importantTask.computeTimeLeft(),1 + " days.");
    }

    @Test
    public void computeTimeLeftInMonthsTest() {
        importantTask.setDueDate(MonthDay.of(MonthDay.now().getMonthValue() + 1, MonthDay.now().getDayOfMonth()));
        assertEquals(importantTask.computeTimeLeft(),1 + " months " + 0 + " days.");
    }

    @Test
    public void computeTimeLeftInDaysAndMonthsTest() {
        importantTask.setDueDate(MonthDay.of(MonthDay.now().getMonthValue() + 1,
                MonthDay.now().getDayOfMonth() + 1));
        assertEquals(importantTask.computeTimeLeft(),1 + " months " + 1 + " days.");
    }
}