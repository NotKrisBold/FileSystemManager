package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.mockito.Mockito.verify;

public class LsCommandTest extends AbstractNoArgsCommandTest<LsCommand>{
    // The command key for the command
    private static final String COMMAND_KEY = "ls";

    private final int argNumber = 0;

    private String fullinputCommand = COMMAND_KEY;

    @Override
    protected LsCommand createCommand() {
        return new LsCommand(fullinputCommand, new ArrayList<>());
    }

    // No need to test constructor, it just calls the super constructor

    // Test the execute method of the class
    @Test
    @Override
    public void execute() {
        // Invoke the execute method
        command.execute();

        // Verify that the execute method calls the cd method of the file system handler with the directory name
        verify(fileSystemHandler).ls();
    }

    @Override
    String getCommandKey() {
        return COMMAND_KEY;
    }

    @Override
    protected int getExpectedArgNumber() {
        return argNumber;
    }

    @Override
    protected Field getField() throws NoSuchFieldException {
        return LsCommand.class.getDeclaredField("fileSystemHandler");
    }
}
