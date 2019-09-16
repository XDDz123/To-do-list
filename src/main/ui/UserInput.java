package ui;

import java.util.*;

public class UserInput extends Messages {
    //private String InputTemp1 = "";
    //private String InputBoolean1;
    //private ArrayList<String> InputStorage = new ArrayList<>();

    /*public void TextInput()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter text input here (type 'stop' to stop): ");

        while(!(InputTemp1).equals("stop"))
        {
            InputTemp1 = keyboard.nextLine();
            if(!(InputTemp1).equals("stop"))
            {
                InputStorage.add(InputTemp1);
            }
        }

        System.out.println(InputStorage);

    }*/

    /*public static Boolean CheckInput()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Press y to start input: ");
        return (keyboard.nextLine()).equalsIgnoreCase("y");
    }*/

    public Boolean checkExit() {
        Scanner keyboard = new Scanner(System.in);
        return (keyboard.nextLine()).equalsIgnoreCase("exit");
    }

    public void userSelection(Memory memory) {
        Scanner keyboard = new Scanner(System.in);
        String temp = keyboard.nextLine();

        if ((temp.equals("1"))) {
            selectEnterTask(memory);
        } else if ((temp.equals("2"))) {
            selectPrintAllTasks(memory);
        } else if ((temp.equals("3"))) {
            selectToDeleteTask(memory, keyboard);
        } else if ((temp.equals("4"))) {
            selectDeleteAllTasks(memory);
        } else {
            selectNotAnOption();
        }
    }

    public void selectEnterTask(Memory memory) {
        taskMessage();
        Task task = new Task();
        task.createTask();
        memory.storeTask(task);
        continueMessage();
    }

    public void selectPrintAllTasks(Memory memory) {
        printList(memory);
        continueMessage();
    }

    public void selectToDeleteTask(Memory memory, Scanner keyboard) {
        int temp;
        selectTaskMessage(memory);

        if (keyboard.hasNextInt()) {
            temp = keyboard.nextInt();

            if (temp > (memory.getTaskList()).size()) {
                outOfBounds();
            } else {
                memory.deleteTask(temp);
            }
        } else {
            notInteger();
        }

        printList(memory);
        continueMessage();
    }

    public void selectDeleteAllTasks(Memory memory) {
        memory.clearTaskList();
        printList(memory);
    }

    public void selectNotAnOption() {
        notAnOption();
        continueMessage();
    }
}
