package photosfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.util.ArrayList;

public class AdminController {

    //TODO: this class

    @FXML private ListView<String> userList;
    @FXML private TextField usernameField;

    private ObservableList<String> users;

    public void initialize() {
        // Dummy list for now, can be replaced with real user loading
        users = FXCollections.observableArrayList("stock", "demoUser");
        userList.setItems(users);
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
        users.add(newUser);
        usernameField.clear();
    }

    public void deleteUser(ActionEvent event) {
        String selectedUser = userList.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("No user selected.");
            return;
        }
        if (selectedUser.equals("stock")) {
            showAlert("Cannot delete stock user.");
            return;
        }
        users.remove(selectedUser);
    }

    public void logout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Admin Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
