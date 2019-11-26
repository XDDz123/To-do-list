package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.task.Task;

import java.io.IOException;
import java.time.LocalDate;

class TaskEditor {
    @FXML private TextField taskContent;
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<String> urgencySelection;
    @FXML private Button save;
    @FXML private Button cancel;

    private Task task;
    private Stage window;
    private Parent root;

    TaskEditor(Task task) {
        this.task = task;
        window = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/TaskEditor.fxml"));
        fxmlLoader.setController(this);

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayWindow() {
        window.setTitle("Hello World 2");
        window.setScene(new Scene(root, 400, 300));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);
        window.setTitle("Edit Task");
        window.getScene().getStylesheets().add(getClass().getResource("styling/DarkTheme.css").toExternalForm());
        setTaskFields();
        window.showAndWait();
    }

    private void setTaskFields() {
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
        if (taskContent.getText() != null && datePicker.getValue() != null) {
            LocalDate dueDate = datePicker.getValue();
            if (dueDate.isBefore(LocalDate.now())) {
                (new AlertBox()).display("Selected due date is in the past!");
            } else {
                updateTask(dueDate);
                window.close();
            }
        } else {
            (new AlertBox()).display("Fields can't be empty!");
        }
    }

    private void updateTask(LocalDate dueDate) {
        task.setContent(taskContent.getText());
        task.setDueDate(dueDate);
        task.setUrgency(MainSceneController.getUrgency(urgencySelection.getValue()));
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
