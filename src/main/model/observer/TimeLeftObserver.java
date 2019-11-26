package model.observer;

import model.task.Task;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class TimeLeftObserver implements Serializable {

    private String timeLeft = "tbd";

    //MODIFIES: this
    //EFFECTS: Updates time left until due to the most recent time left until due
    public void update(Task task) {
        if (task.getDueDateObj() != null) {
            if (task.isCompleted()) {
                timeLeft = "Completed";
            } else {
                timeLeft = computeTimeLeft(task.getDueDateObj());
            }
        }
    }

    //EFFECTS: Returns how much time is left until the task is due
    public String getTimeLeft() {
        return timeLeft;
    }

    //EFFECTS: Returns the time left until task is due if given year matches the current year
    //         Returns due today if the due date matches the current date
    //         Returns the number of days left until due if due date is within a month from current date
    //         Returns the number of months and days otherwise.
    private String computeTimeLeft(LocalDate dueDate) {

        Period difference = Period.between(dueDate, LocalDate.now());

        if (dueDate.equals(LocalDate.now())) {
            return "due today";
        } else if (dueDate.isAfter(LocalDate.now())) {
            if (difference.getYears() == 0) {
                if (difference.getMonths() == 0) {
                    return Math.abs(difference.getDays()) + " day(s)";
                } else {
                    return Math.abs(difference.getMonths()) + " month(s) " + Math.abs(difference.getDays()) + " day(s)";
                }
            } else {
                return Math.abs(difference.getYears()) + " year(s) " + Math.abs(difference.getMonths())
                        + " month(s) " + Math.abs(difference.getDays()) + " day(s)";
            }
        } else {
            return "past due";
        }
    }
}