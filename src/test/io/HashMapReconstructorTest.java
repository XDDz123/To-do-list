package io;

import exceptions.TaskException;
import model.*;
//import model.task.IncompleteTask;
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
    void testLoadInToHashMap() {
        TaskListHashMap taskListHashMap = new TaskListHashMap();
        ArrayList<Name> listOfKeys = new ArrayList<>();
        listOfKeys.add(new Name("a"));
        listOfKeys.add(new Name("a"));
        HashMapReconstructor hashMapReconstructor = new HashMapReconstructor();
        try {
            taskList.add(new Task(null,"", LocalDate.now(), null, false, false));
            taskList.add(new Task(null,"", LocalDate.now(), null, false, false));
            hashMapReconstructor.loadIntoHashMap(taskListHashMap, taskList, listOfKeys);
        } catch (TaskException e) {
            fail();
        }
    }
}
