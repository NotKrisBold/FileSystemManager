package ch.supsi.fsci.client.command;

import java.util.List;

public abstract class Command {

    private String fullInputCommand;
    private List<String> argumentList;

    // Constructor used in CommandCreator's factory method
    protected Command(String fullInputCommand, List<String> argumentList) {
        if(fullInputCommand == null || fullInputCommand.isEmpty())
            throw new IllegalArgumentException("command can't be null or empty");
        // Argument list validation is checked in subclasses

        this.fullInputCommand = fullInputCommand;
        this.argumentList = argumentList;
    }

    public Command(String fullInputCommand){
        if(fullInputCommand == null || fullInputCommand.isEmpty())
            throw new IllegalArgumentException("input command can't be null or empty");
        this.fullInputCommand = fullInputCommand;
    }

    public List<String> getArgumentList() {
        return argumentList;
    }

    public abstract void execute();

    // Full input command from user
    public String getFullInputCommand() {
        return fullInputCommand;
    }

    // Number of required arguments for a specific command
    abstract public int getArgNumber();

    // Verify that all receivers and command arguments are correctly initialized
    abstract public boolean isInitialized();
}
