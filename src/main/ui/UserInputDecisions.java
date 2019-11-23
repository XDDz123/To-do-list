package ui;

import exceptions.*;
import io.Load;
import io.Save;
import model.*;
//import model.task.CompletedTask;
//import model.task.IncompleteTask;
//import model.task.Task;
import model.task.Task;
import model.task.Urgency;
import model.tasklist.TaskList;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.*;

class UserInputDecisions {

    private final String saveFile = "save";
    private final Messages messages = new Messages();
    private final TaskInputDecisions taskInputDecisions = new TaskInputDecisions();

    //EFFECTS: Checks if keyboard input is equal to "exit".
    private Boolean checkExit() {
        messages.continueMessage();
        Scanner keyboard = new Scanner(System.in);
        return (keyboard.nextLine()).equalsIgnoreCase("exit");
    }

    //EFFECTS: Checks if keyboard input is equal to "exit".
    //         Used for exiting the menus of lists
    private Boolean checkExitList() {
        messages.continueMessageForList();
        Scanner keyboard = new Scanner(System.in);
        return (keyboard.nextLine()).equalsIgnoreCase("exit");
    }

    //EFFECTS: Takes in user selection in the form of a number and decides between:
    //         (1) enter a new task
    //         (2) view entered tasks
    //         (3) delete a task or all tasks
    //         (4) modify a task
    //         (5) sort the list of tasks
    //         else output error message for not an option
    //         returns on boolean depending on whether the user decides to exit
    private boolean userSelection(TaskList taskList) throws NotAnOptionException, TaskException {
        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();

        if ((input.equals("1"))) {
            selectEnterTask(taskList);
        } else if ((input.equals("2"))) {
            selectViewTasksBy(taskList);
        } else if ((input.equals("3"))) {
            selectDeleteTasks(taskList);
        } else if ((input.equals("4"))) {
            selectModifyTask(taskList);
        } else if ((input.equals("5"))) {
            sortTaskList(taskList);
        } else {
            throw new NotAnOptionException();
        }
        return checkExitList();
    }

