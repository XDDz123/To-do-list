package io;

import model.TaskList;

import java.io.IOException;

public interface Loadable {
    void load(TaskList taskList, String file) throws IOException;
}
