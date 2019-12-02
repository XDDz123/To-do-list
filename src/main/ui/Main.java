package ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {

    //MODIFIES: primaryStage
    //EFFECTS: Loads MainScene.fxml to set and display the stage
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainScene.fxml"));
        Parent root = loader.load();
        MainSceneController mainSceneController = loader.getController();
        setStage(primaryStage, root, mainSceneController);

        Locale.setDefault(Locale.US);
        primaryStage.show();
    }

    //MODIFIES: primaryStage
    //EFFECTS: Sets the dimensions, styling, title, and close request action of the given stage
    private void setStage(Stage primaryStage, Parent root, MainSceneController mainSceneController) {
        setStageDimensions(primaryStage, root);
        setStageStyling(primaryStage);
        setStageOnCloseRequest(primaryStage, mainSceneController);
        primaryStage.setTitle("To Do List");
    }

    //MODIFIES: primaryStage
    //EFFECTS: Sets the close request action of the given stage
    private void setStageOnCloseRequest(Stage primaryStage, MainSceneController mainSceneController) {
        primaryStage.setOnCloseRequest(event -> mainSceneController.save());
    }

    //MODIFIES: primaryStage
    //EFFECTS: Sets the styling of the give stage by loading a css file
    private void setStageStyling(Stage primaryStage) {
        primaryStage.getScene().getStylesheets().add(getClass().getResource("styling/DarkTheme.css").toExternalForm());
    }

    //MODIFIES: primaryStage
    //EFFECTS: Sets the scene and dimensions of the given stage
    private void setStageDimensions(Stage primaryStage, Parent root) {
        primaryStage.setScene(new Scene(root, 1250, 800));
        primaryStage.setMinWidth(1250);
        primaryStage.setMinHeight(800);
    }

    //EFFECTS: Launches the application
    public static void main(String[] args) {
        launch(args);
    }
}
