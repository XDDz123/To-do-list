package model.observer;

import java.time.LocalDate;
import java.time.Period;

public class TimeLeftObserver implements Observer {

    private String timeLeft;

    //MODIFIES: this
    //EFFECTS: Updates time left until due to the most recent time left until due
    @Override
    public void update(ObserverState observerState) {
        if (observerState.getState() != null) {
            timeLeft = computeTimeLeft((LocalDate) observerState.getState());
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
        } else {
            if (difference.getMonths() == 0) {
                return Math.abs(difference.getDays()) + " days";
            } else {
                return Math.abs(difference.getMonths()) + " months " + Math.abs(difference.getDays()) + " days";
            }
        }
    }
}