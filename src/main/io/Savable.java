package io;

import model.TaskList;

import java.io.IOException;

public interface Savable {
    void save(TaskList taskList, String file) throws IOException;
}
