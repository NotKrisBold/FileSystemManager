package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;

import java.util.List;

public class ClearCommand extends Command implements ActsOnViewInterface {
    private static final String COMMAND_KEY = "clear";

    // Command receivers
    private ApplicationOutputViewInterface viewInterface;

    // Constructor used in CommandCreator's factory method
    public ClearCommand(String fullinputCommand, List<String> argumentList) {
        super(fullinputCommand, argumentList);
    }

    @Override
    public void execute() {
        viewInterface.clearAllOutput();
    }

    @Override
    public int getArgNumber() {
        return 0;
    }


    @Override
    public void setViewInterface(ApplicationOutputViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public boolean isInitialized() {
        return viewInterface!= null;
    }

    public ApplicationOutputViewInterface getViewInterface() {
        return viewInterface;
    }
}
