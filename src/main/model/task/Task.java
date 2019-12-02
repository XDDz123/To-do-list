package model.task;

import exceptions.TaskException;
import model.Name;
import model.observer.TimeLeftObserver;
import model.tasklist.TaskList;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Task implements Serializable {

    protected transient TaskList taskList;
    private TimeLeftObserver timeLeftObserver = new TimeLeftObserver();
    private Name key;
    private String taskContent;
    private LocalDate taskDueDate;
    private boolean completed;
    private Urgency taskUrgency;
    private Boolean starred;

    //MODIFIES: this, taskList
    //EFFECTS: Constructs a new task.
    //         This task is stored in the given taskList if the given taskList exists (not null).
    public Task(TaskList taskList, String taskContent, LocalDate taskDueDate,
                Urgency taskUrgency, Boolean starred, Boolean completed)
            throws TaskException {
        this.taskList = taskList;
        this.taskContent = taskContent;
        this.taskDueDate = taskDueDate;
        this.taskUrgency = taskUrgency;
        this.starred = starred;
        this.completed = completed;

        if (taskList != null) {
            taskList.storeTask(this);
            key = taskList.getName();
        }

        setTimeLeft();
    }

    public TimeLeftObserver getTimeLeftObserver() {
        return timeLeftObserver;
    }

    //EFFECTS: Returns whether this task is starred.
    public Boolean isStarred() {
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
        taskList.notifyObserver(this);
    }

    //EFFECTS: Returns the task urgency of this task in the form of its string.
    public Urgency getUrgency() {
        return taskUrgency;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        setTimeLeft();
        taskList.notifyObserver(this);
    }

    //EFFECTS: Returns the key for this task
    public Name getKey() {
        return key;
    }

    //MODIFIES: this
    //EFFECTS: Sets the key of this task to the given key
    public void setKey(Name key) {
        this.key = key;
    }

    //EFFECTS: Returns the taskList of this task
    public TaskList getTaskList() {
        return taskList;
    }

    //MODIFIES: this, taskList
    //EFFECTS: Sets the taskList of this task to the given taskList
    //         If the given taskList is null, set the taskList and key of this task to null
    public void setTaskList(TaskList taskList) throws TaskException {
        try {
            if (!taskList.equals(this.taskList)) {
                if (this.taskList != null) {
                    this.taskList.getTaskList().remove(this);
                }
                this.taskList = taskList;
                this.key = taskList.getName();
                taskList.storeTask(this);
            }
        } catch (NullPointerException e) {
            this.taskList = null;
            this.key = null;
        }
    }

    //MODIFIES: this
    //EFFECTS: Changes this task's task content to given content.
    public void setContent(String taskContent) {
        this.taskContent = taskContent;
        taskList.notifyObserver(this);
    }

    //EFFECTS: Returns the task content of this task.
    public String getContent() {
        return taskContent;
    }

    //MODIFIES: this
    //EFFECTS: Sets the due date of this task in the form of month and day.
    public void setDueDate(LocalDate taskDueDate) {
        this.taskDueDate = taskDueDate;
        setTimeLeft();
        taskList.notifyObserver(this);
    }

    //EFFECTS: Returns the due date of this task in the form of a string. ie. 1/1/2019
    public String getDueDate() {
        return getDate(taskDueDate);
    }

    //EFFECTS: Returns the due date of this task as the LocalDate object
    private String getDate(LocalDate localDate) {
        return localDate.getMonthValue() + "/" + localDate.getDayOfMonth() + "/" + localDate.getYear();
    }

    //EFFECTS: Returns the due date of this task in the form of object LocalDate.
    public LocalDate getDueDateObj() {
        return taskDueDate;
    }

    //EFFECTS: Returns the properties of this task in the following format as a string
    @Override
    public String toString() {
        return printTaskContentAndDate()
                + " Urgency: " + getUrgency().getString() + "  "
                + " Time left: " + getTimeLeft();
    }

    //MODIFIES: this
    //EFFECTS: Updates time left until due to the most recent time left until due
    //         Creates a new timeLeftUpdater if the current timeLeftUpdater is null
    //         new timeLeftUpdater should be called after deserialization
    public void setTimeLeft() {
        timeLeftObserver.update(this);
    }

    //EFFECTS: Returns how much time is left until the task is due
    public String getTimeLeft() {
        return timeLeftObserver.getTimeLeft();
    }

    //EFFECTS: Returns the taskDueDate of this task
    public LocalDate getTaskDueDate() {
        return taskDueDate;
    }

    //EFFECTS: Prints the task content and due date in the following format
    public String printTaskContentAndDate() {
        return getContent() + "   " + "Due: " + getDueDate() + "  ";
    }

    //EFFECTS: checks whether the given task is identical to this task
    //Method generated by IDE, partially modified
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task task = (Task) o;

        if (!Objects.equals(taskList, task.taskList)) {
            return false;
        }

        if (!Objects.equals(taskContent, task.taskContent)) {
            return false;
        }

        return Objects.equals(taskDueDate, task.taskDueDate);
    }

    //EFFECTS: Calculates the hashcode for this task.
    //Method generated by IDE
    @Override
    public int hashCode() {
        int result = taskList != null ? taskList.hashCode() : 0;
        result = 31 * result + (taskContent != null ? taskContent.hashCode() : 0);
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);
        return result;
    }
}
