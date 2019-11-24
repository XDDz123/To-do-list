package ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SettingsMenu {

    private Stage window;

    void windowSetter(Stage window) {
        this.window = window;
    }

    @FXML
    void uselessFunction() {
        System.out.println("...");
    }

    @FXML
    void saveAction() {
        System.out.println("saved");
        window.close();
    }

    @FXML
    void cancelAction() {
        window.close();
    }

}
