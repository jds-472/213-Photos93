package photosfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import model.User;
import model.Data;
import java.util.Optional;

/**
 * The AdminController class is responsible for managing the admin interface in the PhotosFX application.
 * It allows the admin to view, create, and delete users, as well as manage the user list displayed in the UI.
 * 
 * <p>This class interacts with the application's data layer to retrieve and modify user information.
 * It also provides feedback to the admin through alerts and confirmation dialogs.</p>
 * 
 * <p>FXML elements such as {@code ListView} and {@code TextField} are used to display and input user data.
 * The class is initialized with the current FXML context and populates the user list from the data source.</p>
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Displaying the list of users</li>
 *   <li>Adding a new user</li>
 *   <li>Deleting an existing user</li>
 *   <li>Handling user input and providing feedback</li>
 * </ul>
 * 
 * <p>Note: This class uses JavaFX components and requires proper setup of the FXML file and data layer.</p>
 */
public class AdminController {

    @FXML private ListView<String> userList;
    @FXML private TextField usernameField;

    private ObservableList<String> users = FXCollections.observableArrayList();

    /**
     * Initializes the AdminController by setting the current FXML context and populating the user list.
     * This method is called automatically by the JavaFX framework when the FXML file is loaded.
     * 
     * 
     */
    public void initialize() {
        Data.setCurrentFXML(Data.ADMINFXML);
        for (User user : Data.getUsers()) {
            this.users.add(user.getName()); //added this keyword for clarity
        }
        userList.setItems(users);
    }

    /**
     * Handles the key press event for the username field.
     * If the Enter key is pressed, it triggers the createUser method.
     * 
     * @param keyEvent the key event triggered by the user
     */
    public void newUsernameFieldKeyPressed(javafx.scene.input.KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            createUser(new ActionEvent(usernameField, null));
        }
    }

    /**
     * Creates a new user based on the input from the username field.
     * If the username is empty or already exists, an alert is shown to the user.
     * @param event the action event triggered by the user
     */
    public void createUser(ActionEvent event) {
        String newUser = usernameField.getText().trim();
        if (newUser.isEmpty()) {
            showAlert("Username cannot be empty.");
            return;
        }
        if (users.contains(newUser)) {
            showAlert("Username already exists.");
            return;
        }
        Data.addUser(new User(newUser));
        users.add(newUser);
        usernameField.clear();
    }

    /**
     * Deletes the selected user from the user list.
     * If no user is selected, an alert is shown to the user.
     * A confirmation dialog is displayed to confirm the deletion.
     * @param event the action event triggered by the user
     */
    public void deleteUser(ActionEvent event) {
        String selectedUser = userList.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("No user selected.");
            return;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("No", "No", "Yes");
        dialog.setTitle("Delete User");
        dialog.setHeaderText("Are you sure you want to delete " + selectedUser + " with all its albums?");
        dialog.setContentText("Choose your option:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals("Yes")) {
            users.remove(selectedUser);
            Data.removeUser(Data.getUser(selectedUser));
        }
    }

    /**
     * Displays an alert with the specified message.
     * This helper method is used to provide feedback to the admin for various actions.
     * 
     * @param message the message to be displayed in the alert
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Admin Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
