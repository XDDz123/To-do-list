package model;

import java.time.LocalDate;
import java.time.Period;

public class IncompleteTask extends Task {

    private String timeLeft;
    private String taskUrgency;

    //EFFECTS: Constructs a new incomplete task.
    //MODIFIES: this
    public IncompleteTask(String taskContent, LocalDate taskDueDate, String taskUrgency) {
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

    //EFFECTS: Returns the time left until task is due if given year matches the current year
    //         Returns due today if the due date matches the current date
    //         Returns the number of days left until due if due date is within a month from current date
    //         Returns the number of months and days otherwise.
    private String computeTimeLeft() {
        
        Period difference = Period.between(taskDueDate, LocalDate.now());

        if (taskDueDate.equals(LocalDate.now())) {
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
