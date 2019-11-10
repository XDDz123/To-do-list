package model.tasklist;

import exceptions.TaskException;
import model.task.CompletedTask;
import model.task.IncompleteTask;
import model.task.Task;

import java.util.ArrayList;

class TaskListFilter {

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
    ArrayList<Task> getTaskByUrgency(String urgency, TaskList taskList) {
        ArrayList<Task> tempList = new ArrayList<>();
        for (Task task : filterOutCompleted(taskList)) {
            if (((IncompleteTask) task).getUrgency().getString().equalsIgnoreCase(urgency)) {
                tempList.add(task);
            }
        }
        return tempList;
    }
}