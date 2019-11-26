package model.observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.task.Task;

import java.util.*;

public class TaskListObserver implements Observer {
    private ObservableList<Task> observableList = FXCollections.observableArrayList();

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

    private void checkOperationType(Task task, String type) {
        if (type.equals("edit")) {
            observableList.set(observableList.indexOf(task), task);
        } else {
            observableList.remove(task);
        }
    }

    private void replaceAll(ArrayList<?> o) {
        observableList.clear();
        observableList.addAll((Collection<? extends Task>) o);
    }

    public ObservableList<Task> getObservableList() {
        return observableList;
    }
}
