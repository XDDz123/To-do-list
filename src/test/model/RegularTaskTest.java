package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularTaskTest {

    private RegularTask regularTask;
    private String taskContent = "empty task";
    private LocalDate taskDueDate = LocalDate.now();
    private String taskUrgency = "unassigned";

    @BeforeEach
    public void runBefore() {
        regularTask = new RegularTask(taskContent, taskDueDate, taskUrgency);
    }

    @Test
    public void setUrgencyTest() {
        regularTask.setUrgency("high");
        assertEquals(regularTask.getUrgency(), "high");
    }

    @Test
    public void getUrgencyTest() {
        assertEquals(regularTask.getUrgency(), "unassigned");
    }

    @Test
    public void printTaskTest() {
        assertEquals(regularTask.printTask(), "empty task"  + "  " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "  " + "Urgency: " + "unassigned" + "  " + "Time left: tbd");
    }


    @Test
    public void setTimeLeftTest() {
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(), "due today");
    }

    @Test
    public void getTimeLeftTest() {
        assertEquals(regularTask.getTimeLeft(), "tbd");
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(), "due today");
    }

    @Test
    public void changeTimeLeftTest() {
        regularTask.changeTimeLeft("1 day");
        assertEquals(regularTask.getTimeLeft(), "1 day");
    }

    @Test
    public void changeTimeLeftAltTest() {
        regularTask.changeTimeLeft("2 days");
        assertEquals(regularTask.getTimeLeft(), "2 days");
    }


    @Test
    public void computeTimeLeftDueTodayTest() {
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(),"due today");
    }

    @Test
    public void computeTimeLeftInDaysTest() {
        regularTask.setDueDate(LocalDate.of(2019, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth() + 1));
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(),1 + " days");
    }

    @Test
    public void computeTimeLeftInMonthsTest() {
        regularTask.setDueDate(LocalDate.of(2019,LocalDate.now().getMonthValue() + 1, LocalDate.now().getDayOfMonth()));
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(),1 + " months " + 0 + " days");
    }

    @Test
    public void computeTimeLeftInDaysAndMonthsTest() {
        regularTask.setDueDate(LocalDate.of(2019,LocalDate.now().getMonthValue() + 1,
                LocalDate.now().getDayOfMonth() + 1));
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(),1 + " months " + 1 + " days");
    }
}
