package ch.supsi.fsci.client.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {
    // Define a concrete subclass of Command for testing
    private static class WithArgsCommand extends Command {
        private static final String COMMAND_KEY = "concrete";

        private final int argNumber = 1;

        public WithArgsCommand(String fullInputCommand, List<String> argumentList) {
            super(fullInputCommand, argumentList);
        }

        // Abstract method, no need to test
        @Override
        public void execute() {
        }

        // Abstract method, no need to test
        @Override
        public int getArgNumber() {
            return argNumber;
        }

        // Abstract method, no need to test
        @Override
        public boolean isInitialized() {
            return true;
        }
    }

    private static class WithoutArgsCommand extends Command {
        private static final String COMMAND_KEY = "concrete";

        private final int argNumber = 0;

        public WithoutArgsCommand(String fullInputCommand) {
            super(fullInputCommand);
        }

        // Abstract method, no need to test
        @Override
        public void execute() {
        }

        // Abstract method, no need to test
        @Override
        public int getArgNumber() {
            return argNumber;
        }

        // Abstract method, no need to test
        @Override
        public boolean isInitialized() {
            return true;
        }
    }

    private String withArgsFullInputCommand;
    private ArrayList<String> argumentsList;
    private WithArgsCommand withArgsCommand;

    private String withoutArgsFullInputCommand;
    private WithoutArgsCommand withoutArgsCommand;

    @BeforeEach
    public void setup() {
        String commandKey = "key";
        String arg0 = "arg0";
        withArgsFullInputCommand = commandKey + " " + arg0;
        argumentsList = new ArrayList<>();
        argumentsList.add(arg0);
        withArgsCommand = new WithArgsCommand(withArgsFullInputCommand, argumentsList);

        withoutArgsFullInputCommand = commandKey;
        withoutArgsCommand = new WithoutArgsCommand(withoutArgsFullInputCommand);
    }

    // Test first constructor with valid arguments
    @Test
    public void constructor0() {
        assertEquals(withArgsFullInputCommand, withArgsCommand.getFullInputCommand());
        assertEquals(argumentsList, withArgsCommand.getArgumentList());
    }

    // Test first constructor with invalid arguments
    @Test
    public void constructor1() {
        assertThrows(IllegalArgumentException.class, () -> new WithArgsCommand(null, new ArrayList<>()));
    }

    // Test second constructor with valid arguments
    @Test
    public void constructor2() {
        assertEquals(withoutArgsFullInputCommand, withoutArgsCommand.getFullInputCommand());
        assertNull(withoutArgsCommand.getArgumentList());
    }

    // Test second constructor with invalid arguments
    @Test
    public void constructor3() {
        assertThrows(IllegalArgumentException.class, () -> new WithoutArgsCommand(null));
    }

    @Test
    public void getArgumentList() throws NoSuchFieldException, IllegalAccessException {
        Field field = Command.class.getDeclaredField("argumentList");
        field.setAccessible(true);
        ArrayList<String> toCheck = (ArrayList<String>) field.get(withArgsCommand);
        assertEquals(argumentsList, toCheck);
    }

    @Test
    public void getFullInputCommand() throws NoSuchFieldException, IllegalAccessException {
        Field field = Command.class.getDeclaredField("fullInputCommand");
        field.setAccessible(true);
        String toCheck = (String) field.get(withArgsCommand);
        assertEquals(withArgsFullInputCommand, toCheck);
    }
}
