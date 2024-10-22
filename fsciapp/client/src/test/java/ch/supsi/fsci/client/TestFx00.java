package ch.supsi.fsci.client;

import javafx.application.Platform;
import javafx.stage.WindowEvent;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.base.WindowMatchers;

import static org.testfx.api.FxAssert.verifyThat;


public class TestFx00 extends AbstractMainGUITest {

    @Test
    public void walkThrough(){
        testApplicationIsShowing();
        testExit();
    }

    // verify that the application main window is showing when it starts
    private void testApplicationIsShowing() {
        step("startApplication", () -> {
            verifyThat(primaryStage,WindowMatchers.isShowing());
            sleep(SLEEP_INTERVAL);
        });

    }

    // verify that the application main window closes when clicking on the X (close) button
    // the event fired on X button calls exit() in ApplicationExitController
    private void testExit() {
        step("exit", () -> {
            Platform.runLater(() ->
                    primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST))
                    );

            sleep(SLEEP_INTERVAL);
            sleep(SLEEP_INTERVAL);
            verifyThat(primaryStage,WindowMatchers.isNotShowing());
        });
    }
}
