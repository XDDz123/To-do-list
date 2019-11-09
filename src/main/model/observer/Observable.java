package model.observer;

public abstract class Observable {
    protected void notifyObserver(ObserverState observerState, Observer observer) {
        observer.update(observerState);
    }
}
