package ui;

import exceptions.TaskException;
import io.Load;
import io.Save;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Name;
import model.TaskListHashMap;
import model.task.Task;
import model.task.Urgency;
import model.tasklist.TaskList;

import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.Locale;

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
    @FXML private Button settingsButton;
    @FXML private Button newListButton;
    @FXML private Button sortList;
    @FXML private ComboBox<String> viewSelection;

    private TaskListHashMap taskListHashMap;
    private TaskList currentList;

    @FXML
    void sortListAction() {
        currentList.sortByDueDate();
    }

    @FXML
    void deleteListAction() {
        if (currentList != null) {
            taskListHashMap.removeTaskList(currentList.getName());
            findCurrentListButton();
            clearCurrentList();
        }
    }

    private void findCurrentListButton() {
        ArrayList<Object> tempList = new ArrayList<>(listBox.getChildren());
        tempList.forEach(button -> {
            if (button instanceof Button && ((Button) button).getText().equals(currentList.getName().toString())) {
                listBox.getChildren().remove(button);
            }
        });
    }

    private void clearCurrentList() {
        currentList = null;
        listNameField.clear();
        listView.getItems().clear();
    }

    @FXML
    void settingsAction() throws IOException {
        Stage window = new Stage();

        //https://stackoverflow.com/questions/14370183/passing-parameters-to-a-controller-when-loading-an-fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/SettingsMenu.fxml"));
        Parent root = loader.load();

        SettingsMenu settingsMenu = loader.getController();
        settingsMenu.windowSetter(window);

        window.setTitle("Hello World 3");
        window.setScene(new Scene(root, 400, 300));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);

        window.getScene().getStylesheets().add(getClass().getResource("styling/DarkTheme.css").toExternalForm());

        window.showAndWait();
    }

    @FXML
    void editTaskAction() throws IOException {
        ArrayList<Task> tempList = new ArrayList<>(listView.getSelectionModel().getSelectedItems());
        if (tempList.size() == 1) {
            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            tempList.clear();
            tempList.addAll(listView.getSelectionModel().getSelectedItems());

            displayTaskEditor(tempList.get(0));
        } else {
            (new AlertBox()).display("One task at a time!");
        }

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void displayTaskEditor(Task task) throws IOException {
        Stage window = new Stage();

        //https://stackoverflow.com/questions/14370183/passing-parameters-to-a-controller-when-loading-an-fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/TaskEditor.fxml"));
        Parent root = loader.load();
        TaskEditor taskEditor = loader.getController();
        taskEditor.taskSetter(task, window);

        window.setTitle("Hello World 2");
        window.setScene(new Scene(root, 400, 300));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);
        window.getScene().getStylesheets().add(getClass().getResource("styling/DarkTheme.css").toExternalForm());

        window.showAndWait();
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
                (new AlertBox()).display(e.getMessage() + "!");
            }
        });
    }

    @FXML
    void storeTaskAction() {
        if (!(taskContentField.getText().equals("") || datePicker.getEditor().getText().equals(""))) {
            if (currentList != null) {
                try {
                    createTask();
                } catch (TaskException e) {
                    (new AlertBox()).display(e.getMessage());
                }
            } else {
                (new AlertBox()).display("Select a list first!");
            }
        }
        resetTaskFields();
    }

    private void resetTaskFields() {
        taskContentField.clear();
        datePicker.getEditor().clear();
        urgencySelection.setValue("Mid Urgency");
    }

    private void createTask() throws TaskException {
        LocalDate dueDate = createLocalDate(datePicker);
/*        if (dueDate.isBefore(LocalDate.now())) {
            (new AlertBox()).display("Selected due date is in the past!");
        } else {
            currentList.storeTask(new Task(currentList, taskContentField.getText(), dueDate,
                    getUrgency(urgencySelection.getValue()), false, false));
        }*/
        currentList.storeTask(new Task(currentList, taskContentField.getText(), dueDate,
                getUrgency(urgencySelection.getValue()), false, false));
    }

    static LocalDate createLocalDate(DatePicker datePicker) {
        return LocalDate.of(Integer.parseInt(datePicker.getEditor().getText().split("/")[2]),
                Integer.parseInt(datePicker.getEditor().getText().split("/")[0]),
                Integer.parseInt(datePicker.getEditor().getText().split("/")[1]));
    }

    static Urgency getUrgency(String urgencySelection) {
        switch (urgencySelection) {
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
                if (isListButton(button)) {
                    if (isSameName((Button) button)) {
                        Name newName = new Name(listNameField.getText(), nameGenerator(listNameField.getText()));
                        updateNames((Button) button, newName);
                    }
                }
            });
        }
    }

    private void updateNames(Button button, Name newName) {
        taskListHashMap.remap(currentList.getName(), newName);
        currentList.setName(newName);
        button.setText(currentList.getName().toString());
        button.getTooltip().setText(button.getText());
        listNameField.setText(currentList.getName().toString());
    }

    private boolean isListButton(Node button) {
        return (button instanceof Button) && !((Button) button).getText().equals("Settings");
    }

    //replaceFirst("\\s++$", "") inspired by https://stackoverflow.com/questions/48052726/remove-whitespaces-only-at-the-end-of-a-string-java
    private boolean isSameName(Button button) {
        return button.getText().equals(currentList.getName().toString())
                && !currentList.getName().toString().equals(listNameField.getText().replaceFirst("\\s++$", ""));
    }

    @FXML
    void newListButtonAction() {
        TaskList taskList = new TaskList(new Name("Untitled List", nameGenerator("Untitled List")));
        taskListHashMap.storeTaskList(taskList);

        Button button = new Button(taskList.getName().toString());
        button.setPrefSize(135,35);
        setListButtonAction(taskList, button);
        listBox.getChildren().add(listBox.getChildren().size(), button);
        button.setTooltip(new Tooltip(button.getText()));
    }

    private void setListButtonAction(TaskList taskList, Button button) {
        button.setOnAction(event -> {
            setCurrentList(taskList);

            unHighLightAllButtons();
            highLightButton(button);

            button.setTooltip(new Tooltip(button.getText()));
        });
    }

    private void setCurrentList(TaskList taskList) {
        currentList = taskList;
        listView.setItems(currentList.getObservableListObserver().getObservableList());
        listNameField.setText(currentList.getName().toString());
    }

    private void highLightButton(Button button) {
        button.getStylesheets().clear();
        button.getStylesheets().addAll(getClass().getResource("styling/listHighlight.css").toExternalForm());
    }

    private void unHighLightAllButtons() {
        listBox.getChildren().forEach(tempButton -> {
            if (tempButton instanceof Button) {
                ((Button) tempButton).getStylesheets().clear();
                ((Button) tempButton).getStylesheets().addAll(
                        getClass().getResource("styling/ListUnHighlight.css").toExternalForm());
            }
        });
    }

    private int nameGenerator(String name) {
        int largest = 0;
        for (Name key : taskListHashMap.getKeys()) {
            if (key.getRootName().equals(name) && checkRoot(key, name)) {
                largest = key.getCount();
            }
        }
        return largest + 1;
    }

    private Boolean checkRoot(Name name, String str) {
        return name.getCount() == 0 || !name.toString().equals(str);
    }

    @FXML
    public void initialize() throws ClassNotFoundException, IOException, TaskException {
        taskListHashMap = new TaskListHashMap();
        setUrgencySelection();
        setListView();
        //generateTestTasks();

        new Load().load(taskListHashMap, "data/save");
        listButtonReCreator();

        viewSelection.getItems().addAll("View All", "High Urgency", "Mid Urgency", "Low Urgency");
        viewSelection.setValue("View All");
        viewSelection.setOnAction(event -> {
            if (viewSelection.getValue().equals("View All")) {
                currentList.notifyObserver(currentList.getTaskList());
            } else if (viewSelection.getValue().equals("High Urgency")) {
                currentList.notifyObserver(currentList.getTaskByUrgency("high"));
            } else if (viewSelection.getValue().equals("Mid Urgency")) {
                currentList.notifyObserver(currentList.getTaskByUrgency("mid"));
            } else {
                currentList.notifyObserver(currentList.getTaskByUrgency("low"));
            }
        });
    }

    void save() {
        try {
            new Save().save(taskListHashMap, "data/save");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void listButtonReCreator() {
        taskListHashMap.getKeys().forEach(name -> {
            TaskList taskList = taskListHashMap.getTaskList(name);
            Button button = new Button(taskList.getName().toString());
            button.setPrefSize(135,35);
            setListButtonAction(taskList, button);
            listBox.getChildren().add(listBox.getChildren().size(), button);
            button.setTooltip(new Tooltip(button.getText()));
        });
    }

    private void setListView() {
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setCellFactory(cell -> new CellController());
    }

    private void generateTestTasks() {
        newListButtonAction();
        ((Button) listBox.getChildren().get(0)).fire();

        for (int i = 1; i <= 50; i++) {
            try {
                currentList.storeTask(new Task(currentList, "task " + i, LocalDate.now(), Urgency.MID, false, false));
            } catch (TaskException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUrgencySelection() {
        addUrgencyItems(urgencySelection);
        urgencySelection.setValue("Mid Urgency");
    }

    static void addUrgencyItems(ChoiceBox<String> urgencySelection) {
        urgencySelection.getItems().addAll("High Urgency", "Mid Urgency", "Low Urgency");
    }
}
