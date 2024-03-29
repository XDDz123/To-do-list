package model.tasklist;

import model.task.Task;

import java.util.ArrayList;

class TaskListSorter {

    //inspired by post by user zb226 @ https://stackoverflow.com/questions/16252269/how-to-sort-an-arraylist
    //            and https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
    //MODIFIES: taskList
    //EFFECTS: Sorts the current task list chronologically based on due dates. Starts from most recently due.
    void sortByDueDate(TaskList taskList) {
        taskList.getTaskList().sort((a, b) -> {
            if (a.isCompleted() && b.isCompleted()) {
                return 0;
            } else if (a.isCompleted()) {
                return 1;
            } else if (b.isCompleted()) {
                return -1;
            } else {
                if (a.getDueDateObj().isBefore(b.getDueDateObj())) {
                    return -1;
                } else if (a.getDueDateObj().isAfter(b.getDueDateObj())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        /*taskList.notifyObserver(taskList.getTaskList());*/
    }

    //MODIFIES: taskList
    //EFFECTS: Sorts the current task list chronologically based on due dates. Starts from most recently due.
    void sortByDueDate(TaskList taskList, ArrayList<Task> list) {
        list.sort((a, b) -> {
            if (a.isCompleted() && b.isCompleted()) {
                return 0;
            } else if (a.isCompleted()) {
                return 1;
            } else if (b.isCompleted()) {
                return -1;
            } else {
                if (a.getDueDateObj().isBefore(b.getDueDateObj())) {
                    return -1;
                } else if (a.getDueDateObj().isAfter(b.getDueDateObj())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        taskList.notifyObserver(list);
    }
}