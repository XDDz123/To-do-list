package io;

import exceptions.TaskException;
import exceptions.TooManyIncompleteTasksException;
import model.TaskListHashMap;

import java.io.IOException;

public interface Loadable {
    void load(TaskListHashMap taskListHashMap, String file) throws IOException, TaskException;
}
