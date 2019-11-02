package io;

import exceptions.TaskException;
import model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaveAndLoad implements Loadable, Savable {

    private String taskContent;
    private String taskUrgency;
    private LocalDate taskDueDate;
    private String completionStatus;

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
            ArrayList<String> partsOfLine = separateOnTilde(line);

            if (partsOfLine.get(0).equals("*") || partsOfLine.get(0).equals("@")) {
                setGeneralTaskField(partsOfLine);
                createTaskSetYearFromLoad(partsOfLine, taskList, partsOfLine.get(0));
            } else {
                createCompletedTaskFromLoad(partsOfLine, taskList);
            }

            listOfKeys.add(partsOfLine.get(2));
        }
        loadIntoHashMap(taskListHashMap, taskList, listOfKeys);
    }

    //MODIFIES: taskListHashMap
    //EFFECTS: Creates TaskLists for each key and maps them into the HashMap.
    //         Stores the given list of tasks using the given list of keys in their
    //         respective TaskList (with the same name) in the given HashMap.
    //         Tasks and keys are mapped linearly, with index to index, i.e. task 0 --> key 0.
    void loadIntoHashMap(TaskListHashMap taskListHashMap, ArrayList<Task> taskList, ArrayList<String> listOfKeys) {
        //creates TaskLists with the name of each unique key
        for (String key: eliminateDuplicates(listOfKeys)) {
            TaskList taskListTemp = new TaskList(key);
            taskListHashMap.storeTaskList(taskListTemp);
        }

        //stores Tasks in the given ArrayList
        for (int i = 0; i < listOfKeys.size(); i++) {
            try {
                taskListHashMap.getTaskList(listOfKeys.get(i)).storeTask(taskList.get(i));
            } catch (TaskException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //EFFECTS: Returns a new list of strings by eliminating any duplicate strings in the given list of strings.
    //inspired by https://www.geeksforgeeks.org/how-to-remove-duplicates-from-arraylist-in-java/
    List<String> eliminateDuplicates(ArrayList<String> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    //EFFECTS: If the year found in the loaded input matches the current year, then
    //              -If the due date is before the current date then create a completed task from the given info
    //              -else create a task from the given info and sets the time until using the current year
    //         else create an important task from the given info and sets the time until due using the next year.
    //         Stores created tasks in the given taskList.
    void createTaskSetYearFromLoad(ArrayList<String> partsOfLine, ArrayList<Task> taskList, String taskType)
            throws TaskException {
        if (taskDueDate.isBefore(LocalDate.now())) {
            createPastDueFromLoad(taskList);
        } else {
            if (taskType.equals("*")) {
                createImportantTaskFromLoad(partsOfLine, taskList);
            } else {
                createIncompleteTaskFromLoad(partsOfLine, taskList);
            }
            ((IncompleteTask) taskList.get(taskList.size() - 1)).setTimeLeft();
        }
    }

    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new completed task
    //         if the given information of an important task shows it is past due
    //         Stores the created task in the list of tasks.
    void createPastDueFromLoad(ArrayList<Task> taskList) throws TaskException {
        taskList.add(new CompletedTask(null, taskContent, taskDueDate, "past due."));
        //taskList.storeTask(completedTask);
    }

    //MODIFIES: this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new completed task.
    //         Stores the created task in the list of tasks.
    private void createCompletedTaskFromLoad(ArrayList<String> partsOfLine,  ArrayList<Task> taskList)
            throws TaskException {
        setCompletedTaskField(partsOfLine);
        taskList.add(new CompletedTask(null, taskContent, taskDueDate, completionStatus));
        //taskList.storeTask(new CompletedTask(taskList, taskContent, taskDueDate, completionStatus));
    }

    //MODIFIES: this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new incomplete task.
    //         Stores the created task in the list of tasks.
    private void createIncompleteTaskFromLoad(ArrayList<String> partsOfLine, ArrayList<Task> taskList)
            throws TaskException {
        taskUrgency = partsOfLine.get(6);
        setGeneralTaskField(partsOfLine);
        taskList.add(new IncompleteTask(null, taskContent, taskDueDate, taskUrgency));
        //taskList.storeTask(new IncompleteTask(taskList, taskContent, taskDueDate, taskUrgency));
    }

    //MODIFIES: this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new important task.
    //         Stores the created task in the list of tasks.
    private void createImportantTaskFromLoad(ArrayList<String> partsOfLine, ArrayList<Task> taskList)
            throws TaskException {
        taskUrgency = partsOfLine.get(6);
        taskList.add(new ImportantTask(null, taskContent, taskDueDate, taskUrgency, partsOfLine.get(7)));
        //taskList.storeTask(new ImportantTask(taskList, taskContent, taskDueDate, taskUrgency, partsOfLine.get(6)));
    }

    //MODIFIES: this
    //EFFECTS: Loads information from the list partsOfLine into the temporary variables of:
    //         task content, urgency, and due date
    void setGeneralTaskField(ArrayList<String> partsOfLine) {
        taskContent = partsOfLine.get(1);
        int month = Integer.parseInt(partsOfLine.get(3));
        int day = Integer.parseInt(partsOfLine.get(4));
        int year = Integer.parseInt(partsOfLine.get(5));
        taskDueDate = LocalDate.of(year, month, day);
    }

    //MODIFIES: this
    //EFFECTS: Loads information from the list partsOfLine into the temporary variables of:
    //         task content, due date and completion status
    private void setCompletedTaskField(ArrayList<String> partsOfLine) {
        setGeneralTaskField(partsOfLine);
        completionStatus = partsOfLine.get(6);
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

        for (Object key : taskListHashMap.getKeys()) {
            TaskList taskList = taskListHashMap.getTaskList((String) key);
            for (int i = 1; i <= taskList.getTaskListSize(); i++) {
                if (taskList.getTask(i) instanceof ImportantTask) {
                    writer.println(formatImportantTaskInfo(taskList, i));
                } else if (taskList.getTask(i) instanceof IncompleteTask) {
                    writer.println(formatIncompleteTaskInfo(taskList, i));
                } else {
                    writer.println(formatCompletedTaskInfo(taskList, i));
                }
            }
        }

        writer.close();
    }

    //EFFECTS: Returns the task due date with added symbols in the following format
    private String formatTaskDueDateInfo(TaskList taskList, int i) {
        return taskList.getName() + "~"
                + taskList.getTask(i).getDueDateObj().getMonthValue() + "~"
                + taskList.getTask(i).getDueDateObj().getDayOfMonth() + "~"
                + taskList.getTask(i).getDueDateObj().getYear();
    }

    //EFFECTS: Returns the task content with added symbols in the following format
    private String formatGeneralTaskInfo(TaskList taskList, int i) {
        return taskList.getTask(i).getContent() + "~"
                + formatTaskDueDateInfo(taskList, i) + "~"
                + ((IncompleteTask) taskList.getTask(i)).getUrgency();
    }

    //EFFECTS: Returns information in an important task in the following format
    private String formatImportantTaskInfo(TaskList taskList, int i) {
        return "*" + "~" + formatGeneralTaskInfo(taskList, i) + "~"
                + ((ImportantTask) taskList.getTask(i)).getImportance();
    }

    //EFFECTS: Returns information in a incomplete task in the following format
    private String formatIncompleteTaskInfo(TaskList taskList, int i) {
        return "@" + "~" + formatGeneralTaskInfo(taskList, i);
    }

    //EFFECTS: Returns information in a completed task in the following format
    private String formatCompletedTaskInfo(TaskList taskList, int i) {
        return "#" + "~" + taskList.getTask(i).getContent() + "~"
                + formatTaskDueDateInfo(taskList, i) + "~"
                + ((CompletedTask) taskList.getTask(i)).getCompletionStatus();
    }
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

    //EFFECTS: returns a new list of strings with sub-strings separated from the given string
    //         Sub-strings in the givens string is separated by a "~"
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    ArrayList<String> separateOnTilde(String line) {
        String[] partOfLine = line.split("~");
        return new ArrayList<>(Arrays.asList(partOfLine));
    }
}
