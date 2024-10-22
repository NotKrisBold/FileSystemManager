package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RmCommandTest extends AbstractWithArgsCommandTest<RmCommand>{
    private static final String COMMAND_KEY = "rm";
    private final int argNumber = 1;
    private final String directoryName = "foo";
    private String fullInputCommand = COMMAND_KEY + " " + directoryName;

    @Override
    protected RmCommand createCommand() {
        ArrayList<String> argumentList = new ArrayList<>(argNumber);
        argumentList.add(directoryName);
        return new RmCommand(fullInputCommand, argumentList);
    }

    @Override
    protected int getExpectedArgNumber() {
        return argNumber;
    }

    @Override
    protected Field getField() throws NoSuchFieldException {
        return RmCommand.class.getDeclaredField("fileSystemHandler");
    }

    @Test
    @Override
    void isInitialized2() {
        //Invalid argument list
        RmCommand rmCommand = new RmCommand(fullInputCommand, new ArrayList<>());
        rmCommand.setFileSystemHandler(fileSystemHandler);
        boolean result = rmCommand.isInitialized();
        assertFalse(result);
    }

    // Test the constructor of the class
    @Test
    void constructor0() {
        // Verify that the constructor sets the fields correctly
        assertEquals(directoryName, command.getArgumentList().get(0));
    }

    // Test constructor with invalid fields
    @Test
    void constructor1() {
        RmCommand invalidRmCommand = new RmCommand(fullInputCommand, new ArrayList<>());
        assertFalse(invalidRmCommand.isInitialized());
    }

    // Test the execute method of the class
    @Test
    @Override
    public void execute() {
        command.execute();
        Mockito.verify(fileSystemHandler).rm(directoryName, fullInputCommand);
    }

    @Override
    protected Method fileSystemHandlerMethod() throws NoSuchMethodException {
        return FileSystemHandler.class.getMethod(COMMAND_KEY, String.class, String.class);
    }
}
