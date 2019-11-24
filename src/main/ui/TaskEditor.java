package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.task.Task;

import java.time.LocalDate;

public class TaskEditor {

    @FXML private TextField taskContent;
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<String> urgencySelection;
    @FXML private Button save;
    @FXML private Button cancel;

    private Task task;
    private Stage window;

    @FXML
    public void taskSetter(Task task, Stage window) {
        this.window = window;
        this.task = task;
        taskContent.setText(task.getContent());
        datePicker.getEditor().setText(task.getTaskDueDate().getMonthValue() + "/" + task.getTaskDueDate().getDayOfMonth() + "/" + task.getTaskDueDate().getYear());
        urgencySelection.setValue(convertUrgency(task.getUrgency().getString()));
    }

    private String convertUrgency(String urgency) {
        if (urgency.equals("high")) {
            return "High Urgency";
        } else if (urgency.equals("mid")) {
            return "Mid Urgency";
        } else if (urgency.equals("low")) {
            return "Low Urgency";
        } else {
            return "";
        }
    }

    private void setUrgencySelection() {
        urgencySelection.getItems().addAll("High Urgency", "Mid Urgency", "Low Urgency");
    }

    @FXML
    void saveAction() {
        task.setContent(taskContent.getText());
        task.setDueDate(LocalDate.of(Integer.parseInt(datePicker.getEditor().getText().split("/")[2]),
                Integer.parseInt(datePicker.getEditor().getText().split("/")[0]),
                Integer.parseInt(datePicker.getEditor().getText().split("/")[1])));
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
