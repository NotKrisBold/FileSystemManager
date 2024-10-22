package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.view.ApplicationOutputView;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HelpCommandTest {
    private HelpCommand helpCommand;
    private ApplicationOutputViewInterface viewInterfaceMock;


    @BeforeEach
    public void setUp(){
        helpCommand = new HelpCommand("help", new ArrayList<>());
        viewInterfaceMock = Mockito.mock(ApplicationOutputView.class);

        // Set view interface
        helpCommand.setViewInterface(viewInterfaceMock);
    }

    @Test
    public void testExecute() {
        helpCommand.execute();

        // Assert
        Mockito.verify(viewInterfaceMock, Mockito.atLeastOnce()).printOutput("help." + Mockito.anyString());
    }

    @Test
    public void testSetViewInterface() {
        // Assert
        assertEquals(viewInterfaceMock, helpCommand.getViewInterface());
    }

    @Test
    public void testGetArgNumber() {
        // Assert
        assertEquals(0, helpCommand.getArgNumber());
    }

    @Test
    public void testIsInitialized0() {
        // Assert
        assertTrue(helpCommand.isInitialized());
    }

    @Test
    public void testIsInitialized1() {
        // Non initialized Help command
        helpCommand.setViewInterface(null);

        // Assert
        assertFalse(helpCommand.isInitialized());
    }
}
