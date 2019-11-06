package io;

import model.Task;
import model.TaskList;
import model.TaskListHashMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class Save {

    //MODIFIES: save
    //EFFECTS: Takes current hash map of task lists and writes (serializes) the included information
    //         in the following format to save.txt.
    //inspired by https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    //inspired by https://www.geeksforgeeks.org/customized-serialization-and-deserialization-in-java/
    public void save(TaskListHashMap taskListHashMap, String saveFile, String keyList) throws IOException {
        PrintWriter writer = new PrintWriter(keyList,"UTF-8");
        FileOutputStream fos = new FileOutputStream(saveFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        saveHashMap(taskListHashMap, writer, oos);
        writer.close();
        oos.close();
        fos.close();
    }

    //EFFECTS: Calls saveTaskList to serialize every taskList stored in the given HashMap
    private void saveHashMap(TaskListHashMap taskListHashMap, PrintWriter writer, ObjectOutputStream oos)
            throws IOException {
        for (Object key : taskListHashMap.getKeys()) {
            TaskList taskList = taskListHashMap.getTaskList((String) key);
            saveTaskList(writer, taskList, oos);
        }
    }

    //EFFECTS: serializes tasks in the given taskList and writes the information to the save file
    //inspired by https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    //inspired by https://www.geeksforgeeks.org/customized-serialization-and-deserialization-in-java/
    private void saveTaskList(PrintWriter writer, TaskList taskList, ObjectOutputStream oos) throws IOException {
        for (Task task : taskList.getTaskList()) {
            writer.println(task.getTaskList().getName());
            oos.writeObject(task);
        }
    }
}
