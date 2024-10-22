package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputView;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandCreatorTest {
    private CommandCreator commandCreator;
    private ApplicationOutputViewInterface viewInterface;
    private FileSystemHandler fileSystemHandler;

    @BeforeEach
    public void setUp() {
        viewInterface = Mockito.mock(ApplicationOutputView.class);
        fileSystemHandler = Mockito.mock(FileSystemHandler.class);

        commandCreator = new CommandCreator(viewInterface, fileSystemHandler);
    }

    @Test
    // ValidCommand
    public void create0() {
        String fullInputCommand = "ls";
        String commandKey = "ls";
        List<String> argumentList = new ArrayList<>();
        LsCommand lsCommand= new LsCommand("test", new ArrayList<>());
        String fullyQualifiedClassCommandClassName = lsCommand.getClass().getName();

        Command command = commandCreator.create(fullInputCommand, commandKey, argumentList, fullyQualifiedClassCommandClassName);

        assertNotNull(command);
        assertTrue(command instanceof LsCommand);
    }

    @Test
    // InvalidCommand
    public void create1() {
        String fullInputCommand = "nonexistent";
        String commandKey = null;
        List<String> argumentList = new ArrayList<>();
        String fullyQualifiedClassCommandClassName = "ch.supsi.fsci.client.command.NonExistentCommand";

        Command command = commandCreator.create(fullInputCommand, commandKey, argumentList, fullyQualifiedClassCommandClassName);

        assertNotNull(command);
        assertTrue(command instanceof InvalidInputCommand);
    }

    @Test
    // CommandWithWrongArguments
    public void create2() {
        String fullInputCommand = "ls arg1 arg2";
        String commandKey = "ls";
        List<String> argumentList = new ArrayList<>();
        argumentList.add("arg1");
        argumentList.add("arg2");
        LsCommand lsCommand= new LsCommand("test", new ArrayList<>());
        String fullyQualifiedClassCommandClassName = lsCommand.getClass().getName();

        Command command = commandCreator.create(fullInputCommand, commandKey, argumentList, fullyQualifiedClassCommandClassName);

        assertNotNull(command);
        assertTrue(command instanceof InvalidInputCommand);
    }
}
