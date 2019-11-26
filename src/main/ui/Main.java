package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.TaskListHashMap;

import java.util.Locale;

public class Main extends Application {

/*    //EFFECTS: Main method. Starts the program.
    public static void main(String[] args) {
        UserInputDecisions userInputDecisions = new UserInputDecisions();
        userInputDecisions.run();
    }*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainScene.fxml"));
        Parent root = loader.load();
        MainSceneController mainSceneController = loader.getController();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("styling/DarkTheme.css").toExternalForm());
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.setOnCloseRequest(event -> mainSceneController.save());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
