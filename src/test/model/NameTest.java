package model;

import model.tasklist.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NameTest {
    private Name name;

    @BeforeEach
    void runBefore() {
        name = new Name("name", 1);
    }

    @Test
    void testGetCount() {
        assertEquals(name.getCount(), 1);
    }

    @Test
    void testSetCount() {
        name.setCount(2);
        assertEquals(name.getCount(), 2);
    }

    @Test
    void testSetRootName() {
        name.setRootName("new name");
        assertEquals(name.getRootName(), "new name");
    }

    @Test
    void testEquals() {
        assertNotEquals(name, new Name("name 1", 0));
        assertNotEquals(name, new Name("name", 2));
        assertNotEquals(name, new Name("name 1", 0));
        assertNotEquals(name, null);
        TaskList list = new TaskList(new Name(""));
        TaskList list1 = null;
        assertNotEquals(name, list);
        assertNotEquals(name, list1);
        assertEquals(name, new Name("name", 1));
        assertEquals(name, name);
    }

    @Test
    void testHashCode() {
        assertEquals(name.hashCode(), 31 * name.getCount() + (name.getRootName() != null ? name.getRootName().hashCode() : 0));
        Name newName = new Name(null, 1);
        assertEquals(newName.hashCode(), 31 * newName.getCount() + (newName.getRootName() != null ? newName.getRootName().hashCode() : 0));
    }
}
