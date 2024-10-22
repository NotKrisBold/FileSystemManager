package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MkdirCommandTest extends AbstractWithArgsCommandTest<MkdirCommand> {
    private static final String COMMAND_KEY = "mkdir";

    private final int argNumber = 1;

    private final String directoryName = "foo";

    private String fullinputCommand = COMMAND_KEY + " " + directoryName;

    @Test
    void constructor0() {
        // Verify that the constructor sets the fields correctly
        assertEquals(directoryName, command.getArgumentList().get(0));
    }

    // Test constructor with invalid fields
    @Test
    void constructor1() {
        MkdirCommand invalidMkdirCommand = new MkdirCommand(fullinputCommand, new ArrayList<>());
        assertFalse(invalidMkdirCommand.isInitialized());
    }

    @Test
    @Override
    void isInitialized2() {
        //Invalid argument list
        MkdirCommand mkdirCommand = new MkdirCommand(fullinputCommand, new ArrayList<>());
        mkdirCommand.setFileSystemHandler(fileSystemHandler);
        boolean result = mkdirCommand.isInitialized();
        assertFalse(result);
    }

    @Override
    protected MkdirCommand createCommand() {
        ArrayList<String> argumentList = new ArrayList<>(argNumber);
        argumentList.add(directoryName);
        return new MkdirCommand(fullinputCommand, argumentList);
    }

    @Test
    @Override
    protected void execute() {
        command.execute();
        Mockito.verify(fileSystemHandler).mkdir(directoryName, fullinputCommand);
    }

    @Override
    protected Method fileSystemHandlerMethod() throws NoSuchMethodException {
        return FileSystemHandler.class.getMethod(COMMAND_KEY, String.class, String.class);
    }

    @Override
    protected int getExpectedArgNumber() {
        return argNumber;
    }

    @Override
    protected Field getField() throws NoSuchFieldException {
        return MkdirCommand.class.getDeclaredField("fileSystemHandler");
    }
}
