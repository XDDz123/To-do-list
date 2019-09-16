package ui;

public class Messages {

    public void welcomeMessage() {
        System.out.println("Type in 'exit' to exit this program.");
        System.out.println("No data will be saved after this session. Feature coming in a future update.");
        System.out.println("  ");
        System.out.println("  ");
        System.out.println("------------------------------------------------------------");
    }

    public void optionsMessage() {
        //System.out.println("Character checks not implemented yet,
        //please type in what you are supposed to type in. Or maybe they are...");
        System.out.println("Type in '1' to enter a new task.");
        System.out.println("Type in '2' to see all entered tasks.");
        System.out.println("Type in '3' to delete a task.");
        System.out.println("Type in '4' to delete all entered tasks.");
    }

    public void continueMessage() {
        System.out.println("Press anything to continue or type 'exit' to exit");
    }

    public void taskMessage() {
        System.out.println("Enter your task here: ");
    }

    //move method to Memory class
    public void printList(Memory memory) {
        System.out.println("-------------------------------------");
        if (memory.isTaskListEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (int i = 0; i < (memory.getTaskListSize()); i++) {
                System.out.println((i + 1) + " : " + (memory.getTask(i)).getTaskContent());
            }
        }
        System.out.println("-------------------------------------");
    }

    public void selectTaskMessage(Memory memory) {
        System.out.println("Select task by entering number");
        printList(memory);
        System.out.println("Selection: ");
    }

    public void outOfBounds() {
        System.out.println("Selection out of bounds!");
    }

    public void notInteger() {
        System.out.println("Error enter an integer!");
    }

    public void notAnOption() {
        System.out.println("Error! Not an option.");
    }
}
