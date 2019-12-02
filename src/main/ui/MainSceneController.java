package ui;

import animatefx.animation.*;
import exceptions.TaskException;
import io.Load;
import io.Save;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Name;
import model.TaskListHashMap;
import model.task.Task;
import model.task.Urgency;
import model.tasklist.TaskList;

import javax.jnlp.ClipboardService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainSceneController {

    @FXML private VBox listBox;
    @FXML private ListView<Task> listView;
    @FXML private TextField taskContentField;
    @FXML private TextField listNameField;
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<String> urgencySelection;
    @FXML private ComboBox<String> viewSelection;
    @FXML private Button newListButton;
    @FXML private HBox createTaskBox;
    @FXML private Button newTaskButton;
    @FXML private BorderPane mainPane;
    @FXML private VBox leftBox;
    @FXML private Button hamburger;
    @FXML private HBox hamburgerBox;
    @FXML private SplitMenuButton listEditor;

    private TaskListHashMap taskListHashMap;
    private TaskList currentList;

    @FXML
    void hideCompletedAction() {
        if (currentList != null) {
            if (!listView.getItems().equals(currentList.filterOutCompleted())) {
                currentList.notifyObserver(currentList.filterOutCompleted());
                new FadeIn(listView).setSpeed(1.5).play();
            } else {
                currentList.notifyObserver(currentList.getTaskList());
                new FadeIn(listView).setSpeed(1.5).play();
            }
        }
    }

    //MODIFIES: currentList
    //EFFECTS: Calls sortByDueDate on the currentList upon button press
    @FXML
    void sortListAction() {
        if (currentList != null) {
            currentList.sortByDueDate();
            currentList.sortByDueDate(new ArrayList<>(listView.getItems()));
        }

        new FadeIn(listView).setSpeed(1.5).play();

        if (listView.getItems().contains(selectedTask)) {
            listView.getSelectionModel().select(selectedTask);
        }
    }

    //MODIFIES: currentList, listView, listNameField
    //EFFECTS: Deletes and clears the currentList and the current list view
    @FXML
    void deleteListAction() {
        if (currentList != null) {
            if (new ConfirmationBox().display("<" + currentList.getName().toString() + ">" + "\n will be permanently deleted.")) {
                taskListHashMap.removeTaskList(currentList.getName());
                findToRemoveCurrentListButton();
                clearCurrentList();
            }
        }
    }

    //MODIFIES: listBox
    //EFFECTS: Searches all children of listBox for a button with the name of the currentList and removes said button
    private void findToRemoveCurrentListButton() {
        ArrayList<Object> tempList = new ArrayList<>(listBox.getChildren());
        tempList.forEach(button -> {
            if (button instanceof Button && ((Button) button).getText().equals(currentList.getName().toString())) {
                listBox.getChildren().remove(button);
            }
        });
    }

    //MODIFIES: currentList, listNameField, listView
    //EFFECTS: Sets currentList to null, clears contents of listView and listNameField
    private void clearCurrentList() {
        currentList = null;
        listNameField.clear();
        listView.getItems().clear();
    }

    //MODIFIES: listView.getSelectionModel().getSelectedItems()
    //EFFECTS: Displays the task editor scene loaded with information from the selected task
    //         If selected one and only one item, then pass this item into displayTaskEditor
    //         else alert the user to select one task of a time
    @FXML
    void editTaskAction() {
        ArrayList<Task> tempList = new ArrayList<>(listView.getSelectionModel().getSelectedItems());
        if (tempList.size() == 1) {
            tempList.clear();
            tempList.addAll(listView.getSelectionModel().getSelectedItems());

            editPane.setVisible(true);
            //editPane.setPrefWidth(239);

            new SlideInRight(editPane).setSpeed(2.5).play();

            setTaskFields(tempList.get(0));

            //displayTaskEditor(tempList.get(0));
        } else {
            (new AlertBox()).display("Select one task at a time!");
        }
        listView.getSelectionModel().clearSelection();
        //listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    //MODIFIES: task
    //EFFECTS: Creates a new task editor with information from the given task
    private void displayTaskEditor(Task task) {
        new TaskEditor(task).displayWindow();
    }

/*    //MODIFIES: currentList
    //EFFECTS: Clears all tasks from the current list upon button press
    @FXML
    void clearListAction() {
        if (currentList != null) {
            currentList.clearTaskList();
        }
    }*/

    //MODIFIES: currentList
    //EFFECTS: Searches listView's children for the selected task(s) and removes them from
    //         currentList when found upon button press
    @FXML
    void deleteTaskAction() {
        (new ArrayList<>(listView.getSelectionModel().getSelectedItems())).forEach(task -> {
            try {
                currentList.deleteTask(task);
            } catch (TaskException e) {
                (new AlertBox()).display(e.getMessage() + "!");
            }
        });
        listView.getSelectionModel().clearSelection();
    }

    //MODIFIES: currentList
    //EFFECTS: If taskContentField and datePicker are not empty and currentList is not null,
    //         then create a task and add it to the current list
    //         else alert the users that a list must be selected first
    //         Alert the user when the number of tasks stored in one list exceeds the limit
    //         resets/clears text fields
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

        resetNewTaskButton();
    }

    private void resetNewTaskButton() {
        newTaskButton.setVisible(true);
        new FadeIn(newTaskButton).setSpeed(3.5).play();
        createTaskBox.setVisible(false);
    }

    //MODIFIES: taskContentField, datePicker, urgencySelection
    //EFFECTS: Clears text fields of taskContentField and datePicker, sets value of datePicker to current date
    //         resets urgencySelection to its default value
    private void resetTaskFields() {
        taskContentField.clear();
        datePicker.getEditor().clear();
        datePicker.setValue(null);
        urgencySelection.setValue("Mid Urgency");
    }

    //MODIFIES: currentList
    //EFFECTS: If the selected date of the date picker is before the current date
    //         then alert the user that selected due date cannot be in the past
    //         else create a new task with values from currentList, taskContentField and urgencySelection
    //         and add this task to currentList
    private void createTask() throws TaskException {
        LocalDate dueDate = datePicker.getValue();
        if (dueDate.isBefore(LocalDate.now())) {
            (new AlertBox()).display("Selected due date is in the past!");
        } else {
            currentList.storeTask(new Task(currentList, taskContentField.getText(), dueDate,
                    getUrgency(urgencySelection.getValue()), false, false));
        }
    }

    //EFFECTS: Takes a string from urgencySelection's choices, returns corresponding Enum Urgency
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

    //MODIFIES: listBox.getChildren()
    //EFFECTS: Searches through the children of listBox and and update its name to the text contents in listNameField
    //         upon pressing the enter key
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

    //REQUIRES: != null
    //MODIFIES: taskListHashMap, currentList, button, listNameField
    //EFFECTS: Sets the names of currentList, button, and listNameField to the given name
    //         Remaps the currentList in taskListHashMap with a new key as the given name
    //         while preserving the order of the taskListHashMap
    private void updateNames(Button button, Name newName) {
        taskListHashMap.remap(currentList.getName(), newName);
        currentList.setName(newName);
        button.setText(currentList.getName().toString());
        button.getTooltip().setText(button.getText());
        listNameField.setText(currentList.getName().toString());
    }

    //EFFECTS: Returns whether the given node is a button and its text is not "Settings"
    private boolean isListButton(Node button) {
        return (button instanceof Button) && !((Button) button).getText().equals("Settings");
    }

    //replaceFirst("\\s++$", "") inspired by https://stackoverflow.com/questions/48052726/remove-whitespaces-only-at-the-end-of-a-string-java
    //EFFECTS: Returns whether the given button's text field is the same as the name of the current list after removing
    //         Spaces at the end of the string in the given button's text field
    private boolean isSameName(Button button) {
        return button.getText().equals(currentList.getName().toString())
                && !currentList.getName().toString().equals(listNameField.getText().replaceFirst("\\s++$", ""));
    }

    //MODIFIES: taskListHashMap, listBox
    //EFFECTS: Upon on button press creates and stores a new TaskList with the name "Untitled List" + #
    //         Creates a button with the name of this list and sets its action and tooltip
    @FXML
    void newListButtonAction() {

        TaskList taskList = new TaskList(new Name("Untitled List", nameGenerator("Untitled List")));
        taskListHashMap.storeTaskList(taskList);

        Button button = new Button(taskList.getName().toString());
        button.setPrefSize(382,35);
        setListButtonAction(taskList, button);
        listBox.getChildren().add(listBox.getChildren().size(), button);
        button.setTooltip(new Tooltip(button.getText()));
        resetStoreTask();
    }

    //MODIFIES: button
    //EFFECTS: On button press, set current list to the given taskList, set style of all list buttons to unHighLighted,
    //         then set the style of this given button to highLightButton
    //         Updates the too tip of this button to its text field
    private void setListButtonAction(TaskList taskList, Button button) {
        button.setOnAction(event -> {

            if (!taskList.equals(currentList)) {
                new FadeIn(listView).setSpeed(1.5).play();
                new FadeIn(listNameField).setSpeed(1.5).play();
            }

            setCurrentList(taskList);

            unHighLightAllButtons();
            highLightButton(button);

            button.setTooltip(new Tooltip(button.getText()));

            resetStoreTask();
            cancelAction();
        });
    }

    //EFFECTS: currentList, listView, listNameField
    //MODIFIES: Sets the currentList to the given taskList, sets listView to the given taskList, set listNameField to
    //          the name of the currentList
    private void setCurrentList(TaskList taskList) {
        currentList = taskList;
        listView.setItems(currentList.getTaskListObserver().getObservableList());
        listNameField.setText(currentList.getName().toString());
    }

    //MODIFIES: button
    //EFFECTS: Sets the styling of this button to ListHighlight.css
    private void highLightButton(Button button) {
        button.getStylesheets().clear();
        button.getStylesheets().addAll(getClass().getResource("styling/ListHighlight.css").toExternalForm());
    }

    //MODIFIES: listBox.getChildren()
    //EFFECTS: Sets all buttons in listBox.getChildren() to the style of ListUnHighlight.css
    private void unHighLightAllButtons() {
        listBox.getChildren().forEach(tempButton -> {
            if (tempButton instanceof Button) {
                ((Button) tempButton).getStylesheets().clear();
                ((Button) tempButton).getStylesheets().addAll(
                        getClass().getResource("styling/ListUnHighlight.css").toExternalForm());
            }
        });
    }

    //EFFECTS: If the given string is an existing room name, then return the a string in the form of
    //         the given name + (the largest count of existing names + 1)
    private int nameGenerator(String name) {
        int largest = 0;
        for (Name key : taskListHashMap.getKeys()) {
            if (key.getRootName().equals(name) && checkRoot(key, name)) {
                largest = key.getCount();
            }
        }
        return largest + 1;
    }

    //EFFECTS: Checks if the given string is a root name for the given name
    //         return true if the given name is a root name or the given string is not equal to the given name
    private Boolean checkRoot(Name name, String str) {
        return name.getCount() == 0 || !name.toString().equals(str);
    }

    //EFFECTS: initializes fields, loads data from data/save
    @FXML
    public void initialize() throws ClassNotFoundException, IOException, TaskException {
        taskListHashMap = new TaskListHashMap();
        setUrgencySelection();
        setListView();

        new Load().load(taskListHashMap, "data/save");

        listButtonReCreator();

        editPane.setVisible(false);

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listView.setOnMouseClicked(mouseEvent -> {
            if (!listView.getSelectionModel().getSelectedItems().isEmpty()) {
                int index = listView.getSelectionModel().getSelectedIndex();
                if (!editPane.isVisible()) {
                    resetStoreTask();
                    editTaskAction();
                    listView.getSelectionModel().select(index);
                } else {
                    if (!selectedTask.equals(listView.getSelectionModel().getSelectedItem())) {
                        editTaskAction();
                        listView.getSelectionModel().select(index);
                    } else {
                        cancelAction();
                    }
                }
            } else {
                cancelAction();
                resetStoreTask();
            }
        });

        newTaskButton.setVisible(true);
        createTaskBox.setVisible(false);

        leftBox.managedProperty().bind(leftBox.visibleProperty());
        leftBox.setVisible(false);

        editPane.managedProperty().bind(editPane.visibleProperty());

        listEditor.setOnMouseClicked(mouseEvent -> {
            resetStoreTask();
        });

        //https://stackoverflow.com/questions/26895534/javafx-split-menu-button-arrow-trigger-event
        listEditor.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (isNowShowing) {
                resetStoreTask();
            }
        });

        hamburger.setOnAction(event -> {
            resetStoreTask();
            leftBox.setVisible(!leftBox.isVisible());
            if (leftBox.isVisible()) {
                hamburger.setText("❌");
                new SlideInLeft(leftBox).setSpeed(2.5).play();
            } else {
                hamburger.setText("☰");
            }
            new FadeIn(hamburger).setSpeed(4).play();
        });

        listNameField.setFocusTraversable(false);

        listNameField.setOnMouseClicked(mouseEvent -> {
            resetStoreTask();
        });

        viewSelection.setOnMouseClicked(mouseEvent -> {
            resetStoreTask();
        });

        setViewSelection();
    }

    void resetStoreTask() {
        if (createTaskBox.isVisible()) {
            resetNewTaskButton();
            resetTaskFields();
        }
    }

    @FXML
    void newTaskButtonAction() {
        //new FadeOut(newTaskButton).setSpeed(3.5).play();
        cancelAction();
        newTaskButton.setVisible(false);
        createTaskBox.setVisible(true);
        new FadeIn(createTaskBox).setSpeed(3.5).play();
    }

    //MODIFIES: currentList
    //EFFECTS: If view selection is "View All", then display all tasks in the current list
    //         If view selection is "High Urgency", then display high urgency tasks in the current list
    //         If view selection is "Mid Urgency", then display mid urgency tasks in the current list
    //         else display low urgency tasks in the current list
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

        new FadeIn(listView).setSpeed(1.5).play();

        if (listView.getItems().contains(selectedTask)) {
            listView.getSelectionModel().select(selectedTask);
        }
    }

    //MODIFIES: viewSelection
    //EFFECTS: Adds the following menu items into the viewSelection combo box
    //         Sets the default value of viewSelection to View All
    private void setViewSelection() {
        viewSelection.getItems().addAll("View All", "High Urgency", "Mid Urgency", "Low Urgency");
        viewSelection.setValue("View All");
    }

    //MODIFIES: data/save
    //EFFECTS: Saves the current taskListHashMap into data/save
    void save() {
        try {
            new Save().save(taskListHashMap, "data/save");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: listBox.getChildren()
    //EFFECTS: Creates buttons with the names of each list in taskListHashMap linked to its respective list
    //         Adds this new button to the listBox and creates a new tooltip for this button with its contents
    //         being the name of this button
    @FXML
    private void listButtonReCreator() {
        taskListHashMap.getKeys().forEach(name -> {
            TaskList taskList = taskListHashMap.getTaskList(name);
            Button button = new Button(taskList.getName().toString());
            button.setPrefSize(382,35);
            setListButtonAction(taskList, button);
            listBox.getChildren().add(listBox.getChildren().size(), button);
            button.setTooltip(new Tooltip(button.getText()));
        });
    }

    //MODIFIES: listView
    //EFFECTS: Sets the selection mode of listView to multiple
    //         Sets the cell factory of listView to CellController
    private void setListView() {
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setCellFactory(cell -> new CellController(listView));
    }

    //MODIFIES: urgencySelection
    //EFFECTS: Adds choices to urgencySelection and set its default value to Mid Urgency
    private void setUrgencySelection() {
        addUrgencyItems(urgencySelection);
        urgencySelection.setValue("Mid Urgency");
    }

    //MODIFIES: urgencySelection
    //EFFECTS: Adds the following choices to urgencySelection
    static void addUrgencyItems(ChoiceBox<String> urgencySelection) {
        urgencySelection.getItems().addAll("High Urgency", "Mid Urgency", "Low Urgency");
    }

    @FXML private ScrollPane editPane;
    @FXML private TextField taskContent1;
    @FXML private DatePicker datePicker1;
    @FXML private ChoiceBox<String> urgencySelection1;
    @FXML private Button save;
    @FXML private Button cancel;

    private Task selectedTask;


    //MODIFIES: this
    //EFFECTS: Sets the values of taskContent, datePicker, and urgencySelection to the values of corresponding fields
    //         in task
    private void setTaskFields(Task task) {
        selectedTask = task;
        taskContent1.setText(task.getContent());
        datePicker1.setValue(task.getDueDateObj());
        setUrgency1Selection();
        urgencySelection1.setValue(convertUrgency(task.getUrgency().getString()));
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
    private void setUrgency1Selection() {
        urgencySelection1.getItems().clear();
        MainSceneController.addUrgencyItems(urgencySelection1);
    }

    //MODIFIES: this
    //EFFECTS: Updates the fields in task in correspondence to the values of taskContent, the given dueDate and
    //         urgencySelection
    private void updateTask(Task task) {
        task.setContent(taskContent1.getText());
        task.setDueDate(datePicker1.getValue());
        task.setUrgency(MainSceneController.getUrgency(urgencySelection1.getValue()));
    }

    //EFFECTS: Event action of the save button
    //         If taskContent is not empty and the selected due date is not in the past, then update corresponding
    //         fields of task, else if the selected due date is in the past,  display
    //         "Selected due date is in the past!" in an alert box.
    //         If taskContent is empty, alert the user with an alert box of "Fields can't be empty!"
    @FXML
    void saveAction() {
/*        if (taskContent.getText() != null) {
            LocalDate dueDate = datePicker.getValue();
            if (dueDate.isBefore(LocalDate.now())) {
                (new AlertBox()).display("Selected due date is in the past!");
            } else {
                updateTask(dueDate);
                window.close();
            }
        } else {
            (new AlertBox()).display("Fields can't be empty!");
        }*/


/*        AnimationFX slideOut = new SlideOutRight(editPane);
        slideOut.setOnFinished(event -> editPane.setVisible(false));
        slideOut.setSpeed(16).play();*/
        editPane.setVisible(false);

        updateTask(selectedTask);
        listView.getSelectionModel().clearSelection();
    }

    //EFFECTS: Event action of the cancel button
    //         Closes the current window
    @FXML
    void cancelAction() {
        //window.close();
        editPane.setVisible(false);
/*        AnimationFX slideOut = new SlideOutRight(editPane);
        slideOut.setOnFinished(event -> editPane.setVisible(false));
        slideOut.setSpeed(16).play();*/
        //editPane.setPrefWidth(0);
        listView.getSelectionModel().clearSelection();

    }
}
