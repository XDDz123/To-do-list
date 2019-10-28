package io;

import model.TaskListHashMap;
import java.io.IOException;

public interface Savable {
    void save(TaskListHashMap taskListHashMap, String file) throws IOException;
}
