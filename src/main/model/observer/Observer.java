package model.observer;

public interface Observer {
    //EFFECTS: Updates this Observer with the given observerState
    void update(ObserverState observerState);
}
