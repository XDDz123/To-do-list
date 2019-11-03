package model;

import java.util.HashMap;
import java.util.Set;

public class TaskListHashMap {

    private HashMap<String, TaskList> taskListHashMap;

    //MODIFIES: this
    //EFFECTS: creates a new HashMap
    public TaskListHashMap() {
        taskListHashMap = new HashMap<>();
    }

    //EFFECTS: returns taskList in this HashMap associated with a given key
    public TaskList getTaskList(String key) {
        return taskListHashMap.get(key);
    }

    //MODIFIES: this
    //EFFECTS: added the given taskList to this HashMap, with its name as its key
    public void storeTaskList(TaskList taskList) {
        taskListHashMap.put(taskList.getName(), taskList);
    }

    //EFFECTS: returns a set of key values stored in the HashMap
    public Set getKeys() {
        return taskListHashMap.keySet();
    }

    //MODIFIES: this
    //EFFECTS: removes the taskList associated with the given key and the given key from this HashMap
    public void removeTaskList(String key) {
        taskListHashMap.remove(key, taskListHashMap.get(key));
    }

    //EFFECTS: returns this HashMap
    HashMap<String, TaskList> getTaskListMap() {
        return taskListHashMap;
    }
}
