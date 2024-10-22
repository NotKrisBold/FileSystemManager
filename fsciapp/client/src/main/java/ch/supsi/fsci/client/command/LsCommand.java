package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import ch.supsi.fsci.engine.DirectoryElement;

import java.util.List;
import java.util.Objects;

public class LsCommand extends Command implements ActsOnFileSystem{
    private static final String COMMAND_KEY = "ls";
    private final int argNumber = 0;

    // Command receiver
    private FileSystemHandler fileSystemHandler;


    // Constructor to be used in CommandCreator's factory method
    protected LsCommand(String fullinputCommand, List<String> argumentList) {
        super(fullinputCommand, argumentList); // ArgumentList not used for ls
    }


    @Override
    public void execute() {
        fileSystemHandler.ls();
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
