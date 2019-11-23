/*
package model.task;

import exceptions.TaskException;
import model.tasklist.TaskList;
import model.observer.ObserverState;
import model.observer.TimeLeftObserver;

import java.io.Serializable;
import java.time.LocalDate;

public class IncompleteTask extends Task implements Serializable {

    private transient TimeLeftObserver timeLeftObserver = new TimeLeftObserver();
    private Urgency taskUrgency;
    private Boolean starred;

    //EFFECTS: Constructs a new incomplete task.
    //MODIFIES: this
    public IncompleteTask(TaskList taskList, String taskContent, LocalDate taskDueDate, Urgency taskUrgency,
                          Boolean starred)
            throws TaskException {
        super(taskList, taskContent, taskDueDate);
        this.taskUrgency = taskUrgency;
        this.starred = starred;
        notifyObserver(new ObserverState<>(taskDueDate), timeLeftObserver);
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
    public void setUrgency(Urgency taskUrgency) {
        this.taskUrgency = taskUrgency;
    }

    //EFFECTS: Returns the task urgency of this task in the form of its string.
    public Urgency getUrgency() {
        return taskUrgency;
    }

    //EFFECTS: Prints the properties of this task in the following format.
    @Override
    public String printTask() {
        return super.printTaskContentAndDate()
                + "Urgency: " + getUrgency().getString() + "  "
                + "Time left: " + getTimeLeft() + "  "
                + "Starred: " + starred;
    }

    //MODIFIES: this
    //EFFECTS: Updates time left until due to the most recent time left until due
    //         Creates a new timeLeftUpdater if the current timeLeftUpdater is null
    //         new timeLeftUpdater should be called after deserialization
    public void setTimeLeft() {
        if (timeLeftObserver == null) {
            timeLeftObserver = new TimeLeftObserver();
        }
        notifyObserver(new ObserverState<>(taskDueDate), timeLeftObserver);
    }

    //EFFECTS: Returns how much time is left until the task is due
    public String getTimeLeft() {
        return timeLeftObserver.getTimeLeft();
    }

    //EFFECTS: Returns the taskDueDate of this task
    public LocalDate getTaskDueDate() {
        return taskDueDate;
    }
}
*/
