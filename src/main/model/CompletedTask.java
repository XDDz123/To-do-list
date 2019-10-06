package model;

import java.time.MonthDay;

public class CompletedTask extends Task {

    private String completionStatus;

    //MODIFIES: this
    //EFFECTS: Constructs a completed task
    public CompletedTask(String taskContent, MonthDay taskDueDate, String completionStatus) {
        super(taskContent, taskDueDate);
        this.completionStatus = completionStatus;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    @Override
    public String printTask() {
        return super.printTaskContentAndDate() + "Completed on: " + getCompletionStatus();
    }
}