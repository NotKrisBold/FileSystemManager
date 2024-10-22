package ch.supsi.fsci.client.eventHandler;

import ch.supsi.fsci.client.command.Command;
import ch.supsi.fsci.client.command.CommandCreator;
import ch.supsi.fsci.client.interpreter.CommandExpression;
import ch.supsi.fsci.client.interpreter.Context;
import ch.supsi.fsci.client.interpreter.Expression;
import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;


public class InputEventHandler implements EventHandler<ActionEvent> {
    private final ApplicationOutputViewInterface viewInterface;
    private final FileSystemHandler fileSystemHandler;

    public InputEventHandler(ApplicationOutputViewInterface viewInterface, FileSystemHandler fileSystemHandler) {
        if (viewInterface == null)
            throw new IllegalArgumentException("view interface can't be null");
        this.viewInterface = viewInterface;
        if (fileSystemHandler == null)
            throw new IllegalArgumentException("file system handler can't be null");
        this.fileSystemHandler = fileSystemHandler;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent == null)
            throw new IllegalArgumentException("action event can't be null");

        TextField textField = (TextField) actionEvent.getSource();

        // Skip if command is empty
        String command = textField.getText();
        if (command == null || command.isEmpty()) {
            return;
        }

        // Send command
        Context context = new Context(new CommandCreator(viewInterface, fileSystemHandler));
        Expression commandExpression = new CommandExpression(command);
        Command retrievedCommand = commandExpression.interpret(context);
        if (retrievedCommand != null) {
            retrievedCommand.execute();
        }

        // Empty text field
        textField.setText("");
    }
}
