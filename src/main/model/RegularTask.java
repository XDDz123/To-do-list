package model;

import java.time.MonthDay;

public class RegularTask extends Task {

    private String taskUrgency;

    //EFFECTS: Constructs a new task.
    //MODIFIES: this
    public RegularTask(String taskContent, MonthDay taskDueDate, String taskUrgency) {
        super(taskContent, taskDueDate);
        this.taskUrgency = taskUrgency;
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
    public String printTask() {
        return super.printTaskContentAndDate() + "Urgency: " + getUrgency();
    }
}
