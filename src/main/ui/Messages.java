/*
package ui;

import model.task.Task;
import model.tasklist.TaskList;
import model.TaskListHashMap;
import model.tasklist.TaskListToString;
import network.JokeFetcher;

import java.util.ArrayList;

public class Messages {

    //EFFECTS: Prints the following.
    void welcomeMessage() {
        //System.out.println("Type in 'exit' to exit this program.");
        System.out.println("  ");
        System.out.println((new JokeFetcher()).fetchJoke());
        System.out.println("  ");
        System.out.println("Your data will be automatically saved after this session.");
        System.out.println("  ");
    }

    //EFFECTS: Prints the following.
    void optionsMessage() {
        //System.out.println("Character checks not implemented yet,
        //please type in what you are supposed to type in. Or maybe they are...");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Type in '1' to enter a new task.");
        System.out.println("Type in '2' to see all entered tasks.");
        System.out.println("Type in '3' to delete a task or all tasks.");
        System.out.println("Type in '4' to modify a task.");
        System.out.println("Type in '5' to sort tasks by due date.");
    }

    //EFFECTS: Prints the following.
    void selectListOptionsMessage() {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Type in '1' to view existing task lists.");
        System.out.println("Type in '2' to create a new task list.");
        System.out.println("Type in '3' to delete a task list.");
    }

    //EFFECTS: Prints the following.
    void deleteListMessage(TaskListHashMap taskListHashMap) {
        System.out.println(taskListHashMap.getKeys().toString());
        System.out.println("Enter the name of the task list to delete: ");
    }

    //EFFECTS: Prints the following.
    void selectListMessage(TaskListHashMap taskListHashMap) {
        System.out.println(taskListHashMap.getKeys().toString());
        System.out.println("Enter the name of the task list to select: ");
    }

    void createNewListMessage() {
        System.out.println("Enter the name of your new task list: ");
    }

    //EFFECTS: Prints the following.
    void continueMessage() {
        System.out.println("Press enter to continue or type 'exit' to exit from the program.");
    }

    //EFFECTS: Prints the following.
    void continueMessageForList() {
        System.out.println("Press enter to continue or type 'exit' to exit from the current list.");
    }

    //EFFECTS: Prints the following.
    void selectDeleteTaskMessage() {
        System.out.println("Type in '1' to delete a task.");
        System.out.println("Type in '2' to delete all tasks.");
        System.out.println("Type in '0' to return to the previous menu.");
    }

    //EFFECTS: Prints the following.
    void selectViewTasksByMessage() {
        System.out.println("Type in '1' to view all tasks.");
        System.out.println("Type in '2' to view all tasks by urgency.");
    }

    //EFFECTS: Prints the following.
    void taskMessage() {
        System.out.println("Enter your task here: ");
    }

    //EFFECTS: Prints the following.
    void setUrgencyMessage() {
        System.out.println("Set urgency. Enter '1' for high urgency, '2' for mid urgency, '3' for low urgency.");
    }

    //EFFECTS: Prints the following.
    void notAnOptionTryAgainError() {
        System.out.println("Not an option. Please try again.");
        System.out.println("  ");
    }

    //EFFECTS: Prints the following.
    void selectUrgencyMessage() {
        System.out.println("Select urgency. Type in 'high', 'mid' or 'low': ");
    }

    //EFFECTS: Prints the following.
    void setDueDateMessage() {
        System.out.println("Set a due date for your task.");
    }

    //EFFECTS: Prints the following.
    void useDefaultDateMessage() {
        System.out.println("Enter 'y' to set a due date. Unset due dates are defaulted to the current date.");
        System.out.println("Press enter for default due date.");
    }

    //EFFECTS: Prints the following.
    void enterMonthMessage() {
        System.out.print("Enter month: ");
    }

    //EFFECTS: Prints the following.
    void enterDayMessage() {
        System.out.print("Enter Day: ");
    }

    //EFFECTS: Prints the following.
    void dayMonthError() {
        System.out.println("Not a valid day or month.");
    }

    //EFFECTS: Prints the following.
    void modifyIncompleteTaskMessage() {
        System.out.println("Type in '1' to set task as complete.");
        System.out.println("Type in '2' to change task due date.");
        System.out.println("Type in '3' to change task urgency.");
        System.out.println("Type in '4' to change task content.");
        System.out.println("Type in '5' to star or un-star task.");
        System.out.println("Type in '0' to return to the previous menu.");
    }

*/
/*    //EFFECTS: Prints the following.
    void printTask(Task task) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(task.printTask());
        System.out.println("-----------------------------------------------------------------------------------------");
    }*//*


    //EFFECTS: Prints the current task list in the following format.
    void printList(TaskList taskList) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(taskList.printTaskList());
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    //EFFECTS: Prints the current task list in the following format.
    void printIncompleteTasksList(ArrayList<Task> taskList) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println((new TaskListToString()).printTaskList(taskList));
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    //EFFECTS: Prints the current task list and the following text.
    void selectTaskMessage(TaskList taskList) {
        System.out.println("Select task by entering number");
        printList(taskList);
        System.out.println("Type in '0' to return to the previous menu.");
        System.out.println("Selection: ");
    }

    //EFFECTS: Prints the following.
    void checkBeforeDeleteAll() {
        System.out.println("Delete all tasks?");
        System.out.println("Type in 'yes' to delete all tasks.");
        System.out.println("Type in anything else to cancel.");
    }

    //EFFECTS: Prints the following.
    void taskDeletedMessage() {
        System.out.println("Tasks deleted successfully.");
    }

    //EFFECTS: Prints the following.
    void starTaskMessage() {
        System.out.println("Task is now starred.");
    }

    //EFFECTS: Prints the following.
    void unStarTaskMessage() {
        System.out.println("Task is now un-starred.");
    }

    //EFFECTS: Prints the following.
    void nameAlreadyExistsError() {
        System.out.println("Error! A list with the same name already exists, try a different name.");
    }

    //EFFECTS: Prints the following.
    void outOfBoundsError() {
        System.out.println("Selection out of bounds!");
    }

    //EFFECTS: Prints the following.
    void notIntegerError() {
        System.out.println("Error enter an integer!");
    }

    //EFFECTS: Prints the following.
    void exceptionErrorMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    //EFFECTS: Prints the following.
    void loadAttemptedMessage() {
        System.out.println("Loading attempted.");
    }

    //EFFECTS: Prints the following.
    void fileNotFoundError() {
        System.out.println("File not found or file empty!");
    }

    //EFFECTS: Prints the following.
    void badFormattingError() {
        System.out.println("bad formatting");
    }

    //EFFECTS: Prints the following.
    public void listSizeObserverMessage(int size, String name) {
        System.out.println("There are now " + size + " incomplete task(s) in the list named <"
                + name + ">.");
    }
}
*/
