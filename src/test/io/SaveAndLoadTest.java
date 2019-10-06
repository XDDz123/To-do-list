package io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void separateOnTildeTest() {
        String line = "task I~high~2~3~false";
        assertEquals(saveAndLoad.separateOnTilde(line).get(0), "task I");
        assertEquals(saveAndLoad.separateOnTilde(line).get(1), "high");
        assertEquals(saveAndLoad.separateOnTilde(line).get(2), "2");
        assertEquals(saveAndLoad.separateOnTilde(line).get(3), "3");
        assertEquals(saveAndLoad.separateOnTilde(line).get(4), "false");
    }

    @Test
    public void checkFirstElementTest() {
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add("*");
        assertTrue(saveAndLoad.checkFirstElement(partsOfLine, "*"));
    }
}
