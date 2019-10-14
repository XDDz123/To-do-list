package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;


class TaskTest {
    private Task task;
    private LocalDate taskDueDate = LocalDate.now();

    @BeforeEach
    void runBefore() {
        task = new RegularTask("empty task", taskDueDate, "unassigned");
    }

    @Test
    void setContentTest() {
        task.setContent("this is a test!");
        assertEquals(task.getContent(), "this is a test!");
    }

    @Test
    void setContentTestEmpty() {
        task.setContent("");
        assertEquals(task.getContent(), "");
    }

    @Test
    void getContentTest() {
        assertEquals(task.getContent(), "empty task");
    }

    @Test
    void setDueDateTest() {
        LocalDate localDate = LocalDate.of(2019,1,2);
        task.setDueDate(localDate);
        assertEquals(task.getDueDateObj(), localDate);
    }

    @Test
    void getDueDateObjTest() {
        assertEquals(task.getDueDateObj(), LocalDate.now());
    }

    @Test
    void getDueDateTest() {
        LocalDate localDate = LocalDate.now();
        assertEquals(task.getDueDate(), localDate.getMonthValue() + "/" + localDate.getDayOfMonth());
    }

    @Test
    void printTaskContentAndDateTest() {
        assertEquals(task.printTaskContentAndDate(), "empty task"  + "  " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "  ");
    }
}
