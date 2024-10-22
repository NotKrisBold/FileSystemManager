package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.model.FileSystemModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractCommandTest<T extends Command & ActsOnFileSystem> {

    protected FileSystemHandler fileSystemHandler;
    protected T command;

    @BeforeEach
    void setUp() {
        fileSystemHandler = Mockito.mock(FileSystemHandler.class);
        command = createCommand();
        command.setFileSystemHandler(fileSystemHandler);
    }

    @Test
    void getArgNumber() {
        // Common test for getArgNumber
        assertEquals(getExpectedArgNumber(), command.getArgNumber());
    }

    @Test
    void isInitialized0() {
        // Common test for isInitialized with valid arguments
        assertTrue(command.isInitialized());
    }

    @Test
    void isInitialized1() {
        // Common test for isInitialized with invalid arguments
        command.setFileSystemHandler(null);
        assertFalse(command.isInitialized());
    }

    @Test
    void setFileSystemHandler() throws NoSuchFieldException, IllegalAccessException {
        // Common test for setFileSystemHandler
        FileSystemHandler newFileSystemHandler = new FileSystemModel();

        command.setFileSystemHandler(newFileSystemHandler);

        // Use reflection to access private field
        Field field = getField();
        field.setAccessible(true);
        FileSystemHandler toCheck = (FileSystemHandler) field.get(command);

        assertEquals(newFileSystemHandler, toCheck);
    }

    @Test
    protected abstract void execute();

    protected abstract T createCommand();

    protected abstract Method fileSystemHandlerMethod() throws NoSuchMethodException;

    protected abstract int getExpectedArgNumber();

    protected abstract Field getField() throws NoSuchFieldException;
}
