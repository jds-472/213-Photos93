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
import model.Album;
import model.Data;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class UserController {

    @FXML private ListView<String> albumList;
    @FXML private TextField albumNameField;
    @FXML private Label userWelcome;

    private ObservableList<String> albums = FXCollections.observableArrayList();
    private Set<Album> userAlbums;
    private Map<String, Album> albumMap = new HashMap<>(); // Map to store album names and their corresponding Album objects

    public void initialize() {
        userAlbums = Data.getCurrentUser().getAlbums();
        userWelcome.setText("Welcome " + Data.getCurrentUser().getName() + " to your Albums!");
        for (Album album : userAlbums) {
            String displayText = displayAlbumData(album);
            albums.add(displayText);
            albumMap.put(displayText, album);  // Map full display string to album
        }
        albumList.setItems(albums);
        // Load user albums from model later
        // albums = FXCollections.observableArrayList();
    }

    public void albumNameFieldKeyPressed(javafx.scene.input.KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            createAlbum(new ActionEvent(albumNameField, null));
        }
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
        Data.getCurrentUser().addAlbum(new Album(name)); // Add the new album to the user's albums
        albumMap.put(name, new Album(name)); // Add the new album to the map
        albumNameField.clear();
    }

    public void deleteAlbum(ActionEvent event) {
        String selected = albumList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No album selected.");
            return;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("No", "No", "Yes");
        dialog.setTitle("Delete Album");
        dialog.setHeaderText("Are you sure you want to delete " + selected + " with all its photos?");
        dialog.setContentText("Choose your option:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals("Yes")) {
            albums.remove(selected);
            Data.getCurrentUser().removeAlbum(albumMap.get(selected)); // Remove the album from the user's albums
            albumMap.remove(selected); // Remove the album from the map
        }
    }

    public void renameAlbum(ActionEvent event) {
        String selected = albumList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No album selected.");
            return;
        }
        Album selectedAlbum = albumMap.get(selected); // Get the Album object from the map
        if (selectedAlbum == null) {
            showAlert("Album not found.");
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
                albumMap.remove(selected); // Remove the old name from the map
                albums.set(albums.indexOf(selected), newName);
                Data.getCurrentUser().getAlbum(selected).setName(newName); // Update the album name in the user's albums
                selectedAlbum.setName(newName); // Update the album name in the Album object
                albumMap.put(newName, selectedAlbum); // Add the new name to the map
            }
        });
    }

    public void openAlbum(ActionEvent event) {
        if (albumList.getSelectionModel().getSelectedItem() == null) {
            showAlert("No album selected.");
            return;
        }
        try {
            Data.setCurrentFXML(Data.ALBUMFXML);
            String selected = albumList.getSelectionModel().getSelectedItem();
            Data.setCurrentAlbum(albumMap.get(selected)); // Get the Album object from the map
            Parent root = FXMLLoader.load(getClass().getResource("album.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String displayAlbumData(Album album) {
        int count = album.getPhotoCount();
        LocalDateTime start = album.getEarliestDate();
        LocalDateTime end = album.getLatestDate();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String dateRange = (start == null || end == null)
            ? "No date info"
            : fmt.format(start) + " – " + fmt.format(end);
        return String.format("%s (%d photo%s, %s)", album.getName(), count, count == 1 ? "" : "s", dateRange);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
