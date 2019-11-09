package io;

import exceptions.TaskException;
import model.*;
import model.task.CompletedTask;
import model.task.IncompleteTask;
import model.task.Task;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Load {

    private final HashMapReconstructor hashMapReconstructor = new HashMapReconstructor();

    //MODIFIES: taskList
    //EFFECTS: Reads information (de-serializes) from save file to create tasks with said information
    //         Adds these tasks to the given HashMap, a HashMap of lists of tasks.
    //inspired by https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    //inspired by https://www.geeksforgeeks.org/customized-serialization-and-deserialization-in-java/
    //inspired by https://drive.google.com/open?id=1hA9g_u-N0K0ZEzxBMYXl6IzEyoXSo4m3 (example given on edx)
    public void load(TaskListHashMap taskListHashMap, String saveFile)
            throws IOException, TaskException, NumberFormatException, ClassNotFoundException {

        FileInputStream fileInputStream = new FileInputStream(saveFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        ArrayList<Task> taskList = new ArrayList<>();
        ArrayList<String> listOfKeys = new ArrayList<>();
        populateTaskList(taskList, objectInputStream, listOfKeys);

        objectInputStream.close();
        fileInputStream.close();

        hashMapReconstructor.loadIntoHashMap(taskListHashMap, taskList, listOfKeys);
    }

    //MODIFIES: taskList
    //EFFECTS: Loops until save file is out of tasks to de-serialize or errors are encountered during de-serialization
    private void populateTaskList(ArrayList<Task> taskList, ObjectInputStream objectInputStream,
                                  ArrayList<String> listOfKeys)
            throws TaskException, ClassNotFoundException {
        boolean run = true;
        while (run) {
            run = createTask(taskList, objectInputStream, listOfKeys);
        }
    }

    //MODIFIES: taskList
    //EFFECTS: De-serializes a task object from save file
    //         If save file is out of de-serializable data, catch NullPointerException or IOException (EOFException)
    //         and return false.
    //         If saved data is not de-serializable, catch ClassNotFoundException, return false.
    //         If there are too many tasks already in the list, catch TaskException, return false.
    //inspired by https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    //inspired by https://www.geeksforgeeks.org/customized-serialization-and-deserialization-in-java/
    private boolean createTask(ArrayList<Task> taskList, ObjectInputStream objectInputStream,
                               ArrayList<String> listOfKeys)
            throws TaskException, ClassNotFoundException {
        try {
            readTaskObject(taskList, objectInputStream, listOfKeys);
        } catch (NullPointerException | IOException e) {
            return false;
        }
        return true;
    }

    //MODIFIES: taskList, task
    //EFFECTS: Recreates a task object from de-serializing data from the save file
    //         Adds this task to the taskList if the task's due date is not before the current time
    //         Otherwise creates a past due completed task with identical information and adds this
    //         completed task to taskList. If this task is an incomplete task, set/update time left
    //         for this task.
    private void readTaskObject(ArrayList<Task> taskList, ObjectInputStream objectInputStream,
                                ArrayList<String> listOfKeys)
            throws IOException, ClassNotFoundException, TaskException {
        Task task = (Task) objectInputStream.readObject();
        listOfKeys.add(task.getKey());
        //check if task is past due
        task = checkTaskPastDue(task);
        //adds task to list
        taskList.add(task);
    }

    //MODIFIES: task
    //EFFECTS: checks whether the given task's due date has already passed, if past due, then return a completed tasks
    //         created from the given task's information, ow return the original task.
    private Task checkTaskPastDue(Task task) throws TaskException {
        if (task.getDueDateObj().isBefore(LocalDate.now()) && task instanceof IncompleteTask) {
            task = new CompletedTask(null, task.getContent(), task.getDueDateObj(), CompletedTask.pastDue);
        }
        return task;
    }
}