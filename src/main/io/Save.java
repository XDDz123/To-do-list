package io;

import model.Task;
import model.TaskList;
import model.TaskListHashMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Save {

    //MODIFIES: save
    //EFFECTS: Takes current hash map of task lists and writes (serializes) the included information
    //         in the following format to save.txt.
    //inspired by https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    //inspired by https://www.geeksforgeeks.org/customized-serialization-and-deserialization-in-java/
    public void save(TaskListHashMap taskListHashMap, String saveFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        saveHashMap(taskListHashMap, objectOutputStream);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    //EFFECTS: Calls saveTaskList to serialize every taskList stored in the given HashMap
    private void saveHashMap(TaskListHashMap taskListHashMap, ObjectOutputStream objectOutputStream)
            throws IOException {
        for (Object key : taskListHashMap.getKeys()) {
            TaskList taskList = taskListHashMap.getTaskList((String) key);
            saveTaskList(taskList, objectOutputStream);
        }
    }

    //EFFECTS: serializes tasks in the given taskList and writes the information to the save file
    //inspired by https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    //inspired by https://www.geeksforgeeks.org/customized-serialization-and-deserialization-in-java/
    private void saveTaskList(TaskList taskList, ObjectOutputStream objectOutputStream) throws IOException {
        for (Task task : taskList.getTaskList()) {
            objectOutputStream.writeObject(task);
        }
    }
}
