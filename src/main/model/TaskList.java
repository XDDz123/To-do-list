package model;

import exceptions.EmptyListException;
import exceptions.TaskDoesNotExistException;
import exceptions.TooManyIncompleteTasksException;

import java.util.*;

public class TaskList {

    private ArrayList<Task> taskList;
    static final int maxSize = 10;

    //MODIFIES: this
    //EFFECTS: Constructs a new taskList as an ArrayList.
    public TaskList() {
        taskList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Inserts a task to the current task list
    public void storeTask(Task task) throws TooManyIncompleteTasksException {
        if (filterOutCompleted(taskList).size() > maxSize) {
            if (!(task instanceof CompletedTask)) {
                throw new TooManyIncompleteTasksException();
            } else {
                taskList.add(task);
            }
        } else {
            taskList.add(task);
        }
    }

    //EFFECTS: Returns the ArrayList that stores the current task list
    public ArrayList getTaskList() {
        return taskList;
    }

    //EFFECTS: Returns a task store in the current task list based on an index that starts at 1.
    public Task getTask(int index) {
        return taskList.get(index - 1);
    }

    //REQUIRES: index must refer to an existing index in the list of tasks
    //MODIFIES: this
    //EFFECTS: Removes a task in the current task list based on an index that starts at 1.
    public void deleteTask(int index) throws TaskDoesNotExistException {
        try {
            taskList.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskDoesNotExistException();
        }
    }

    //MODIFIES: this
    //EFFECTS: Removes all tasks in the current task list
    public void clearTaskList() {
        taskList.clear();
    }

    //EFFECTS: Returns true if task list is empty, false otherwise.
    public boolean isTaskListEmpty() {
        return taskList.isEmpty();
    }

    //EFFECTS: Returns the size of the current task list
    public int getTaskListSize() {
        return taskList.size();
    }

    //EFFECTS: Prints the contents of incomplete tasks in the list
    public String printIncompleteTasks() {
        try {
            return printTaskList(filterOutCompleted(taskList));
        } catch (EmptyListException e) {
            return e.getMessage();
        }
    }

    //EFFECTS: Prints the contents of the current task list
    public String printTaskList() {
        try {
            return printTaskList(taskList);
        } catch (EmptyListException e) {
            return e.getMessage();
        }
    }

    //EFFECTS: Returns "No tasks found." if current task list is empty, returns all tasks in the current task list ow.
    private String printTaskList(ArrayList<Task> list) throws EmptyListException {
        StringBuilder taskListPrint = new StringBuilder();

        if (isTaskListEmpty()) {
            throw new EmptyListException();
        } else {
            for (int i = 0; i < list.size(); i++) {
                taskListPrint.append(i + 1).append(" : ").append((list.get(i)).printTask()).append("\n");
            }
            //substring to -1 removes the last line break
            return taskListPrint.substring(0, taskListPrint.length() - 1);
        }
    }

    //EFFECTS: takes in an list of tasks, returns a new list with only incomplete tasks.
    private ArrayList<Task> filterOutCompleted(ArrayList<Task> taskList) {
        ArrayList<Task> filteredList = new ArrayList<>();
        for (Task task : taskList) {
            if (!(task instanceof CompletedTask)) {
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    //EFFECTS: Returns a new task list that contains all tasks in the current task list
    //         with the specified urgency level.
    public TaskList getTaskByUrgency(String urgency) throws TooManyIncompleteTasksException {
        TaskList tempList = new TaskList();
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
    public void sortByDueDate() {
        taskList.sort((a, b) -> {
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
