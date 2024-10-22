package ch.supsi.fsci.client;


import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.logging.Logger;

abstract public class AbstractMainGUITest extends ApplicationTest {

    protected static final int SLEEP_INTERVAL = 1;

    protected static final Logger LOGGER = Logger.getAnonymousLogger();

    protected int stepNo;

    protected final String commandArea = "#commandArea";
    protected final String outputArea = "#outputArea";
    protected Stage primaryStage;

    @BeforeAll
    public static void setupSpec() {
        if(Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    protected void step(final String step, final Runnable runnable) {
        //setUp();
        ++stepNo;
        LOGGER.info("STEP" + stepNo + ":" + step);
        runnable.run();
        LOGGER.info("STEP" + stepNo + ":" + "end");
    }

    public void start(final Stage stage) throws Exception {
        final MainFx main = new MainFx();
        primaryStage = stage;
        main.start(stage);
    }
}
