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
import java.util.List;

public class Load {

    private final HashMapReconstructor hashMapReconstructor = new HashMapReconstructor();

    //MODIFIES: taskList
    //EFFECTS: Reads information (de-serializes) from save file to create tasks with said information
    //         Adds these tasks to the given HashMap, a HashMap of lists of tasks.
    //inspired by https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    //inspired by https://www.geeksforgeeks.org/customized-serialization-and-deserialization-in-java/
    public void load(TaskListHashMap taskListHashMap, String saveFile, String keyList)
            throws IOException, TaskException, NumberFormatException, ClassNotFoundException {

        List<String> lines = Files.readAllLines(Paths.get(keyList));
        ArrayList<Task> taskList = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(saveFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ArrayList<String> listOfKeys = new ArrayList<>(lines);

        populateTaskList(taskList, objectInputStream);

        objectInputStream.close();
        fileInputStream.close();

        hashMapReconstructor.loadIntoHashMap(taskListHashMap, taskList, listOfKeys);
    }

    //MODIFIES: taskList
    //EFFECTS: Loops until save file is out of tasks to de-serialize or errors are encountered during de-serialization
    private void populateTaskList(ArrayList<Task> taskList, ObjectInputStream objectInputStream)
            throws TaskException, ClassNotFoundException {
        boolean run = true;
        while (run) {
            run = createTask(taskList, objectInputStream);
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
    private boolean createTask(ArrayList<Task> taskList, ObjectInputStream objectInputStream)
            throws TaskException, ClassNotFoundException {
        try {
            readTaskObject(taskList, objectInputStream);
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
    private void readTaskObject(ArrayList<Task> taskList, ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException, TaskException {
        Task task = (Task) objectInputStream.readObject();
        //check if task is past due
        task = checkTaskPastDue(task);
        //adds task to list
        taskList.add(task);
        //sets time left for incomplete task
        if (task instanceof IncompleteTask) {
            ((IncompleteTask) task).setTimeLeft();
        }
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