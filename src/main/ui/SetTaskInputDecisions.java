package ui;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Scanner;

class SetTaskInputDecisions extends Messages {

    private static final String high = "high";
    private static final String mid = "mid";
    private static final String low = "low";
    private String taskUrgency = "unassigned";
    private LocalDate taskDueDate;
    private String taskImportance = "unassigned";

    private static final String importanceExtreme = "Extreme Importance";
    private static final String importanceHigh = "High Importance";
    private static final String importanceMid = "Medium Importance";


    //MODIFIES: this
    //EFFECTS: Checks the validity of the user input and returns a task urgency based on user input:
    //         (1) high
    //         (2) mid
    //         (3) low
    //         else displays error message and restarts the method.
    String setUrgencyDecision(String taskUrgency) {
        setUrgencyMessage();
        Scanner keyboard = new Scanner(System.in);

        if (keyboard.hasNextInt()) {
            int input = keyboard.nextInt();

            if (input == 1) {
                return this.taskUrgency = high;
            } else if (input == 2) {
                return this.taskUrgency = mid;
            } else if (input == 3) {
                return this.taskUrgency = low;
            } else {
                return setUrgencyError(taskUrgency);
            }
        } else {
            return setUrgencyError(taskUrgency);
        }
    }

    //EFFECTS: Displays error message and starts the method that prompts the user to set urgency.
    private String setUrgencyError(String taskUrgency) {
        notAnOptionTryAgainError();
        setUrgencyDecision(taskUrgency);
        return this.taskUrgency;
    }

    //EFFECTS: Prompts user to set due date. Returns a new LocalDate created from user input.
    LocalDate setMonthAndDay(LocalDate taskDueDate) {
        setDueDateMessage();
        return setYear(setDay(setMonth(taskDueDate)));
    }

    //MODIFIES: this
    //EFFECTS: Checks the validity of the user input and returns a LocalDate with modified month based on user input.
    private LocalDate setMonth(LocalDate taskDueDate) {
        enterMonthMessage();
        Scanner keyboard = new Scanner(System.in);
        int month;
        if (keyboard.hasNextInt()) {
            month = keyboard.nextInt();
            if (checkMonth(month)) {
                return this.taskDueDate = taskDueDate.with(Month.of(month));
            } else {
                setMonth(taskDueDate);
                return this.taskDueDate;
            }
        } else {
            dayMonthError();
            setMonth(taskDueDate);
            return this.taskDueDate;
        }
    }

    //MODIFIES: this
    //EFFECTS: Checks the validity of the user input and returns a LocalDate with modified day based on user input:
    private LocalDate setDay(LocalDate taskDueDate) {
        enterDayMessage();
        Scanner keyboard = new Scanner(System.in);
        int day;
        if (keyboard.hasNextInt()) {
            day = keyboard.nextInt();
            if (checkDay(day, taskDueDate)) {
                return this.taskDueDate = taskDueDate.withDayOfMonth(day);
            } else {
                //restarts set month and set day
                setMonthAndDay(taskDueDate);
                return this.taskDueDate;
            }
        } else {
            dayMonthError();
            setDay(taskDueDate);
            return this.taskDueDate;
        }
    }

    private LocalDate setYear(LocalDate taskDueDate) {
        if (taskDueDate.isBefore(LocalDate.now())) {
            return this.taskDueDate.withYear(Year.now().getValue() + 1);
        } else {
            return this.taskDueDate;
        }
    }

    //MODIFIES: this
    //EFFECTS: Takes the system/machine time and checks whether the current year is a leap year, if so return true,
    //         returns false otherwise.
    private boolean checkLeapYear() {
        Year year = Year.now();
        return year.isLeap();
    }

    //EFFECTS: Checks if the given month is within 1 and 12, return true if within, return false otherwise.
    boolean checkMonth(int month) {
        if (month >= 1 && month <= 12) {
            return true;
        } else {
            return checkDayMonthHelper();
        }
    }

    //EFFECTS: Check if the given day exists in the given month, return true if it exists, return false otherwise.
    boolean checkDay(int day, LocalDate taskDueDate) {
        if (day >= 1 && day <= taskDueDate.getMonth().length(checkLeapYear())) {
            return true;
        } else {
            return checkDayMonthHelper();
        }
    }

    //EFFECTS: Prints not a day or month error message, returns false.
    private boolean checkDayMonthHelper() {
        dayMonthError();
        return false;
    }

    //EFFECTS: Prompts the user to enter the task content of a task.
    //         Returns the user input.
    String setTaskContentDecisions() {
        taskMessage();
        Scanner keyboard = new Scanner(System.in);
        return keyboard.nextLine();
    }

    //EFFECTS: Prompts the user to set the level of importance of a task
    //         Returns the selected level of importance
    //         (1) Extreme Importance
    //         (2) High Importance
    //         (3) Medium Importance
    //         else displays error message and restarts the method.
    String setImportanceDecision(String taskImportance) {
        setImportanceMessage();

        Scanner keyboard = new Scanner(System.in);

        if (keyboard.hasNextInt()) {
            int input = keyboard.nextInt();

            if (input == 1) {
                return this.taskImportance = importanceExtreme;
            } else if (input == 2) {
                return this.taskImportance = importanceHigh;
            } else if (input == 3) {
                return this.taskImportance = importanceMid;
            } else {
                return setImportanceError(taskImportance);
            }
        } else {
            return setImportanceError(taskImportance);
        }
    }

    //EFFECTS: Displays error message and starts the method that prompts the user to set importance.
    private String setImportanceError(String taskImportance) {
        notAnOptionTryAgainError();
        setImportanceDecision(taskImportance);
        return this.taskImportance;
    }
}
