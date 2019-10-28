package model;

import java.util.HashMap;
import java.util.Set;

public class TaskListHashMap {

    private HashMap<String, TaskList> taskListHashMap;

    public TaskListHashMap() {
        taskListHashMap = new HashMap<>();
    }

    public TaskList getTaskList(String key) {
        return taskListHashMap.get(key);
    }

    public void storeTaskList(TaskList taskList) {
        taskListHashMap.put(taskList.getName(), taskList);
    }

    public Set getKeys() {
        return taskListHashMap.keySet();
    }

    public void removeTaskList(String key) {
        taskListHashMap.remove(key, taskListHashMap.get(key));
    }

    /*
    public void remapTaskList(String oldKey, String newKey) {
        TaskList taskList = taskListHashMap.get(oldKey);
        taskListHashMap.remove(oldKey);
        taskList.setName(newKey);
        taskListHashMap.put(newKey, taskList);
    }
    */

    public HashMap<String, TaskList> getTaskListMap() {
        return taskListHashMap;
    }
}
