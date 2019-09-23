package ui.model;

import java.util.*;

public class TaskList {

    private ArrayList<Task> taskList;

    //EFFECTS: Constructs a new taskList as an ArrayList.
    //MODIFIES: this
    public TaskList() {
        taskList = new ArrayList<>();
    }

    //EFFECTS: Inserts a task to the current task list
    //MODIFIES: this
    public void storeTask(Task task) {
        taskList.add(task);
    }

    //EFFECTS: Returns the ArrayList that stores the current task list
    public ArrayList getTaskList() {
        return taskList;
    }

    //EFFECTS: Returns a task store in the current task list based on an index that starts at 1.
    public Task getTask(int index) {
        return taskList.get(index - 1);
    }

    //EFFECTS: Removes a task in the current task list based on an index that starts at 1.
    //MODIFIES: this
    //REQUIRES: index must refer to an existing index in the list of tasks
    public void deleteTask(int index) {
        taskList.remove(index - 1);
    }

    //EFFECTS: Removes all tasks in the current task list
    //MODIFIES: this
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

    //EFFECTS: Prints "No tasks found." if current task list is empty, prints all tasks in the current task list ow.
    public void printTaskList() {
        if (isTaskListEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (int i = 0; i < (getTaskListSize()); i++) {
                System.out.println((i + 1) + " : " + (taskList.get(i)).printTask());
            }
        }
    }

    //EFFECTS: Returns a new task list that contains all tasks in the current task list
    //         with the specified urgency level.
    public TaskList getTaskByUrgency(String urgency) {
        TaskList tempList = new TaskList();
        for (Task task : taskList) {
            if (task.getUrgency().equalsIgnoreCase(urgency)) {
                tempList.storeTask(task);
            }
        }
        return tempList;
    }

    //inspired by post by user zb226 @ https://stackoverflow.com/questions/16252269/how-to-sort-an-arraylist
    //EFFECTS: Sorts the current task list chronologically based on due dates. Starts from most recently due.
    //MODIFIES: this
    public void sortByDueDate() {
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task a, Task b) {
                if (a.getDueDateObj().isBefore(b.getDueDateObj())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

}
