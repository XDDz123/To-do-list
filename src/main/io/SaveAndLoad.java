package io;

import model.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveAndLoad implements Loadable, Savable {

    private String taskContent;
    private String taskUrgency;
    private LocalDate taskDueDate;
    private String completionStatus;

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

            if (partsOfLine.get(0).equals("*") || partsOfLine.get(0).equals("@")) {
                setGeneralTaskField(partsOfLine);
                createTaskSetYearFromLoad(partsOfLine, taskList, partsOfLine.get(0));
            } else {
                createCompletedTaskFromLoad(partsOfLine, taskList);
            }
        }
    }

    //MODIFIES: taskList
    //EFFECTS: If the year found in the loaded input matches the current year, then
    //              -If the due date is before the current date then create a completed task from the given info
    //              -else create a task from the given info and sets the time until using the current year
    //         else create an important task from the given info and sets the time until due using the next year.
    //         Stores created tasks in the given taskList.
    void createTaskSetYearFromLoad(ArrayList<String> partsOfLine, TaskList taskList, String taskType) {
        if (taskDueDate.isBefore(LocalDate.now())) {
            createPastDueFromLoad(taskList);
        } else {
            if (taskType.equals("*")) {
                createImportantTaskFromLoad(partsOfLine, taskList);
            } else {
                createRegularTaskFromLoad(partsOfLine, taskList);
            }
            ((RegularTask) taskList.getTask(taskList.getTaskListSize())).setTimeLeft();
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new completed task
    //         if the given information of an important task shows it is past due
    //         Stores the created task in the list of tasks.
    void createPastDueFromLoad(TaskList taskList) {
        CompletedTask completedTask = new CompletedTask(taskContent, taskDueDate, "past due.");
        taskList.storeTask(completedTask);
    }

    //MODIFIES: taskList
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new completed task.
    //         Stores the created task in the list of tasks.
    private void createCompletedTaskFromLoad(ArrayList<String> partsOfLine, TaskList taskList) {
        setCompletedTaskField(partsOfLine);
        taskList.storeTask(new CompletedTask(taskContent, taskDueDate, completionStatus));
    }

    //MODIFIES: taskList
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new regular task.
    //         Stores the created task in the list of tasks.
    private void createRegularTaskFromLoad(ArrayList<String> partsOfLine, TaskList taskList) {
        taskUrgency = partsOfLine.get(5);
        setGeneralTaskField(partsOfLine);
        taskList.storeTask(new RegularTask(taskContent, taskDueDate, taskUrgency));
    }

    //MODIFIES: taskList, this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new important task.
    //         Stores the created task in the list of tasks.
    private void createImportantTaskFromLoad(ArrayList<String> partsOfLine, TaskList taskList) {
        taskUrgency = partsOfLine.get(5);
        taskList.storeTask(new ImportantTask(taskContent, taskDueDate, taskUrgency, partsOfLine.get(6)));
    }

    //MODIFIES: this
    //EFFECTS: Loads information from the list partsOfLine into the temporary variables of:
    //         task content, urgency, and due date
    void setGeneralTaskField(ArrayList<String> partsOfLine) {
        taskContent = partsOfLine.get(1);
        int month = Integer.parseInt(partsOfLine.get(2));
        int day = Integer.parseInt(partsOfLine.get(3));
        int year = Integer.parseInt(partsOfLine.get(4));
        taskDueDate = LocalDate.of(year, month, day);
    }

    //MODIFIES: this
    //EFFECTS: Loads information from the list partsOfLine into the temporary variables of:
    //         task content, due date and completion status
    private void setCompletedTaskField(ArrayList<String> partsOfLine) {
        setGeneralTaskField(partsOfLine);
        completionStatus = partsOfLine.get(5);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //REQUIRES: save.txt to exist
    //MODIFIES: save.txt
    //EFFECTS: Takes current task list and writes the included information in the following format to save.txt.
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    @Override
    public void save(TaskList taskList, String file) throws IOException {
        PrintWriter writer = new PrintWriter(file,"UTF-8");

        for (int i = 1; i <= taskList.getTaskListSize(); i++) {
            if (taskList.getTask(i) instanceof ImportantTask) {
                writer.println(formatImportantTaskInfo(taskList, i));
            } else if (taskList.getTask(i) instanceof RegularTask) {
                writer.println(formatRegularTaskInfo(taskList, i));
            } else {
                writer.println(formatCompletedTaskInfo(taskList, i));
            }
        }
        writer.close();
    }

    //EFFECTS: Returns the task due date with added symbols in the following format
    private String formatTaskDueDateInfo(TaskList taskList, int i) {
        return taskList.getTask(i).getDueDateObj().getMonthValue() + "~"
                + taskList.getTask(i).getDueDateObj().getDayOfMonth() + "~"
                + taskList.getTask(i).getDueDateObj().getYear();
    }

    //EFFECTS: Returns the task content with added symbols in the following format
    private String formatGeneralTaskInfo(TaskList taskList, int i) {
        return taskList.getTask(i).getContent() + "~"
                + formatTaskDueDateInfo(taskList, i) + "~"
                + ((RegularTask) taskList.getTask(i)).getUrgency();
    }

    //EFFECTS: Returns information in an important task in the following format
    private String formatImportantTaskInfo(TaskList taskList, int i) {
        return "*" + "~" + formatGeneralTaskInfo(taskList, i) + "~"
                + ((ImportantTask) taskList.getTask(i)).getImportance();
    }

    //EFFECTS: Returns information in a regular task in the following format
    private String formatRegularTaskInfo(TaskList taskList, int i) {
        return "@" + "~" + formatGeneralTaskInfo(taskList, i);
    }

    //EFFECTS: Returns information in a completed task in the following format
    private String formatCompletedTaskInfo(TaskList taskList, int i) {
        return "#" + "~" + taskList.getTask(i).getContent() + "~"
                + formatTaskDueDateInfo(taskList, i) + "~"
                + ((CompletedTask) taskList.getTask(i)).getCompletionStatus();
    }

    //MODIFIES: save.txt
    //EFFECTS: Clears/formats the current save file
    public void clearSave(String fileName, TaskList taskList) throws IOException {
        File file = new File(fileName);
        file.delete();
        file.createNewFile();
        taskList.clearTaskList();
    }

    //EFFECTS: returns a new list of strings with sub-strings separated from the given string
    //         Sub-strings in the givens string is separated by a "~"
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    ArrayList<String> separateOnTilde(String line) {
        String[] partOfLine = line.split("~");
        return new ArrayList<>(Arrays.asList(partOfLine));
    }
}
