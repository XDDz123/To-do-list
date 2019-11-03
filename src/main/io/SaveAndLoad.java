package io;

import exceptions.TaskException;
import model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveAndLoad implements Loadable, Savable {

    static final String separator = "~";
    private static final int identifierIndex = 0;
    private static final int keyIndex = 2;
    private final SaveInfoFormatter saveInfoFormatter = new SaveInfoFormatter();
    private final HashMapReconstructor hashMapReconstructor = new HashMapReconstructor();
    private final TaskReconstructor taskReconstructor = new TaskReconstructor();

    //REQUIRES: save.txt to exist and follow the expected format
    //MODIFIES: taskList
    //EFFECTS: Reads information from save file to create tasks with said information
    //         Adds these tasks to the given HashMap, a HashMap of lists of tasks.
    //         "*" denotes an important task
    //         "@" denotes an incomplete task
    //         else task is completed task, also denoted by "#"
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3 (example given on edx)
    @Override
    public void load(TaskListHashMap taskListHashMap, String file)
            throws IOException, TaskException, NumberFormatException {

        List<String> lines = Files.readAllLines(Paths.get(file));
        ArrayList<Task> taskList = new ArrayList<>();
        ArrayList<String> listOfKeys = new ArrayList<>();

        for (String line : lines) {
            ArrayList<String> partsOfLine = separateLine(line);
            reconstructTasks(taskList, partsOfLine);
            listOfKeys.add(partsOfLine.get(keyIndex));
        }

        hashMapReconstructor.loadIntoHashMap(taskListHashMap, taskList, listOfKeys);
    }

    //MODIFIES: taskReconstructor
    //EFFECTS: Reconstructs tasks from loaded data
    private void reconstructTasks(ArrayList<Task> taskList, ArrayList<String> partsOfLine) throws TaskException {
        String identifier = partsOfLine.get(identifierIndex);
        taskReconstructor.createTasks(taskList, partsOfLine, identifier);
    }

    //EFFECTS: returns a new list of strings with sub-strings separated from the given string
    //         Sub-strings in the givens string is separated by the separator
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    ArrayList<String> separateLine(String line) {
        String[] partOfLine = line.split(separator);
        return new ArrayList<>(Arrays.asList(partOfLine));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //REQUIRES: save.txt to exist
    //MODIFIES: save.txt
    //EFFECTS: Takes current hash map of task lists and writes the included information
    //         in the following format to save.txt.
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    @Override
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
