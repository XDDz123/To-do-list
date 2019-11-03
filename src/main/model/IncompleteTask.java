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
                + "Time left: " + getTimeLeft();
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
}
