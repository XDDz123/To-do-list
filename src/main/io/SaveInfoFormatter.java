package io;

import model.*;

import java.io.PrintWriter;

class SaveInfoFormatter {

    private String separator = Load.separator;

    //EFFECTS: Saves information in tasks onto the save file
    void saveTaskInfo(PrintWriter writer, Task task) {
        if (task instanceof IncompleteTask) {
            writer.println(formatIncompleteTaskInfo(task));
        } else {
            writer.println(formatCompletedTaskInfo(task));
        }
    }

    //EFFECTS: Returns the task due date with added symbols in the following format
    private String formatTaskDueDateInfo(Task task) {
        return task.getTaskList().getName() + separator
                + task.getDueDateObj().getMonthValue() + separator
                + task.getDueDateObj().getDayOfMonth() + separator
                + task.getDueDateObj().getYear();
    }

    //EFFECTS: Returns the task content with added symbols in the following format
    private String formatGeneralTaskInfo(Task task) {
        return task.getContent() + separator
                + formatTaskDueDateInfo(task) + separator
                + ((IncompleteTask) task).getUrgency() + separator
                + ((IncompleteTask) task).getStarred();
    }

/*    //EFFECTS: Returns information in an important task in the following format
    private String formatImportantTaskInfo(Task task) {
        return TaskReconstructor.importantTaskIdentifier + separator
                + formatGeneralTaskInfo(task) + separator
                + ((ImportantTask) task).getImportance();
    }*/

    //EFFECTS: Returns information in a incomplete task in the following format
    private String formatIncompleteTaskInfo(Task task) {
        return TaskReconstructor.incompleteTaskIdentifier + separator
                + formatGeneralTaskInfo(task);
    }

    //EFFECTS: Returns information in a completed task in the following format
    private String formatCompletedTaskInfo(Task task) {
        return TaskReconstructor.completedTaskIdentifier + separator
                + task.getContent() + separator
                + formatTaskDueDateInfo(task) + separator
                + ((CompletedTask) task).getCompletionStatus();
    }
}