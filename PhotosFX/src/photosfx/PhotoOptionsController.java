package photosfx;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.Data;
import model.Photo;
import model.Tag;

/**
 * The PhotoOptionsController class is responsible for managing the options available for a selected photo in the PhotosFX application.
 * It provides functionalities for adding and removing tags, displaying the photo, and captioning the photo.
 * 
 * <p>This class interacts with the application's data layer to retrieve the current photo and manage its associated tags and captions.
 * 
 * <p>FXML elements such as {@code Button}, {@code Label}, and {@code VBox} are used to display and interact with photo options.
 * 
 * <p>Key functionalities include:
 * <ul>
 *   <li>Adding a tag to the photo</li>
 *   <li>Removing a tag from the photo</li>
 *   <li>Displaying the photo in a new window</li>
 *   <li>Captioning the photo</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */
public class PhotoOptionsController {

    private AlbumController albumController;
    

    /**
     * Adds a new tag to the current Photo object.
     * If the current photo is null, it shows an alert indicating that no photo is selected.
     * It allows the user to select an existing tag or create a new one.
     * 
     * @param event the action event triggered by the user
     */
    public void addTag(ActionEvent event)
    {
        Photo currentPhoto = Data.getCurrentPhoto();
        if (currentPhoto == null) {
            noPhotoAlert();
            return;
        }
        //create popup to list current tags to select from and add new tags
        ChoiceDialog<String> choice = new ChoiceDialog<>("create new tag", Tag.tagTypes);
        choice.getItems().add("create new tag");
        choice.setTitle("Add a Tag");
        choice.setHeaderText("Select a tag to add to the photo or create a new one");
        Optional<String> result = choice.showAndWait();
        if (result.isPresent()) {
            if (result.get().equals("create new tag")) {
                TextInputDialog newTagDialog = new TextInputDialog("new tag name");
                newTagDialog.setTitle("Create a new tag");
                Optional<String> newTagResult = newTagDialog.showAndWait();
                if (newTagResult.isPresent()) {
                    Tag.tagTypes.add(newTagResult.get());
                }
            } else {
                TextInputDialog dialog = new TextInputDialog("new tag value");
                dialog.setTitle("Set the value for the tag " + result.get());
                Optional<String> tagValueResult = dialog.showAndWait();
                if (tagValueResult.isPresent()) {
                    Tag tag = new Tag(result.get(), tagValueResult.get());
                    currentPhoto.addTag(tag);
                }
            }
        }
    }

    /**
     * Removes a tag from the current Photo object.
     * If the current photo is null, it shows an alert indicating that no photo is selected.
     * It allows the user to select a tag to remove from the photo.
     * 
     * @param event the action event triggered by the user
     */
    public void removeTag(ActionEvent event) {
        Photo currentPhoto = Data.getCurrentPhoto();
        if (currentPhoto == null) {
            noPhotoAlert();
            return;
        }
        //create popup to list current tags to select from and remove
        if(currentPhoto.getTags().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Tags");
            alert.setHeaderText("No tags to remove");
            alert.setContentText("This photo has no tags to remove.");
            alert.showAndWait();
            return;
        }
        ChoiceDialog<String> choice = new ChoiceDialog<>("remove tag", currentPhoto.getTagsAsString());
        choice.setTitle("Remove a Tag");
        choice.setHeaderText("Select a tag to remove from the photo");
        Optional<String> result = choice.showAndWait();
        if (result.isPresent()) {
            for (Tag tag : currentPhoto.getTags()) {
                if (tag.toString().equals(result.get())) {
                    currentPhoto.removeTag(tag);
                    break;
                }
            }
        }
    }

    /**
     * Displays the current photo in a new window.
     * If the current photo is null, it shows an alert indicating that no photo is selected.
     * 
     * @param event the action event triggered by the user
     */
    public void displayPhoto(ActionEvent event) {
        Photo currentPhoto = Data.getCurrentPhoto();
        if (currentPhoto == null) {
            noPhotoAlert();
            return;
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("display.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Captions the current photo with a user-defined caption.
     * If the current photo is null, it shows an alert indicating that no photo is selected.
     * 
     * @param event the action event triggered by the user
     */
    public void captionPhoto(ActionEvent event) {
        Photo currentPhoto = Data.getCurrentPhoto();
        if (currentPhoto == null) {
            noPhotoAlert();
            return;
        }
        TextInputDialog dialog = new TextInputDialog(Data.getCurrentPhoto().getCaption());
        dialog.setTitle("Caption Photo");
        Optional<String> result = dialog.showAndWait();
        while(!result.isPresent() || result.get().equals("")) {
            dialog.setContentText("Caption is invalid, try again");
            result = dialog.showAndWait();
        }
        currentPhoto.setCaption(result.get());
        albumController.refresh();
        albumController.updatePhotoOptionsLabel();
    }

    /**
     * Sets the AlbumController for this PhotoOptionsController.
     * 
     * @param albumController the AlbumController to set
     */
    public void setAlbumController(AlbumController albumController) {
        this.albumController = albumController;
    }

    /**
     * Displays an alert indicating that no photo is selected.
     */
    private void noPhotoAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Photo Selected");
        alert.setHeaderText("No photo selected");
        alert.setContentText("Please select a photo to view options.");
        alert.showAndWait();
    }
}
