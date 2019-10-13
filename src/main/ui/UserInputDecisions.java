package ui;

import io.Loadable;
import io.Savable;
import io.SaveAndLoad;
import model.*;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.LocalDate;
import java.util.*;

class UserInputDecisions extends SetTaskInputDecisions {

    private final String fileName = "save.txt";

    //EFFECTS: Checks if keyboard input is equal to "exit".
    private Boolean checkExit() {
        continueMessage();
        Scanner keyboard = new Scanner(System.in);
        return (keyboard.nextLine()).equalsIgnoreCase("exit");
    }

    //EFFECTS: Takes in user selection in the form of a number and decides between:
    //         (1) enter a new task
    //         (2) view entered tasks
    //         (3) delete a task or all tasks
    //         (4) modify a task
    //         (5) sort the list of tasks
    //         (6) save list of tasks to file or clear previous save
    //         else output error message for not an option
    private void userSelection(TaskList taskList) throws IOException {
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
    private void saveAndClearSave(TaskList taskList) throws IOException {
        SaveAndLoad saveTasks = new SaveAndLoad();
        Scanner selection = new Scanner(System.in);
        saveAndClearSaveMessage();
        String input = selection.nextLine();

        if (!input.equals("0")) {
            if (input.equals("1")) {
                saveTasks.save(taskList, fileName);
            } else if (input.equals("2")) {
                saveTasks.clearSave(fileName, taskList);
            } else {
                selectNotAnOption();
            }
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Prompts the user to select between:
    //         (1) delete a task from the current list of tasks
    //         (2) delete all tasks from the current list of tasks
    //         (0) close current menu
    //         to delete task(s) from the current list of tasks
    private void selectDeleteTasks(TaskList taskList, Scanner keyboard) {
        Scanner selection = new Scanner(System.in);
        selectDeleteTaskMessage();
        String input = selection.nextLine();

        if (!input.equals("0")) {
            if (input.equals("1")) {
                selectToDeleteTask(taskList, keyboard);
            } else if (input.equals("2")) {
                selectDeleteAllTasks(taskList);
            } else {
                selectNotAnOption();
            }
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Sorts the current list of tasks chronologically by due dates (starts from most recently due).
    //         Prints out sorted list of tasks.
    private void sortTaskList(TaskList taskList) {
        taskList.sortByDueDate();
        printList(taskList);
    }

    //MODIFIES: taskList.get(index)
    //EFFECTS: Check if user input is valid and that the user is selecting a task that exists in the list of tasks.
    private void selectModifyTask(TaskList taskList, Scanner keyboard) {
        int input;
        selectTaskMessage(taskList);

        try {
            input = keyboard.nextInt();

            if (! (input == 0)) {
                try {
                    tryModifyTask(taskList, input);
                } catch (IndexOutOfBoundsException e) {
                    outOfBoundsError();
                }
            }
        } catch (InputMismatchException e) {
            notIntegerError();
        }
    }

    //REQUIRES: taskList.get(index) must be of type RegularTask or ImportantTask
    //MODIFIES: taskList.get(index)
    //EFFECTS: Prompts user to select which part of a task to modify and modifies the corresponding task according to
    //         user input after checking whether user input is a valid option. Returns true if the user selects an
    //         option, returns false otherwise. If user input is:
    //         (1) set task as complete, return true
    //         (2) change the due date for this task, return true
    //         (3) change the urgency level of this task, return true
    //         (4) change the content of this task, return true
    //         (0) returns to menu to prompt the user to reselect which task to modify, return true
    //         else returns false
    private Boolean modifyTask(String input, int index, RegularTask task, TaskList taskList, Scanner keyboard) {
        if ((input.equals("1"))) {
            //set complete
            setTaskComplete(taskList, index);
        } else if ((input.equals("2"))) {
            //change due date
            task.setDueDate(setMonthAndDay(task.getDueDateObj()));
        } else if ((input.equals("3"))) {
            //change urgency
            task.setUrgency(setUrgencyDecision(task.getUrgency()));
        } else if ((input.equals("4"))) {
            //change content
            task.setContent(setTaskContentDecisions());
        } else if ((input.equals("0"))) {
            //return to prev
            selectModifyTask(taskList, keyboard);
        } else {
            //no selection
            return false;
        }
        //valid selection
        return true;
    }

    //REQUIRES: taskList.get(index) instanceOf RegularTask
    //MODIFIES: taskList.get(index)
    //EFFECTS: Prompts the user to select which field of a regular task to modify
    //         Prints not an option error message if the user did not select a valid option
    private void modifyRegularTask(TaskList taskList, int index) {
        modifyRegularTaskMessage();
        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();

        RegularTask regularTask = (RegularTask)(taskList.getTask(index));

        if (! modifyTask(input, index, regularTask, taskList, keyboard)) {
            selectNotAnOption();
        }
    }

    //REQUIRES: taskList.get(index) instanceOf ImportantTask
    //MODIFIES: taskList.get(index)
    //EFFECTS: Prompts the user to select which field of a regular task to modify.
    //         Displays a change importance option in addition to options displayed in modifyTask().
    //         Prints not an option error message if the user did not select a valid option.
    private void modifyImportantTask(TaskList taskList, int index) {

        modifyImportantTaskMessage();

        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();

        ImportantTask importantTask = (ImportantTask) (taskList.getTask(index));

        if (! modifyTask(input, index, importantTask, taskList, keyboard)) {
            if ((input.equals("5"))) {
                importantTask.setImportance(setImportanceDecision(importantTask.getImportance()));
            } else {
                selectNotAnOption();
            }
        }
    }

    //MODIFIES: taskList.get(index)
    //EFFECTS: Prompts the user to modify important task is taskList.get(index) is an ImportantTask.
    //         Prompts the user to modify regular task if taskList.get(index) is a RegularTask.
    //         Else return cannot modify completed task error message.
    private void tryModifyTask(TaskList taskList, int index) {
        if (taskList.getTask(index) instanceof ImportantTask) {
            modifyImportantTask(taskList, index);
        } else if (taskList.getTask(index) instanceof RegularTask) {
            modifyRegularTask(taskList, index);
        } else {
            cantModifyCompletedTaskError();
        }
    }

    //MODIFIES: taskList.get(index)
    //EFFECTS: Removes the given important task or regular task from the list of all tasks.
    //         Creates a new completed task with the content, due date of the give task and the current time
    //         Stores this completed task in the list of all tasks.
    private void setTaskComplete(TaskList taskList, int index) {
        Task task = taskList.getTask(index);
        CompletedTask completedTask;
        completedTask = new CompletedTask(task.getContent(), task.getDueDateObj(), task.getDate(LocalDate.now()));
        taskList.storeTask(completedTask);
        taskList.deleteTask(index);
    }

    //MODIFIES: taskList
    //EFFECTS: Creates new regular task or important task  with the following initial variables.
    //         Prompts the user to assign or enter task information.
    //         Adds this new task to the list of tasks.
    //         Else returns not an option error.
    private void selectEnterTask(TaskList taskList) {
        String taskContent = "empty RegularTask";
        LocalDate taskDueDate = LocalDate.now();
        String taskUrgency = "unassigned";
        String taskImportance = "important";

        Scanner keyboard = new Scanner(System.in);
        selectTaskTypeMessage();
        String input = keyboard.nextLine();

        if ((input.equals("1"))) {
            RegularTask regularTask = new RegularTask(taskContent, taskDueDate, taskUrgency);
            setGeneralRegularTask(taskList, regularTask);
        } else if ((input.equals("2"))) {
            ImportantTask importantTask = new ImportantTask(taskContent, taskDueDate, taskUrgency, taskImportance);
            setGeneralRegularTask(taskList, importantTask);
            importantTask.setImportance(setImportanceDecision(importantTask.getImportance()));
        } else {
            selectNotAnOption();
        }
    }

    //MODIFIES: taskList, regularTask
    //EFFECTS: Prompts the user to set all fields in a regular task.
    //         Sets all fields present in regular tasks if given a important task.
    private void setGeneralRegularTask(TaskList taskList, RegularTask regularTask) {
        Scanner keyboard = new Scanner(System.in);

        regularTask.setContent(setTaskContentDecisions());
        regularTask.setUrgency(setUrgencyDecision(regularTask.getUrgency()));
        useDefaultDateMessage();
        if ((keyboard.nextLine()).equalsIgnoreCase("y")) {
            regularTask.setDueDate(setMonthAndDay(regularTask.getDueDateObj()));
        }
        taskList.storeTask(regularTask);

        regularTask.setTimeLeft();
    }

    //EFFECTS: Prompts the user to select between:
    //         (1) Prints all tasks in the current task list
    //         (2) Prints tasks in the current task list based on urgency
    //         else display not an option error and restarts the method
    private void selectViewTasksBy(TaskList taskList) {
        selectViewTasksByMessage();

        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();
        if (input.equals("1")) {
            printList(taskList);
        } else if (input.equals("2")) {
            selectViewTaskByUrgency(taskList);
        } else {
            selectNotAnOption();
            selectViewTasksBy(taskList);
        }
    }

    //EFFECTS: Prompts the user to select an urgency level and prints out all tasks in the current task list with the
    //         selected urgency level. Displays not an option error message if input is not an urgency level and
    //         restarts the method.
    private void selectViewTaskByUrgency(TaskList taskList) {
        String high = "high";
        String mid = "mid";
        String low = "low";

        Scanner keyboard = new Scanner(System.in);
        selectUrgencyMessage();
        String input = keyboard.nextLine();
        if (input.equalsIgnoreCase(high) || input.equalsIgnoreCase(mid) || input.equalsIgnoreCase(low)) {
            printIncompleteTasksList(taskList.getTaskByUrgency(input));
        } else {
            selectNotAnOption();
            selectViewTaskByUrgency(taskList);
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Prompts the user to enter a number to select which task from the task list to delete.
    //         Check if user input is valid and that the user is selecting a task that exists in the list of tasks.
    private void selectToDeleteTask(TaskList taskList, Scanner keyboard) {
        int input;
        selectTaskMessage(taskList);

        try {
            input = keyboard.nextInt();

            if (!(input == 0)) {
                if (input > (taskList.getTaskList()).size()) {
                    outOfBoundsError();
                } else {
                    taskList.deleteTask(input);
                }
            }
        } catch (InputMismatchException e) {
            notIntegerError();
        }

        printList(taskList);
    }

    //MODIFIES: taskList
    //EFFECTS: removes all tasks from the current list of tasks
    private void selectDeleteAllTasks(TaskList taskList) {
        taskList.clearTaskList();
        printList(taskList);
    }

    //EFFECTS: Displays the not an option error message.
    private void selectNotAnOption() {
        notAnOptionError();
    }

    //EFFECTS: Displays welcome message and runs the program while the user does not exit.
    //         Load list of tasks from save file, displays file not found error if file not found
    //         Saves list of tasks once the user selects exit.
    void run() throws IOException {
        welcomeMessage();

        TaskList taskList = new TaskList();
        Loadable loadTasks = new SaveAndLoad();
        Savable saveTasks = new SaveAndLoad();

        try {
            loadTasks.load(taskList, fileName);
        } catch (NoSuchFileException e) {
            System.out.println("File not found!");
        }

        do {
            optionsMessage();
            userSelection(taskList);
        } while (!checkExit());

        saveTasks.save(taskList, fileName);
    }
}
