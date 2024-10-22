package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvalidInputCommandTest {
    @Test
    public void constructor() {
        // Test passing null params
        assertThrows(IllegalArgumentException.class, () -> new InvalidInputCommand("","", null));

        // Create a mock for ApplicationOutputViewInterface
        ApplicationOutputViewInterface mockViewInterface = mock(ApplicationOutputViewInterface.class);

        // Create an InvalidInputCommand instance
        String input = "invalidCommand";
        InvalidInputCommand invalidInputCommand = new InvalidInputCommand(input, "errorMessage", mockViewInterface);

        // Verify that the viewInterface field is set correctly
        assertSame(mockViewInterface, invalidInputCommand.getViewInterface());

        // Verify that the super constructor was called with the correct command name
        assertEquals(input, invalidInputCommand.getFullInputCommand());
    }

    @Test
    public void execute() {
        // Create a mock for ApplicationOutputViewInterface
        ApplicationOutputViewInterface mockViewInterface = mock(ApplicationOutputViewInterface.class);

        // Create an InvalidInputCommand instance with the mock view interface
        InvalidInputCommand invalidInputCommand = new InvalidInputCommand("mkdh", "errorMessage", mockViewInterface);

        // Call the execute method
        invalidInputCommand.execute();
        List<Object> list = new ArrayList<>();
        list.add(invalidInputCommand.getFullInputCommand());
        // Verify that the printOutput method was called on the mock view interface
       verify(mockViewInterface, times(1)).printOutput(invalidInputCommand.getOutput(),list );
    }
}
