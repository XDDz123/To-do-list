package ui;

import java.util.*;

public class Task {
    private String taskInput;
    private String taskUrgency;

    public Task() {
        taskInput = "empty task";
        taskUrgency = "unassigned";
    }

    public void createTask() {
        Scanner keyboard = new Scanner(System.in);
        taskInput = keyboard.nextLine();
    }

    public void setUrgency(String urgency) {
        this.taskUrgency = urgency;
    }

    public String getUrgency() {
        return taskUrgency;
    }


    public String getTaskContent() {
        return taskInput;
    }
}
