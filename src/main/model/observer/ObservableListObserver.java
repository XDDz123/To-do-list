package model.observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.task.Task;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ObservableListObserver implements Observer {
    private ObservableList<Task> observableList = FXCollections.observableArrayList();

    @Override
    public void update(Observable observable, Object o) {
        observableList.clear();
        observableList.addAll((ArrayList<Task>) o);
    }

    public ObservableList<Task> getObservableList() {
        return observableList;
    }
}
