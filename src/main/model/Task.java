package model;

import java.time.MonthDay;

public abstract class Task {

    private String taskContent;
    private MonthDay taskDueDate;

    //EFFECTS: Constructs a new task.
    //MODIFIES: this
    public Task(String taskContent, MonthDay taskDueDate) {
        this.taskContent = taskContent;
        this.taskDueDate = taskDueDate;
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
    public void setDueDate(MonthDay taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    //EFFECTS: Returns the due date of this task in the form of month/day. ie. 1/1
    public String getDueDate() {
        return getDate(taskDueDate);
    }

    public String getDate(MonthDay monthDay) {
        return monthDay.getMonthValue() + "/" + monthDay.getDayOfMonth();
    }

    //EFFECTS: Returns the due date of this task in the form of object MonthDay.
    public MonthDay getDueDateObj() {
        return taskDueDate;
    }

    //EFFECTS: Prints the properties of this task in the following format.
    public abstract String printTask();

    public String printTaskContentAndDate() {
        return getContent() + "  " + "Due: " + getDueDate() + "  ";
    }
}
