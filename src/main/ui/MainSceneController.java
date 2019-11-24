package ui;

import exceptions.TaskException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Name;
import model.TaskListHashMap;
import model.task.Task;
import model.task.Urgency;
import model.tasklist.TaskList;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainSceneController {

    @FXML private VBox listBox;
    @FXML private ListView<Task> listView;
    @FXML private TextField taskContentField;
    @FXML private TextField listNameField;
    @FXML private Button storeTask;
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<String> urgencySelection;
    @FXML private Button nameUpdater;
    @FXML private Button clearList;
    @FXML private MenuItem deleteTask;
    @FXML private MenuItem editTask;
    @FXML private MenuItem starTask;
    @FXML private Button settingsButton;
    @FXML private Button newListButton;

    private TaskListHashMap taskListHashMap;
    private TaskList currentList;

    @FXML
    void deleteListAction() {
        if (currentList != null) {
            taskListHashMap.removeTaskList(currentList.getName());
            System.out.println(taskListHashMap.getKeys().toString());
            ArrayList<Object> tempList = new ArrayList<>(listBox.getChildren());
            tempList.forEach(button -> {
                if (button instanceof Button && ((Button) button).getText().equals(currentList.getName().toString())) {
                    listBox.getChildren().remove(button);
                }
            });
            currentList = null;
            listNameField.clear();
            listView.getItems().clear();
        }
    }

    @FXML
    void settingsAction() throws IOException {
/*        Stage window = new Stage();

        //https://stackoverflow.com/questions/14370183/passing-parameters-to-a-controller-when-loading-an-fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settingsMenu.fxml"));
        Parent root = loader.load();

        SettingsMenu settingsMenu = loader.getController();
        settingsMenu.windowSetter(window);

        window.setTitle("Hello World 3");
        window.setScene(new Scene(root, 400, 300));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);

        window.showAndWait();*/
    }

    @FXML
    void editTaskAction() throws IOException {
        ArrayList<Task> tempList = new ArrayList<>(listView.getSelectionModel().getSelectedItems());

        if (tempList.size() == 1) {

            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            tempList.clear();
            tempList.addAll(listView.getSelectionModel().getSelectedItems());

            displayTaskEditor(tempList);

        } else {
            System.out.println("one task at a time!");
        }

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void displayTaskEditor(ArrayList<Task> tempList) throws IOException {
/*        Stage window = new Stage();

        //https://stackoverflow.com/questions/14370183/passing-parameters-to-a-controller-when-loading-an-fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("taskeditor.fxml"));
        Parent root = loader.load();
        TaskEditor taskEditor = loader.getController();
        taskEditor.taskSetter(tempList.get(0), window);

        window.setTitle("Hello World 2");
        window.setScene(new Scene(root, 400, 300));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);
        //window.getScene().getStylesheets().add("DarkTheme.css");


        window.showAndWait();*/
    }

    @FXML
    void starTaskAction() {
        (new ArrayList<>(listView.getSelectionModel().getSelectedItems())).forEach(task -> task.setStarred(!task.isStarred()));
    }

    @FXML
    void clearListAction() {
        currentList.clearTaskList();
    }

    @FXML
    void deleteTaskAction() {
        (new ArrayList<>(listView.getSelectionModel().getSelectedItems())).forEach(task -> {
            try {
                currentList.deleteTask(task);
            } catch (TaskException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void storeTaskAction() {

        if (!(taskContentField.getText().equals("") || datePicker.getEditor().getText().equals(""))) {

            if (currentList != null) {

                try {
                    currentList.storeTask(new Task(
                            currentList,
                            taskContentField.getText(),
                            LocalDate.of(Integer.parseInt(datePicker.getEditor().getText().split("/")[2]),
                                    Integer.parseInt(datePicker.getEditor().getText().split("/")[0]),
                                    Integer.parseInt(datePicker.getEditor().getText().split("/")[1])), getUrgency(), false, false));
                } catch (TaskException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("create a list first");
            }

        }

        taskContentField.clear();
        datePicker.getEditor().clear();
    }

    public Urgency getUrgency() {
        switch (urgencySelection.getValue()) {
            case "High Urgency":
                return Urgency.HIGH;
            case "Mid Urgency":
                return Urgency.MID;
            case "Low Urgency":
                return Urgency.LOW;
            default:
                return Urgency.UNASSIGNED;
        }
    }

    @FXML
    void nameUpdaterAction() {
        if (!listNameField.getText().equals("") && !taskListHashMap.getTaskListMap().isEmpty()) {

            listBox.getChildren().forEach(button -> {
                if ((button instanceof Button) && !((Button) button).getText().equals("Settings")) {

                    //replaceFirst("\\s++$", "") inspired by https://stackoverflow.com/questions/48052726/remove-whitespaces-only-at-the-end-of-a-string-java
                    if (((Button) button).getText().equals(currentList.getName().toString()) && !currentList.getName().toString().equals(listNameField.getText().replaceFirst("\\s++$", ""))) {

                        String name = listNameField.getText();
                        Name newName = new Name(name, nameGenerator(name));

                        taskListHashMap.remap(currentList.getName(), newName);
                        currentList.setName(newName);
                        ((Button) button).setText(currentList.getName().toString());
                        ((Button) button).getTooltip().setText(((Button) button).getText());
                        listNameField.setText(currentList.getName().toString());

                        System.out.println(taskListHashMap.getKeys());
                    }
                }});
        }
    }

    @FXML
    void newListButtonAction() {
        TaskList taskList = new TaskList(new Name("Untitled List", nameGenerator("Untitled List")));
        taskListHashMap.storeTaskList(taskList);

        System.out.println(taskListHashMap.getKeys().toString());
        Button button = new Button(taskList.getName().toString());
        button.setPrefSize(135,35);
        button.setOnAction(event -> {
            currentList = taskList;
            listView.setItems(taskList.getObservableListObserver().getObservableList());
            listNameField.setText(taskList.getName().toString());

            listBox.getChildren().forEach(tempButton -> {
                if (tempButton instanceof Button) {
                    ((Button) tempButton).getStylesheets().clear();
                    ((Button) tempButton).getStylesheets().addAll(getClass().getResource("./styling/ListUnHighlight.css").toExternalForm());
                }
            });

            button.setTooltip(new Tooltip(button.getText()));

            button.getStylesheets().clear();
            button.getStylesheets().addAll(getClass().getResource("./styling/listHighlight.css").toExternalForm());

        });
        listBox.getChildren().add(listBox.getChildren().size(), button);
    }

    int nameGenerator(String name) {
        int largest = 0;
        for (Name key : taskListHashMap.getKeys()) {
            if (key.getRootName().equals(name) && checkRoot(key, name)) {
                largest = key.getCount();
            }
        }
        return largest + 1;
    }

    Boolean checkRoot(Name name, String str) {
        return name.getCount() == 0 || !name.toString().equals(str);
    }

    @FXML
    public void initialize() {
        System.out.println("initialize");
        setUrgencySelection();

        taskListHashMap = new TaskListHashMap();

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listView.setCellFactory(cell -> new CellController());

        newListButtonAction();
        ((Button) listBox.getChildren().get(0)).fire();

        for (int i = 1; i <= 2; i++) {
            try {
                currentList.storeTask(new Task(currentList, "task " + i, LocalDate.now(), Urgency.MID, false, false));
            } catch (TaskException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUrgencySelection() {
        urgencySelection.getItems().addAll("High Urgency", "Mid Urgency", "Low Urgency");
        urgencySelection.setValue("Mid Urgency");
    }
}
