package io;

import exceptions.TaskException;
import model.CompletedTask;
import model.IncompleteTask;
import model.Task;
import model.TaskListHashMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Load {

    static final String separator = "~";
    private static final int identifierIndex = 0;
    private static final int keyIndex = 2;
    private final HashMapReconstructor hashMapReconstructor = new HashMapReconstructor();
    //private final TaskReconstructor taskReconstructor = new TaskReconstructor();

    //REQUIRES: save.txt to exist and follow the expected format
    //MODIFIES: taskList
    //EFFECTS: Reads information from save file to create tasks with said information
    //         Adds these tasks to the given HashMap, a HashMap of lists of tasks.
    //         "*" denotes an important task
    //         "@" denotes an incomplete task
    //         else task is completed task, also denoted by "#"
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3 (example given on edx)
    //inspired by https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    //inspired by https://www.geeksforgeeks.org/customized-serialization-and-deserialization-in-java/
    public void load(TaskListHashMap taskListHashMap, String saveFile, String keyList)
            throws IOException, TaskException, NumberFormatException, ClassNotFoundException {

        List<String> lines = Files.readAllLines(Paths.get(keyList));
        ArrayList<Task> taskList = new ArrayList<>();

        FileInputStream fis = new FileInputStream(saveFile);
        ObjectInputStream ois = new ObjectInputStream(fis);

        ArrayList<String> listOfKeys = new ArrayList<>(lines);
        System.out.println(listOfKeys);


        createTasks(taskList, ois);

        ois.close();
        fis.close();

        hashMapReconstructor.loadIntoHashMap(taskListHashMap, taskList, listOfKeys);
    }

    private void createTasks(ArrayList<Task> taskList, ObjectInputStream ois) {
        try {
            while (true) {
                Task task = (Task) ois.readObject();
                if (task.getDueDateObj().isBefore(LocalDate.now()) && task instanceof IncompleteTask) {
                    task = new CompletedTask(null, task.getContent(), task.getDueDateObj(), CompletedTask.pastDue);
                }
                taskList.add(task);

                if (task instanceof IncompleteTask) {
                    ((IncompleteTask) task).setTimeLeft();
                }
            }
        } catch (Exception e) {
            System.out.println(taskList);
            System.out.println("loaded");
        }
    }
/*
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
    }*/
}
