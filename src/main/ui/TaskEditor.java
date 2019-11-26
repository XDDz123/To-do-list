package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.task.Task;

public class TaskEditor {

    @FXML private TextField taskContent;
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<String> urgencySelection;
    @FXML private Button save;
    @FXML private Button cancel;

    private Task task;
    private Stage window;

    @FXML
    void taskSetter(Task task, Stage window) {
        this.window = window;
        this.task = task;
        taskContent.setText(task.getContent());
        datePicker.getEditor().setText(task.getDueDate());
        urgencySelection.setValue(convertUrgency(task.getUrgency().getString()));
    }

    private String convertUrgency(String urgency) {
        switch (urgency) {
            case "high":
                return "High Urgency";
            case "mid":
                return "Mid Urgency";
            case "low":
                return "Low Urgency";
            default:
                return "";
        }
    }

    private void setUrgencySelection() {
        MainSceneController.addUrgencyItems(urgencySelection);
    }

    @FXML
    void saveAction() {
        task.setContent(taskContent.getText());
        task.setDueDate(datePicker.getValue());
        task.setUrgency(MainSceneController.getUrgency(urgencySelection.getValue()));
        window.close();
    }

    @FXML
    void cancelAction() {
        window.close();
    }

    @FXML
    private void initialize() {
        setUrgencySelection();
    }
}
