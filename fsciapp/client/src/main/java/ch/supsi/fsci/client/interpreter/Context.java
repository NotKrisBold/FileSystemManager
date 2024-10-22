package ch.supsi.fsci.client.interpreter;

import ch.supsi.fsci.client.command.Command;
import ch.supsi.fsci.client.command.CommandCreator;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Context {
    private final Map<String, String > availableCommands = new HashMap<>();

    private String fullInputCommand;

    private String commandKey = null;

    private List<String> argumentList = new ArrayList<>();

    private CommandCreator commandCreator;


    // Constructor
    public Context(CommandCreator commandCreator) {
        this.commandCreator = commandCreator;

        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("available_commands.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                availableCommands.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Getters and setters
    public Map<String, String> getAvailableCommands() {
        return availableCommands;
    }

    public String getFullInputCommand() {
        return fullInputCommand;
    }

    public void setFullInputCommand(String fullInputCommand) {
        this.fullInputCommand = fullInputCommand;
    }

    public String getCommandKey() {
        return commandKey;
    }

    public void setCommandKey(String commandKey) {
        this.commandKey = commandKey;
    }

    public List<String> getArgumentList() {
        return argumentList;
    }


    // Instance methods
    public boolean isValidKey(String commandKey) {
        return availableCommands.containsKey(commandKey);
    }

    public void addArguments(String nextToken) {
        argumentList.add(nextToken);
    }

    public Command retrieveCommand() {
        String fullyQualifiedClassCommandClassName = commandKey== null? null: availableCommands.get(commandKey);
        Command newCommand = commandCreator.create(fullInputCommand, commandKey, argumentList, fullyQualifiedClassCommandClassName);
        clear();
        return newCommand;
    }

    // Class utility methods
    private void clear() {
        fullInputCommand = null;
        commandKey = null;
        argumentList = new ArrayList<>();
    }
}
