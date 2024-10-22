package ch.supsi.fsci.client.model;

import ch.supsi.fsci.client.Event.CommandExecutedEvent;
import ch.supsi.fsci.engine.DirectoryElement;
import ch.supsi.fsci.engine.FileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileSystemModel extends Observable implements FileSystemHandler {
    private final FileSystem fileSystem;
    private List<Object> arguments;
    private String output;

    public FileSystemModel() {
        this.fileSystem = FileSystem.getInstance();
        arguments = new ArrayList<>();
    }

    public DirectoryElement getRoot() {
        return fileSystem.getRoot();
    }

    public DirectoryElement getCurrent() {
        return fileSystem.getCurrent();
    }

    public void setCurrent(DirectoryElement dir) {
        fileSystem.setCurrent(dir);
    }

    public void ls() {
        arguments.clear();
        String lsString = fileSystem.ls();
        output = Objects.requireNonNullElse(lsString, "");
        if (output.isEmpty()) {
            output= "ls.emptyDirectory";
        }
        pcs.firePropertyChange(new CommandExecutedEvent(this));
    }

    public void pwd() {
        arguments.clear();
        output = fileSystem.pwd();
        pcs.firePropertyChange(new CommandExecutedEvent(this));
    }


    public void cd(String path) {
        arguments.clear();
        DirectoryElement dir = fileSystem.cd(path);
        if (dir != null) {
            output = "command.cd.success";
             arguments.add(dir.getName());

        }else {
            output = "command.cd.fail";
            arguments.add(path);
        }
        pcs.firePropertyChange(new CommandExecutedEvent(this));
    }

    public void rm(String path,String inputCommand) {
        arguments.clear();
        if(fileSystem.rm(path)) {
            output = "command.rm.success";
             arguments.add(path);
        }
        else {
            output = "error.general";
            arguments.add(inputCommand);
        }
        pcs.firePropertyChange(new CommandExecutedEvent(this));

    }

    public void mv(String source, String target,String inputCommand) {
        arguments.clear();
        if(fileSystem.mv(source,target)) {
            output = "command.moved.success";
            arguments.add(source);
            arguments.add(target);
        }
        else {
            output = "error.general";
            arguments.add(inputCommand);
        }
        pcs.firePropertyChange(new CommandExecutedEvent(this));
    }

    public void mkdir(String name, String inputCommand) {
        arguments.clear();
        DirectoryElement dir = fileSystem.mkdir(name);
        if(dir != null) {
            output = "command.mkdir.success";
             arguments.add(dir.getName());
        }
        else {
            output = "error.general";
            arguments.add(inputCommand);
        }
        pcs.firePropertyChange(new CommandExecutedEvent(this));
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public String getOutput() {
        return output;
    }
}
