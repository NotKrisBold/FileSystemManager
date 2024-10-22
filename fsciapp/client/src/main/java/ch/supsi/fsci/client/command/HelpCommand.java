package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.CmdDescriptionsHandler;
import ch.supsi.fsci.client.model.CmdDescriptionsModel;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends Command implements ActsOnViewInterface {
    private static final String COMMAND_KEY = "help";
    private final int argNumber = 0;

    // Command receivers
    private ApplicationOutputViewInterface viewInterface;

    // Constructor to be used in CommandCreator's factory method
    protected HelpCommand(String fullinputCommand, List<String> argumentList) {
        super(fullinputCommand, argumentList);

        // If using argumentList check for null and size
    }

    @Override
    public void execute() {
        CmdDescriptionsHandler descriptionsHandler = new CmdDescriptionsModel();
        descriptionsHandler.init("available_commands.properties");
        ArrayList<String> keys = descriptionsHandler.getAllDescriptionKeys();
        keys.forEach(s -> viewInterface.printOutput(s));
    }

    @Override
    public void setViewInterface(ApplicationOutputViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public int getArgNumber() {
        return argNumber;
    }

    @Override
    public boolean isInitialized() {
        return viewInterface != null;
    }

    public ApplicationOutputViewInterface getViewInterface() {
        return viewInterface;
    }
}
