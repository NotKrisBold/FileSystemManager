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

public class MvCommandTest extends AbstractWithArgsCommandTest<MvCommand>{
    private static final String COMMAND_KEY = "mv";

    private final int argNumber = 2;

    private final String testingOrigin = "originDir";
    private final String testingDestination = "destinationDir";
    private final String fullInputCommand = COMMAND_KEY + " " + testingOrigin + " " + testingDestination;

    @Test
    @Override
    void constructor0() {
        // Verify that the constructor sets the fields correctly
        assertEquals(testingOrigin, command.getArgumentList().get(0));
        assertEquals(testingDestination, command.getArgumentList().get(1));
    }

    @Test
    @Override
    void constructor1() {
        MvCommand mvCommand = new MvCommand(fullInputCommand, new ArrayList<>());
        assertFalse(mvCommand.isInitialized());
    }

    @Test
    @Override
    void isInitialized2() {
        //Invalid argument list
        MvCommand mvCommand = new MvCommand(fullInputCommand, new ArrayList<>());
        mvCommand.setFileSystemHandler(fileSystemHandler);
        boolean result = mvCommand.isInitialized();
        assertFalse(result);
    }

    @Override
    protected MvCommand createCommand() {
        ArrayList<String> argumentList = new ArrayList<>();
        argumentList.add(testingOrigin);
        argumentList.add(testingDestination);
        return new MvCommand(fullInputCommand, argumentList);
    }

    @Test
    @Override
    protected void execute() {
        command.execute();
        Mockito.verify(fileSystemHandler).mv(testingOrigin, testingDestination, fullInputCommand);
    }

    @Override
    protected Method fileSystemHandlerMethod() throws NoSuchMethodException {
        return FileSystemHandler.class.getMethod(COMMAND_KEY, String.class, String.class, String.class);
    }

    @Override
    protected int getExpectedArgNumber() {
        return argNumber;
    }

    @Override
    protected Field getField() throws NoSuchFieldException {
        return MvCommand.class.getDeclaredField("fileSystemHandler");
    }
}
