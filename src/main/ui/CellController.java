package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import model.task.Task;

import java.io.IOException;
import java.util.ArrayList;

//https://stackoverflow.com/questions/40507262/javafx-listview-with-custom-cell-factory-not-retaining-the-selected-cell
//https://stackoverflow.com/questions/19588029/customize-listview-in-javafx-with-fxml
public class CellController extends ListCell<Task> {

    @FXML private HBox cell;
    @FXML private CheckBox checkBox;
    @FXML private Label textInfo;
    @FXML private CheckBox star;
    @FXML private Tooltip taskContent;

    private ArrayList<Task> taskArrayList;

    CellController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Cell.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (task == null || empty) {
            setText(null);
            setGraphic(null);
        } else {
            textInfo.setText(task.toString());
            taskContent.setText(task.toString());

            checkBox.setOnAction(event -> {

                if (this.getItem().isCompleted()) {
                    textInfo.getStylesheets().clear();
                    textInfo.getStylesheets().addAll(getClass().getResource(
                            "./styling/StrikethroughFalse.css").toExternalForm());
                    this.getItem().setCompleted(false);
                } else {
                    textInfo.getStylesheets().clear();
                    textInfo.getStylesheets().addAll(getClass().getResource(
                            "./styling/StrikethroughTrue.css").toExternalForm());
                    this.getItem().setCompleted(true);
                }});

            if (task.isCompleted()) {
                textInfo.getStylesheets().clear();
                textInfo.getStylesheets().addAll(getClass().getResource(
                        "./styling/StrikethroughTrue.css").toExternalForm());
                checkBox.setSelected(true);
            } else {
                textInfo.getStylesheets().clear();
                textInfo.getStylesheets().addAll(getClass().getResource(
                        "./styling/StrikethroughFalse.css").toExternalForm());
                checkBox.setSelected(false);
            }

            if (task.isStarred()) {
                star.setSelected(true);
            } else {
                star.setSelected(false);
            }

            star.setOnAction(event -> task.setStarred(!task.isStarred()));

            setGraphic(cell);
        }
    }

    //@FXML
    public void checkBoxAction() {
        System.out.println(this.getItem().toString());

        if (this.getItem().isCompleted()) {
            textInfo.getStylesheets().clear();
            textInfo.getStylesheets().addAll(getClass().getResource(
                    "./styling/StrikethroughFalse.css").toExternalForm());
            this.getItem().setCompleted(false);
        } else {
            textInfo.getStylesheets().clear();
            textInfo.getStylesheets().addAll(getClass().getResource(
                    "./styling/StrikethroughTrue.css").toExternalForm());
            this.getItem().setCompleted(true);
        }
    }
}
