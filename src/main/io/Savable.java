package io;

import model.TaskListHashMap;
import java.io.IOException;

public interface Savable {
    //EFFECTS: Saves information in the given HashMap to the file at the given destination
    void save(TaskListHashMap taskListHashMap, String file) throws IOException;
}
