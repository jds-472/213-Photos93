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

/**
 * The UserController class is responsible for managing the user interface in the PhotosFX application.
 * It handles user interactions related to album management, including creating, deleting, renaming, and opening albums.
 * 
 * <p>This class interacts with the application's data layer to retrieve the current user and their albums.
 * 
 * <p>FXML elements such as {@code ListView}, {@code TextField}, and {@code Label} are used to display and interact with user albums.
 * 
 * <p>Key functionalities include:
 * <ul>
 *  <li>Creating a new album</li>
 *  <li>Deleting an existing album</li>
 *  <li>Renaming an album</li>
 *  <li>Opening an album to view its photos</li>
 *  <li>Searching for photos</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */
public class UserController {

    @FXML private ListView<String> albumList;
    @FXML private TextField albumNameField;
    @FXML private Label userWelcome;

    private ObservableList<String> albums = FXCollections.observableArrayList();
    private Set<Album> userAlbums;
    private Map<String, Album> albumMap = new HashMap<>(); // Map to store album names and their corresponding Album objects

    /**
     * Initializes the UserController by setting the current FXML context and populating the album list.
     * This method is called automatically by the JavaFX framework when the FXML file is loaded.
     */
    public void initialize() {
        Data.setCurrentFXML(Data.USERFXML);
        albumList.getItems().clear();
        albumMap.clear(); // Clear the map before populating it again
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

    /**
     * Handles the key press event for the album name field.
     * If the Enter key is pressed, it triggers the createAlbum method.
     * 
     * @param keyEvent the key event triggered by the user
     */
    public void albumNameFieldKeyPressed(javafx.scene.input.KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            createAlbum(new ActionEvent(albumNameField, null));
        }
    }

    /**
     * Creates a new album with the name provided in the album name field.
     * Validates the input and updates the album list accordingly.
     * 
     * @param event the action event triggered by the user
     */
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
        initialize();
    }

    /**
     * Deletes the selected album from the album list.
     * If no album is selected, an alert is shown to the user.
     * A confirmation dialog is displayed to confirm the deletion.
     * 
     * @param event the action event triggered by the user
     */
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

    /**
     * Renames the selected album with a new name provided by the user.
     * If no album is selected, an alert is shown to the user.
     * If the new name is empty or already exists, an alert is shown to the user.
     * 
     * @param event the action event triggered by the user
     */
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

    /**
     * Opens the selected album and transitions to the album view.
     * If no album is selected, an alert is shown to the user.
     * 
     * @param event the action event triggered by the user
     */
    public void openAlbum(ActionEvent event) {
        if (albumList.getSelectionModel().getSelectedItem() == null) {
            showAlert("No album selected.");
            return;
        }
        try {
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

    /**
     * Displays the album data in a formatted string.
     * 
     * @param album the album to display
     * @return the formatted string representing the album data
     */
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

    /**
     * Searches for photos in the application and transitions to the search view.
     * 
     * @param event the action event triggered by the user
     */
    public void searchPhotos(ActionEvent event) {
        try {
            Data.setCurrentFXML(Data.SEARCHFXML);
            Parent root = FXMLLoader.load(getClass().getResource("search.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

    /**
     * Displays an alert with the specified message.
     * 
     * @param message the message to display in the alert
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
