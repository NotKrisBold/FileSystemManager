package ch.supsi.fsci.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileSystemTest {

    private FileSystem fileSystem;

    @BeforeEach
    void setUp() {
        fileSystem = new FileSystem();
    }

    @Test
    void getInstanceTest() {
        FileSystem instance1 = FileSystem.getInstance();
        FileSystem instance2 = FileSystem.getInstance();
        assertEquals(instance1, instance2);
    }

    @Test
    void getRootTest() {
        assertNotNull(fileSystem.getRoot());
        assertEquals("/", fileSystem.getRoot().getName());
    }

    @Test
    void setCurrentTest() {
        DirectoryElement dir = new DirectoryElement("test", fileSystem.getRoot());
        fileSystem.setCurrent(dir);
        assertEquals(dir, fileSystem.getCurrent());
    }

    @Test
    void lsTest() {
        DirectoryElement dir = new DirectoryElement("test", fileSystem.getRoot());
        fileSystem.getRoot().add(dir);
        String ls = fileSystem.ls();
        assertTrue(ls.contains("test"));
        assertFalse(ls.contains("nonexistent"));
    }

    @Test
    void pwdTest() {
        DirectoryElement dir = new DirectoryElement("test", fileSystem.getRoot());
        fileSystem.setCurrent(dir);
        String pwd = fileSystem.pwd();
        assertTrue(pwd.contains("test"));
        assertFalse(pwd.contains("nonexistent"));
    }

    @Test
    void cdTest() {
        DirectoryElement dir = new DirectoryElement("test", fileSystem.getRoot());
        fileSystem.getRoot().add(dir);
        fileSystem.cd("test");
        assertEquals(dir, fileSystem.getCurrent());
        fileSystem.cd("nonexistent");
        assertEquals(dir, fileSystem.getCurrent());
    }

    @Test
    void rmTest() {
        DirectoryElement dir = new DirectoryElement("test", fileSystem.getRoot());
        fileSystem.getRoot().add(dir);
        assertTrue(fileSystem.rm("test"));
        assertFalse(fileSystem.rm("test"));
    }

    @Test
    void mkdir() {
        // Test creating a new directory
        DirectoryElement dir = fileSystem.mkdir("test");
        assertNotNull(dir);
        assertEquals("test", dir.getName());
        assertTrue(fileSystem.getCurrent().getChildren().contains(dir));

        // Test trying to create a directory that already exists
        DirectoryElement existingDir = fileSystem.mkdir("test");
        assertEquals(1, fileSystem.getCurrent().getChildren().size());

        // Test creating a directory with a relative path
        DirectoryElement pathDir = fileSystem.mkdir("test/subdir");
        assertNotNull(pathDir);
        assertEquals("subdir", pathDir.getName());
        assertTrue(fileSystem.getCurrent().getChildren().contains(pathDir.getParent()));

        // Test creating a directory with a absolute path
        DirectoryElement abspathDir = fileSystem.mkdir("/test/subdir2");
        assertNotNull(abspathDir);
        assertEquals("subdir2", abspathDir.getName());
        assertTrue(fileSystem.getCurrent().getChildren().contains(abspathDir.getParent()));
    }
    @Test
    public void testMv() {
        // Creazione di un file system fittizio
        fileSystem.mkdir("dir1");
        fileSystem.mkdir("dir2");

        // Testare il caso di successo
        assertTrue(fileSystem.mv("dir1", "dir2"));
        assertEquals(fileSystem.ls(),"dir2");
        fileSystem.cd("dir2");
        assertEquals(fileSystem.ls(),"dir1");

        // Reset del file system
        fileSystem.rm("dir2");
        fileSystem.mkdir("dir1");
        fileSystem.mkdir("dir2");
        // Testare quando origin o destination non esistono
        assertFalse(fileSystem.mv("nonexistent", "dir2"));
        assertFalse(fileSystem.mv("dir1", "nonexistent"));

        // Testare quando origin e destination sono la stessa directory
        assertFalse(fileSystem.mv("dir1", "dir1"));

        // Testare quando destination contiene già un elemento con lo stesso nome di origin
        fileSystem.cd("dir2");
        fileSystem.mkdir("dir1");
        fileSystem.cd("/");
        assertFalse(fileSystem.mv("dir1", "dir2"));
    }




}