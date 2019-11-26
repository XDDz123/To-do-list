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

    //MODIFIES: this
    //EFFECTS: Constructs a new TaskEditor
    //         Loads TaskEditor.fxml and sets its controller to this
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

    //EFFECTS: Sets up and displays the window for task editor based on the loaded fxml file
    void displayWindow() {
        window.setScene(new Scene(root, 400, 300));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);
        window.setTitle("Edit Task");
        window.getScene().getStylesheets().add(getClass().getResource("styling/DarkTheme.css").toExternalForm());
        setTaskFields();
        window.showAndWait();
    }

    //MODIFIES: this
    //EFFECTS: Sets the values of taskContent, datePicker, and urgencySelection to the values of corresponding fields
    //         in task
    private void setTaskFields() {
        taskContent.setText(task.getContent());
        datePicker.setValue(task.getDueDateObj());
        urgencySelection.setValue(convertUrgency(task.getUrgency().getString()));
    }

    //EFFECTS: Given a string, if given "high", "mid", or "low", return "High Urgency", "Mid Urgency", and
    //         "Low Urgency" respectively, else return empty string ""
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

    //MODIFIES: this
    //EFFECTS: Adds items to the urgencySelection choice box
    private void setUrgencySelection() {
        MainSceneController.addUrgencyItems(urgencySelection);
    }

    //MODIFIES: this
    //EFFECTS: Updates the fields in task in correspondence to the values of taskContent, the given dueDate and
    //         urgencySelection
    private void updateTask(LocalDate dueDate) {
        task.setContent(taskContent.getText());
        task.setDueDate(dueDate);
        task.setUrgency(MainSceneController.getUrgency(urgencySelection.getValue()));
    }

    //EFFECTS: Event action of the save button
    //         If taskContent is not empty and the selected due date is not in the past, then update corresponding
    //         fields of task, else if the selected due date is in the past,  display
    //         "Selected due date is in the past!" in an alert box.
    //         If taskContent is empty, alert the user with an alert box of "Fields can't be empty!"
    @FXML
    void saveAction() {
        if (taskContent.getText() != null) {
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

    //EFFECTS: Event action of the cancel button
    //         Closes the current window
    @FXML
    void cancelAction() {
        window.close();
    }

    //EFFECTS: initializes by calling setUrgencySelection
    @FXML
    private void initialize() {
        setUrgencySelection();
    }
}
