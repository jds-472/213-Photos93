package photosfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Photo;
import model.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.TilePane;
import javafx.geometry.Pos;

/**
 * The AlbumController class is responsible for managing the album interface in the PhotosFX application.
 * It allows the user to view, add, remove, and manage photos within an album.
 * 
 * <p>This class interacts with the application's data layer to retrieve and modify album and photo information.
 * It also provides feedback to the user through alerts and confirmation dialogs.</p>
 * 
 * <p>FXML elements such as {@code Label}, {@code Button}, and {@code ImageView} are used to display and interact with album data.
 * 
 * <p>Key functionalities include:</p>
 * <ul>
 *  <li>Displaying the album name and options</li>
 * <li>Adding a new photo to the album</li>
 * <li>Removing a photo from the album</li>
 * <li>Moving or copying a photo to another album</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */
public class AlbumController {

    @FXML private Label albumLabel;
    @FXML private Label photoOptionsLabel;
    @FXML private Label albumOptionsLabel;
    @FXML private Button copyPhotoButton;
    @FXML private Button movePhotoButton;
    @FXML private ImageView stock;
    @FXML private VBox pictureBox;
    @FXML private TilePane displayPane;
    @FXML private VBox photoOptions;
    @FXML private VBox albumOptionsBox;
    private PhotoOptionsController photoOptionsController;
    private ArrayList<Photo> photos;

    private Map<VBox, Photo> photoMap = new HashMap<>(); //Map for storing the photo and its corresponding VBox

