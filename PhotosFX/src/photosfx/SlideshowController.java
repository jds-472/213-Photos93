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
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The SlideshowController class is responsible for managing the slideshow functionality in the PhotosFX application.
 * It allows users to view photos in a slideshow format, displaying the image, caption, date, and associated tags.
 * 
 * <p>This class interacts with the application's data layer to retrieve the current album and its photos.
 * 
 * <p>FXML elements such as {@code ImageView}, {@code Label}, and {@code VBox} are used to display the photo and its details.
 * 
 * <p>Key functionalities include:
 * <ul>
 *  <li>Displaying photos in a slideshow format</li>
 *  <li>Navigating to the next and previous photos</li>
 *  <li>Exiting the slideshow and returning to the album view</li>
 * </ul>
 * 
 */
public class SlideshowController {

    @FXML private ImageView slideImage;
    @FXML private Label slideCaption;
    @FXML private Label slideDate;
    @FXML private VBox tagBox;

    private List<Photo> photos;
    private int index = 0;

    /**
     * Initializes the SlideshowController by setting the current FXML context and loading the photos from the current album.
     * This method is called automatically by the JavaFX framework when the FXML file is loaded.
     */
    public void initialize() {
        Data.setCurrentFXML(Data.SLIDESHOWFXML);
        photos = new ArrayList<>(Data.getCurrentAlbum().getPhotos());
        if (photos.isEmpty()) return;
        displayPhoto(0);
    }

    /**
     * Displays the photo at the specified index in the slideshow.
     * 
     * @param i the index of the photo to display
     */
    private void displayPhoto(int i) {
        Photo photo = photos.get(i);
        Image image = photo.getPicture();
        slideImage.setImage(image);
        slideCaption.setText(photo.getCaption());

        // Format date
        LocalDateTime date = photo.getDate();
        if (date != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
            slideDate.setText(date.format(fmt));
        } else {
            slideDate.setText("Date unknown");
        }

        // Load tags
        tagBox.getChildren().clear();
        for (Tag tag : photo.getTags()) {
            tagBox.getChildren().add(new Label(tag.toString()));
        }
    }

    /** 
     * Navigates to the next photo in the slideshow.
     * If the end of the list is reached, it wraps around to the first photo.  
     * @param event the action event triggered by the user
    */
    public void nextSlide(ActionEvent event) {
        if (photos.isEmpty()) return;
        index = (index + 1) % photos.size();
        displayPhoto(index);
    }

    /**
     * Navigates to the previous photo in the slideshow.
     * If the beginning of the list is reached, it wraps around to the last photo.
     * 
     * @param event the action event triggered by the user
     */
    public void previousSlide(ActionEvent event) {
        if (photos.isEmpty()) return;
        index = (index - 1 + photos.size()) % photos.size();
        displayPhoto(index);
    }

    /**
     * Exits the slideshow and returns to the album view.
     * 
     * @param event the action event triggered by the user
     */
    public void exitSlideshow(ActionEvent event) {
        try {
            Data.setCurrentPhoto(null);
            Parent root = FXMLLoader.load(getClass().getResource("album.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
