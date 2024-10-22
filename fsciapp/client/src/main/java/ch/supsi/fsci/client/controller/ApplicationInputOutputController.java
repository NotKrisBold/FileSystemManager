package ch.supsi.fsci.client.controller;

import ch.supsi.fsci.client.eventHandler.InputEventHandler;
import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import javafx.scene.control.TextField;

public class ApplicationInputOutputController {
    private final TextField commandTextField;
    private final ApplicationOutputViewInterface viewInterface;
    private FileSystemHandler fileSystemHandler;

    public ApplicationInputOutputController(TextField textField, ApplicationOutputViewInterface viewInterface,FileSystemHandler fileSystemHandler) {
        if(textField == null)
            throw new IllegalArgumentException("text field can't be null");
        commandTextField = textField;

        if(viewInterface == null)
            throw new IllegalArgumentException("view interface can't be null");
        this.viewInterface = viewInterface;

        if(fileSystemHandler == null)
            throw new IllegalArgumentException("fileSystem handler can't be null");
        this.fileSystemHandler = fileSystemHandler;
    }

    public void registerEvents() {
        commandTextField.setOnAction(new InputEventHandler(viewInterface,fileSystemHandler));
    }
}
