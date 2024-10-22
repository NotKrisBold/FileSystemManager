package ch.supsi.fsci.client.view;


import javafx.scene.control.TextArea;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface ApplicationOutputViewInterface extends PropertyChangeListener {

    void printOutput(String commandOutput);

    void printOutput(String commandOutput, List<Object> arguments);

    void clearAllOutput();

    void init(TextArea textArea);

    boolean isInitialized();
}
