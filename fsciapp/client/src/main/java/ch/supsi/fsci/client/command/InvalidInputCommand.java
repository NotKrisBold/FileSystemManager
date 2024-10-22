package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;

import java.util.ArrayList;
import java.util.List;

public class InvalidInputCommand extends Command implements ActsOnViewInterface {
    private String output = "command.invalid";

    // Command receivers
    private ApplicationOutputViewInterface viewInterface;

    public InvalidInputCommand(String fullinputCommand, String errorMessage, ApplicationOutputViewInterface viewInterface) {
        super(fullinputCommand);

        if(viewInterface == null)
            throw new IllegalArgumentException("view interface can't be null");
        this.viewInterface = viewInterface;

        if (errorMessage != null) {
            output = errorMessage;
        }
    }

    @Override
    public void execute() {
        List<Object> arguments = new ArrayList<>();
        arguments.add(getFullInputCommand());
        viewInterface.printOutput(output, arguments);
    }

    @Override
    public int getArgNumber() {
        return 0;
    }

    public String getOutput() {
        return output;
    }

    @Override
    public void setViewInterface(ApplicationOutputViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    // All receivers and params must be initialized
    public boolean isInitialized() {
        return viewInterface!=null;
    }

    public ApplicationOutputViewInterface getViewInterface() {
        return viewInterface;
    }
}
