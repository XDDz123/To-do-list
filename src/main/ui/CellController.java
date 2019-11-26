package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import model.task.Task;

import java.io.IOException;

public class CellController extends ListCell<Task> {

    private final String strikeThroughTrue = "styling/StrikeThroughTrue.css";
    private final String strikeThroughFalse = "styling/StrikeThroughFalse.css";

    @FXML private HBox cell;
    @FXML private CheckBox checkBox;
    @FXML private Label textInfo;
    @FXML private CheckBox star;
    @FXML private Tooltip taskContent;

    //EFFECTS: Constructs a new CellController
    //         Loads Cell.fxml and sets its controller to this
    CellController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Cell.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Partially inspired by the following,
    //https://stackoverflow.com/questions/40507262/javafx-listview-with-custom-cell-factory-not-retaining-the-selected-cell
    //https://stackoverflow.com/questions/19588029/customize-listview-in-javafx-with-fxml
    //MODIFIES: this
    //EFFECTS: If the given task is not null or empty, set values of fields in this to corresponding fields
    //         of the given task and graphic to this cell
    //         Else set text and graphic to null
    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (task == null || empty) {
            setText(null);
            setGraphic(null);
        } else {
            setElements(task);

            setGraphic(cell);
        }
    }

    //MODIFIES: this
    //EFFECTS: Sets value and/or style of checkBox, textInfo, star and taskContent according to values of corresponding
    //         fields in task
    private void setElements(Task task) {
        textInfo.setText(task.toString());
        taskContent.setText(task.toString());
        setCompletionStatus(task);
        setPastDueHighLight(task);
        setStarStatus(task);
    }

    //MODIFIES: this
    //EFFECTS: If the given task is not completed and past due, then set textInfo to the style of PastDueHighLight.css
    private void setPastDueHighLight(Task task) {
        if (!task.isCompleted() && task.getTimeLeft().equals("past due")) {
            textInfo.getStylesheets().addAll(
                    getClass().getResource("styling/PastDueHighLight.css").toExternalForm());
        }
    }

    //MODIFIES: this
    //EFFECTS: Sets the state of star depending on the value of starred in the given task
    //         If task is starred, the check/select star
    //         else uncheck/deselect star
    private void setStarStatus(Task task) {
        if (task.isStarred()) {
            star.setSelected(true);
        } else {
            star.setSelected(false);
        }
    }

    //MODIFIES: this
    //EFFECTS: If task is completed, then set label style as strikeThroughTrue and select checkbox
    //         Else set label style as strikeThroughFalse and deselect checkbox
    private void setCompletionStatus(Task task) {
        if (task.isCompleted()) {
            setStrikeThrough(strikeThroughTrue);
            checkBox.setSelected(true);
        } else {
            setStrikeThrough(strikeThroughFalse);
            checkBox.setSelected(false);
        }
    }

    //MODIFIES: this
    //EFFECTS: Clears the style sheet of textInfo and applies the new style sheet at the given path
    private void setStrikeThrough(String path) {
        textInfo.getStylesheets().clear();
        textInfo.getStylesheets().addAll(getClass().getResource(
                path).toExternalForm());
    }

    //MODIFIES: task
    //EFFECTS: Upon star check/uncheck, sets the starred field in task to its negated value
    @FXML
    public void starAction() {
        this.getItem().setStarred(!this.getItem().isStarred());
    }

    //MODIFIES: this, task
    //EFFECTS: Upon check box check/uncheck, if task is completed, then set label's styling to strikeThroughFalse
    //         and set task to incomplete, else then set label's styling to strikeThroughTrue and set task as completed
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
