package model.task;

import exceptions.TaskException;
import model.tasklist.TaskList;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Task implements Serializable {

    private String key;
    protected transient TaskList taskList;
    private String taskContent;
    LocalDate taskDueDate;

    //MODIFIES: this, taskList
    //EFFECTS: Constructs a new task.
    //         This task is stored in the given taskList if the given taskList exists (not null).
    Task(TaskList taskList, String taskContent, LocalDate taskDueDate) throws TaskException {
        this.taskList = taskList;
        this.taskContent = taskContent;
        this.taskDueDate = taskDueDate;

        if (taskList != null) {
            taskList.storeTask(this);
            key = taskList.getName();
        }
    }

    //EFFECTS: Returns the key for this task
    public String getKey() {
        return key;
    }

    //EFFECTS: Returns the taskList of this task
    public TaskList getTaskList() {
        return taskList;
    }

    //MODIFIES: this, taskList
    //EFFECTS: Sets the taskList of this task to the given taskList
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
    }

    //EFFECTS: Returns the task content of this task.
    public String getContent() {
        return taskContent;
    }

    //MODIFIES: this
    //EFFECTS: Sets the due date of this task in the form of month and day.
    public void setDueDate(LocalDate taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    //EFFECTS: Returns the due date of this task in the form of month/day. ie. 1/1
    public String getDueDate() {
        return getDate(taskDueDate);
    }

    //EFFECTS: Returns the due date of this task as the LocalDate object
    public String getDate(LocalDate localDate) {
        return localDate.getMonthValue() + "/" + localDate.getDayOfMonth();
    }

    //EFFECTS: Returns the due date of this task in the form of object LocalDate.
    public LocalDate getDueDateObj() {
        return taskDueDate;
    }

    //EFFECTS: abstract method for printing information store in the task
    public abstract String printTask();

    //EFFECTS: Prints the task content and due date in the following format
    public String printTaskContentAndDate() {
        return getContent() + "  " + "Due: " + getDueDate() + "  ";
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