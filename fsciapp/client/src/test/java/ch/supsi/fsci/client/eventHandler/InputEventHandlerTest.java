package ch.supsi.fsci.client.eventHandler;

import ch.supsi.fsci.client.AbstractMainGUITest;
import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InputEventHandlerTest extends AbstractMainGUITest {

    private InputEventHandler inputEventHandler;
    private ApplicationOutputViewInterface viewInterface;
    private FileSystemHandler fileSystemHandler;

    @BeforeEach
    public void setUp() {
        viewInterface = mock(ApplicationOutputViewInterface.class);
        fileSystemHandler = mock(FileSystemHandler.class);
        inputEventHandler = new InputEventHandler(viewInterface, fileSystemHandler);
    }

    @Test
    public void testConstructor0() {
        assertThrows(IllegalArgumentException.class, () -> new InputEventHandler(null, fileSystemHandler));
        assertThrows(IllegalArgumentException.class, () -> new InputEventHandler(viewInterface, null));
    }

    @Test
    public void testConstructor1() throws NoSuchFieldException, IllegalAccessException {
        Field viewInterfaceField = InputEventHandler.class.getDeclaredField("viewInterface");
        viewInterfaceField.setAccessible(true);
        assertSame(viewInterface, viewInterfaceField.get(inputEventHandler));

        Field fileSystemHandlerField = InputEventHandler.class.getDeclaredField("fileSystemHandler");
        fileSystemHandlerField.setAccessible(true);
        assertSame(fileSystemHandler, fileSystemHandlerField.get(inputEventHandler));
    }

    @Test
    public void testHandle0() {
        assertThrows(IllegalArgumentException.class, () -> inputEventHandler.handle(null));
    }

    @Test
    public void testHandle1() {
        // Set up
        TextField textField = new TextField("someCommand");
        ActionEvent actionEvent = mock(ActionEvent.class);
        when(actionEvent.getSource()).thenReturn(textField);

        // Invoke
        inputEventHandler.handle(actionEvent);


        // Assert
        verify(viewInterface, times(1)).printOutput(anyString(), any(List.class));
        assertEquals(textField.getText(), "");
    }

    @Test
    public void testHandle2() {
        // Set up
        TextField textField = new TextField("");
        ActionEvent actionEvent = mock(ActionEvent.class);
        when(actionEvent.getSource()).thenReturn(textField);

        // Invoke
        inputEventHandler.handle(actionEvent);


        // Assert
        verify(viewInterface, never()).printOutput(anyString(), any(List.class));
        assertEquals(textField.getText(), "");
    }

    @Test
    public void testHandle3() {
        // Set up
        TextField textField = new TextField();
        ActionEvent actionEvent = mock(ActionEvent.class);
        when(actionEvent.getSource()).thenReturn(textField);

        // Invoke
        inputEventHandler.handle(actionEvent);


        // Assert
        verify(viewInterface, never()).printOutput(anyString(), any(List.class));
        assertEquals(textField.getText(), "");
    }
}
