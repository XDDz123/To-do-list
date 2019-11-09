package model;

import exceptions.*;
import java.util.*;

public class TaskList {

    private final TaskListSorter taskListFilterAndSorter = new TaskListSorter();
    private final TaskListToString taskListToString = new TaskListToString();
    private final Observer listSizeObserver = new ListSizeObserver();
    private ArrayList<Task> taskList;
    private String name;
    static final int maxSize = 10;

    //MODIFIES: this
    //EFFECTS: Constructs a new taskList as an ArrayList.
    public TaskList(String name) {
        taskList = new ArrayList<>();
        this.name = name;
    }

    //MODIFIES: this, task
    //EFFECTS: Inserts a task to the current task list
    //         If an identical task already exists in this list, throw DuplicateTaskException
    //         If the size of this list is greater than maxSize and the given task is an incomplete task,
    //         then throwTooManyIncompleteTasksException
    //         else insert the task into this list and modify TaskList of the given task to this list
    public void storeTask(Task task) throws TaskException {
        if (!taskList.contains(task)) {
            if (((ListSizeObserver) listSizeObserver).getSize() > maxSize && !(task instanceof CompletedTask)) {
                throw new TooManyIncompleteTasksException();
            } else {
                taskList.add(task);
                task.setTaskList(this);
                if (task instanceof IncompleteTask) {
                    listSizeObserver.update(new ObserverState<>(1, name));
                }
            }
        }
    }

    //EFFECTS: returns the name of this list
    public String getName() {
        return name;
    }

    //EFFECTS: Returns the ArrayList that stores the current task list
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    //EFFECTS: Returns a task store in the current task list based on an index that starts at 1.
    public Task getTask(int index) {
        return taskList.get(index - 1);
    }

    //REQUIRES: index must refer to an existing index in the list of tasks
    //MODIFIES: this, task
    //EFFECTS: Removes a task in the current task list based on an index that starts at 1.
    //         Sets this task's TaskList to null
    public void deleteTask(int index) throws TaskDoesNotExistException, TaskException {
        Task task;
        try {
            task = taskList.get(index - 1);
            taskList.remove(index - 1);
            if (task instanceof IncompleteTask) {
                listSizeObserver.update(new ObserverState<>(-1, name));
            }
        } catch (IndexOutOfBoundsException e) {
            throw new TaskDoesNotExistException();
        }
        task.setTaskList(null);
    }

    //MODIFIES: this
    //EFFECTS: Removes all tasks in the current task list
    public void clearTaskList() {
        listSizeObserver.update(new ObserverState<>(-taskList.size(), name));
        taskList.clear();
    }

    //EFFECTS: Returns true if task list is empty, false otherwise.
    boolean isTaskListEmpty() {
        return taskList.isEmpty();
    }

    //EFFECTS: Returns the size of the current task list
    int getTaskListSize() {
        return taskList.size();
    }

    //EFFECTS: Prints the contents of incomplete tasks in the list
    public String printIncompleteTasks() {
        return taskListToString.printIncompleteTasks(this);
    }

    //EFFECTS: Prints the contents of the current task list
    public String printTaskList() {
        return taskListToString.printTaskList(this);
    }

    //EFFECTS: Returns a new task list that contains all tasks in the current task list
    //         with the specified urgency level.
    public TaskList getTaskByUrgency(String urgency) throws TaskException {
        return taskListFilterAndSorter.getTaskByUrgency(urgency, this);
    }

    //inspired by post by user zb226 @ https://stackoverflow.com/questions/16252269/how-to-sort-an-arraylist
    //            and https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
    //MODIFIES: this
    //EFFECTS: Sorts the current task list chronologically based on due dates. Starts from most recently due.
    public void sortByDueDate() {
        taskListFilterAndSorter.sortByDueDate(this);
    }

    //EFFECTS: takes in an list of tasks, returns a new list with only incomplete tasks.
    ArrayList<Task> filterOutCompleted() {
        return taskListFilterAndSorter.filterOutCompleted(this);
    }
}
