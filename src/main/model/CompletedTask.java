package model;

import java.time.LocalDate;

public class CompletedTask extends Task {

    private String completionStatus;

    //MODIFIES: this
    //EFFECTS: Constructs a completed task
    public CompletedTask(String taskContent, LocalDate taskDueDate, String completionStatus) {
        super(taskContent, taskDueDate);
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
}