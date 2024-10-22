package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CdCommandTest extends AbstractWithArgsCommandTest<CdCommand> {

    private static final String COMMAND_KEY = "cd";
    private final int argNumber = 1;
    private final String directoryName = "foo";
    private String fullInputCommand = COMMAND_KEY + " " + directoryName;

    @Override
    protected CdCommand createCommand() {
        ArrayList<String> argumentList = new ArrayList<>(argNumber);
        argumentList.add(directoryName);
        return new CdCommand(fullInputCommand, argumentList);
    }

    @Override
    protected int getExpectedArgNumber() {
        return argNumber;
    }

    @Override
    protected Field getField() throws NoSuchFieldException {
        return CdCommand.class.getDeclaredField("fileSystemHandler");
    }

    @Test
    @Override
    void isInitialized2() {
        //Invalid argument list
        CdCommand cdCommand = new CdCommand(fullInputCommand, new ArrayList<>());
        cdCommand.setFileSystemHandler(fileSystemHandler);
        boolean result = cdCommand.isInitialized();
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
        CdCommand invalidCdCommand = new CdCommand(fullInputCommand, new ArrayList<>());
        assertFalse(invalidCdCommand.isInitialized());
    }

    // Test the execute method of the class
    @Test
    @Override
    public void execute() {
        command.execute();
        Mockito.verify(fileSystemHandler).cd(directoryName);
    }

    @Override
    protected Method fileSystemHandlerMethod() throws NoSuchMethodException {
        return FileSystemHandler.class.getMethod(COMMAND_KEY, String.class);
    }
}
