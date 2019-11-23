/*
package model.task;

import exceptions.TaskException;
import model.tasklist.TaskList;

import java.io.Serializable;
import java.time.LocalDate;

public class CompletedTask extends Task implements Serializable {

    private String completionStatus;
    public static final String pastDue = "past due";

    //MODIFIES: this
    //EFFECTS: Constructs a completed task
    public CompletedTask(TaskList taskList, String taskContent, LocalDate taskDueDate, String completionStatus)
            throws TaskException {
        super(taskList, taskContent, taskDueDate);
        this.completionStatus = completionStatus;
    }

    //EFFECTS: Returns the completion status of this task
    public String getCompletionStatus() {
        return completionStatus;
    }

    //EFFECTS: Prints the properties of this task in the following format.
    @Override
    public String printTask() {
        return super.printTaskContentAndDate() + "Completed on: " + getCompletionStatus();
    }
}*/
