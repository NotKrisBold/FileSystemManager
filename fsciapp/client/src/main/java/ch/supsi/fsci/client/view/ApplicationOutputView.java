package ch.supsi.fsci.client.view;

import ch.supsi.fsci.client.model.FileSystemModel;
import ch.supsi.fsci.client.model.LocalizationHandler;
import ch.supsi.fsci.client.model.LocalizationModel;
import ch.supsi.fsci.client.model.Observable;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ApplicationOutputView implements ApplicationOutputViewInterface {
    private static ApplicationOutputView instance; // Singleton instance
    private boolean isInitialized = false;
    private static final String notInitializedString = "View interface is not initialized";
    private static final IllegalStateException notInitializedException = new IllegalStateException(notInitializedString);

    private TextArea textArea;

    private LocalizationHandler localizationHandler = LocalizationModel.getInstance();

    // Private constructor to prevent external instantiation
    private ApplicationOutputView() {

    }

    // Method to get the Singleton instance
    public static ApplicationOutputView getInstance() {
        if (instance == null) {
            instance = new ApplicationOutputView();
        }
        return instance;
    }

    @Override
    public void init(TextArea textArea) {
        if (textArea == null)
            throw new IllegalArgumentException("text area can't be null");
        if (!isInitialized) {
            this.textArea = textArea;
            isInitialized = true;
        }
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void printOutput(String output, List<Object> arguments) {
        if (!isInitialized)
            throw notInitializedException;


        if (output == null)
            throw new IllegalArgumentException("output can't be null or empty");

        String localizedOutput = MessageFormat.format(localizationHandler.localize(output), arguments.toArray());

        Platform.runLater(() -> {
            if(!textArea.getText().isEmpty()) textArea.appendText(System.lineSeparator());
            textArea.appendText(localizedOutput);
            //textArea.setScrollTop(Double.MAX_VALUE);
            textArea.positionCaret(textArea.getText().length()); //Alternativa a setScrollTop
        });
    }

    @Override
    public void printOutput(String output) {
        printOutput(output,new ArrayList<>());
    }

    @Override
    public void clearAllOutput() {
        if (!isInitialized)
            throw notInitializedException;

        Platform.runLater(() -> textArea.clear());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() instanceof FileSystemModel)
            printOutput(((FileSystemModel)evt.getSource()).getOutput(), ((FileSystemModel)evt.getSource()).getArguments());
    }
}
