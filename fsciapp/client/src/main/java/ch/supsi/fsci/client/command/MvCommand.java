package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;

import java.util.List;

public class MvCommand extends Command implements ActsOnFileSystem{
    private static final String COMMAND_KEY = "mv";
    private final int argNumber = 2;

    // Command receiver
    private FileSystemHandler fileSystemHandler;

    // Command arguments
    private String originDiv;
    private String destinationDiv;

    // Constructor to be used in CommandCreator's factory method
    protected MvCommand(String fullinputCommand, List<String> argumentList) {
        super(fullinputCommand, argumentList);

        if (argumentList != null && argumentList.size() == argNumber) {
            originDiv = argumentList.get(0);
            destinationDiv = argumentList.get(1);
        }
    }

    @Override
    public void execute() {
        fileSystemHandler.mv(originDiv,destinationDiv,getFullInputCommand());
    }

    @Override
    public int getArgNumber() {
        return argNumber;
    }

    @Override
    public boolean isInitialized() {
        return fileSystemHandler != null && originDiv != null && destinationDiv!=null;
    }

    @Override
    public void setFileSystemHandler(FileSystemHandler fileSystemHandler) {
        this.fileSystemHandler = fileSystemHandler;
    }
}
