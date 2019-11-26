package model;

import java.io.Serializable;
import java.util.Objects;

public class Name implements Serializable {

    private int count;
    private String rootName;

    //MODIFIES: this
    //EFFECTS: Creates a new Name with a rootName and a count
    public Name(String rootName, int count) {
        this.rootName = rootName;
        this.count = count;
    }

    //MODIFIES: this
    //EFFECTS: Creates a new Name with only a rootName
    public Name(String rootName) {
        this.rootName = rootName;
    }

    //EFFECTS: Returns count
    public int getCount() {
        return count;
    }

    //MODIFIES: this
    //EFFECTS: Sets count to the given int
    public void setCount(int count) {
        this.count = count;
    }

    //EFFECTS: Returns rootName
    public String getRootName() {
        return rootName;
    }

    //MODIFIES: this
    //EFFECTS: Sets rootName to the given string
    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    //EFFECTS: Returns a string in the form "rootName (#)"
    @Override
    public String toString() {
        return rootName + " " + "(" + count + ")";
    }

    //EFFECTS: Returns true if the given Name's count and root name equals the count and root name of this name
    //         else return false
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Name name = (Name) o;

        if (count != name.count) {
            return false;
        }

        return Objects.equals(rootName, name.rootName);
    }

    //EFFECTS: Returns custom hashcode based on count and root name
    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + (rootName != null ? rootName.hashCode() : 0);
        return result;
    }
}
