package ch.supsi.fsci.client.controller;

import ch.supsi.fsci.client.MainFx;
import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicationInputOutputControllerTest extends ApplicationTest {

    private TextField mockTextField;
    private ApplicationOutputViewInterface mockViewInterface;
    private FileSystemHandler mockFileSystemHandler;

    @BeforeEach
    void setUp() {
        mockTextField = mock(TextField.class);
        mockViewInterface = mock(ApplicationOutputViewInterface.class);
        mockFileSystemHandler = mock(FileSystemHandler.class);
    }

    @Test
    void constructorShouldThrowExceptionForNullTextField() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ApplicationInputOutputController(null, mockViewInterface, mockFileSystemHandler));

        // Verify that the exception message is correct
        assertEquals("text field can't be null", exception.getMessage());
    }

    @Test
    void constructorShouldThrowExceptionForNullViewInterface() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ApplicationInputOutputController(mockTextField, null, mockFileSystemHandler));

        // Verify that the exception message is correct
        assertEquals("view interface can't be null", exception.getMessage());
    }

    @Test
    void constructorShouldThrowExceptionForNullFileSystemHandler() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ApplicationInputOutputController(mockTextField, mockViewInterface, null));

        // Verify that the exception message is correct
        assertEquals("fileSystem handler can't be null", exception.getMessage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainFx mainFx = new MainFx();
        mainFx.start(stage);
        stage.hide();
    }
}
