package ch.supsi.fsci.client.controller;

import javafx.stage.Stage;


public class ApplicationExitController {
    private Stage stage;

    public ApplicationExitController(Stage stage) {
        this.stage = stage;
    }

    public void exit() {
        stage.close();
    }
}
