package model;

import java.time.MonthDay;
import java.time.Period;
import java.time.Year;

public class ImportantTask extends RegularTask {

    private String timeLeft;
    private MonthDay dueDate;
    private MonthDay currentDate;
    private String taskImportance;

    //MODIFIES: this
    //EFFECTS: Constructs an important task
    public ImportantTask(String taskContent, MonthDay taskDueDate, String taskUrgency, String taskImportance) {
        super(taskContent, taskDueDate, taskUrgency);
        this.taskImportance = taskImportance;
        timeLeft = "tbd";
    }

    //MODIFIES: this
    //EFFECTS: Sets the task importance to the give importance level
    public void setImportance(String taskImportance) {
        this.taskImportance = taskImportance;
    }

    //EFFECTS: Returns the importance level of the task
    public String getImportance() {
        return taskImportance;
    }

    //MODIFIES: this
    //EFFECTS: Updates time left until due to the most recent time left until due
    public void setTimeLeft() {
        timeLeft = computeTimeLeft();
    }

    //EFFECTS: Returns how much time is left until the task is due
    public String getTimeLeft() {
        return timeLeft;
    }

    //EFFECTS: Changes the time left to the give time left
    public void changeTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    //EFFECTS: Prints the information stored in the task in the following format
    public String printTask() {
        return super.printTask() + "  " + "Time left: " + getTimeLeft() + "  " + "*" + getImportance() + "*";
    }

    //MODIFIES: this
    //EFFECTS: Updates currentDate to the current date and month
    //         Updates dueDate to the due date of this task
    public void setCurrentDateAndDueDate() {
        currentDate = MonthDay.now();
        dueDate = super.getDueDateObj();
    }

    //EFFECTS: Returns the current MonthDay, based on the system time
    public MonthDay getCurrentDate() {
        return currentDate;
    }

    //EFFECTS: Returns the MonthDay of the due date of this task
    public MonthDay getDueDateTemp() {
        return dueDate;
    }

    //EFFECTS: Computes how much time is left until the task is due.
    //         Returns number of days or number of months and days.
    //         Returns "due today" if the due date is the current date.
    public String computeTimeLeft() {
        int currentYear = Year.now().getValue();

        setCurrentDateAndDueDate();

        Period difference = Period.between(dueDate.atYear(currentYear), currentDate.atYear(currentYear));

        if (dueDate.equals(currentDate)) {
            return "due today";
        } else {
            if (difference.getMonths() == 0) {
                return Math.abs(difference.getDays()) + " days.";
            } else {
                return Math.abs(difference.getMonths()) + " months " + Math.abs(difference.getDays()) + " days.";
            }
        }
    }
}
