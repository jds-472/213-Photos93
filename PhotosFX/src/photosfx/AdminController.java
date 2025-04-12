package photosfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import model.User;
import java.util.Optional;

public class AdminController {

    @FXML private ListView<String> userList;
    @FXML private TextField usernameField;

    private ObservableList<String> users = FXCollections.observableArrayList();

    public void initialize() {
        for (User user : User.users) {
            this.users.add(user.getName()); //added this keyword for clarity
        }
        userList.setItems(users);
    }

    public void newUsernameFieldKeyPressed(javafx.scene.input.KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            createUser(new ActionEvent(usernameField, null));
        }
    }

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
        User.users.add(new User(newUser));
        users.add(newUser);
        usernameField.clear();
    }

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
            User.users.remove(User.getUser(selectedUser));
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Admin Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
