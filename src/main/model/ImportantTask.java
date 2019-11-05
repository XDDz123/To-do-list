//Note: no longer used.

/*
package model;

import exceptions.TaskException;
import java.time.LocalDate;

public class ImportantTask extends IncompleteTask {

    private String taskImportance;

    //MODIFIES: this
    //EFFECTS: Constructs an important task
    public ImportantTask(TaskList taskList,
                         String taskContent,
                         LocalDate taskDueDate,
                         String taskUrgency,
                         String taskImportance) throws TaskException {
        super(taskList, taskContent, taskDueDate, taskUrgency);
        this.taskImportance = taskImportance;
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

    //EFFECTS: Prints the information stored in the task in the following format
    @Override
    public String printTask() {
        return super.printTask() + "  " + "*" + getImportance() + "*";
    }
}
*/
