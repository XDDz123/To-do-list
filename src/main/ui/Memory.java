package ui;

import java.util.*;

public class Memory {

    private ArrayList<Task> taskList  = new ArrayList<>();

    public void storeTask(Task temp) {
        taskList.add(temp);
    }

    public ArrayList getTaskList() {
        return taskList;
    }

    public Task getTask(int index) {
        return taskList.get(index);
    }


    public void deleteTask(int index) {
        taskList.remove(index - 1);
    }

    public void clearTaskList() {
        taskList.clear();
    }

    public boolean isTaskListEmpty() {
        return taskList.isEmpty();
    }

    public int getTaskListSize() {
        return taskList.size();
    }

}
