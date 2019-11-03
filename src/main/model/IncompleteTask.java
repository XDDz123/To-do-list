package model;

import exceptions.TaskException;

import java.time.LocalDate;

public class IncompleteTask extends Task {

    private final TimeLeftUpdater timeLeftUpdater = new TimeLeftUpdater();
    private String taskUrgency;

    //EFFECTS: Constructs a new incomplete task.
    //MODIFIES: this
    public IncompleteTask(TaskList taskList, String taskContent, LocalDate taskDueDate, String taskUrgency)
            throws TaskException {
        super(taskList, taskContent, taskDueDate);
        this.taskUrgency = taskUrgency;
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
        return super.printTaskContentAndDate()
                + "Urgency: " + getUrgency() + "  "
                + "Time left: " + timeLeftUpdater.getTimeLeft();
    }

    //MODIFIES: this
    //EFFECTS: Updates time left until due to the most recent time left until due
    public void setTimeLeft() {
        timeLeftUpdater.setTimeLeft(taskDueDate);
    }

    //EFFECTS: Returns how much time is left until the task is due
    public String getTimeLeft() {
        return timeLeftUpdater.getTimeLeft();
    }

    //EFFECTS: Changes the time left to the given time left
    void changeTimeLeft(String timeLeft) {
        timeLeftUpdater.changeTimeLeft(timeLeft);
    }

    public LocalDate getTaskDueDate() {
        return taskDueDate;
    }


    /*    //EFFECTS: Returns the time left until task is due if given year matches the current year
    //         Returns due today if the due date matches the current date
    //         Returns the number of days left until due if due date is within a month from current date
    //         Returns the number of months and days otherwise.
    private String computeTimeLeft() {
        return timeLeftUpdater.computeTimeLeft(taskDueDate);
    }*/
}
