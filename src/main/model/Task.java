package model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Task {

    private String taskContent;
    protected LocalDate taskDueDate;

    //EFFECTS: Constructs a new task.
    //MODIFIES: this
    Task(String taskContent, LocalDate taskDueDate) {
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
    public void setDueDate(LocalDate taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    //EFFECTS: Returns the due date of this task in the form of month/day. ie. 1/1
    public String getDueDate() {
        return getDate(taskDueDate);
    }

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
    String printTaskContentAndDate() {
        return getContent() + "  " + "Due: " + getDueDate() + "  ";
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task task = (Task) o;

        if (!Objects.equals(taskContent, task.taskContent)) {
            return false;
        }

        return Objects.equals(taskDueDate, task.taskDueDate);
    }

    @Override
    public int hashCode() {
        int result = taskContent != null ? taskContent.hashCode() : 0;
        result = 31 * result + (taskDueDate != null ? taskDueDate.hashCode() : 0);
        return result;
    }*/
}
