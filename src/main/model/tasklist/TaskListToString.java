package model.tasklist;

import exceptions.EmptyListException;
import model.task.Task;
import java.util.ArrayList;

public class TaskListToString {

    //EFFECTS: Prints the contents of incomplete tasks in the list
    String printIncompleteTasks(TaskList taskList) {
        try {
            return printList(taskList.filterOutCompleted());
        } catch (EmptyListException e) {
            return e.getMessage();
        }
    }

    //EFFECTS: Prints the contents of the current task list
    public String printTaskList(ArrayList<Task> list) {
        try {
            return printList(list);
        } catch (EmptyListException e) {
            return e.getMessage();
        }
    }

    //EFFECTS: Returns "No tasks found." if current task list is empty, returns all tasks in the current task list ow.
    private String printList(ArrayList<Task> list) throws EmptyListException {
        StringBuilder taskListPrint = new StringBuilder();

        if (list.isEmpty()) {
            throw new EmptyListException();
        } else {
            for (int i = 0; i < list.size(); i++) {
                taskListPrint.append(i + 1).append(" : ").append((list.get(i)).printTask()).append("\n");
            }
            //substring to -1 to remove the last line break
            return taskListPrint.substring(0, taskListPrint.length() - 1);
        }
    }
}