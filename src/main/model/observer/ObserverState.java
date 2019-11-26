package model.observer;

//inspired by https://docs.oracle.com/javase/tutorial/java/generics/types.html
public class ObserverState<T1,T2> {
    private T1 stateOne;
    private T2 stateTwo;

    //MODIFIES: this
    //EFFECTS: Creates a generic type with 1 parameter
    public ObserverState(T1 stateOne) {
        this.stateOne = stateOne;
    }

    //MODIFIES: this
    //EFFECTS: Creates a generic type with 2 parameters
    public ObserverState(T1 stateOne, T2 stateTwo) {
        this.stateOne = stateOne;
        this.stateTwo = stateTwo;
    }

    //EFFECTS: Return state one of type one
    public T1 getStateOne() {
        return stateOne;
    }

    //EFFECTS: Return state two of type two
    T2 getStateTwo() {
        return stateTwo;
    }
}

