package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;


class AlertBox {

    void display(String message) {
        Stage window = new Stage();
        VBox vbox = new VBox();

        window.setScene(new Scene(vbox, 200, 100));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);
        window.setTitle("Alert");

        setContents(message, vbox);

        window.showAndWait();
    }

    private void setContents(String message, VBox vbox) {
        Label label = new Label(message);

        vbox.setStyle("-fx-background-color: rgba(18, 18, 18, 0.95);");
        label.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.87); -fx-font-size: 15;");

        label.wrapTextProperty().setValue(true);
        label.setTextAlignment(TextAlignment.CENTER);
        vbox.setAlignment(Pos.CENTER);

        vbox.getChildren().add(label);
    }
}
