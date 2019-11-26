package ui;

import exceptions.TaskException;
import io.Load;
import io.Save;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Name;
import model.TaskListHashMap;
import model.task.Task;
import model.task.Urgency;
import model.tasklist.TaskList;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainSceneController {

    @FXML private VBox listBox;
    @FXML private ListView<Task> listView;
    @FXML private TextField taskContentField;
    @FXML private TextField listNameField;
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<String> urgencySelection;
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
    void editTaskAction() {
        ArrayList<Task> tempList = new ArrayList<>(listView.getSelectionModel().getSelectedItems());
        if (tempList.size() == 1) {
            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            tempList.clear();
            tempList.addAll(listView.getSelectionModel().getSelectedItems());
            displayTaskEditor(tempList.get(0));
        } else {
            (new AlertBox()).display("Select one task at a time! \n(Or select a task first)");
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void displayTaskEditor(Task task) {
        new TaskEditor(task).displayWindow();
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
        LocalDate dueDate = datePicker.getValue();
        if (dueDate.isBefore(LocalDate.now())) {
            (new AlertBox()).display("Selected due date is in the past!");
        } else {
            currentList.storeTask(new Task(currentList, taskContentField.getText(), dueDate,
                    getUrgency(urgencySelection.getValue()), false, false));
        }

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
        if (currentList != null) {
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
        listView.setItems(currentList.getTaskListObserver().getObservableList());
        listNameField.setText(currentList.getName().toString());
    }

    private void highLightButton(Button button) {
        button.getStylesheets().clear();
        button.getStylesheets().addAll(getClass().getResource("styling/ListHighlight.css").toExternalForm());
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

        new Load().load(taskListHashMap, "data/save");
        listButtonReCreator();

        setViewSelection();
    }

    @FXML
    void viewSelectionAction() {
        if (currentList != null) {
            String view = viewSelection.getValue();
            switch (view) {
                case "View All":
                    currentList.notifyObserver(currentList.getTaskList());
                    break;
                case "High Urgency":
                    currentList.notifyObserver(currentList.getTaskByUrgency("high"));
                    break;
                case "Mid Urgency":
                    currentList.notifyObserver(currentList.getTaskByUrgency("mid"));
                    break;
                default:
                    currentList.notifyObserver(currentList.getTaskByUrgency("low"));
                    break;
            }
        }
    }

    private void setViewSelection() {
        viewSelection.getItems().addAll("View All", "High Urgency", "Mid Urgency", "Low Urgency");
        viewSelection.setValue("View All");
    }

    void save() {
        try {
            new Save().save(taskListHashMap, "data/save");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void listButtonReCreator() {
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

    private void setUrgencySelection() {
        addUrgencyItems(urgencySelection);
        urgencySelection.setValue("Mid Urgency");
    }

    static void addUrgencyItems(ChoiceBox<String> urgencySelection) {
        urgencySelection.getItems().addAll("High Urgency", "Mid Urgency", "Low Urgency");
    }
}
