package io;

import exceptions.TaskException;
import model.CompletedTask;
import model.IncompleteTask;
import model.Task;

import java.time.LocalDate;
import java.util.ArrayList;

class TaskReconstructor {

    private static final int starredIndex = 7;
    //private static final int taskImportanceIndex = 7;
    private static final int completionStatusIndex = 6;
    private static final int taskUrgencyIndex = 6;
    private static final int yearIndex = 5;
    private static final int dayIndex = 4;
    private static final int monthIndex = 3;
    private static final int taskContentIndex = 1;

    //static final String importantTaskIdentifier = "*";
    static final String incompleteTaskIdentifier = "@";
    static final String completedTaskIdentifier = "#";

    private String taskContent;
    private LocalDate taskDueDate;
    private String completionStatus;
    private Boolean starred;

    //EFFECTS: If the year found in the loaded input matches the current year, then
    //              -If the due date is before the current date then create a completed task from the given info
    //              -else create a task from the given info and sets the time until using the current year
    //         else create an important task from the given info and sets the time until due using the next year.
    //         Stores created tasks in the given taskList.
    void createTaskSetYear(ArrayList<String> partsOfLine, ArrayList<Task> taskList, String taskType)
            throws TaskException {
        if (taskDueDate.isBefore(LocalDate.now())) {
            createPastDueTask(taskList);
        } else {
            createIncompleteTask(partsOfLine, taskList);
            ((IncompleteTask) taskList.get(taskList.size() - 1)).setTimeLeft();
        }
    }

    //from above
    /*            if (taskType.equals(importantTaskIdentifier)) {
                createImportantTask(partsOfLine, taskList);
            } else {
                createIncompleteTask(partsOfLine, taskList);
            }*/
    //fetches and updates most recent/the last entry in the list

    //EFFECTS: returns true if given identifier equals importantTaskIdentifier or incompleteTaskIdentifier
    private boolean checkIdentifier(String identifier) {
        //return identifier.equals(importantTaskIdentifier) || identifier.equals(incompleteTaskIdentifier);
        return identifier.equals(incompleteTaskIdentifier);
    }

    //EFFECTS: creates tasks from information in the save file
    void createTasks(ArrayList<Task> taskList, ArrayList<String> partsOfLine, String identifier)
            throws TaskException {
        if (checkIdentifier(identifier)) {
            setGeneralTaskField(partsOfLine);
            createTaskSetYear(partsOfLine, taskList, identifier);
        } else {
            createCompletedTask(partsOfLine, taskList);
        }
    }

    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new completed task
    //         if the given information of an important task shows it is past due
    //         Stores the created task in the list of tasks.
    void createPastDueTask(ArrayList<Task> taskList) throws TaskException {
        taskList.add(new CompletedTask(null, taskContent, taskDueDate, CompletedTask.pastDue));
        //taskList.storeTask(completedTask);
    }

    //MODIFIES: this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new completed task.
    //         Stores the created task in the list of tasks.
    private void createCompletedTask(ArrayList<String> partsOfLine, ArrayList<Task> taskList)
            throws TaskException {
        setCompletedTaskField(partsOfLine);
        taskList.add(new CompletedTask(null, taskContent, taskDueDate, completionStatus));
    }

    //MODIFIES: this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new incomplete task.
    //         Stores the created task in the list of tasks.
    private void createIncompleteTask(ArrayList<String> partsOfLine, ArrayList<Task> taskList)
            throws TaskException {
        setIncompleteTaskField(partsOfLine);
        taskList.add(new IncompleteTask(null, taskContent, taskDueDate, partsOfLine.get(taskUrgencyIndex), starred));
    }

/*    //MODIFIES: this
    //EFFECTS: Uses the given information stored in the list partsOfLine to create a new important task.
    //         Stores the created task in the list of tasks.
    private void createImportantTask(ArrayList<String> partsOfLine, ArrayList<Task> taskList)
            throws TaskException {
        taskList.add(new ImportantTask(
                null,
                taskContent,
                taskDueDate,
                partsOfLine.get(taskUrgencyIndex),
                partsOfLine.get(taskImportanceIndex)));
    }*/

    //MODIFIES: this
    //EFFECTS: Loads information from the list partsOfLine into the temporary variables of:
    //         task content, urgency, and due date
    void setGeneralTaskField(ArrayList<String> partsOfLine) {
        taskContent = partsOfLine.get(taskContentIndex);
        int month = Integer.parseInt(partsOfLine.get(monthIndex));
        int day = Integer.parseInt(partsOfLine.get(dayIndex));
        int year = Integer.parseInt(partsOfLine.get(yearIndex));
        taskDueDate = LocalDate.of(year, month, day);
    }

    private void setIncompleteTaskField(ArrayList<String> partsOfLine) {
        setGeneralTaskField(partsOfLine);
        starred = Boolean.parseBoolean(partsOfLine.get(starredIndex));
    }

    //MODIFIES: this
    //EFFECTS: Loads information from the list partsOfLine into the temporary variables of:
    //         task content, due date and completion status
    private void setCompletedTaskField(ArrayList<String> partsOfLine) {
        setGeneralTaskField(partsOfLine);
        completionStatus = partsOfLine.get(completionStatusIndex);
    }
}