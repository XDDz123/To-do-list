package model;

//inspired by https://docs.oracle.com/javase/tutorial/java/generics/types.html
class ObserverState<T,T1> {
    private T state;
    private T1 stateOne;

    ObserverState(T state) {
        this.state = state;
    }

    ObserverState(T state, T1 stateOne) {
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
