package ch.supsi.fsci.client.interpreter;

import ch.supsi.fsci.client.command.Command;
import ch.supsi.fsci.client.command.CommandCreator;
import ch.supsi.fsci.client.model.FileSystemHandler;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ContextTest {
    private Context context;

    @BeforeEach
    void setUp() {
        ApplicationOutputViewInterface mockViewInterface = mock(ApplicationOutputViewInterface.class);
        FileSystemHandler mockFileSystemHandler = mock(FileSystemHandler.class);
        context = new Context(new CommandCreator(mockViewInterface, mockFileSystemHandler));
    }

    @ParameterizedTest
    @CsvSource({
            "testKey1, testValue1",
            "testKey2, testValue2",
            "testKey3, testValue3",
    })
    void constructor(String testKey, String testValue) {
        // Assert that the map contains the expected key-value pair
        assertEquals(testValue, context.getAvailableCommands().get(testKey));
    }

    @Test
    void isValidKey() {
        assertTrue(context.isValidKey("testKey1"));
        assertTrue(context.isValidKey("testKey2"));
        assertTrue(context.isValidKey("testKey3"));

        assertFalse(context.isValidKey("InvalidKey"));
    }

    @Test
    void setFullInputCommand() throws NoSuchFieldException, IllegalAccessException {
        String testValue = "command";
        context.setFullInputCommand(testValue);
        Field field = context.getClass().getDeclaredField("fullInputCommand");
        field.setAccessible(true);
        assertEquals(testValue, field.get(context));
    }

    @Test
    void getFullInputCommand() {
        String testValue = "command";
        context.setFullInputCommand(testValue); // Setter already tested
        assertEquals(testValue, context.getFullInputCommand());
    }


    @Test
    void setCommandKey() throws NoSuchFieldException, IllegalAccessException {
        String testValue = "key";
        context.setCommandKey(testValue);
        Field field = context.getClass().getDeclaredField("commandKey");
        field.setAccessible(true);
        assertEquals(testValue, field.get(context));
    }

    @Test
    void getCommandKey() {
        String testValue = "key";
        context.setCommandKey(testValue); // Setter already tested
        assertEquals(testValue, context.getCommandKey());
    }

    @Test
    void addArguments() {
        String testValue = "arg1";
        context.addArguments(testValue);
        assertTrue(context.getArgumentList().contains(testValue));
        assertFalse(context.getArgumentList().contains("invalidArg"));
        assertTrue(context.getArgumentList().size() == 1);

        testValue = "arg2";
        context.addArguments(testValue);
        assertTrue(context.getArgumentList().contains(testValue));
        assertTrue(context.getArgumentList().size() == 2);
    }

    @Test
    void clear() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Context.class.getDeclaredMethod("clear");
        method.setAccessible(true);
        method.invoke(context);
        assertNull(context.getFullInputCommand());
        assertNull(context.getCommandKey());
        assertTrue(context.getArgumentList().isEmpty());
    }

    @Test
    void getAvailableCommands() throws IllegalAccessException, NoSuchFieldException {
        Field field = context.getClass().getDeclaredField("availableCommands");
        field.setAccessible(true);
        assertSame(field.get(context), context.getAvailableCommands());
    }

    @Test
    void getArgumentList() throws NoSuchFieldException, IllegalAccessException {
        Field field = context.getClass().getDeclaredField("argumentList");
        field.setAccessible(true);
        assertSame(field.get(context), context.getArgumentList());
    }


    @Test
    void retrieveCommand() {
        context.setFullInputCommand("simpleTest");
        context.setCommandKey("simpleTest");

        Command command = context.retrieveCommand();
        assertSame(SimpleTestCommand.class, command.getClass());

        // Check if context is cleared after retrieving the command
        assertEquals(null, context.getFullInputCommand());
        assertEquals(null, context.getCommandKey());
        assertEquals(0, context.getArgumentList().size());
    }

}