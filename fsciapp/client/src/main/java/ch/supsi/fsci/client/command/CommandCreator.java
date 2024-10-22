package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandCreator {
    private ApplicationOutputViewInterface viewInterface;
    private final FileSystemHandler fileSystemHandler;

    public CommandCreator(ApplicationOutputViewInterface viewInterface, FileSystemHandler fileSystemHandler) {
        this.viewInterface = viewInterface;
        this.fileSystemHandler = fileSystemHandler;
    }

    public Command create(String fullInputCommand, String commandKey, List<String> argumentList, String fullyQualifiedClassCommandClassName) {
        if (commandKey == null) {
            return new InvalidInputCommand(fullInputCommand, "commandcreator.nonexistent", viewInterface);
        }

        try {
            Class<?> commandClass = Class.forName(fullyQualifiedClassCommandClassName);
            Command commandInstance = (Command) commandClass.getDeclaredConstructor(String.class, List.class).newInstance(fullInputCommand, argumentList);

            if (argumentList.size() != commandInstance.getArgNumber()) {
                String message = commandKey + ".syntax";
                return new InvalidInputCommand(fullInputCommand, message, viewInterface);
            }

            for (String arg : argumentList) {
                if(!isValidPath(arg))
                    return new InvalidInputCommand(fullInputCommand, "path.syntax", viewInterface);

            }
            // Set necessary receivers in the retrieved command instance
            if (commandInstance instanceof ActsOnViewInterface) {
                ((ActsOnViewInterface) commandInstance).setViewInterface(viewInterface);
            }

            if (commandInstance instanceof ActsOnFileSystem) {
                ((ActsOnFileSystem) commandInstance).setFileSystemHandler(fileSystemHandler);
            }

            // Return command
            if (commandInstance.isInitialized()) {
                return commandInstance;
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            return new InvalidInputCommand(fullInputCommand, "error.unspecified", viewInterface);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isValidPath(String path) {
        String regex = "^(/?([a-zA-Z0-9]+/?)*$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }
    public void setViewInterface(ApplicationOutputViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }
}
