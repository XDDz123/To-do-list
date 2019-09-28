package ui;

import io.Loadable;
import io.Savable;
import io.SaveAndLoad;
import model.Task;
import model.TaskList;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.*;

public class UserInputDecisions extends SetTaskInputDecisions {

    private String fileName = "save.txt";

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
    public void userSelection(TaskList taskList) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();

        if ((input.equals("1"))) {
            selectEnterTask(taskList);
        } else if ((input.equals("2"))) {
            selectViewTasksBy(taskList);
        } else if ((input.equals("3"))) {
            selectDeleteTasks(taskList, keyboard);
        } else if ((input.equals("4"))) {
            selectModifyTask(taskList, keyboard);
        } else if ((input.equals("5"))) {
            sortTaskList(taskList);
        } else if ((input.equals("6"))) {
            saveAndClearSave(taskList);
        } else {
            selectNotAnOption();
        }
    }

    //MODIFIES: save.txt
    //EFFECTS: Prompts the user to select either:
    //        (1) save current list of task to file
    //        (2) clear/format the current save file
    //        (0) close current menu
    //        to make adjustments to the save file
    public void saveAndClearSave(TaskList taskList) throws IOException {
        Savable saveTasks = new SaveAndLoad();
        Scanner selection = new Scanner(System.in);
        saveAndClearSaveMessage();
        String input = selection.nextLine();

        if (!input.equals("0")) {
            if (input.equals("1")) {
                saveTasks.save(taskList, fileName);
            } else if (input.equals("2")) {
                ((SaveAndLoad) saveTasks).clearSave(fileName);
            } else {
                notAnOptionError();
            }
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Prompts the user to select between:
    //         (1) delete a task from the current list of tasks
    //         (2) delete all tasks from the current list of tasks
    //         (0) close current menu
    //         to delete task(s) from the current list of tasks
    public void selectDeleteTasks(TaskList taskList, Scanner keyboard) {
        Scanner selection = new Scanner(System.in);
        selectDeleteTaskMessage();
        String input = selection.nextLine();

        if (!input.equals("0")) {
            if (input.equals("1")) {
                selectToDeleteTask(taskList, keyboard);
            } else if (input.equals("2")) {
                selectDeleteAllTasks(taskList);
            } else {
                notAnOptionError();
            }
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Sorts the current list of tasks chronologically by due dates (starts from most recently due).
    //         Prints out sorted list of tasks.
    public void sortTaskList(TaskList taskList) {
        taskList.sortByDueDate();
        printList(taskList);
    }

    //MODIFIES: taskList.get(index)
    //EFFECTS: Check if user input is valid and that the user is selecting a task that exists in the list of tasks.
    public void selectModifyTask(TaskList taskList, Scanner keyboard) {
        int input;
        selectTaskMessage(taskList);

        if (keyboard.hasNextInt()) {
            input = keyboard.nextInt();
            if (! (input == 0)) {
                if (input > (taskList.getTaskList()).size()) {
                    outOfBoundsError();
                    selectModifyTask(taskList, keyboard);
                } else {
                    modifyTask(taskList, input);
                }
            }
        } else {
            notIntegerError();
            selectModifyTask(taskList, keyboard);
        }
    }


    //MODIFIES: taskList.get(index)
    //EFFECTS: Prompts user to select which part of a task to modify and modifies the corresponding task according to
    //         user input after checking whether user input is a valid option. If user input is:
    //         (1) set task as complete
    //         (2) change the due date for this task
    //         (3) change the urgency level of this task
    //         (4) change the content of this task
    //         (5) returns to menu to prompt the user to reselect which task to modify
    //         else output not an option error message and restarts the method
    public void modifyTask(TaskList taskList, int index) {
        modifyTaskMessage();
        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();

        if ((input.equals("1"))) {
            //set complete
            taskList.getTask(index).setStatus(true);
        } else if ((input.equals("2"))) {
            //change due date
            taskList.getTask(index).setDueDate(setMonthAndDay(taskList.getTask(index).getDueDateObj()));
        } else if ((input.equals("3"))) {
            //change urgency
            taskList.getTask(index).setUrgency(setUrgencyDecision(taskList.getTask(index).getUrgency()));
        } else if ((input.equals("4"))) {
            //change content
            taskList.getTask(index).setContent(setTaskContentDecisions());
        } else if ((input.equals("0"))) {
            //return to prev
            selectModifyTask(taskList, keyboard);
        } else {
            selectNotAnOption();
            modifyTask(taskList, index);
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Creates new task and prompts the user to assign or enter task content, task urgency, and task due date.
    //         Adds this new task to the list of tasks.
    public void selectEnterTask(TaskList taskList) {
        Scanner keyboard = new Scanner(System.in);
        Task task = new Task();
        task.setContent(setTaskContentDecisions());
        task.setUrgency(setUrgencyDecision(task.getUrgency()));
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
        String input = keyboard.nextLine();
        if (input.equals("1")) {
            printList(taskList);
        } else if (input.equals("2")) {
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
        String input = keyboard.nextLine();
        if (input.equalsIgnoreCase("high") || input.equalsIgnoreCase("mid") || input.equalsIgnoreCase("low")) {
            printList(taskList.getTaskByUrgency(input));
        } else {
            notAnOptionError();
            selectViewTaskByUrgency(taskList);
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Prompts the user to enter a number to select which task from the task list to delete.
    //         Check if user input is valid and that the user is selecting a task that exists in the list of tasks.
    public void selectToDeleteTask(TaskList taskList, Scanner keyboard) {
        int input;
        selectTaskMessage(taskList);

        if (keyboard.hasNextInt()) {
            input = keyboard.nextInt();

            if (!(input == 0)) {
                if (input > (taskList.getTaskList()).size()) {
                    outOfBoundsError();
                } else {
                    taskList.deleteTask(input);
                }
            }
        } else {
            notIntegerError();
        }

        printList(taskList);
    }

    //MODIFIES: taskList
    //EFFECTS: removes all tasks from the current list of tasks
    public void selectDeleteAllTasks(TaskList taskList) {
        taskList.clearTaskList();
        printList(taskList);
    }

    //EFFECTS: Displays the not an option error message.
    public void selectNotAnOption() {
        notAnOptionError();
    }

    //EFFECTS: Displays welcome message and runs the program while the user does not exit.
    public void run() throws IOException {
        welcomeMessage();

        TaskList taskList = new TaskList();
        Loadable loadTasks = new SaveAndLoad();

        try {
            loadTasks.load(taskList, fileName);
        } catch (NoSuchFileException e) {
            System.out.println("File not found!");
        }

        do {
            optionsMessage();
            userSelection(taskList);
        } while (!checkExit());
    }
}
