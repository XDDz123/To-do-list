package io;

import model.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveAndLoad implements Loadable, Savable {

    private String taskContent;
    private String taskUrgency;
    private MonthDay taskDueDate;
    private String taskImportance;
    private String completionStatus;

    private int month;
    private int day;

    //REQUIRES: save.txt to exist and follow the expected format
    //MODIFIES: taskList
    //EFFECTS: Reads information from save file to create tasks with said information
    //         Adds these tasks to the taskList, the list of tasks.
    //         "*" denotes an important task
    //         "@" denotes a regular task
    //         else task is completed task, also denoted by "#"
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    @Override
    public void load(TaskList taskList, String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file));

        for (String line : lines) {
            ArrayList<String> partsOfLine = separateOnTilde(line);

            if (checkFirstElement(partsOfLine, "*")) {
                setIncompleteTaskField(partsOfLine);
                checkImportantTaskLoad(partsOfLine, taskList);
            } else if (checkFirstElement(partsOfLine, "@")) {
                createRegularTaskFromLoad(partsOfLine, taskList);
            } else {
                createCompletedTaskFromLoad(partsOfLine, taskList);
            }
        }
    }

    //MODIFIES: taskList
    //EFFECTS: If the year found in the loaded input matches the current year, then
    //              -If the due date is before the current date then create a completed task from the given info
    //              -else create an important task from the given info and sets the time until due for the important
    //               using the current year
    //         else create an important task from the given info and sets the time until due for the important
    //         using the current year + 1, i.e. next year.
    //         Stores created tasks in the given taskList.
    public void checkImportantTaskLoad(ArrayList<String> partsOfLine, TaskList taskList) {
        if (Integer.parseInt(partsOfLine.get(partsOfLine.size() - 1)) == Year.now().getValue()) {
            if (taskDueDate.isBefore(MonthDay.now())) {
                createPastDueFromImportant(taskList);
            } else {
                createImportantTaskFromLoad(partsOfLine, taskList);
                //fetches the just created important task from the list of tasks and mutates time left
                ((ImportantTask) taskList.getTask(taskList.getTaskListSize())).setTimeLeft(Year.now().getValue());
            }
        } else {
            createImportantTaskFromLoad(partsOfLine, taskList);
            //fetches the just created important task from the list of tasks and mutates time left
            ((ImportantTask) taskList.getTask(taskList.getTaskListSize())).setTimeLeft(Year.now().getValue() + 1);
        }
    }

    //EFFECTS: Checks if the first element of the given list matches the give string.
    //         Returns true if yes, otherwise return false.
    public boolean checkFirstElement(ArrayList<String> partsOfLine, String str) {
        return partsOfLine.get(0).equals(str);
    }

    //MODIFIES: taskList
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new completed task.
    //         Stores the created task in the list of tasks.
    public void createCompletedTaskFromLoad(ArrayList<String> partsOfLine, TaskList taskList) {
        setGeneralTaskField(partsOfLine);

        CompletedTask completedTask = new CompletedTask(taskContent, taskDueDate, completionStatus);
        taskList.storeTask(completedTask);
    }

    //MODIFIES: taskList
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new regular task.
    //         Stores the created task in the list of tasks.
    public void createRegularTaskFromLoad(ArrayList<String> partsOfLine, TaskList taskList) {
        setIncompleteTaskField(partsOfLine);

        RegularTask regularTask = new RegularTask(taskContent, taskDueDate, taskUrgency);
        taskList.storeTask(regularTask);
    }

    //MODIFIES: taskList, this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new important task.
    //         Stores the created task in the list of tasks.
    public void createImportantTaskFromLoad(ArrayList<String> partsOfLine, TaskList taskList) {
        taskImportance = partsOfLine.get(5);
        ImportantTask importantTask;
        importantTask = new ImportantTask(taskContent, taskDueDate, taskUrgency, taskImportance);
        taskList.storeTask(importantTask);
    }

    //MODIFIES: taskList
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new completed task
    //         if the given information of an important task shows it is past due
    //         Stores the created task in the list of tasks.
    public void createPastDueFromImportant(TaskList taskList) {
        CompletedTask completedTask = new CompletedTask(taskContent, taskDueDate, "past due.");
        taskList.storeTask(completedTask);
    }

    //MODIFIES: this
    //EFFECTS: Loads information from the list partsOfLine into the temporary variables of:
    //         task content, urgency, and due date
    public void setIncompleteTaskField(ArrayList<String> partsOfLine) {
        taskContent = partsOfLine.get(1);
        taskUrgency = partsOfLine.get(2);
        month = Integer.parseInt(partsOfLine.get(3));
        day = Integer.parseInt(partsOfLine.get(4));
        taskDueDate = MonthDay.of(month, day);
    }

    //MODIFIES: this
    //EFFECTS: Loads information from the list partsOfLine into the temporary variables of:
    //         task content, due date and completion status
    public void setGeneralTaskField(ArrayList<String> partsOfLine) {
        taskContent = partsOfLine.get(1);
        month = Integer.parseInt(partsOfLine.get(2));
        day = Integer.parseInt(partsOfLine.get(3));
        taskDueDate = MonthDay.of(month, day);
        completionStatus = partsOfLine.get(4);
    }

    //REQUIRES: save.txt to exist
    //MODIFIES: save.txt
    //EFFECTS: Takes current task list and writes the included information in the following format to save.txt.
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    @Override
    public void save(TaskList taskList, String file) throws IOException {
        PrintWriter writer = new PrintWriter(file,"UTF-8");

        for (int i = 1; i <= taskList.getTaskListSize(); i++) {
            if (taskList.getTask(i) instanceof ImportantTask) {
                if (taskList.getTask(i).getDueDateObj().isBefore(MonthDay.now())) {
                    writer.println(formatImportantTaskInfo(taskList, i) + (Year.now().getValue() + 1));
                } else {
                    writer.println(formatImportantTaskInfo(taskList, i)  + Year.now().getValue());
                }
            } else if (taskList.getTask(i) instanceof RegularTask) {
                writer.println(formatRegularTaskInfo(taskList, i));
            } else {
                writer.println(formatCompletedTaskInfo(taskList, i));
            }
        }
        writer.close();
    }

    //EFFECTS: Returns the task content with added symbols in the following format
    public String formatTaskInfo(TaskList taskList, int i) {
        return taskList.getTask(i).getContent() + "~"
                + ((RegularTask) taskList.getTask(i)).getUrgency() + "~"
                + formatTaskDueDateInfo(taskList, i);
    }

    //EFFECTS: Returns the task due date with added symbols in the following format
    public String formatTaskDueDateInfo(TaskList taskList, int i) {
        return taskList.getTask(i).getDueDateObj().getMonthValue() + "~"
                + taskList.getTask(i).getDueDateObj().getDayOfMonth();
    }

    //EFFECTS: Returns information in a completed task in the following format
    public String formatCompletedTaskInfo(TaskList taskList, int i) {
        return "#" + "~"
                + taskList.getTask(i).getContent() + "~"
                + formatTaskDueDateInfo(taskList, i) + "~"
                + ((CompletedTask) taskList.getTask(i)).getCompletionStatus();
    }

    //EFFECTS: Returns information in an important task in the following format
    public String formatImportantTaskInfo(TaskList taskList, int i) {
        return "*" + "~" + formatTaskInfo(taskList, i) + "~"
                + ((ImportantTask) taskList.getTask(i)).getImportance() + "~";
    }

    //EFFECTS: Returns information in a regular task in the following format
    public String formatRegularTaskInfo(TaskList taskList, int i) {
        return "@" + "~" + formatTaskInfo(taskList, i);
    }

    //MODIFIES: save.txt
    //EFFECTS: Clears/formats the current save file
    public void clearSave(String fileName) throws IOException {
        File file = new File(fileName);
        file.delete();
        file.createNewFile();
    }

    //EFFECTS: returns a new list of strings with sub-strings separated from the given string
    //         Sub-strings in the givens string is separated by a "~"
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    public ArrayList<String> separateOnTilde(String line) {
        String[] partOfLine = line.split("~");
        return new ArrayList<>(Arrays.asList(partOfLine));
    }
}
