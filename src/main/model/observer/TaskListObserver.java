package model.observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.task.Task;

import java.util.*;

public class TaskListObserver implements Observer {
    private ObservableList<Task> observableList = FXCollections.observableArrayList();

    //REQUIRES: Object o is type ArrayList<Task> or ObserverState<String, Task>
    //MODIFIES: this
    //EFFECTS: Given an object o
    //         -if o is type ObserverState<String, Task>, check the desired operation type and modifies the
    //          observableList by either replacing and replacing the given task in the ObserverState
    //         -else, all elements of this observableList is replaced by the given ArrayList
    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof ObserverState) {
            Task task = (Task) ((ObserverState) o).getStateTwo();
            if (observableList.contains(task)) {
                checkOperationType(task, (String) ((ObserverState) o).getStateOne());
            } else {
                observableList.add(task);
            }
        } else {
            replaceAll((ArrayList<?>) o);
        }
    }

    //REQUIRES: observableList contains task
    //MODIFIES: this
    //EFFECTS: If given string type is "edit", sets the task in the observableList to the given task
    //         else removes the given task from this observableList
    private void checkOperationType(Task task, String type) {
        if (type.equals("edit")) {
            observableList.set(observableList.indexOf(task), task);
        } else {
            observableList.remove(task);
        }
    }

    //REQUIRES: the given ArrayList is type ArrayList<Task>
    //MODIFIES: this
    //EFFECTS: Clears the observableList, then adds all elements in the given ArrayList to the observableList
    private void replaceAll(ArrayList<?> o) {
        observableList.clear();
        observableList.addAll((Collection<? extends Task>) o);
    }

    //EFFECTS: Returns this observableList
    public ObservableList<Task> getObservableList() {
        return observableList;
    }
}
