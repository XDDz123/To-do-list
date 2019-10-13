package model;

import java.time.LocalDate;
import java.time.Period;


public class RegularTask extends Task {

    private String timeLeft;
    private LocalDate dueDate;
    private LocalDate currentDate;
    private String taskUrgency;

    //EFFECTS: Constructs a new regular task.
    //MODIFIES: this
    public RegularTask(String taskContent, LocalDate taskDueDate, String taskUrgency) {
        super(taskContent, taskDueDate);
        this.taskUrgency = taskUrgency;
        timeLeft = "tbd";
    }

    //MODIFIES: this
    //EFFECTS: Sets this task's urgency to given urgency.
    public void setUrgency(String taskUrgency) {
        this.taskUrgency = taskUrgency;
    }

    //EFFECTS: Returns the task urgency of this task.
    public String getUrgency() {
        return taskUrgency;
    }

    //EFFECTS: Prints the properties of this task in the following format.
    @Override
    public String printTask() {
        return super.printTaskContentAndDate() + "Urgency: " + getUrgency() + "  " + "Time left: " + getTimeLeft();
    }

    //MODIFIES: this
    //EFFECTS: Updates time left until due to the most recent time left until due
    public void setTimeLeft() {
        timeLeft = computeTimeLeft();
    }

    //EFFECTS: Returns how much time is left until the task is due
    public String getTimeLeft() {
        return timeLeft;
    }

    //EFFECTS: Changes the time left to the given time left
    void changeTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    //MODIFIES: this
    //EFFECTS: Updates currentDate to the current date and month
    //         Updates dueDate to the due date of this task
    void setCurrentDateAndDueDate() {
        currentDate = LocalDate.now();
        dueDate = super.getDueDateObj();
    }

    //EFFECTS: Returns the current LocalDate, based on the system time
    LocalDate getCurrentDate() {
        return currentDate;
    }

    //EFFECTS: Returns the LocalDate of the due date of this task
    LocalDate getDueDateTemp() {
        return dueDate;
    }

    //EFFECTS: Returns the time left until task is due if given year matches the current year
    //         Returns due today if the due date matches the current date
    //         Returns the number of days left until due if due date is within a month from current date
    //         Returns the number of months and days otherwise.
    private String computeTimeLeft() {
        setCurrentDateAndDueDate();

        Period difference = Period.between(dueDate, currentDate);

        if (dueDate.equals(currentDate)) {
            return "due today";
        } else {
            if (difference.getMonths() == 0) {
                return Math.abs(difference.getDays()) + " days.";
            } else {
                return Math.abs(difference.getMonths()) + " months " + Math.abs(difference.getDays()) + " days.";
            }
        }
    }
}
