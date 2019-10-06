package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

//Unit test for RegularTask
public class TaskTest {
    private RegularTask task;
    private String taskContent = "empty task";
    private MonthDay taskDueDate = MonthDay.now();
    private String taskUrgency = "unassigned";
    private String taskImportance = "important";

    @BeforeEach
    public void runBefore() {
        task = new RegularTask(taskContent, taskDueDate, taskUrgency);
    }

    @Test
    public void setContentTest() {
        task.setContent("this is a test!");
        assertEquals(task.getContent(), "this is a test!");
    }

    @Test
    public void setContentTestEmpty() {
        task.setContent("");
        assertEquals(task.getContent(), "");
    }

    @Test
    public void getContentTest() {
        assertEquals(task.getContent(), "empty task");
    }

    @Test
    public void setUrgencyTest() {
        task.setUrgency("high");
        assertEquals(task.getUrgency(), "high");
    }

    @Test
    public void getUrgencyTest() {
        assertEquals(task.getUrgency(), "unassigned");
    }

    @Test
    public void setDueDateTest() {
        MonthDay monthDay = MonthDay.of(1,2);
        task.setDueDate(monthDay);
        assertEquals(task.getDueDateObj(), monthDay);
    }

    @Test
    public void getDueDateObjTest() {
        assertEquals(task.getDueDateObj(), MonthDay.now());
    }

    @Test
    public void getDueDateTest() {
        MonthDay monthDay = MonthDay.now();
        assertEquals(task.getDueDate(), monthDay.getMonthValue() + "/" + monthDay.getDayOfMonth());
    }
}
