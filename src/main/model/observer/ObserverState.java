package model.observer;

//inspired by https://docs.oracle.com/javase/tutorial/java/generics/types.html
public class ObserverState<T,T1> {
    private T state;
    private T1 stateOne;

    public ObserverState(T state) {
        this.state = state;
    }

    public ObserverState(T state, T1 stateOne) {
        this.state = state;
        this.stateOne = stateOne;
    }

    T getState() {
        return state;
    }

    T1 getStateOne() {
        return stateOne;
    }
}
