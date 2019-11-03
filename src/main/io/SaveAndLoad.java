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


/*    //EFFECTS: Returns information in an important task in the following format
    private String formatImportantTaskInfo(TaskList taskList, int i) {
        return saveInfoFormatter.formatImportantTaskInfo(taskList, i);
    }

    //EFFECTS: Returns information in a incomplete task in the following format
    private String formatIncompleteTaskInfo(TaskList taskList, int i) {
        return saveInfoFormatter.formatIncompleteTaskInfo(taskList, i);
    }

    //EFFECTS: Returns information in a completed task in the following format
    private String formatCompletedTaskInfo(TaskList taskList, int i) {
        return saveInfoFormatter.formatCompletedTaskInfo(taskList, i);
    }*/
/*

    //MODIFIES: save.txt
    //EFFECTS: Clears/formats the current save file
    public void clearSave(String fileName, TaskList taskList) throws IOException {
        File file = new File(fileName);
        file.delete();
        file.createNewFile();
        taskList.clearTaskList();
    }
*/

//////////////////////////////////////////////////////////////////////////////////////

    /*    //MODIFIES: taskListHashMap
    //EFFECTS: Creates TaskLists for each key and maps them into the HashMap.
    //         Stores the given list of tasks using the given list of keys in their
    //         respective TaskList (with the same name) in the given HashMap.
    //         Tasks and keys are mapped linearly, with index to index, i.e. task 0 --> key 0.
    void loadIntoHashMap(TaskListHashMap taskListHashMap, ArrayList<Task> taskList, ArrayList<String> listOfKeys) {
        //creates TaskLists with the name of each unique key

        //stores Tasks in the given ArrayList
        hashMapReconstructor.loadIntoHashMap(taskListHashMap, taskList, listOfKeys);
    }

    //EFFECTS: Returns a new list of strings by eliminating any duplicate strings in the given list of strings.
    //inspired by https://www.geeksforgeeks.org/how-to-remove-duplicates-from-arraylist-in-java/
    List<String> eliminateDuplicates(ArrayList<String> list) {
        return hashMapReconstructor.eliminateDuplicates(list);
    }*/

    /*
    //MODIFIES: this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new completed task.
    //         Stores the created task in the list of tasks.
    private void createCompletedTaskFromLoad(ArrayList<String> partsOfLine,  ArrayList<Task> taskList)
            throws TaskException {
        //taskList.storeTask(new CompletedTask(taskList, taskContent, taskDueDate, completionStatus));
        taskReconstructor.createCompletedTaskFromLoad(partsOfLine, taskList);
    }

    //MODIFIES: this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new incomplete task.
    //         Stores the created task in the list of tasks.
    private void createIncompleteTaskFromLoad(ArrayList<String> partsOfLine, ArrayList<Task> taskList)
            throws TaskException {
        //taskList.storeTask(new IncompleteTask(taskList, taskContent, taskDueDate, taskUrgency));
        taskReconstructor.createIncompleteTaskFromLoad(partsOfLine, taskList);
    }

    //MODIFIES: this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new important task.
    //         Stores the created task in the list of tasks.
    private void createImportantTaskFromLoad(ArrayList<String> partsOfLine, ArrayList<Task> taskList)
            throws TaskException {
        //taskList.storeTask(new ImportantTask(taskList, taskContent, taskDueDate, taskUrgency, partsOfLine.get(6)));
        taskReconstructor.createImportantTaskFromLoad(partsOfLine, taskList);
    }*/

    /*
    //MODIFIES: this
    //EFFECTS: Loads information from the list partsOfLine into the temporary variables of:
    //         task content, due date and completion status
    private void setCompletedTaskField(ArrayList<String> partsOfLine) {
        taskReconstructor.setCompletedTaskField(partsOfLine);
    }*/

    /*    //MODIFIES: taskListHashMap
    //EFFECTS: Creates TaskLists for each key and maps them into the HashMap.
    //         Stores the given list of tasks using the given list of keys in their
    //         respective TaskList (with the same name) in the given HashMap.
    //         Tasks and keys are mapped linearly, with index to index, i.e. task 0 --> key 0.
    void loadIntoHashMap(TaskListHashMap taskListHashMap, ArrayList<Task> taskList, ArrayList<String> listOfKeys) {
        //creates TaskLists with the name of each unique key
        //stores Tasks in the given ArrayList
        hashMapReconstructor.loadIntoHashMap(taskListHashMap, taskList, listOfKeys);
    }*/

/*
    //EFFECTS: If the year found in the loaded input matches the current year, then
    //              -If the due date is before the current date then create a completed task from the given info
    //              -else create a task from the given info and sets the time until using the current year
    //         else create an important task from the given info and sets the time until due using the next year.
    //         Stores created tasks in the given taskList.
    void createTaskSetYearFromLoad(ArrayList<String> partsOfLine, ArrayList<Task> taskList, String taskType)
            throws TaskException {
        taskReconstructor.createTaskSetYearFromLoad(partsOfLine, taskList, taskType);
    }

    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new completed task
    //         if the given information of an important task shows it is past due
    //         Stores the created task in the list of tasks.
    void createPastDueFromLoad(ArrayList<Task> taskList) throws TaskException {
        //taskList.storeTask(completedTask);
        taskReconstructor.createPastDueFromLoad(taskList);
    }

    //MODIFIES: this
    //EFFECTS: Loads information from the list partsOfLine into the temporary variables of:
    //         task content, urgency, and due date
    void setGeneralTaskField(ArrayList<String> partsOfLine) {
        taskReconstructor.setGeneralTaskField(partsOfLine);
    }*/
}
