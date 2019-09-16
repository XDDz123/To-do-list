package ui;

public class Main {
    /*public static void print1()
    {
        System.out.println("some text here");
    }

    public static void print2()
    {
        System.out.println("some more text here");
    }*/


    public static void main(String[] args) {
        /*print1();
        print2();
        TextPrints.print3();

        String input = "natural recursion";

        System.out.println(TextPrints.recursion1(input));*/

        /*if(UserInput.CheckInput())
        {
            UserInput UserInput1 = new UserInput();
            UserInput1.TextInput();

        }*/

        UserInput userInput = new UserInput();
        Memory memory = new Memory();

        userInput.welcomeMessage();

        do {
            userInput.optionsMessage();
            userInput.userSelection(memory);
        } while (!userInput.checkExit());

    }
}
