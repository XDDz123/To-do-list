package model;

import java.time.MonthDay;

public class Task {

    private String taskContent;
    private String taskUrgency;
    private MonthDay taskDueDate;
    private boolean taskStatus;

    //EFFECTS: Constructs a new task.
    //MODIFIES: this
    public Task() {
        taskContent = "empty task";
        taskUrgency = "unassigned";
        taskDueDate = MonthDay.now();
        taskStatus = false;
    }

    //MODIFIES: this
    //EFFECTS: Changes this task's task content to given content.
    public void setContent(String taskContent) {
        this.taskContent = taskContent;
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

    //EFFECTS: Returns the task content of this task.
    public String getTaskContent() {
        return taskContent;
    }

    //MODIFIES: this
    //EFFECTS: Sets the due date of this task in the form of month and day.
    public void setDueDate(MonthDay taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    //EFFECTS: Returns the due date of this task in the form of month/day. ie. 1/1
    public String getDueDate() {
        return taskDueDate.getMonthValue() + "/" + taskDueDate.getDayOfMonth();
    }

    //EFFECTS: Returns the due date of this task in the form of object MonthDay.
    public MonthDay getDueDateObj() {
        return taskDueDate;
    }

    //EFFECTS: Returns status of completion of this task.
    public boolean getStatus() {
        return taskStatus;
    }

    //MODIFIES: this
    //EFFECTS: Sets the completion status of this task (true/false) to the give status (true/false).
    public void setStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    //EFFECTS: Prints the properties of this task in the following format.
    public String printTask() {
        return getTaskContent() + "  " + "Due: " + getDueDate() + "  " + "Urgency: " + getUrgency() + "  "
               + "Completed: " + getStatus();
    }
}
