package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import model.task.Task;

import java.io.IOException;

//https://stackoverflow.com/questions/40507262/javafx-listview-with-custom-cell-factory-not-retaining-the-selected-cell
//https://stackoverflow.com/questions/19588029/customize-listview-in-javafx-with-fxml

public class CellController extends ListCell<Task> {

    private final String strikeThroughTrue = "./styling/StrikeThroughTrue.css";
    private final String strikeThroughFalse = "./styling/StrikeThroughFalse.css";

    @FXML private HBox cell;
    @FXML private CheckBox checkBox;
    @FXML private Label textInfo;
    @FXML private CheckBox star;
    @FXML private Tooltip taskContent;

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
            setCompletionStatus(task);
            setStarStatus(task);
            setGraphic(cell);
        }
    }

    private void setStarStatus(Task task) {
        if (task.isStarred()) {
            star.setSelected(true);
        } else {
            star.setSelected(false);
        }
    }

    private void setCompletionStatus(Task task) {
        if (task.isCompleted()) {
            setStrikeThrough(strikeThroughTrue);
            checkBox.setSelected(true);
        } else {
            setStrikeThrough(strikeThroughFalse);
            checkBox.setSelected(false);
        }
    }

    private void setStrikeThrough(String s) {
        textInfo.getStylesheets().clear();
        textInfo.getStylesheets().addAll(getClass().getResource(
                s).toExternalForm());
    }

    @FXML
    public void starAction() {
        this.getItem().setStarred(!this.getItem().isStarred());
    }

    @FXML
    public void checkBoxAction() {
        if (this.getItem().isCompleted()) {
            setStrikeThrough(strikeThroughFalse);
            this.getItem().setCompleted(false);
        } else {
            setStrikeThrough(strikeThroughTrue);
            this.getItem().setCompleted(true);
        }
    }
}
