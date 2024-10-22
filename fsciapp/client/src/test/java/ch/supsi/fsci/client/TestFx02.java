package ch.supsi.fsci.client;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO TESTARE LA LINGUA NON PER TUTTI I COMANDI
//TODO TESTO CHE LE RISORSE DI TRADUZIONE SIANO UGUALI A QUELLE RISORSE MAIN

public class TestFx02 extends AbstractMainGUITest {

    //Usiamo questo line separator perchè la text area ritorna sempre "/n"
    final static String LINE_SEPARATOR = "\n";


    @Test
    void walkThrough() {
        testAreaEmpty();
        testCommand();
        testClear();
    }


    private void testAreaEmpty() {
        step("test area empty", () -> {
            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            // Verify that any non-empty output was printed
            assertTrue(textArea.getText().isEmpty());
            sleep(SLEEP_INTERVAL);
        });

    }

    private void testCommand() {
        step("test valid command", () -> {
            clickOn(commandArea).write("testCommand"); // Any command except clear
            type(KeyCode.ENTER);
            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            // Verify that any non-empty output was printed
            assertFalse(textArea.getText().isEmpty());
            sleep(SLEEP_INTERVAL);
        });
    }


    private void testClear() {
        step("test clear", () -> {
            write("clear");
            type(KeyCode.ENTER);
            TextArea textArea = lookup(outputArea).queryAs(TextArea.class);
            assertTrue(textArea.getText().isEmpty());
            sleep(SLEEP_INTERVAL);
        });
    }
}