    /**
     * Initializes the AlbumController by setting the current FXML context and populating the album options.
     * This method is called automatically by the JavaFX framework when the FXML file is loaded.
     */
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("photo_options.fxml"));
            photoOptions = loader.load();
            photoOptionsController = loader.getController();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        photoOptionsController.setAlbumController(this);
        albumOptionsBox.getChildren().add(photoOptions);
        Data.setCurrentFXML(Data.ALBUMFXML);
        Data.setCurrentPhoto(null);
        albumLabel.setText("You are in the album " + Data.getCurrentAlbum().getName() + "!");
        albumOptionsLabel.setText("Album Options for " + Data.getCurrentAlbum().getName() + ":");
        refresh();
    }

    /**
     * Refreshes the display pane to show the current photos in the album.
     * Clears the existing photos and repopulates the display pane with the updated list of photos.
     */
    public void refresh()
    {
        displayPane.getChildren().clear();
        if (Data.getCurrentAlbum() == null) {return;}
        photos = new ArrayList<>(Data.getCurrentAlbum().getPhotos());
        for (Photo photo : photos) {
            ImageView imageView = new ImageView(photo.getPicture());
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            VBox pictureContainer = new VBox(imageView, new Label(photo.getCaption()));
            pictureContainer.spacingProperty().setValue(5);
            pictureContainer.alignmentProperty().setValue(Pos.CENTER);
            pictureContainer.setOnMouseClicked(this::showOptions);
            photoMap.put(pictureContainer, photo);
            displayPane.getChildren().add(pictureContainer);
        }
    }

    /**
     * Displays the photo options when a photo is clicked.
     * Sets the current photo in the data model and updates the photo options label.
     * 
     * @param event the mouse event triggered by the user
     */
    public void showOptions(MouseEvent event)
    {
        VBox pictureContainer = (VBox) event.getSource();
        Data.setCurrentPhoto(photoMap.get(pictureContainer));
        photoOptionsLabel.setText("Photo Options for " + Data.getCurrentPhoto().getCaption() + ":");
    }

    /**
     * Updates the photo options label to reflect the current photo's caption.
     */
    public void updatePhotoOptionsLabel() {
        photoOptionsLabel.setText("Photo Options for " + Data.getCurrentPhoto().getCaption() + ":");
    }

    /**
     * Adds a new photo to the current album.
     * Opens a file chooser dialog to select an image file and adds it to the album.
     * Checks for duplicate photos and provides feedback to the user.
     * 
     * @param event the action event triggered by the user
     */
    public void addPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        java.io.File file = fileChooser.showOpenDialog(stage);
        Photo newPhoto = new Photo(file.getName(), file.toURI().toString());
        if (file != null) {
            for (model.Album album : Data.getCurrentUser().getAlbums()) {
                for (Photo photo : album.getPhotos()) {
                    if (photo.equals(newPhoto) && album.getName().equals(Data.getCurrentAlbum().getName())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Duplicate Photo");
                        alert.setHeaderText("Photo already exists in album " + album.getName());
                        alert.setContentText("Please select a different photo.");
                        alert.showAndWait();
                        return;
                    }
                    else if (photo.equals(newPhoto))
                    {
                        onlyCopy(Data.getCurrentAlbum(), photo); //use existing reference to keep tags/caption when copying
                        Data.saveData(); //save after copying
                        refresh();
                        return;
                    }
                }
            }
            Data.getCurrentAlbum().addPhoto(newPhoto); //have to check if photo is a copy here
            Data.saveData(); //save after adding
            refresh();
        }
    }

    /**
     * Removes the currently selected photo from the album.
     * Displays a confirmation dialog to confirm the deletion.
     * 
     * @param event the action event triggered by the user
     */
    public void removePhoto(ActionEvent event) {
        if (Data.getCurrentPhoto() == null) {
            noPhotoAlert();
            return;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("No", "No", "Yes");
        dialog.setTitle("Delete Photo");
        dialog.setHeaderText("Are you sure you want to delete " + Data.getCurrentPhoto().getCaption() + "?");
        dialog.setContentText("Choose your option:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals("Yes")) {
            Data.getCurrentAlbum().removePhoto(Data.getCurrentPhoto());
        }
        refresh();
        
    }

    /**
     * Moves or copies the currently selected photo to another album.
     * Displays a choice dialog to select the target album.
     * 
     * @param event the action event triggered by the user
     */
    public void moveOrCopy(ActionEvent event)
    {
        String text = "";
        if (event.getSource() == copyPhotoButton) {
            text = "Copy";
        } else if (event.getSource() == movePhotoButton) {
            text = "Move";
        }
        else {
            return;
        }
        if (Data.getCurrentPhoto() == null) {
            noPhotoAlert();
            return;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(Data.getCurrentAlbum().toString(), Data.getCurrentUser().getAlbumsAsString());
        dialog.setTitle(text +  " Photo");
        text = text.toLowerCase();
        dialog.setHeaderText("Select an album to " + text + " the photo to:");
        dialog.setContentText("Choose your option:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selectedAlbumName = result.get();
            if (selectedAlbumName.equals(Data.getCurrentAlbum().getName())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Selection");
                alert.setHeaderText("Cannot " + text + " photo to the same album");
                alert.setContentText("Cancelling " + text + " photo operation.");
                alert.showAndWait();
                return;
            }
            Data.getCurrentUser().getAlbum(selectedAlbumName).addPhoto(Data.getCurrentPhoto());
            if (text.equals("move"))
            {
                Data.getCurrentAlbum().removePhoto(Data.getCurrentPhoto());
            }
            refresh();
        }
    }
    
    /**
     * Copies the currently selected photo to another album.
     * Displays a choice dialog to select the target album.
     * 
     * @param event the action event triggered by the user
     */
    private void onlyCopy(model.Album target, Photo photo)
    {
        target.addPhoto(photo);
        refresh();
    }

    /**
     * Displays an alert when no photo is selected.
     */
    private void noPhotoAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Photo Selected");
        alert.setHeaderText("No photo selected");
        alert.setContentText("Please select a photo to view options.");
        alert.showAndWait();
    }

    /**
     * Displays the slideshow view when the "Slideshow" button is clicked.
     * 
     * @param event the action event triggered by the user
     */
    public void slideShow(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("slideshow.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
