package model;

import exceptions.EmptyListException;

import java.util.ArrayList;

class TaskListToString {

    //EFFECTS: Prints the contents of incomplete tasks in the list
    String printIncompleteTasks(TaskList taskList) {
        try {
            return printTaskList(taskList.filterOutCompleted(), taskList);
        } catch (EmptyListException e) {
            return e.getMessage();
        }
    }

    //EFFECTS: Prints the contents of the current task list
    String printTaskList(TaskList taskList) {
        try {
            return printTaskList(taskList.getTaskList(),  taskList);
        } catch (EmptyListException e) {
            return e.getMessage();
        }
    }

    //EFFECTS: Returns "No tasks found." if current task list is empty, returns all tasks in the current task list ow.
    private String printTaskList(ArrayList<Task> list, TaskList taskList) throws EmptyListException {
        StringBuilder taskListPrint = new StringBuilder();

        if (taskList.isTaskListEmpty()) {
            throw new EmptyListException();
        } else {
            for (int i = 0; i < list.size(); i++) {
                taskListPrint.append(i + 1).append(" : ").append((list.get(i)).printTask()).append("\n");
            }
            //substring to -1 removes the last line break
            return taskListPrint.substring(0, taskListPrint.length() - 1);
        }
    }
}