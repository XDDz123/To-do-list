package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainScene.fxml"));
        Parent root = loader.load();
        MainSceneController mainSceneController = loader.getController();
        setStage(primaryStage, root, mainSceneController);
        Locale.setDefault(Locale.US);
        primaryStage.show();
    }

    private void setStage(Stage primaryStage, Parent root, MainSceneController mainSceneController) {
        setStageDimensions(primaryStage, root);
        setStageStyling(primaryStage);
        setStageOnCloseRequest(primaryStage, mainSceneController);
        primaryStage.setTitle("To Do List");
    }

    private void setStageOnCloseRequest(Stage primaryStage, MainSceneController mainSceneController) {
        primaryStage.setOnCloseRequest(event -> mainSceneController.save());
    }

    private void setStageStyling(Stage primaryStage) {
        primaryStage.getScene().getStylesheets().add(getClass().getResource("styling/DarkTheme.css").toExternalForm());
    }

    private void setStageDimensions(Stage primaryStage, Parent root) {
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
