package ch.supsi.fsci.client.model;

import ch.supsi.fsci.client.Event.CommandExecutedEvent;
import ch.supsi.fsci.engine.DirectoryElement;
import ch.supsi.fsci.engine.FileSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileSystemModelTest {
    private FileSystemModel fileSystemModel;
    private FileSystem fileSystem;
    private PropertyChangeListener pcl;
    private List<Object> arguments;
    private String output;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        fileSystem = Mockito.mock(FileSystem.class);

        Field instanceField = FileSystem.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, fileSystem);

        // Da fare dopo il settaggio di file system (vedi costruttore di FileSystemModel)
        fileSystemModel = new FileSystemModel();

        pcl = Mockito.mock(PropertyChangeListener.class);
        fileSystemModel.removePropertyChangeListener(pcl);
        fileSystemModel.addPropertyChangeListener(pcl);

        Field argumentsField = FileSystemModel.class.getDeclaredField("arguments");
        argumentsField.setAccessible(true);
        arguments = (List<Object>) argumentsField.get(fileSystemModel);

        Field outputField = FileSystemModel.class.getDeclaredField("output");
        outputField.setAccessible(true);
        output = (String) outputField.get(fileSystemModel);
    }

    @Test
    public void testConstructor() throws NoSuchFieldException, IllegalAccessException {
        Field argumentsField = FileSystemModel.class.getDeclaredField("arguments");
        argumentsField.setAccessible(true);
        assertSame(arguments, argumentsField.get(fileSystemModel));

        Field outputField = FileSystemModel.class.getDeclaredField("output");
        outputField.setAccessible(true);
        assertSame(output, outputField.get(fileSystemModel));
    }

    @Test
    public void testGetRoot() {
        assertSame(fileSystemModel.getRoot(), fileSystemModel.getRoot());
    }

    @Test
    public void testGetCurrent() {
        assertEquals(fileSystemModel.getCurrent(), fileSystemModel.getCurrent());
    }

    @Test
    public void testSetCurrent() {
        DirectoryElement dir = Mockito.mock(DirectoryElement.class);
        fileSystemModel.setCurrent(dir);
        verify(fileSystem).setCurrent(dir);
    }

    @Test
    public void testLs() {
        // Preparazione
        String expectedOutput = "ls.emptyDirectory";
        when(fileSystem.ls()).thenReturn(null);

        // Esecuzione
        fileSystemModel.ls();

        // Verifica
        assertTrue(fileSystemModel.getArguments().isEmpty());

        assertEquals(expectedOutput, fileSystemModel.getOutput());
        verify(fileSystem).ls();

        verify(pcl).propertyChange(any(CommandExecutedEvent.class));
    }

    @Test
    public void testPwd() {
        // Preparazione
        String expectedOutput = "/home/user";
        when(fileSystem.pwd()).thenReturn(expectedOutput);

        // Esecuzione
        fileSystemModel.pwd();

        // Verifica
        assertEquals(expectedOutput, fileSystemModel.getOutput());
        verify(fileSystem).pwd();

        verify(pcl).propertyChange(any(CommandExecutedEvent.class));

        assertEquals(0, arguments.size());
    }

    @Test
    public void testCdSuccess() {
        // Preparazione
        String path = "testPath";
        DirectoryElement dir = Mockito.mock(DirectoryElement.class);
        when(fileSystem.cd(path)).thenReturn(dir);
        when(dir.getName()).thenReturn(path);

        // Esecuzione
        fileSystemModel.cd(path);

        // Verifica
        assertEquals("command.cd.success", fileSystemModel.getOutput());
        verify(fileSystem).cd(path);

        verify(pcl).propertyChange(any(CommandExecutedEvent.class));

        assertTrue(arguments.contains(dir.getName()));
        assertEquals(1, arguments.size());
    }

    @Test
    public void testCdFail() {
        // Preparazione
        String path = "testPath";
        when(fileSystem.cd(path)).thenReturn(null);

        // Esecuzione
        fileSystemModel.cd(path);

        // Verifica
        assertEquals("command.cd.fail", fileSystemModel.getOutput());
        verify(fileSystem).cd(path);

        verify(pcl).propertyChange(any(CommandExecutedEvent.class));

        assertTrue(arguments.contains(path));
        assertEquals(1, arguments.size());
    }

    @Test
    public void testRmSuccess() {
        // Preparazione
        String path = "testPath";
        String inputCommand = "rm " + path;
        when(fileSystem.rm(path)).thenReturn(true);

        // Esecuzione
        fileSystemModel.rm(path, inputCommand);

        // Verifica
        assertEquals("command.rm.success", fileSystemModel.getOutput());
        verify(fileSystem).rm(path);

        verify(pcl).propertyChange(any(CommandExecutedEvent.class));

        assertTrue(arguments.contains(path));
        assertEquals(1, arguments.size());
    }

    @Test
    public void testRmFail() {
        // Preparazione
        String path = "testPath";
        String inputCommand = "rm " + path;
        when(fileSystem.rm(path)).thenReturn(false);

        // Esecuzione
        fileSystemModel.rm(path, inputCommand);

        // Verifica
        assertEquals("error.general", fileSystemModel.getOutput());
        verify(fileSystem).rm(path);

        verify(pcl).propertyChange(any(CommandExecutedEvent.class));

        assertTrue(arguments.contains(inputCommand));
        assertEquals(1, arguments.size());
    }

    @Test
    public void testMvSuccess() {
        // Preparazione
        String source = "testSource";
        String target = "testTarget";
        String inputCommand = "mv " + source + " " + target;
        when(fileSystem.mv(source, target)).thenReturn(true);

        // Esecuzione
        fileSystemModel.mv(source, target, inputCommand);

        // Verifica
        assertEquals("command.moved.success", fileSystemModel.getOutput());
        verify(fileSystem).mv(source, target);

        verify(pcl).propertyChange(any(CommandExecutedEvent.class));

        assertTrue(arguments.contains(source));
        assertTrue(arguments.contains(target));
        assertEquals(2, arguments.size());
    }

    @Test
    public void testMvFail() {
        // Preparazione
        String source = "testSource";
        String target = "testTarget";
        String inputCommand = "mv " + source + " " + target;
        when(fileSystem.mv(source, target)).thenReturn(false);

        // Esecuzione
        fileSystemModel.mv(source, target, inputCommand);

        // Verifica
        assertEquals("error.general", fileSystemModel.getOutput());
        verify(fileSystem).mv(source, target);

        verify(pcl).propertyChange(any(CommandExecutedEvent.class));

        assertTrue(arguments.contains(inputCommand));
        assertEquals(1, arguments.size());
    }

    @Test
    public void testMkdirSuccess() {
        // Preparazione
        String path = "testPath";
        String inputCommand = "mkdir " + path;
        DirectoryElement dir = Mockito.mock(DirectoryElement.class);
        when(fileSystem.mkdir(path)).thenReturn(dir);
        when(dir.getName()).thenReturn(path);

        // Esecuzione
        fileSystemModel.mkdir(path, inputCommand);

        // Verifica
        assertEquals("command.mkdir.success", fileSystemModel.getOutput());
        verify(fileSystem).mkdir(path);

        verify(pcl).propertyChange(any(CommandExecutedEvent.class));

        assertTrue(arguments.contains(dir.getName()));
        assertEquals(1, arguments.size());
    }

    @Test
    public void testMkdirFail() {
        // Preparazione
        String path = "testPath";
        String inputCommand = "mkdir " + path;
        when(fileSystem.mkdir(path)).thenReturn(null);

        // Esecuzione
        fileSystemModel.mkdir(path, inputCommand);

        // Verifica
        assertEquals("error.general", fileSystemModel.getOutput());
        verify(fileSystem).mkdir(path);

        verify(pcl).propertyChange(any(CommandExecutedEvent.class));

        assertTrue(arguments.contains(inputCommand));
        assertEquals(1, arguments.size());
    }

    @Test
    public void getArguments() {
        assertSame(arguments, fileSystemModel.getArguments());
    }

    @Test
    public void getOutput() {
        assertSame(output, fileSystemModel.getOutput());
    }
}