    //MODIFIES: taskList
    //EFFECTS: Prompts the user to select between:
    //         (1) delete a task from the current list of tasks
    //         (2) delete all tasks from the current list of tasks
    //         (0) close current menu
    //         to delete task(s) from the current list of tasks
    private void selectDeleteTasks(TaskList taskList) throws NotAnOptionException {
        Scanner selection = new Scanner(System.in);
        messages.selectDeleteTaskMessage();
        String input = selection.nextLine();

        if (!input.equals("0")) {
            if (input.equals("1")) {
                selectToDeleteTask(taskList);
            } else if (input.equals("2")) {
                selectDeleteAllTasks(taskList);
            } else {
                throw new NotAnOptionException();
            }
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Sorts the current list of tasks chronologically by due dates (starts from most recently due).
    //         Prints out sorted list of tasks.
    private void sortTaskList(TaskList taskList) {
        taskList.sortByDueDate();
        messages.printList(taskList);
    }

    //MODIFIES: taskList.get(index)
    //EFFECTS: Check if user input is valid and that the user is selecting a task that exists in the list of tasks.
    private void selectModifyTask(TaskList taskList) {
        Scanner keyboard = new Scanner(System.in);
        int input;
        messages.selectTaskMessage(taskList);

        try {
            input = keyboard.nextInt();

            if (! (input == 0)) {
                try {
                    attemptModifyTask(taskList, input);
                } catch (IndexOutOfBoundsException e) {
                    messages.outOfBoundsError();
                } catch (UIException e) {
                    messages.exceptionErrorMessage(e);
                }
            }
        } catch (InputMismatchException e) {
            messages.notIntegerError();
        }
    }

    //REQUIRES: taskList.get(index) must be of type IncompleteTask or ImportantTask
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
    private Boolean modifyTask(String input, int index, Task task, TaskList taskList) {
        if ((input.equals("1"))) {
            //set complete
            setTaskComplete(taskList, index);
        } else if ((input.equals("2"))) {
            //change due date
            task.setDueDate(taskInputDecisions.setMonthAndDay(task.getDueDateObj()));
            //task.setTimeLeft();
        } else if ((input.equals("3"))) {
            //change urgency
            task.setUrgency(taskInputDecisions.setUrgencyDecision(task.getUrgency()));
        } else if ((input.equals("4"))) {
            //change content
            task.setContent(taskInputDecisions.setTaskContentDecisions());
        } else if ((input.equals("5"))) {
            //sets starred to un-starred if starred, sets un-starred to stared
            task.setStarred(taskInputDecisions.setStarredDecisions(task.isStarred()));
            messages.printTask(task);
        } else if ((input.equals("0"))) {
            //return to prev
            selectModifyTask(taskList);
        } else {
            //no selection
            return false;
        }
        //valid selection
        return true;
    }

    //REQUIRES: taskList.get(index) instanceOf IncompleteTask
    //MODIFIES: taskList.get(index)
    //EFFECTS: Prompts the user to select which field of a incomplete task to modify
    //         Prints not an option error message if the user did not select a valid option
    private void modifyIncompleteTask(TaskList taskList, int index) throws NotAnOptionException {
        messages.modifyIncompleteTaskMessage();
        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();

        Task task = taskList.getTask(index);

        if (!modifyTask(input, index, task, taskList)) {
            throw new NotAnOptionException();
        }
    }

    //MODIFIES: taskList.get(index)
    //EFFECTS: Prompts the user to modify important task is taskList.get(index) is an ImportantTask.
    //         Prompts the user to modify incomplete task if taskList.get(index) is a IncompleteTask.
    //         Else return cannot modify completed task error message.
    private void attemptModifyTask(TaskList taskList, int index) throws UIException {
/*        if (taskList.getTask(index).isCompleted()) {
            modifyIncompleteTask(taskList, index);
        } else {
            throw new ModifyCompletedTaskException();
        }*/
        modifyIncompleteTask(taskList, index);
    }

    //MODIFIES: taskList.get(index)
    //EFFECTS: Removes the given important task or incomplete task from the list of all tasks.
    //         Creates a new completed task with the content, due date of the give task and the current time
    //         Stores this completed task in the list of all tasks.
    private void setTaskComplete(TaskList taskList, int index) {
        //Task task = taskList.getTask(index);
        taskList.getTask(index).setCompleted(true);
/*        try {
            new CompletedTask(
                    taskList,
                    task.getContent(),
                    task.getDueDateObj(),
                    task.getDate(LocalDate.now()));
        } catch (TaskException e) {
            messages.exceptionErrorMessage(e);
        }*/
        try {
            taskList.deleteTask(index);
        } catch (TaskDoesNotExistException | TaskException e) {
            messages.exceptionErrorMessage(e);
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Creates new incomplete task or important task  with the following initial variables.
    //         Prompts the user to assign or enter task information.
    //         Adds this new task to the list of tasks.
    //         Else returns not an option error.
    private void selectEnterTask(TaskList taskList) throws TaskException {
        String taskContent = "empty task";
        LocalDate taskDueDate = LocalDate.now();
        Task task = new Task(null, taskContent, taskDueDate, Urgency.UNASSIGNED, false, false);
        setIncompleteTask(taskList, task);
    }

    //MODIFIES: taskList, task
    //EFFECTS: Prompts the user to set all fields in a incomplete task.
    //         Sets all fields present in incomplete tasks if given a important task.
    private void setIncompleteTask(TaskList taskList, Task task) {
        Scanner keyboard = new Scanner(System.in);

        task.setContent(taskInputDecisions.setTaskContentDecisions());
        task.setUrgency(taskInputDecisions.setUrgencyDecision(task.getUrgency()));
        messages.useDefaultDateMessage();
        if ((keyboard.nextLine()).equalsIgnoreCase("y")) {
            task.setDueDate(taskInputDecisions.setMonthAndDay(task.getDueDateObj()));
        }

        try {
            taskList.storeTask(task);
        } catch (TaskException e) {
            messages.exceptionErrorMessage(e);
        }

        //task.setTimeLeft();
    }

    //EFFECTS: Prompts the user to select between:
    //         (1) Prints all tasks in the current task list
    //         (2) Prints tasks in the current task list based on urgency
    //         else display not an option error and restarts the method
    private void selectViewTasksBy(TaskList taskList) throws NotAnOptionException {
        messages.selectViewTasksByMessage();

        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();
        if (input.equals("1")) {
            messages.printList(taskList);
        } else if (input.equals("2")) {
            selectViewTaskByUrgency(taskList);
        } else {
            throw new NotAnOptionException();
        }
    }

    //EFFECTS: Prompts the user to select an urgency level and prints out all tasks in the current task list with the
    //         selected urgency level. Displays not an option error message if input is not an urgency level and
    //         restarts the method.
    private void selectViewTaskByUrgency(TaskList taskList) throws NotAnOptionException {
        String high = "high";
        String mid = "mid";
        String low = "low";

        Scanner keyboard = new Scanner(System.in);
        messages.selectUrgencyMessage();
        String input = keyboard.nextLine();
        if (input.equalsIgnoreCase(high) || input.equalsIgnoreCase(mid) || input.equalsIgnoreCase(low)) {
            messages.printIncompleteTasksList(taskList.getTaskByUrgency(input));
        } else {
            throw new NotAnOptionException();
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Prompts the user to enter a number to select which task from the task list to delete.
    //         Check if user input is valid and that the user is selecting a task that exists in the list of tasks.
    private void selectToDeleteTask(TaskList taskList) {
        Scanner keyboard = new Scanner(System.in);
        int input;
        messages.selectTaskMessage(taskList);

        try {
            input = keyboard.nextInt();
            if (!(input == 0)) {
                taskList.deleteTask(input);
            }
        } catch (InputMismatchException e) {
            messages.notIntegerError();
        } catch (TaskDoesNotExistException | TaskException e) {
            messages.exceptionErrorMessage(e);
        }

        messages.printList(taskList);
    }

    //MODIFIES: taskList
    //EFFECTS: removes all tasks from the current list of tasks
    private void selectDeleteAllTasks(TaskList taskList) {
        Scanner keyboard = new Scanner(System.in);
        messages.checkBeforeDeleteAll();
        if (keyboard.nextLine().equals("yes")) {
            taskList.clearTaskList();
            messages.taskDeletedMessage();
            messages.printList(taskList);
        }
    }

    //parts involving PrintStream inspired by
    //https://stackoverflow.com/questions/8363493/hiding-system-out-print-calls-of-a-class
    //MODIFIES: taskList
    //EFFECTS: Attempts to load task info from save
    private void tryLoad(TaskListHashMap taskListHashMap) {
        Load loadTasks = new Load();
        PrintStream originalStream = System.out;
        try {
            dummyStream();
            loadTasks.load(taskListHashMap, saveFile);
            System.setOut(originalStream);
        } catch (IOException e) {
            System.setOut(originalStream);
            messages.fileNotFoundError();
        } catch (TaskException e) {
            System.setOut(originalStream);
            messages.exceptionErrorMessage(e);
        } catch (Exception e) {
            System.setOut(originalStream);
            messages.badFormattingError();
        } finally {
            messages.loadAttemptedMessage();
        }
    }

    //MODIFIES: taskListHashMap
    //EFFECTS: menu selection for the user to select between:
    //         (1) Select an existing taskList
    //         (2) Create a new taskList
    //         (3) Delete an existing taskList
    //         else throw not an option error
    private void selectListOptions(TaskListHashMap taskListHashMap) throws NotAnOptionException {
        Scanner keyboard = new Scanner(System.in);
        messages.selectListOptionsMessage();
        String input = keyboard.nextLine();

        if ((input.equals("1"))) {
            try {
                selectExistingTaskLists(taskListHashMap);
            } catch (NoListsFoundException e) {
                messages.exceptionErrorMessage(e);
            }
        } else if ((input.equals("2"))) {
            createNewTaskList(taskListHashMap);
        } else if ((input.equals("3"))) {
            deleteTaskList(taskListHashMap);
        } else {
            throw new NotAnOptionException();
        }
    }

    //MODIFIES: taskListHashMap
    //EFFECTS: removes the corresponding key and TaskList based on user input
    private void deleteTaskList(TaskListHashMap taskListHashMap) {
        Scanner keyboard = new Scanner(System.in);
        messages.deleteListMessage(taskListHashMap);
        String key = keyboard.nextLine();

        if (taskListHashMap.getKeys().contains(key)) {
            taskListHashMap.removeTaskList(new Name(key));
        }
    }

    //MODIFIES: taskListHashMap
    //EFFECTS: Prompts the user to select an existing TaskList stored in the given HashMap
    private void selectExistingTaskLists(TaskListHashMap taskListHashMap)
            throws NotAnOptionException, NoListsFoundException {
        Scanner keyboard = new Scanner(System.in);

        if (!taskListHashMap.getKeys().isEmpty()) {
            messages.selectListMessage(taskListHashMap);
            String input = keyboard.nextLine();
            if (taskListHashMap.getTaskList(new Name(input)) != null) {
                runUserSelection(taskListHashMap.getTaskList(new Name(input)));
            } else {
                throw new NotAnOptionException();
            }
        } else {
            throw new NoListsFoundException();
        }
    }

    //MODIFIES: taskListHashMap
    //EFFECTS: creates a new TaskList based on user input and stores it in the given HashMap
    //         If the user enters a name already taken by a TaskList, then output error message
    private void createNewTaskList(TaskListHashMap taskListHashMap) {
        Scanner keyboard = new Scanner(System.in);
        messages.createNewListMessage();
        String input = keyboard.nextLine();

        if (!taskListHashMap.getKeys().contains(input)) {
            TaskList taskList = new TaskList(new Name(input));
            taskListHashMap.storeTaskList(taskList);
            runUserSelection(taskListHashMap.getTaskList(new Name(input)));
        } else {
            messages.nameAlreadyExistsError();
        }
    }

    //MODIFIES: taskList
    //EFFECTS: Runs the userSelection menu for the given TaskList
    //         Does not stop upon not an option errors
    //         Stops when the user enters "exit"
    private void runUserSelection(TaskList taskList) {
        boolean stop = false;
        while (!stop) {
            messages.optionsMessage();
            try {
                stop = userSelection(taskList);
            } catch (NotAnOptionException | TaskException e) {
                messages.exceptionErrorMessage(e);
                stop = checkExitList();
            }
        }
    }

    //EFFECTS: Displays welcome message and runs the program while the user does not exit.
    //         Load list of tasks from save file, displays file not found error if file not found
    //         Saves list of tasks once the user selects exit.
    void run() {
        messages.welcomeMessage();

        TaskListHashMap taskListHashMap = new TaskListHashMap();
        Save saveTasks = new Save();

        tryLoad(taskListHashMap);

        do {
            try {
                selectListOptions(taskListHashMap);
            } catch (NotAnOptionException e) {
                messages.exceptionErrorMessage(e);
            }
        } while (!checkExit());

        try {
            saveTasks.save(taskListHashMap, saveFile);
        } catch (IOException e) {
            messages.fileNotFoundError();
        }
    }

    //inspired by https://stackoverflow.com/questions/8363493/hiding-system-out-print-calls-of-a-class
    //EFFECTS: creates a dummy system.out stream to hide system.out
    private void dummyStream() {
        PrintStream dummyStream = new PrintStream(new OutputStream() {
            public void write(int b) {
                // NO-OP
            }
        });
        System.setOut(dummyStream);
    }
}
