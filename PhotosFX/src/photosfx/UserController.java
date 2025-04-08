package photosfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class UserController {

    @FXML private ListView<String> albumList;
    @FXML private TextField albumNameField;

    private ObservableList<String> albums;

    public void initialize() {
        // Load user albums from model later
        albums = FXCollections.observableArrayList("Vacation 2023", "Family", "Graduation");
        albumList.setItems(albums);
    }

    public void createAlbum(ActionEvent event) {
        String name = albumNameField.getText().trim();
        if (name.isEmpty()) {
            showAlert("Album name cannot be empty.");
            return;
        }
        if (albums.contains(name)) {
            showAlert("Album already exists.");
            return;
        }
        albums.add(name);
        albumNameField.clear();
    }

    public void deleteAlbum(ActionEvent event) {
        String selected = albumList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No album selected.");
            return;
        }
        albums.remove(selected);
    }

    public void renameAlbum(ActionEvent event) {
        String selected = albumList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No album selected.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selected);
        dialog.setTitle("Rename Album");
        dialog.setHeaderText("Enter new album name:");
        dialog.setContentText("New name:");

        dialog.showAndWait().ifPresent(newName -> {
            if (newName.trim().isEmpty()) {
                showAlert("New name cannot be empty.");
            } else if (albums.contains(newName)) {
                showAlert("Album name already exists.");
            } else {
                albums.set(albums.indexOf(selected), newName);
            }
        });
    }

    public void openAlbum(ActionEvent event) {
        // TODO: Replace with album viewing logic
        showAlert("Opening album not implemented yet.");
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
