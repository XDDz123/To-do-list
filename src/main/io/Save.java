package io;

import model.Task;
import model.TaskList;
import model.TaskListHashMap;
import java.io.IOException;
import java.io.PrintWriter;

public class Save {
    private final SaveInfoFormatter saveInfoFormatter = new SaveInfoFormatter();

    //REQUIRES: save.txt to exist
    //MODIFIES: save.txt
    //EFFECTS: Takes current hash map of task lists and writes the included information
    //         in the following format to save.txt.
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    public void save(TaskListHashMap taskListHashMap, String file) throws IOException {
        PrintWriter writer = new PrintWriter(file,"UTF-8");
        saveHashMap(taskListHashMap, writer);
        writer.close();
    }

    //EFFECTS: saves the given HashMap onto the save file
    private void saveHashMap(TaskListHashMap taskListHashMap, PrintWriter writer) {
        for (Object key : taskListHashMap.getKeys()) {
            TaskList taskList = taskListHashMap.getTaskList((String) key);
            saveTaskList(writer, taskList);
        }
    }

    //EFFECTS: saves the given taskList onto the save file
    private void saveTaskList(PrintWriter writer, TaskList taskList) {
        for (Task task : taskList.getTaskList()) {
            saveInfoFormatter.saveTaskInfo(writer, task);
        }
    }
}
