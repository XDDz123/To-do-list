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
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    @Override
    public void load(TaskList taskList, String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file));

        for (String line : lines) {
            ArrayList<String> partsOfLine = separateOnSlash(line);

            if (checkFirstElement(partsOfLine, "*")) {
                setIncompleteTaskField(partsOfLine);

                if (Integer.parseInt(partsOfLine.get(partsOfLine.size() - 1)) == Year.now().getValue()) {
                    if (taskDueDate.isBefore(MonthDay.now())) {
                        setImportantTaskToPastDue(taskList);
                    } else {
                        createImportantTaskFromLoad(partsOfLine, taskList);
                    }
                }
            } else if (checkFirstElement(partsOfLine, "@")) {
                createRegularTaskFromLoad(partsOfLine, taskList);
            } else if (checkFirstElement(partsOfLine, "#")) {
                createCompletedTaskFromLoad(partsOfLine, taskList);
            }
        }
    }

    public boolean checkFirstElement(ArrayList<String> partsOfLine, String str) {
        return partsOfLine.get(0).equals(str);
    }

    public void createCompletedTaskFromLoad(ArrayList<String> partsOfLine, TaskList taskList) {
        setCompleteTaskField(partsOfLine);

        CompletedTask completedTask = new CompletedTask(taskContent, taskDueDate, completionStatus);
        taskList.storeTask(completedTask);
    }

    public void createRegularTaskFromLoad(ArrayList<String> partsOfLine, TaskList taskList) {
        setIncompleteTaskField(partsOfLine);

        RegularTask regularTask = new RegularTask(taskContent, taskDueDate, taskUrgency);
        taskList.storeTask(regularTask);
    }

    public void createImportantTaskFromLoad(ArrayList<String> partsOfLine, TaskList taskList) {
        taskImportance = partsOfLine.get(5);
        ImportantTask importantTask;
        importantTask = new ImportantTask(taskContent, taskDueDate, taskUrgency, taskImportance);
        importantTask.setTimeLeft();
        taskList.storeTask(importantTask);
    }

    public void setImportantTaskToPastDue(TaskList taskList) {
        CompletedTask completedTask = new CompletedTask(taskContent, taskDueDate, "past due.");
        taskList.storeTask(completedTask);
    }

    public void setIncompleteTaskField(ArrayList<String> partsOfLine) {
        taskContent = partsOfLine.get(1);
        taskUrgency = partsOfLine.get(2);
        month = Integer.parseInt(partsOfLine.get(3));
        day = Integer.parseInt(partsOfLine.get(4));
        taskDueDate = MonthDay.of(month, day);
    }

    public void setCompleteTaskField(ArrayList<String> partsOfLine) {
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
            } else if (taskList.getTask(i) instanceof CompletedTask) {
                writer.println(formatCompletedTaskInfo(taskList, i));
            }
        }
        writer.close();
    }

    public String getTaskInfo(TaskList taskList, int i) {
        return taskList.getTask(i).getContent() + "~"
                + ((RegularTask) taskList.getTask(i)).getUrgency() + "~"
                + getTaskDueDateInfo(taskList, i);
    }

    public String getTaskDueDateInfo(TaskList taskList, int i) {
        return taskList.getTask(i).getDueDateObj().getMonthValue() + "~"
                + taskList.getTask(i).getDueDateObj().getDayOfMonth();
    }

    public String formatCompletedTaskInfo(TaskList taskList, int i) {
        return "#" + "~"
                + taskList.getTask(i).getContent() + "~"
                + getTaskDueDateInfo(taskList, i) + "~"
                + ((CompletedTask) taskList.getTask(i)).getCompletionStatus();
    }

    public String formatImportantTaskInfo(TaskList taskList, int i) {
        return "*" + "~" + getTaskInfo(taskList, i) + "~" + ((ImportantTask) taskList.getTask(i)).getImportance() + "~";
    }

    public String formatRegularTaskInfo(TaskList taskList, int i) {
        return "@" + "~" + getTaskInfo(taskList, i);
    }

    //MODIFIES: save.txt
    //EFFECTS: Clears/formats the current save file
    public void clearSave(String fileName) throws IOException {
        File file = new File(fileName);
        file.delete();
        file.createNewFile();
    }

    //EFFECTS: returns a new list of strings with sub-strings separated from the given string
    //         Sub-strings in the givens string is separated by a "/"
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    public ArrayList<String> separateOnSlash(String line) {
        String[] partOfLine = line.split("~");
        return new ArrayList<>(Arrays.asList(partOfLine));
    }
}
