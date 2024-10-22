package ch.supsi.fsci.client;

import ch.supsi.fsci.client.model.UserPreferencesHandler;
import ch.supsi.fsci.client.model.UserPreferencesModel;
import ch.supsi.fsci.client.model.UserPreferencesModelTest;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class TestFx01 extends AbstractMainGUITest{

    @Test
    public void walkThrough(){
        testMainScene();
        testCommandAreaEditable();
        testScrolling();
    }

    private void testMainScene(){
        step("test main scene", () -> {
            // Verify that the command area is visible
            verifyThat(commandArea, isVisible());
            verifyThat(commandArea, TextInputControlMatchers.hasText(""));

            // Verify that the output area is visible
            verifyThat(outputArea, isVisible());
            verifyThat(outputArea, TextInputControlMatchers.hasText(""));
        });
    }

    private void testCommandAreaEditable(){
        step("test command area is editable", () -> {
            TextField textField = lookup(commandArea).queryAs(TextField.class);
            assertTrue(textField.isEditable());
            sleep(SLEEP_INTERVAL);
        });
    }

    private void testScrolling(){
        step("check output area is scrollable", () -> {
            // Get row count from preferences
            UserPreferencesHandler prefs = UserPreferencesModel.getInstance();
            prefs.init("default_preferences.properties", UserPreferencesModelTest.class);
            int outputAreaRowCount = Integer.parseInt(prefs.getPreference("prefOutputAreaRowCount"));

            for (int i = 0; i < outputAreaRowCount + 3; i++) {
                clickOn(commandArea).write("ls"); // Write any command to generate output
                type(KeyCode.ENTER);
            }

            sleep(SLEEP_INTERVAL);

            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            assertEquals(textArea.getText().length(), textArea.getCaretPosition());
        });
    }
}