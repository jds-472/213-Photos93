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
import javafx.scene.layout.TilePane;
import javafx.geometry.Pos;
import javafx.stage.Popup;

public class AlbumController {

    @FXML private Label albumLabel;
    @FXML private Label photoOptionsLabel;
    @FXML private Label albumOptionsLabel;
    @FXML private ImageView stock;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("photo_options.fxml"));
    Node photoOptions;
    @FXML private VBox pictureBox;
    @FXML private TilePane displayPane;
    private ArrayList<Photo> photos;

    private Map<VBox, Photo> photoMap = new HashMap<>(); //Map for storing the photo and its corresponding VBox

    private Popup popup = new Popup(); //Popup for the photo options

    //I have no idea how to get the objects from the output stream yet so I'm just gonna initalize the photos to the stock

    public void initialize() {
        try {
            photoOptions = loader.load();
            popup.getContent().add(photoOptions);
            popup.setAutoHide(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        albumLabel.setText("You are in the album " + Data.getCurrentAlbum().getName() + "!");
        albumOptionsLabel.setText("Album Options for " + Data.getCurrentAlbum().getName() + ":");
        displayPane.getChildren().clear();
        if (Data.getCurrentAlbum() == null) {return;}
        photos = new ArrayList<>(Data.getCurrentAlbum().getPhotos());
        for (int i = 0; i < photos.size(); i++) {
            ImageView imageView = new ImageView(photos.get(i).getPicture());
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            VBox pictureContainer = new VBox(imageView, new Label(photos.get(i).getCaption()));
            pictureContainer.spacingProperty().setValue(5);
            pictureContainer.alignmentProperty().setValue(Pos.CENTER);
            pictureContainer.setOnMouseClicked(this::showOptions);
            // pictureContainer.setPrefWidth(120);
            photoMap.put(pictureContainer, photos.get(i));
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


    public void slideShow(ActionEvent event) {
        Data.setCurrentFXML(Data.SLIDESHOWFXML);
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
