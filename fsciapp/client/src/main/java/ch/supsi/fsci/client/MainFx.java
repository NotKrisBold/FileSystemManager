package ch.supsi.fsci.client;

import ch.supsi.fsci.client.controller.ApplicationExitController;
import ch.supsi.fsci.client.model.*;
import ch.supsi.fsci.client.controller.ApplicationInputOutputController;
import ch.supsi.fsci.client.view.ApplicationOutputView;
import ch.supsi.fsci.client.view.ApplicationOutputViewInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.Locale;

public class MainFx extends Application {

    private String applicationTitle;
    private Label commandLabel;
    private int prefCommandSpacerWidth;
    private TextField commandTextField;
    private int commandFieldPrefColumnCount;
    private TextArea outputArea;
    private int prefOutputAreaRowCount;
    private int prefInsetsSize;
    private ApplicationOutputViewInterface applicationOutputView;

    ApplicationExitController applicationExitController;
    ApplicationInputOutputController applicationInputController;
    LocalizationHandler localizationHandler;
    UserPreferencesHandler userPreferencesHandler;
    FileSystemModel fileSystemModel;


    public MainFx() {
        fileSystemModel = new FileSystemModel();
        localizationHandler = LocalizationModel.getInstance();

        // initialize user preferences
        userPreferencesHandler = UserPreferencesModel.getInstance();
        userPreferencesHandler.init("default_preferences.properties", MainFx.class);

        // initialize localization based on given language
        Locale locale = Locale.forLanguageTag(userPreferencesHandler.getPreference("language"));
        localizationHandler.init("i18n.translations", locale);

        this.applicationTitle = localizationHandler.localize("gui.title");
        this.commandLabel = new Label(localizationHandler.localize("gui.command"));
        this.prefCommandSpacerWidth = 11;
        this.commandTextField = new TextField();
        this.commandTextField.setId("commandArea");
        this.commandFieldPrefColumnCount = Integer.parseInt(userPreferencesHandler.getPreference("commandFieldPrefColumnCount"));
        this.outputArea = new TextArea();
        this.outputArea.setId("outputArea");
        this.prefOutputAreaRowCount = Integer.parseInt(userPreferencesHandler.getPreference("prefOutputAreaRowCount"));;
        this.prefInsetsSize = 7;

        //Init views
        applicationOutputView = ApplicationOutputView.getInstance();
        applicationOutputView.init(outputArea);

        //Init controllers
        applicationInputController = new ApplicationInputOutputController(commandTextField, applicationOutputView,fileSystemModel);

        //Register events
        applicationInputController.registerEvents();

        //Add observers
        fileSystemModel.addPropertyChangeListener(applicationOutputView);
    }

    @Override
    public void start(Stage stage) throws Exception {
        if(stage == null)
            throw new IllegalArgumentException("stage can't be null");

        // spacer
        Region spacer = new Region();
        spacer.setPrefWidth(prefCommandSpacerWidth);

        // command textfield
        commandTextField.setPrefColumnCount(commandFieldPrefColumnCount);

        // horizontal box to hold the command line
        HBox commandLinePane = new HBox();
        commandLinePane.setAlignment(Pos.BASELINE_LEFT);
        commandLinePane.setPadding(new Insets(prefInsetsSize));
        commandLinePane.getChildren().add(commandLabel);
        commandLinePane.getChildren().add(spacer);
        commandLinePane.getChildren().add(commandTextField);

        // output area
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(prefOutputAreaRowCount);

        // scroll pane to hold the output area
        ScrollPane outputAreaPane = new ScrollPane();
        outputAreaPane.setFitToHeight(true);
        outputAreaPane.setFitToWidth(true);
        outputAreaPane.setPadding(new Insets(prefInsetsSize));
        outputAreaPane.setContent(outputArea);


        // border pane to hold the command line and the output area
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(commandLinePane);
        borderPane.setCenter(outputAreaPane);

        // scene
        Scene mainScene = new Scene(borderPane);

        // put the scene onto the primary stage
        stage.setTitle(applicationTitle);
        stage.setResizable(false);
        stage.setScene(mainScene);

        applicationExitController = new ApplicationExitController(stage);
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            applicationExitController.exit();
        });

        // show the primary stage
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
