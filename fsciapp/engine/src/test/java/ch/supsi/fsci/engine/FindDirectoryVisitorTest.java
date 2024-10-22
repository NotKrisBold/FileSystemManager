package ch.supsi.fsci.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FindDirectoryVisitorTest {
    private FileSystem fs;

    @BeforeEach
    public void setUp() {
        fs = new FileSystem();
        DirectoryElement dir1 = fs.mkdir("dir1");
        DirectoryElement dir2 = fs.mkdir("dir2");
        fs.setCurrent(dir1);
        DirectoryElement dir3 = fs.mkdir("dir3");
        DirectoryElement dir4 = fs.mkdir("dir4");
        fs.setCurrent(fs.getRoot());
    }

    @Test
    public void testGetFoundDirectory() {
        FindDirectoryVisitor visitor = new FindDirectoryVisitor("/dir1/dir3");
        fs.getCurrent().accept(visitor);
        DirectoryElement foundDirectory = visitor.getFoundDirectoryElement();
        assertNotNull(foundDirectory);
        assertEquals("dir3", foundDirectory.getName());
    }

    @Test
    public void testGetFoundDirectoryNonExistentPath() {
        FindDirectoryVisitor visitor = new FindDirectoryVisitor("/nonExistentDir");
        fs.getRoot().accept(visitor);

        DirectoryElement foundDirectory = visitor.getFoundDirectoryElement();
        assertNull(foundDirectory);
    }

}
