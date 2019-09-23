package ui;

import ui.model.TaskList;

public class Messages {

    //EFFECTS: Prints the following.
    public void welcomeMessage() {
        System.out.println("Type in 'exit' to exit this program.");
        System.out.println("No data will be saved after this session. Feature coming in a future update.");
        System.out.println("  ");
        System.out.println("  ");
    }

    //EFFECTS: Prints the following.
    public void optionsMessage() {
        //System.out.println("Character checks not implemented yet,
        //please type in what you are supposed to type in. Or maybe they are...");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Type in '1' to enter a new task.");
        System.out.println("Type in '2' to see all entered tasks.");
        System.out.println("Type in '3' to delete a task.");
        System.out.println("Type in '4' to delete all entered tasks.");
        System.out.println("Type in '5' to modify a task.");
    }

    //EFFECTS: Prints the following.
    public void continueMessage() {
        System.out.println("Press enter to continue or type 'exit' to exit");
    }

    //EFFECTS: Prints the following.
    public void selectViewTasksByMessage() {
        System.out.println("Type in '1' to view all tasks.");
        System.out.println("Type in '2' to view all tasks by urgency.");
    }

    //EFFECTS: Prints the following.
    public void taskMessage() {
        System.out.println("Enter your task here: ");
    }

    //EFFECTS: Prints the following.
    public void setUrgencyMessage() {
        System.out.println("Set urgency. Enter 1 for high urgency, 2 for mid urgency, 3 for low urgency.");
    }

    //EFFECTS: Prints the following.
    public void urgencyError() {
        System.out.println("Not an option. Please try again.");
        System.out.println("  ");
    }

    //EFFECTS: Prints the following.
    public void selectUrgencyMessage() {
        System.out.println("Select urgency. Type in 'high', 'mid' or 'low': ");
    }

    //EFFECTS: Prints the following.
    public void setDueDateMessage() {
        System.out.println("Set a due date for your task.");
    }

    //EFFECTS: Prints the following.
    public void useDefaultDateMessage() {
        System.out.println("Enter 'y' to set a due date. Unset due dates are defaulted to the current date.");
        System.out.println("Press enter to skip.");
    }

    //EFFECTS: Prints the following.
    public void enterMonthMessage() {
        System.out.print("Enter month: ");
    }

    //EFFECTS: Prints the following.
    public void enterDayMessage() {
        System.out.print("Enter Day: ");
    }

    //EFFECTS: Prints the following.
    public void dayMonthError() {
        System.out.println("Not a valid day or month.");
    }

    //EFFECTS: Prints the following.
    public void modifyTaskMessage() {
        System.out.println("Type in '1' to set task as complete.");
        System.out.println("Type in '2' to change task due date.");
        System.out.println("Type in '3' to change task urgency.");
        System.out.println("Type in '4' to change task content.");
        System.out.println("Type in '5' to return to previous menu.");
    }

    //EFFECTS: Prints the current task list in the following format.
    public void printList(TaskList taskList) {
        System.out.println("-------------------------------------------------------------------------------");
        taskList.printTaskList();
        System.out.println("-------------------------------------------------------------------------------");
    }

    //EFFECTS: Prints the current task list and the following text.
    public void selectTaskMessage(TaskList taskList) {
        System.out.println("Select task by entering number");
        printList(taskList);
        System.out.println("Enter '0' to return to previous menu.");
        System.out.println("Selection: ");
    }

    //EFFECTS: Prints the following.
    public void outOfBoundsError() {
        System.out.println("Selection out of bounds!");
    }

    //EFFECTS: Prints the following.
    public void notIntegerError() {
        System.out.println("Error enter an integer!");
    }

    //EFFECTS: Prints the following.
    public void notAnOptionError() {
        System.out.println("Error! Not an option.");
    }
}
