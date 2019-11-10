package model.observer;

public abstract class Observable {
    //MODIFIES: this
    //EFFECTS: Notifies the given observer and updates it using the given observerState
    protected void notifyObserver(ObserverState observerState, Observer observer) {
        observer.update(observerState);
    }
}
