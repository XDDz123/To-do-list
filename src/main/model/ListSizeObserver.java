package model;

public class ListSizeObserver implements Observer {

    private int size;

    @Override
    public void update(ObserverState observerState) {
        size = size + (int) observerState.getState();
        display(observerState);
    }

    private void display(ObserverState observerState) {
        System.out.println("There are now " + size + " incomplete task(s) in the list named <"
                + observerState.getStateOne() + ">.");
    }

    int getSize() {
        return size;
    }
}
