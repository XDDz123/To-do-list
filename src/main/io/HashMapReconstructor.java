package io;

import exceptions.TaskException;
import model.task.Task;
import model.tasklist.TaskList;
import model.TaskListHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class HashMapReconstructor {

    //MODIFIES: taskListHashMap
    //EFFECTS: Creates TaskLists for each key and maps them into the HashMap.
    //         Stores the given list of tasks using the given list of keys in their
    //         respective TaskList (with the same name) in the given HashMap.
    //         Tasks and keys are mapped linearly, with index to index, i.e. task 0 --> key 0.
    void loadIntoHashMap(TaskListHashMap taskListHashMap, ArrayList<Task> taskList, ArrayList<String> listOfKeys)
            throws TaskException {
        createTaskLists(taskListHashMap, listOfKeys);
        storeTasks(taskListHashMap, taskList, listOfKeys);
    }

    //MODIFIES: taskListHashMap
    //EFFECTS: loads tasks in the given list of tasks into their respective lists in the give HashMap
    private void storeTasks(TaskListHashMap taskListHashMap, ArrayList<Task> taskList, ArrayList<String> listOfKeys)
            throws TaskException {
        for (int i = 0; i < listOfKeys.size(); i++) {
            taskListHashMap.getTaskList(listOfKeys.get(i)).storeTask(taskList.get(i));
        }
    }

    //MODIFIES: taskListHashMap
    //EFFECTS: creates TaskLists with the name of each unique key and maps them to the given HashMap
    private void createTaskLists(TaskListHashMap taskListHashMap, ArrayList<String> listOfKeys) {
        for (String key : eliminateDuplicates(listOfKeys)) {
            TaskList taskListTemp = new TaskList(key);
            taskListHashMap.storeTaskList(taskListTemp);
        }
    }

    //EFFECTS: Returns a new list of strings by eliminating any duplicate strings in the given list of strings.
    //inspired by https://www.geeksforgeeks.org/how-to-remove-duplicates-from-arraylist-in-java/
    private List<String> eliminateDuplicates(ArrayList<String> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}