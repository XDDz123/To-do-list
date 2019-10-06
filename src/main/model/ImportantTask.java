package model;

import java.time.MonthDay;
import java.time.Period;
import java.time.Year;

public class ImportantTask extends RegularTask {

    private String timeLeft;
    private MonthDay dueDate;
    private MonthDay currentDate;
    private int currentYear = Year.now().getValue();
    private String taskImportance;

    public ImportantTask(String taskContent, MonthDay taskDueDate, String taskUrgency, String taskImportance) {
        super(taskContent, taskDueDate, taskUrgency);
        this.taskImportance = taskImportance;
        timeLeft = "tbd";
    }

    public void setImportance(String taskImportance) {
        this.taskImportance = taskImportance;
    }

    public String getImportance() {
        return taskImportance;
    }


    public void setTimeLeft() {
        timeLeft = computeTimeLeft();
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public String printTask() {
        return super.printTask() + "  " + "Time left: " + getTimeLeft() + "  " + "*" + getImportance() + "*";
    }

    public void setCurrentDateAndDueDate() {
        currentDate = MonthDay.now();
        dueDate = super.getDueDateObj();
    }

    private String computeTimeLeft() {

        setCurrentDateAndDueDate();

        Period difference = Period.between(dueDate.atYear(currentYear), currentDate.atYear(currentYear));

        if (dueDate.isAfter(currentDate)) {
            if (difference.getMonths() == 0) {
                return Math.abs(difference.getDays()) + " days.";
            } else {
                return Math.abs(difference.getMonths()) + " months " + Math.abs(difference.getDays()) + " days.";
            }
        } else {
            return "due today";
        }
    }
}
