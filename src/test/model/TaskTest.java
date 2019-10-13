package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TaskTest {
    private Task task;
    private String taskContent = "empty task";
    private LocalDate taskDueDate = LocalDate.now();
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
        LocalDate localDate = LocalDate.of(2019,1,2);
        task.setDueDate(localDate);
        assertEquals(task.getDueDateObj(), localDate);
    }

    @Test
    public void getDueDateObjTest() {
        assertEquals(task.getDueDateObj(), LocalDate.now());
    }

    @Test
    public void getDueDateTest() {
        LocalDate localDate = LocalDate.now();
        assertEquals(task.getDueDate(), localDate.getMonthValue() + "/" + localDate.getDayOfMonth());
    }

    @Test
    public void printTaskContentAndDateTest() {
        assertEquals(task.printTaskContentAndDate(), "empty task"  + "  " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "  ");
    }
}
