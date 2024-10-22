package ch.supsi.fsci.client.interpreter;

import ch.supsi.fsci.client.command.Command;
import ch.supsi.fsci.client.command.ActsOnViewInterface;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;

import java.util.List;

public class SimpleTestCommand extends Command implements ActsOnViewInterface {
    private static final String COMMAND_KEY = "simpleTest";

    // Command receivers
    private ApplicationOutputViewInterface viewInterface;

    // Constructor used in CommandCreator's factory method
    public SimpleTestCommand(String fullinputCommand, List<String> argumentList) {
        super(fullinputCommand, argumentList);
    }

    @Override
    public void execute() {
        //Nothing
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
