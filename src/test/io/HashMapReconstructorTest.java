package io;

import exceptions.TaskException;
import model.*;
import model.task.IncompleteTask;
import model.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HashMapReconstructorTest {

    private ArrayList<Task> taskList;

    @BeforeEach
    void runBefore() {
        taskList = new ArrayList<>();
    }

    @Test
    void loadInToHashMapTest() {
        TaskListHashMap taskListHashMap = new TaskListHashMap();
        ArrayList<String> partsOfLine = new ArrayList<>();
        partsOfLine.add("a");
        partsOfLine.add("a");
        HashMapReconstructor hashMapReconstructor = new HashMapReconstructor();
        try {
            taskList.add(new IncompleteTask(null,"", LocalDate.now(), null, false));
            taskList.add(new IncompleteTask(null,"", LocalDate.now(), null, false));
            hashMapReconstructor.loadIntoHashMap(taskListHashMap, taskList, partsOfLine);
        } catch (TaskException e) {
            fail();
        }
    }
}
