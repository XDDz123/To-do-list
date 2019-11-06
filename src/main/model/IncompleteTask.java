package model;

import exceptions.TaskException;
import java.io.Serializable;
import java.time.LocalDate;

public class IncompleteTask extends Task implements Serializable {

    private transient TimeLeftUpdater timeLeftUpdater = new TimeLeftUpdater();
    private String taskUrgency;
    private Boolean starred;

    //EFFECTS: Constructs a new incomplete task.
    //MODIFIES: this
    public IncompleteTask(TaskList taskList, String taskContent, LocalDate taskDueDate, String taskUrgency,
                          Boolean starred)
            throws TaskException {
        super(taskList, taskContent, taskDueDate);
        this.taskUrgency = taskUrgency;
        this.starred = starred;
    }


    //EFFECTS: Returns whether this task is starred.
    public Boolean getStarred() {
        return starred;
    }

    //MODIFIES: this
    //EFFECTS: Sets stars this task if given starred is true, ow sets starred to false.
    public void setStarred(Boolean starred) {
        this.starred = starred;
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
                + "Time left: " + getTimeLeft() + "  "
                + "Starred: " + starred;
    }

    //MODIFIES: this
    //EFFECTS: Updates time left until due to the most recent time left until due
    //         Creates a new timeLeftUpdater if the current timeLeftUpdater is null
    //         new timeLeftUpdater should be called after deserialization
    public void setTimeLeft() {
        if (timeLeftUpdater == null) {
            timeLeftUpdater = new TimeLeftUpdater();
        }
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
