package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import ch.supsi.fsci.engine.DirectoryElement;

import java.util.List;

public class CdCommand extends Command implements ActsOnFileSystem {
    private static final String COMMAND_KEY = "cd";
    private final int argNumber = 1;

    // Command receiver
    private FileSystemHandler fileSystemHandler;

    // Command arguments
    private String directoryName;

    // Constructor to be used in CommandCreator's factory method
    protected CdCommand(String fullinputCommand, List<String> argumentList) {
        super(fullinputCommand, argumentList);

        if (argumentList != null && argumentList.size() == argNumber) {
            directoryName = argumentList.get(0);
        }
    }

    @Override
    public void execute() {
        fileSystemHandler.cd(directoryName);
    }


    @Override
    public int getArgNumber() {
        return argNumber;
    }

    @Override
    public boolean isInitialized() {
        return fileSystemHandler != null && directoryName != null;
    }

    @Override
    public void setFileSystemHandler(FileSystemHandler fileSystemHandler) {
        this.fileSystemHandler = fileSystemHandler;
    }
}
