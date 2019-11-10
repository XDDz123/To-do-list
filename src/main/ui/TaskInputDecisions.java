package ui;

import model.task.Urgency;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Scanner;

class TaskInputDecisions {

    private static final String high = "high";
    private static final String mid = "mid";
    private static final String low = "low";
    private Urgency taskUrgency;
    private LocalDate taskDueDate;
    private final Messages messages = new Messages();

    //MODIFIES: this
    //EFFECTS: Checks the validity of the user input and returns a task urgency based on user input:
    //         (1) high
    //         (2) mid
    //         (3) low
    //         else displays error message and restarts the method.
    Urgency setUrgencyDecision(Urgency taskUrgency) {
        messages.setUrgencyMessage();
        Scanner keyboard = new Scanner(System.in);

        if (keyboard.hasNextInt()) {
            int input = keyboard.nextInt();

            if (input == 1) {
                return this.taskUrgency = Urgency.HIGH;
            } else if (input == 2) {
                return this.taskUrgency = Urgency.MID;
            } else if (input == 3) {
                return this.taskUrgency = Urgency.LOW;
            } else {
                return setUrgencyError(taskUrgency);
            }
        } else {
            return setUrgencyError(taskUrgency);
        }
    }

    //EFFECTS: Displays error message and starts the method that prompts the user to set urgency.
    private Urgency setUrgencyError(Urgency taskUrgency) {
        messages.notAnOptionTryAgainError();
        setUrgencyDecision(taskUrgency);
        return this.taskUrgency;
    }

    //EFFECTS: Prompts user to set due date. Returns a new LocalDate created from user input.
    LocalDate setMonthAndDay(LocalDate taskDueDate) {
        messages.setDueDateMessage();
        return setYear(setDay(setMonth(taskDueDate)));
    }

    //MODIFIES: this
    //EFFECTS: Checks the validity of the user input and returns a LocalDate with modified month based on user input.
    private LocalDate setMonth(LocalDate taskDueDate) {
        messages.enterMonthMessage();
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
            messages.dayMonthError();
            setMonth(taskDueDate);
            return this.taskDueDate;
        }
    }

    //MODIFIES: this
    //EFFECTS: Checks the validity of the user input and returns a LocalDate with modified day based on user input:
    private LocalDate setDay(LocalDate taskDueDate) {
        messages.enterDayMessage();
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
            messages.dayMonthError();
            setDay(taskDueDate);
            return this.taskDueDate;
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the year for this due date depending on the given due date.
    //         If the given due date is before the current day, set year to next year.
    //         else return the given due date.
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
        messages.dayMonthError();
        return false;
    }

    //EFFECTS: Prompts the user to enter the task content of a task.
    //         Returns the user input.
    String setTaskContentDecisions() {
        messages.taskMessage();
        Scanner keyboard = new Scanner(System.in);
        return keyboard.nextLine();
    }

    //MODIFIES: task
    //EFFECTS: Prompts the user to star or un-star the given task
    //         Inverts starred to un-starred and vise versa, returns the corresponding boolean
    Boolean setStarredDecisions(Boolean starred) {
        if (starred) {
            messages.unStarTaskMessage();
        } else {
            messages.starTaskMessage();
        }
        return !starred;
    }
}
