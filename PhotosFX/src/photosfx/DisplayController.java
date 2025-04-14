package photosfx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXML;
import model.Data;
import model.Tag;
import model.Photo;
import java.time.format.DateTimeFormatter;

/**
 * The DisplayController class is responsible for managing the display of a photo in the PhotosFX application.
 * It handles the initialization of the display, including setting the image, caption, date, and tags associated with the photo.
 * 
 * <p>This class interacts with the application's data layer to retrieve the current photo and its associated information.
 * 
 * <p>FXML elements such as {@code ImageView}, {@code Label}, and {@code VBox} are used to display the photo and its details.
 * 
 * <p>Key functionalities include:
 * <ul>
 *   <li>Initializing the display with the current photo's information</li>
 *  <li>Setting the image, caption, date, and tags in the UI</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */
public class DisplayController {

    @FXML ImageView imageDisplay;
    @FXML Label displayCaption;
    @FXML Label dateLabel;
    @FXML VBox displayContainer;
    @FXML ScrollPane scrollContainer;
    
    /**
     * Initializes the DisplayController by setting the current FXML context and populating the display with the current photo's information.
     * This method is called automatically by the JavaFX framework when the FXML file is loaded.
     */
    public void initialize() {
        Data.setCurrentFXML(Data.DISPLAYFXML);
        Photo photo = Data.getCurrentPhoto();
        Image image = photo.getPicture();
        imageDisplay.setImage(image);
        displayCaption.setText(photo.getCaption());

        // Set date label
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        dateLabel.setText("Date: " + photo.getDate().format(fmt));

        // Load tags
        displayContainer.getChildren().clear();
        for (Tag tag : photo.getTags()) {
            displayContainer.getChildren().add(new Label(tag.toString()));
        }
    }
}
