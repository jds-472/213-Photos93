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
import model.User;
import model.Album;
import model.Data;

import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class UserController {

    @FXML private ListView<String> albumList;
    @FXML private TextField albumNameField;

    private ObservableList<String> albums = FXCollections.observableArrayList();
    private Set<Album> userAlbums;
    private Map<String, Album> albumMap = new HashMap<>(); // Map to store album names and their corresponding Album objects

    public void initialize() {
        userAlbums = Data.getCurrentUser().getAlbums();
        for (Album album : userAlbums) {
            albums.add(album.getName());
            albumMap.put(album.getName(), album); // Store the album in the map
        }
        albumList.setItems(albums);
        // Load user albums from model later
        // albums = FXCollections.observableArrayList();
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
        try {
            String selected = albumList.getSelectionModel().getSelectedItem();
            Data.setCurrentAlbum(albumMap.get(selected)); // Get the Album object from the map
            Parent root = FXMLLoader.load(getClass().getResource("stock.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event) {
        try {
            Data.setCurrentUser(null);
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
