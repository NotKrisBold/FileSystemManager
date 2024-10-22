package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class ClearCommandTest {
    private static final String key = "clear";

    private ClearCommand clearCommand;
    private ApplicationOutputViewInterface viewInterface;

    @BeforeEach
    public void setUp() {
        viewInterface = Mockito.mock(ApplicationOutputViewInterface.class);
        clearCommand = new ClearCommand("clear", null); // Pass null for argumentList
        clearCommand.setViewInterface(viewInterface);
    }

    @Test
    public void constructor() {
        assertEquals(key, clearCommand.getFullInputCommand());
        assertEquals(0, clearCommand.getArgNumber());
    }

    @Test
    public void execute() {
        clearCommand.execute();

        Mockito.verify(viewInterface).clearAllOutput();
    }

    @Test
    public void setViewInterface() {
        ApplicationOutputViewInterface newViewInterface = Mockito.mock(ApplicationOutputViewInterface.class);
        clearCommand.setViewInterface(newViewInterface);
        assertSame(newViewInterface, clearCommand.getViewInterface());
    }

    @Test
    public void isInitialized() {
        assertTrue(clearCommand.isInitialized());

        clearCommand.setViewInterface(null);

        assertFalse(clearCommand.isInitialized());
    }

    @Test
    public void getViewInterface() {
        assertSame(viewInterface, clearCommand.getViewInterface());
    }
}
