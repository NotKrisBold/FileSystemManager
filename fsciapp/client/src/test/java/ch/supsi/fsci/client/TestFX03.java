package ch.supsi.fsci.client;

import ch.supsi.fsci.client.model.UserPreferencesHandler;
import ch.supsi.fsci.client.model.UserPreferencesModel;
import ch.supsi.fsci.client.model.UserPreferencesModelTest;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestFX03 extends AbstractMainGUITest {
    //Usiamo questo line separator perchè la text area ritorna sempre "/n"
    final static String LINE_SEPARATOR = "\n";
    UserPreferencesHandler prefs = UserPreferencesModel.getInstance();
    @Test
    void walkThrough() {
        prefs.init("default_preferences.properties", UserPreferencesModelTest.class);
        testCurrentLanguage();
        testMkdirCommand();
        testLsCommand();
        testMvCommand();
        testCdCommand();
        testPwdCommand();
        testRmCommand();
        testHelpCommand();
    }

    private void testCurrentLanguage() {
        step("test current language", () -> {
            String language = prefs.getPreference("language");
            assertEquals("en-UK", language);
        });
    }
    private void testMkdirCommand() {
        step("test mkdir command", () -> {
            clear();
            // Imposta il nome della directory desiderata
            String expectedDirectory = "nomeDirectory";
            String expectedDirectory2 = "destination";

            // Esegui il comando mkdir
            clickOn(commandArea).write("mkdir " + expectedDirectory);
            type(KeyCode.ENTER);
            sleep(SLEEP_INTERVAL);

            // Esegui il comando mkdir
            clickOn(commandArea).write("mkdir " + expectedDirectory2);
            type(KeyCode.ENTER);
            sleep(SLEEP_INTERVAL);

            // Verifica che l'output sia quello atteso
            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            String expectedOutput = "new directory created: nomeDirectory" + LINE_SEPARATOR + "new directory created: destination";
            assertEquals(expectedOutput, textArea.getText());

        });
    }

    private void testCdCommand() {
        step("test cd command", () -> {
            clear();

            String expectedDirectory = "destination";

            // Esegui il comando cd
            clickOn(commandArea).write("cd " + expectedDirectory);
            type(KeyCode.ENTER);
            sleep(SLEEP_INTERVAL);

            // Verifica che l'output sia quello atteso
            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            String expectedOutput = "changed directory to destination";
            assertEquals(expectedOutput, textArea.getText());

        });
    }

    private void testLsCommand() {
        step("test ls command", () -> {
            clear();
            // Esegui il comando ls
            clickOn(commandArea).write("ls");
            type(KeyCode.ENTER);
            sleep(SLEEP_INTERVAL);

            // Verifica che l'output sia quello atteso
            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            String expectedOutput = "nomeDirectory destination";
            assertEquals(expectedOutput, textArea.getText());

        });
    }

    private void testMvCommand() {
        step("test mv command", () -> {
            clear();

            String originDiv = "nomeDirectory";
            String destinationDiv = "destination";

            // Esegui il comando mv
            clickOn(commandArea).write("mv " + originDiv + " " + destinationDiv);
            type(KeyCode.ENTER);
            sleep(SLEEP_INTERVAL);

            // Verifica che l'output sia quello atteso
            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            String expectedOutput = "moved directory nomeDirectory to destination";
            assertEquals(expectedOutput, textArea.getText());
        });
    }

    private void testPwdCommand() {
        step("test pwd command", () -> {
            clear();

            // Esegui il comando pwd
            clickOn(commandArea).write("pwd");
            type(KeyCode.ENTER);
            sleep(SLEEP_INTERVAL);

            // Verifica che l'output sia quello atteso
            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            String expectedOutput = "/destination";
            assertEquals(expectedOutput, textArea.getText());
        });
    }

    private void testRmCommand() {
        step("test rm command", () -> {
            clear();

            // Imposta il nome della directory desiderata
            String directoryName = "nomeDirectory";

            // Esegui il comando rm
            clickOn(commandArea).write("rm " + directoryName);
            type(KeyCode.ENTER);
            sleep(SLEEP_INTERVAL);

            // Verifica che l'output sia quello atteso
            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            String expectedOutput = "successfully removed nomeDirectory";
            assertEquals(expectedOutput, textArea.getText());
        });
    }

    private void testHelpCommand() {
        step("test help command", () -> {
            clear();

            // Esegui il comando help
            clickOn(commandArea).write("help");
            type(KeyCode.ENTER);
            sleep(SLEEP_INTERVAL);

            // Verifica che l'output sia quello atteso
            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            String expectedOutput = "cd (change directory): cd <dir name>" + LINE_SEPARATOR +
                    "--available commands--" + LINE_SEPARATOR +
                    "help.testKey2" + LINE_SEPARATOR +
                    "help.testKey1" + LINE_SEPARATOR +
                    "help.testKey3" + LINE_SEPARATOR +
                    "ls (list directory content): ls" + LINE_SEPARATOR +
                    "clear (clear output window): clear" + LINE_SEPARATOR +
                    "mv (move directory): mv <from dir name> <to dir name>" + LINE_SEPARATOR +
                    "rm (remove directory): rm <dir name>" + LINE_SEPARATOR +
                    "pwd (print working directory): pwd" + LINE_SEPARATOR +
                    "mkdir (make directory): mkdir <dir name>" + LINE_SEPARATOR +
                    "help.simpleTest";
            assertEquals(expectedOutput, textArea.getText());
        });
    }



    private void clear() {
        clickOn(commandArea).write("clear");
        type(KeyCode.ENTER);
        sleep(SLEEP_INTERVAL);
    }

}
