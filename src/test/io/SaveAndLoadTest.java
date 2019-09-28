package io;

import model.Task;
import model.TaskList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveAndLoadTest {

    private SaveAndLoad saveAndLoad;

    @BeforeEach
    public void runBefore() {
        saveAndLoad = new SaveAndLoad();
    }

    @Test
    public void clearSaveTest() throws IOException {
        File file = new File("clearTest.txt");
        saveAndLoad.clearSave("clearTest.txt");
        assertEquals(file.length(), 0);
    }

    @Test
    public void separateOnSlashTest() {
        String line = "task I/high/2/3/false";
        assertEquals(saveAndLoad.separateOnSlash(line).get(0), "task I");
        assertEquals(saveAndLoad.separateOnSlash(line).get(1), "high");
        assertEquals(saveAndLoad.separateOnSlash(line).get(2), "2");
        assertEquals(saveAndLoad.separateOnSlash(line).get(3), "3");
        assertEquals(saveAndLoad.separateOnSlash(line).get(4), "false");
    }
}
