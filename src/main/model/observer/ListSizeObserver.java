package model.observer;

import ui.Messages;

public class ListSizeObserver implements Observer {

    private int size;
    private final Messages messages = new Messages();

    @Override
    public void update(ObserverState observerState) {
        size = size + (int) observerState.getState();
        messages.listSizeObserverMessage(size, (String) observerState.getStateOne());
    }
}
