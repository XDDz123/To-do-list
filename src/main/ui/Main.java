package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

/*    //EFFECTS: Main method. Starts the program.
    public static void main(String[] args) {
        UserInputDecisions userInputDecisions = new UserInputDecisions();
        userInputDecisions.run();
    }*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/MainScene.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("./styling/DarkTheme.css").toExternalForm());
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
