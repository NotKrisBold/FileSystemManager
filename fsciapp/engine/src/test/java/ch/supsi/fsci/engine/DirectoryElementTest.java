package ch.supsi.fsci.engine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectoryElementTest {
    private DirectoryElement root;
    private DirectoryElement dir1;
    private DirectoryElement dir2;

    @BeforeEach
    public void init() {
        root = new DirectoryElement("root", null);
        dir1 = new DirectoryElement("dir1", root);
        dir2 = new DirectoryElement("dir2", root);
        root.add(dir1);
        root.add(dir2);
    }

    @Test
    public void testGetName() {
        assertEquals("root", root.getName());
        assertEquals("dir1", dir1.getName());
        assertEquals("dir2", dir2.getName());
    }

    @Test
    public void testGetParent() {
        assertNull(root.getParent());
        assertEquals(root, dir1.getParent());
        assertEquals(root, dir2.getParent());
    }

    @Test
    public void testSetParent() {
        DirectoryElement newDir = new DirectoryElement("newDir", null);
        dir2.setParent(newDir);
        assertEquals(newDir, dir2.getParent());
    }

    @Test
    public void testAdd() {
        DirectoryElement subDir = new DirectoryElement("subDir", null);
        dir1.add(subDir);
        assertTrue(dir1.getChildren().contains(subDir));
    }

    @Test
    public void testRemove() {
        root.remove(dir1);
        assertFalse(root.getChildren().contains(dir1));
    }

    @Test
    public void testToString() {
        assertEquals("root", root.toString());
        assertEquals("dir1", dir1.toString());
        assertEquals("dir2", dir2.toString());
    }
}
