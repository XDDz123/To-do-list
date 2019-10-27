/*
package model;

import java.util.HashMap;
import java.util.Set;

public class TaskListHashMap {

    private HashMap<String, TaskList> taskListHashMap;

    public TaskListHashMap() {
        taskListHashMap = new HashMap<>();
    }

    public HashMap<String, TaskList> getTaskListMap() {
        return taskListHashMap;
    }

    public TaskList getTaskList(String key) {
        return taskListHashMap.get(key);
    }

    public void remapTaskList(String oldKey, String newKey) {
        TaskList taskList = taskListHashMap.get(oldKey);
        taskListHashMap.remove(oldKey);
        taskListHashMap.put(newKey, taskList);
    }

    public Set getKeys() {
        return taskListHashMap.keySet();
    }
}
*/
