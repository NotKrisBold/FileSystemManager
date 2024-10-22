package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;

import java.util.List;

public class PwdCommand extends Command implements ActsOnFileSystem {
    private static final String COMMAND_KEY = "pwd";

    private final int argNumber = 0;

    // Command receiver
    private FileSystemHandler fileSystemHandler;

    // Constructor to be used in CommandCreator's factory method
    protected PwdCommand(String fullinputCommand, List<String> argumentList) {
        super(fullinputCommand, argumentList); // ArgumentList not used for pwd
    }

    @Override
    public void execute() {
        fileSystemHandler.pwd();
    }

    @Override
    public int getArgNumber() {
        return argNumber;
    }

    @Override
    public boolean isInitialized() {
        return fileSystemHandler != null;
    }

    @Override
    public void setFileSystemHandler(FileSystemHandler fileSystemHandler) {
        this.fileSystemHandler = fileSystemHandler;
    }
}
