package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegularTaskTest {

    private RegularTask regularTask;
    private LocalDate taskDueDate = LocalDate.now();

    @BeforeEach
    void runBefore() {
        regularTask = new RegularTask("empty task", taskDueDate, "unassigned");
    }

    @Test
    void setUrgencyTest() {
        regularTask.setUrgency("high");
        assertEquals(regularTask.getUrgency(), "high");
    }

    @Test
    void getUrgencyTest() {
        assertEquals(regularTask.getUrgency(), "unassigned");
    }

    @Test
    void printTaskTest() {
        assertEquals(regularTask.printTask(), "empty task"  + "  " + "Due: " + LocalDate.now().getMonthValue()
                + "/" + LocalDate.now().getDayOfMonth() + "  " + "Urgency: " + "unassigned" + "  " + "Time left: tbd");
    }


    @Test
    void setTimeLeftTest() {
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(), "due today");
    }

    @Test
    void getTimeLeftTest() {
        assertEquals(regularTask.getTimeLeft(), "tbd");
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(), "due today");
    }

    @Test
    void changeTimeLeftTest() {
        regularTask.changeTimeLeft("1 day");
        assertEquals(regularTask.getTimeLeft(), "1 day");
    }

    @Test
    void changeTimeLeftAltTest() {
        regularTask.changeTimeLeft("2 days");
        assertEquals(regularTask.getTimeLeft(), "2 days");
    }


    @Test
    void computeTimeLeftDueTodayTest() {
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(),"due today");
    }

    @Test
    void computeTimeLeftInDaysTest() {
        regularTask.setDueDate(LocalDate.of(2019, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth() + 1));
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(),1 + " days");
    }

    @Test
    void computeTimeLeftInMonthsTest() {
        regularTask.setDueDate(LocalDate.of(2019,LocalDate.now().getMonthValue() + 1, LocalDate.now().getDayOfMonth()));
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(),1 + " months " + 0 + " days");
    }

    @Test
    void computeTimeLeftInDaysAndMonthsTest() {
        regularTask.setDueDate(LocalDate.of(2019,LocalDate.now().getMonthValue() + 1,
                LocalDate.now().getDayOfMonth() + 1));
        regularTask.setTimeLeft();
        assertEquals(regularTask.getTimeLeft(),1 + " months " + 1 + " days");
    }
}
