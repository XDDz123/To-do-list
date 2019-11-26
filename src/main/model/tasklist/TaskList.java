package model.tasklist;

import exceptions.*;
import model.Name;

import model.observer.TaskListObserver;

import model.observer.ObserverState;
import model.task.Task;
import java.util.*;

public class TaskList extends Observable {

    private final TaskListSorter taskListSorter = new TaskListSorter();
    private final TaskListFilter taskListFilter = new TaskListFilter();
    private final TaskListToString taskListToString = new TaskListToString();

    private final TaskListObserver taskListObserver = new TaskListObserver();
    private ArrayList<Task> taskList;
    private Name name;
    public static final int maxSize = 30;

    //MODIFIES: this
    //EFFECTS: Constructs a new taskList as an ArrayList.
    public TaskList(Name name) {
        taskList = new ArrayList<>();
        this.name = name;
        addObserver(taskListObserver);
    }

    //MODIFIES: this, task, listSizeObserver
    //EFFECTS: Inserts a task to the current task list
    //         If an identical task already exists in this list, throw DuplicateTaskException
    //         If the size of this list is greater than maxSize and the given task is an incomplete task,
    //         then throwTooManyIncompleteTasksException
    //         else insert the task into this list and modify TaskList of the given task to this list
    //         When an incomplete task is added, notify ListSizeObserver to add one to its size
    public void storeTask(Task task) throws TaskException {
        if (!taskList.contains(task)) {
            if (!task.isCompleted() && filterOutCompleted().size() > maxSize) {
                throw new TooManyIncompleteTasksException();
            } else {
                taskList.add(task);
                task.setTaskList(this);
                notifyObserver(task);
            }
        }
    }

    public TaskListObserver getTaskListObserver() {
        return taskListObserver;
    }

    //REQUIRES: index must refer to an existing index in the list of tasks
    //MODIFIES: this, task, listSizeObserver
    //EFFECTS: Removes a task in the current task list based on an index that starts at 1.
    //         Sets this task's TaskList to null
    //         Notify ListSizeObserver to remove one from its size
    public void deleteTask(int index) throws TaskDoesNotExistException, TaskException {
        Task task;
        try {
            task = taskList.get(index - 1);
            taskList.remove(index - 1);
            notifyObserver(new ObserverState<>("remove", task));
        } catch (IndexOutOfBoundsException e) {
            throw new TaskDoesNotExistException();
        }
        task.setTaskList(null);
    }

    public void deleteTask(Task task) throws TaskException {
        taskList.remove(task);
        notifyObserver(new ObserverState<>("remove", task));
        task.setTaskList(null);
    }

    public void notifyObserver(Task task) {
        setChanged();
        notifyObservers(new ObserverState<>("edit", task));
    }

    public void notifyObserver(ArrayList<Task> taskList) {
        setChanged();
        notifyObservers(taskList);
    }

    public void notifyObserver(ObserverState observerState) {
        setChanged();
        notifyObservers(observerState);
    }

    //MODIFIES: this, listSizeObserver
    //EFFECTS: Removes all tasks in the current task list
    public void clearTaskList() {
        taskList.clear();
        notifyObserver(taskList);
    }

    //EFFECTS: Returns the name of this list
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    //EFFECTS: Returns the ArrayList that stores the current task list
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    //EFFECTS: Returns a task store in the current task list based on an index that starts at 1.
    public Task getTask(int index) {
        return taskList.get(index - 1);
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
        return taskListToString.printIncompleteTasks(this);
    }

    //EFFECTS: Prints the contents of the current task list
    public String printTaskList() {
        return taskListToString.printTaskList(taskList);
    }

    //EFFECTS: Returns a new task list that contains all tasks in the current task list
    //         with the specified urgency level.
    public ArrayList<Task> getTaskByUrgency(String urgency) {
        return taskListFilter.getTaskByUrgency(urgency, this);
    }

    //inspired by post by user zb226 @ https://stackoverflow.com/questions/16252269/how-to-sort-an-arraylist
    //            and https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
    //MODIFIES: this
    //EFFECTS: Sorts the current task list chronologically based on due dates. Starts from most recently due.
    public void sortByDueDate() {
        taskListSorter.sortByDueDate(this);
    }

    //EFFECTS: Takes in an list of tasks, returns a new list with only incomplete tasks.
    ArrayList<Task> filterOutCompleted() {
        return taskListFilter.filterOutCompleted(this);
    }
}
