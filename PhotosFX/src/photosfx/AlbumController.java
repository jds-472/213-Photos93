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
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlbumController {

    @FXML private Label albumLabel;
    @FXML private ImageView stock;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("photo_options.fxml"));
    Node photoOptions;
    @FXML private VBox pictureBox;
    @FXML private VBox leftBox;
    @FXML private VBox rightBox;
    private ArrayList<Photo> photos;

    private Map<VBox, Photo> photoMap = new HashMap<>(); //Map for storing the photo and its corresponding VBox

    //I have no idea how to get the objects from the output stream yet so I'm just gonna initalize the photos to the stock

    public void initialize() {
        try {
            photoOptions = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        albumLabel.setText("You are in the album " + Data.getCurrentAlbum().getName() + "!");
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
