package model;

import java.io.Serializable;
import java.util.Objects;

public class Name implements Serializable {

    private int count;
    private String rootName;

    public Name(String rootName, int count) {
        this.rootName = rootName;
        this.count = count;
    }

    public Name(String rootName) {
        this.rootName = rootName;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    @Override
    public String toString() {
        return rootName + " " + "(" + count + ")";
    }

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

    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + (rootName != null ? rootName.hashCode() : 0);
        return result;
    }
}
