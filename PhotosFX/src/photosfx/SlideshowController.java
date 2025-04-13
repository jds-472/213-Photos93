package photosfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import model.Data;
import model.Photo;
import model.Tag;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SlideshowController {

    @FXML private ImageView slideImage;
    @FXML private Label slideCaption;
    @FXML private Label slideDate;
    @FXML private VBox tagBox;

    private List<Photo> photos;
    private int index = 0;

    public void initialize() {
        photos = new ArrayList<>(Data.getCurrentAlbum().getPhotos());
        if (photos.isEmpty()) return;
        displayPhoto(0);
    }

    private void displayPhoto(int i) {
        Photo photo = photos.get(i);
        Image image = photo.getPicture();
        slideImage.setImage(image);
        slideCaption.setText(photo.getCaption());

        // Format date
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        slideDate.setText(photo.getDate().format(fmt));

        // Load tags
        tagBox.getChildren().clear();
        for (Tag tag : photo.getTags()) {
            tagBox.getChildren().add(new Label(tag.toString()));
        }
    }

    public void nextSlide(ActionEvent event) {
        if (photos.isEmpty()) return;
        index = (index + 1) % photos.size();
        displayPhoto(index);
    }

    public void previousSlide(ActionEvent event) {
        if (photos.isEmpty()) return;
        index = (index - 1 + photos.size()) % photos.size();
        displayPhoto(index);
    }

    public void exitSlideshow(ActionEvent event) {
        try {
            Data.setCurrentPhoto(null);
            Data.setCurrentFXML(Data.ALBUMFXML);
            Parent root = FXMLLoader.load(getClass().getResource("album.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
