package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

class AlertBox {

    //EFFECTS: Displays a pop-up alert box that contains a vBox and a label that contains the given message
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

    //MODIFIES: vbox
    //EFFECTS: Sets the contents of a new label with the given message and adds this new label to the given vBox
    private void setContents(String message, VBox vbox) {
        Label label = new Label(message);

        setStyling(vbox, label);
        setAlignment(vbox, label);

        vbox.getChildren().add(label);
    }

    //MODIFIES: vbox, label
    //EFFECTS: Sets the text alignment of the given vBox and label
    private void setAlignment(VBox vbox, Label label) {
        label.wrapTextProperty().setValue(true);
        label.setTextAlignment(TextAlignment.CENTER);
        vbox.setAlignment(Pos.CENTER);
    }

    //MODIFIES: vbox, label
    //EFFECTS: Sets the styling of the given vBox and label
    private void setStyling(VBox vbox, Label label) {
        vbox.setStyle("-fx-background-color: rgba(18, 18, 18, 0.95);");
        label.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.87); -fx-font-size: 15;");
    }
}
