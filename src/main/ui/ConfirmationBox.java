package ui;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class ConfirmationBox {
    private Boolean confirmation;
    private Parent root;
    private Stage stage;

    @FXML private Label message;
    @FXML private Button delete;
    @FXML private VBox mainBox;

    public boolean display(String string) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/ConfirmationBox.fxml"));
        fxmlLoader.setController(this);

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage = new Stage();
        stage.setScene(new Scene(root, 300, 200));
        stage.resizableProperty().setValue(false);
        stage.getScene().getStylesheets().add(getClass().getResource("styling/ConfirmationBoxStyling.css").toExternalForm());

        message.setText(string);
        message.setTextAlignment(TextAlignment.CENTER);
        mainBox.setAlignment(Pos.CENTER);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();

        return confirmation;
    }

    @FXML
    void cancelAction() {
        confirmation = false;
        stage.close();
    }

    @FXML
    void deleteAction() {
        confirmation = true;
        stage.close();
    }
}
