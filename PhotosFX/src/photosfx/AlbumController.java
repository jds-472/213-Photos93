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

public class AlbumController {

    @FXML private Label albumLabel;
    @FXML private Label photoOptionsLabel;
    @FXML private Label albumOptionsLabel;
    @FXML private Button copyPhotoButton;
    @FXML private Button movePhotoButton;
    @FXML private ImageView stock;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("photo_options.fxml"));
    Node photoOptions;
    @FXML private VBox pictureBox;
    @FXML private TilePane displayPane;
    private ArrayList<Photo> photos;

    private Map<VBox, Photo> photoMap = new HashMap<>(); //Map for storing the photo and its corresponding VBox

    public void initialize() {
        Data.setCurrentFXML(Data.ALBUMFXML);
        Data.setCurrentPhoto(null);
        albumLabel.setText("You are in the album " + Data.getCurrentAlbum().getName() + "!");
        albumOptionsLabel.setText("Album Options for " + Data.getCurrentAlbum().getName() + ":");
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

    // private void removePhotoOptions()
    // {
    //     for (Node node : displayPane.getChildren()) {
    //         if (node instanceof VBox) {
    //             VBox vbox = (VBox) node;
    //             if (vbox.getChildren().contains(photoOptions)) {
    //                 vbox.getChildren().remove(photoOptions);
    //                 return; // Exit the loop once the old photoOptions is removed
    //             }
    //         }
    //     }
    // }

    public void showOptions(MouseEvent event)
    {
        VBox pictureContainer = (VBox) event.getSource();
        Data.setCurrentPhoto(photoMap.get(pictureContainer));
        photoOptionsLabel.setText("Photo Options for " + Data.getCurrentPhoto().getCaption() + ":");
    }

    public void addPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        java.io.File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Data.getCurrentAlbum().addPhoto(new Photo(file.getAbsolutePath(), file.getName())); //have to check if photo is a copy here
            initialize();
        }
    }

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
        initialize();
        
    }

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
            initialize();
        }
    }

    private void noPhotoAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Photo Selected");
        alert.setHeaderText("No photo selected");
        alert.setContentText("Please select a photo to view options.");
        alert.showAndWait();
    }

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
