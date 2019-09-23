package ui;

import ui.model.Task;
import ui.model.TaskList;

import java.time.MonthDay;
import java.util.*;

public class UserInputDecisions extends TaskBehavior {

    //EFFECTS: Checks if keyboard input is equal to "exit".
    public Boolean checkExit() {
        continueMessage();
        Scanner keyboard = new Scanner(System.in);
        return (keyboard.nextLine()).equalsIgnoreCase("exit");
    }

    //EFFECTS: Takes in user selection in the form of a number and decides between:
    //         (1) enter a new task
    //         (2) see all entered tasks
    //         (3) delete a task
    //         (4) delete all entered tasks
    //         (5) modify a task
    //         else output error message for not an option
    public void userSelection(TaskList taskList) {
        Scanner keyboard = new Scanner(System.in);
        String temp = keyboard.nextLine();

        if ((temp.equals("1"))) {
            selectEnterTask(taskList);
        } else if ((temp.equals("2"))) {
            selectViewTasksBy(taskList);
        } else if ((temp.equals("3"))) {
            selectToDeleteTask(taskList, keyboard);
        } else if ((temp.equals("4"))) {
            selectDeleteAllTasks(taskList);
        } else if ((temp.equals("5"))) {
            selectModifyTask(taskList, keyboard);
        } else if ((temp.equals("6"))) {
            sortTaskList(taskList);
        } else {
            selectNotAnOption();
        }
    }

    //EFFECTS: Sorts the current list of tasks chronologically by due dates (starts from most recently due).
    //         Prints out sorted list of tasks.
    //MODIFIES: taskList
    public void sortTaskList(TaskList taskList) {
        taskList.sortByDueDate();
        printList(taskList);
    }

    //EFFECTS: Check if user input is valid and that the user is selecting a task that exists in the list of tasks.
    //MODIFIES: taskList.get(index)
    public void selectModifyTask(TaskList taskList, Scanner keyboard) {
        int temp;
        selectTaskMessage(taskList);

        if (keyboard.hasNextInt()) {
            temp = keyboard.nextInt();
            if (! (temp == 0)) {
                if (temp > (taskList.getTaskList()).size()) {
                    outOfBoundsError();
                    selectModifyTask(taskList, keyboard);
                } else {
                    modifyTask(taskList, temp);
                }
            }
        } else {
            notIntegerError();
            selectModifyTask(taskList, keyboard);
        }
    }


    //EFFECTS: Prompts user to select which part of a task to modify and modifies the corresponding task according to
    //         user input after checking whether user input is a valid option. If user input is:
    //         (1) set task as complete
    //         (2) change the due date for this task
    //         (3) change the urgency level of this task
    //         (4) change the content of this task
    //         (5) returns to menu to prompt the user to reselect which task to modify
    //         else output not an option error message and restarts the method
    //MODIFIES: taskList.get(index)
    public void modifyTask(TaskList taskList, int index) {
        modifyTaskMessage();
        Scanner keyboard = new Scanner(System.in);
        String temp = keyboard.nextLine();

        if ((temp.equals("1"))) {
            //set complete
            taskList.getTask(index).setStatus(true);
        } else if ((temp.equals("2"))) {
            //change due date
            taskList.getTask(index).setDueDate(setMonthAndDay(taskList.getTask(index).getDueDateObj()));
        } else if ((temp.equals("3"))) {
            //change urgency
            taskList.getTask(index).setUrgency(setUrgencyBehavior(taskList.getTask(index).getUrgency()));
        } else if ((temp.equals("4"))) {
            //change content
            taskList.getTask(index).setContent(setTaskContentBehavior());
        } else if ((temp.equals("5"))) {
            //return to prev
            selectModifyTask(taskList, keyboard);
        } else {
            selectNotAnOption();
            modifyTask(taskList, index);
        }
    }

    //EFFECTS: Creates new task and prompts the user to assign or enter task content, task urgency, and task due date.
    //         Adds this new task to the list of tasks.
    //MODIFIES: taskList
    public void selectEnterTask(TaskList taskList) {
        Scanner keyboard = new Scanner(System.in);
        Task task = new Task();
        task.setContent(setTaskContentBehavior());
        task.setUrgency(setUrgencyBehavior(task.getUrgency()));
        useDefaultDateMessage();
        if ((keyboard.nextLine()).equalsIgnoreCase("y")) {
            task.setDueDate(setMonthAndDay(task.getDueDateObj()));
        }
        taskList.storeTask(task);
    }

    //EFFECTS: Prompts the user to select between:
    //         (1) Prints all tasks in the current task list
    //         (2) Prints tasks in the current task list based on urgency
    //         else display not an option error and restarts the method
    public void selectViewTasksBy(TaskList taskList) {
        selectViewTasksByMessage();

        Scanner keyboard = new Scanner(System.in);
        String temp = keyboard.nextLine();
        if (temp.equals("1")) {
            printList(taskList);
        } else if (temp.equals("2")) {
            selectViewTaskByUrgency(taskList);
        } else {
            notAnOptionError();
            selectViewTasksBy(taskList);
        }
    }

    //EFFECTS: Prompts the user to select an urgency level and prints out all tasks in the current task list with the
    //         selected urgency level. Displays not an option error message if input is not an urgency level and
    //         restarts the method.
    public void selectViewTaskByUrgency(TaskList taskList) {
        Scanner keyboard = new Scanner(System.in);
        selectUrgencyMessage();
        String temp = keyboard.nextLine();
        if (temp.equalsIgnoreCase("high") || temp.equalsIgnoreCase("mid") || temp.equalsIgnoreCase("low")) {
            printList(taskList.getTaskByUrgency(temp));
        } else {
            notAnOptionError();
            selectViewTaskByUrgency(taskList);
        }
    }

    //EFFECTS: Prompts the user to enter a number to select which task from the task list to delete.
    //         Check if user input is valid and that the user is selecting a task that exists in the list of tasks.
    //MODIFIES: taskList
    public void selectToDeleteTask(TaskList taskList, Scanner keyboard) {
        int temp;
        selectTaskMessage(taskList);

        if (keyboard.hasNextInt()) {
            temp = keyboard.nextInt();

            if (temp > (taskList.getTaskList()).size()) {
                outOfBoundsError();
            } else {
                taskList.deleteTask(temp);
            }
        } else {
            notIntegerError();
        }

        printList(taskList);
    }

    //EFFECTS: removes all tasks from the current list of tasks
    //MODIFIES: taskList
    public void selectDeleteAllTasks(TaskList taskList) {
        taskList.clearTaskList();
        printList(taskList);
    }

    //EFFECTS: Displays the not an option error message.
    public void selectNotAnOption() {
        notAnOptionError();
    }

    //EFFECTS: Displays welcome message and runs the program while the user does not exit.
    public void run() {
        welcomeMessage();

        TaskList taskList = new TaskList();

        do {
            optionsMessage();
            userSelection(taskList);
        } while (!checkExit());
    }
}
