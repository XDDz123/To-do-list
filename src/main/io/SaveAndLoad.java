package io;

import model.Task;
import model.TaskList;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveAndLoad implements Loadable, Savable {

    private String taskContent;
    private String taskUrgency;
    private MonthDay taskDueDate;
    private boolean taskStatus;
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

            Task task = new Task();
            taskContent = partsOfLine.get(0);
            taskUrgency = partsOfLine.get(1);
            month = Integer.parseInt(partsOfLine.get(2));
            day = Integer.parseInt(partsOfLine.get(3));
            taskDueDate = MonthDay.of(month, day);
            taskStatus = Boolean.parseBoolean(partsOfLine.get(4));
            setTask(task);
            taskList.storeTask(task);
        }
    }

    //REQUIRES: save.txt to exist
    //MODIFIES: save.txt
    //EFFECTS: Takes current task list and writes the included information in the following format to save.txt.
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3
    @Override
    public void save(TaskList taskList, String file) throws IOException {
        PrintWriter writer = new PrintWriter(file,"UTF-8");

        for (int i = 1; i <= taskList.getTaskListSize(); i++) {
            writer.println(taskList.getTask(i).getContent() + "/"
                            + taskList.getTask(i).getUrgency() + "/"
                            + taskList.getTask(i).getDueDateObj().getMonthValue() + "/"
                            + taskList.getTask(i).getDueDateObj().getDayOfMonth() + "/"
                            + taskList.getTask(i).getStatus());
        }

        writer.close();
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
        String[] partOfLine = line.split("/");
        return new ArrayList<>(Arrays.asList(partOfLine));
    }

    //MODIFIES: task
    //EFFECTS: sets the fields of the given tasks to current values.
    public void setTask(Task task) {
        task.setContent(taskContent);
        task.setUrgency(taskUrgency);
        task.setDueDate(taskDueDate);
        task.setStatus(taskStatus);
    }
}
