package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.MonthDay;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TaskTest {
    private Task task;
    private String taskContent = "empty task";
    private MonthDay taskDueDate = MonthDay.now();
    private String taskUrgency = "unassigned";

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

    @Test
    public void printTaskContentAndDateTest() {
        assertEquals(task.printTaskContentAndDate(), "empty task"  + "  " + "Due: " + MonthDay.now().getMonthValue()
                + "/" + MonthDay.now().getDayOfMonth() + "  ");
    }
}
