package model;

import exceptions.TaskException;

import java.util.ArrayList;

class TaskListSorter {

    //EFFECTS: takes in an list of tasks, returns a new list with only incomplete tasks.
    ArrayList<Task> filterOutCompleted(TaskList taskList) {
        ArrayList<Task> filteredList = new ArrayList<>();
        for (Task task : taskList.getTaskList()) {
            if (!(task instanceof CompletedTask)) {
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    //EFFECTS: Returns a new task list that contains all tasks in the current task list
    //         with the specified urgency level.
    TaskList getTaskByUrgency(String urgency, TaskList taskList) throws TaskException {
        TaskList tempList = new TaskList("");
        for (Task task : filterOutCompleted(taskList)) {
            if (((IncompleteTask) task).getUrgency().equalsIgnoreCase(urgency)) {
                tempList.storeTask(task);
            }
        }
        return tempList;
    }


    //inspired by post by user zb226 @ https://stackoverflow.com/questions/16252269/how-to-sort-an-arraylist
    //            and https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
    //MODIFIES: this
    //EFFECTS: Sorts the current task list chronologically based on due dates. Starts from most recently due.
    void sortByDueDate(TaskList taskList) {
        taskList.getTaskList().sort((a, b) -> {
            if (a instanceof CompletedTask && b instanceof CompletedTask) {
                return 0;
            } else if (a instanceof CompletedTask) {
                return 1;
            } else if (b instanceof CompletedTask) {
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
    }
}