package photosfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Photo;
import model.Tag;
import model.Album;
import model.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.*;

public class AlbumController {

    @FXML private Label label;
    @FXML private ImageView stock;
    @FXML private Button photoOption0;
    @FXML private Button photoOption1;
    @FXML private Button photoOption2;
    @FXML private Button photoOption3;
    @FXML private HBox photoOptions;
    @FXML private VBox pictureBox;
    @FXML private VBox leftBox;
    @FXML private VBox rightBox;
    // private ArrayList<ImageView> stocks = new ArrayList<>(java.util.Arrays.asList(stock));
    private ArrayList<Photo> photos;

    private Map<VBox, Photo> photoMap = new HashMap<>(); //Map for storing the photo and its corresponding VBox

    //I have no idea how to get the objects from the output stream yet so I'm just gonna initalize the photos to the stock
    public AlbumController() {
    }

    public void initialize() {
        label.setText("Stock");
        leftBox.getChildren().clear();
        rightBox.getChildren().clear();
        if (Data.getCurrentAlbum() == null) {return;}
        photos = new ArrayList<>(Data.getCurrentAlbum().getPhotos());
        for (int i = 0; i < photos.size(); i++) {
            ImageView stock = new ImageView(photos.get(i).getPicture());
            stock.setFitHeight(200);
            stock.setFitWidth(200);
            VBox pictureContainer = new VBox(stock, new Label(photos.get(i).getCaption()));
            pictureContainer.spacingProperty().setValue(5);
            pictureContainer.alignmentProperty().setValue(javafx.geometry.Pos.CENTER);
            pictureContainer.setOnMouseClicked(this::showOptions);
            photoMap.put(pictureContainer, photos.get(i));
            if (i % 2 == 0) {
                leftBox.getChildren().add(pictureContainer);
            } else {
                rightBox.getChildren().add(pictureContainer);
            }
        }
    }

    private void removePhotoOptions()
    {
        for (Node node : leftBox.getChildren()) {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;
                if (vbox.getChildren().contains(photoOptions)) {
                    vbox.getChildren().remove(photoOptions);
                    return; // Exit the loop once the old photoOptions is removed
                }
            }
        }
        for (Node node : rightBox.getChildren()) {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;
                if (vbox.getChildren().contains(photoOptions)) {
                    vbox.getChildren().remove(photoOptions);
                    return; // Exit the loop once the old photoOptions is removed
                }
            }
        }
    }

    public void showOptions(MouseEvent event)
    {
        removePhotoOptions();
        VBox pictureContainer = (VBox) event.getSource();
        pictureContainer.getChildren().add(photoOptions);
        Data.setCurrentPhoto(photoMap.get(pictureContainer));
    }

    public void addTag(ActionEvent event)
    {
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
                    Data.getCurrentPhoto().addTag(tag);
                }
            }
        }
    }

    public void removeTag(ActionEvent event) {
        //create popup to list current tags to select from and remove
        if(Data.getCurrentPhoto().getTags().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Tags");
            alert.setHeaderText("No tags to remove");
            alert.setContentText("This photo has no tags to remove.");
            alert.showAndWait();
            return;
        }
        ChoiceDialog<String> choice = new ChoiceDialog<>("remove tag", Data.getCurrentPhoto().getTagsAsString());
        choice.setTitle("Remove a Tag");
        choice.setHeaderText("Select a tag to remove from the photo");
        Optional<String> result = choice.showAndWait();
        if (result.isPresent()) {
            for (Tag tag : Data.getCurrentPhoto().getTags()) {
                if (tag.toString().equals(result.get())) {
                    Data.getCurrentPhoto().removeTag(tag);
                    break;
                }
            }
        }
    }

    public void captionPhoto(ActionEvent event) {
        VBox pictureContainer = (VBox) ((Button) event.getSource()).getParent().getParent();
        Label captionLabel = (Label) pictureContainer.getChildren().get(1);
        TextInputDialog dialog = new TextInputDialog(captionLabel.getText());
        dialog.setTitle("Caption Photo");
        Optional<String> result = dialog.showAndWait();
        while(!result.isPresent() || result.get().equals("")) {
            dialog.setContentText("Caption is invalid, try again");
            result = dialog.showAndWait();
        }
        Data.getCurrentPhoto().setCaption(result.get());
        captionLabel.setText(result.get());
        
    }

    public void displayPhoto(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("display.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void slideShow(ActionEvent event) {
        // try {
        //     Parent root = FXMLLoader.load(getClass().getResource("slideshow.fxml"));
        //     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //     stage.setScene(new Scene(root));
        //     stage.show();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    public void logOut(ActionEvent event) {
        // save stuff to disk
        try {
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void quit(ActionEvent event) {
        // save stuff to disk
        System.exit(0);
    }
}