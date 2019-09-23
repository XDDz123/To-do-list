package ui;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.Scanner;

public class TaskBehavior extends Messages {

    private final String high = "high";
    private final String mid = "mid";
    private final String low = "low";
    private String taskUrgencyTemp = "unassigned";
    private  MonthDay taskDueDateTemp;

    //EFFECTS: Checks the validity of the user input and returns a task urgency based on user input:
    //         (1) high
    //         (2) mid
    //         (3) low
    //         else displays error message and restarts the method.
    //MODIFIES: this

    public String setUrgencyBehavior(String taskUrgency) {
        setUrgencyMessage();
        Scanner keyboard = new Scanner(System.in);

        if (keyboard.hasNextInt()) {
            int selection = keyboard.nextInt();

            if (selection == 1) {
                return taskUrgencyTemp = high;
            } else if (selection == 2) {
                return taskUrgencyTemp = mid;
            } else if (selection == 3) {
                return taskUrgencyTemp = low;
            } else {
                return setUrgencyHelper(taskUrgency);
            }
        } else {
            return setUrgencyHelper(taskUrgency);
        }
    }

    //EFFECTS: Displays error message and starts the method that prompts the user to set urgency.
    public String setUrgencyHelper(String taskUrgency) {
        urgencyError();
        setUrgencyBehavior(taskUrgency);
        return taskUrgencyTemp;
    }

    //EFFECTS: Prompts user to set due date. Returns a new MonthDay created from user input.
    public MonthDay setMonthAndDay(MonthDay taskDueDate) {
        setDueDateMessage();
        return setDay(setMonth(taskDueDate));
    }

    //EFFECTS: Checks the validity of the user input and returns a MonthDay with modified month based on user input.
    //MODIFIES: this
    public MonthDay setMonth(MonthDay taskDueDate) {
        enterMonthMessage();
        Scanner keyboard = new Scanner(System.in);
        int month;
        if (keyboard.hasNextInt()) {
            month = keyboard.nextInt();
            if (checkMonth(month)) {
                return taskDueDateTemp = taskDueDate.with(Month.of(month));
            } else {
                setMonth(taskDueDate);
                return taskDueDateTemp;
            }
        } else {
            dayMonthError();
            setMonth(taskDueDate);
            return taskDueDateTemp;
        }
    }

    //EFFECTS: Checks the validity of the user input and returns a MonthDay with modified day based on user input:
    //MODIFIES: this
    public MonthDay setDay(MonthDay taskDueDate) {
        enterDayMessage();
        Scanner keyboard = new Scanner(System.in);
        int day;
        if (keyboard.hasNextInt()) {
            day = keyboard.nextInt();
            if (checkDay(day, taskDueDate)) {
                return taskDueDateTemp = taskDueDate.withDayOfMonth(day);
            } else {
                setDay(taskDueDate);
                return taskDueDateTemp;
            }
        } else {
            dayMonthError();
            setDay(taskDueDate);
            return taskDueDateTemp;
        }
    }

    //EFFECTS: Takes the system/machine time and checks whether the current year is a leap year, if so return true,
    //         returns false otherwise.
    //MODIFIES: this
    private boolean checkLeapYear() {
        Year year = Year.now();
        return year.isLeap();
    }

    //EFFECTS: Checks if the given month is within 1 and 12, return true if within, return false otherwise.
    public boolean checkMonth(int month) {
        if (month >= 1 && month <= 12) {
            return true;
        } else {
            return checkDayMonthHelper();
        }
    }

    //EFFECTS: Check if the given day exists in the given month, return true if it exists, return false otherwise.
    public boolean checkDay(int day, MonthDay taskDueDate) {
        if (day >= 1 && day <= taskDueDate.getMonth().length(checkLeapYear())) {
            return true;
        } else {
            return checkDayMonthHelper();
        }
    }

    //EFFECTS: Prints not a day or month error message, returns false.
    public boolean checkDayMonthHelper() {
        dayMonthError();
        return false;
    }

    //EFFECTS: Prompts the user to enter the task content of a task.
    //         Returns the user input.
    public String setTaskContentBehavior() {
        taskMessage();
        Scanner keyboard = new Scanner(System.in);
        return keyboard.nextLine();
    }
}
