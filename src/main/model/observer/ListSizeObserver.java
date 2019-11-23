/*package model.observer;

import ui.Messages;

public class ListSizeObserver implements Observer {

    private int size;
    private final Messages messages = new Messages();

    //MODIFIES: this
    //EFFECTS: Adds the given change in size stored in observerState to update size
    @Override
    public void update(ObserverState observerState) {
        size = size + (int) observerState.getState();
        messages.listSizeObserverMessage(size, (String) observerState.getStateOne());
    }

    //EFFECTS: Returns size
    public int getSize() {
        return size;
    }

    //MODIFIES: this
    //EFFECTS: Sets size to zero
    public void clearSize() {
        size = 0;
    }
}*/

package model.observer;

import ui.Messages;

import java.util.Observable;
import java.util.Observer;

public class ListSizeObserver implements Observer {

    private int size;
    private final Messages messages = new Messages();

    //MODIFIES: this
    //EFFECTS: Adds the given change in size stored in observerState to update size
    @Override
    public void update(Observable observable, Object o) {
        size = size + (int) ((ObserverState) o).getStateOne();
        messages.listSizeObserverMessage(size, (String) ((ObserverState) o).getStateTwo());
    }

    //EFFECTS: Returns size
    public int getSize() {
        return size;
    }

    //MODIFIES: this
    //EFFECTS: Sets size to zero
    public void clearSize() {
        size = 0;
    }


}
