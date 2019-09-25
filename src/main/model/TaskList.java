package model;

import java.util.*;

public class TaskList {

    private ArrayList<Task> taskList;

    //MODIFIES: this
    //EFFECTS: Constructs a new taskList as an ArrayList.
    public TaskList() {
        taskList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Inserts a task to the current task list
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

    //REQUIRES: index must refer to an existing index in the list of tasks
    //MODIFIES: this
    //EFFECTS: Removes a task in the current task list based on an index that starts at 1.
    public void deleteTask(int index) {
        taskList.remove(index - 1);
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

    //EFFECTS: Returns "No tasks found." if current task list is empty, returns all tasks in the current task list ow.
    public String printTaskList() {
        String taskListPrint = "";

        if (isTaskListEmpty()) {
            return "No tasks found.";
        } else {
            for (int i = 0; i < (getTaskListSize()); i++) {
                taskListPrint = taskListPrint + (i + 1) + " : " + (taskList.get(i)).printTask() + "\n";
            }
            //substring to -1 removes the last line break
            return taskListPrint.substring(0, taskListPrint.length() - 1);
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
    //MODIFIES: this
    //EFFECTS: Sorts the current task list chronologically based on due dates. Starts from most recently due.
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
