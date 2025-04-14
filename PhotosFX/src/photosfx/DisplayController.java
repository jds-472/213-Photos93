package photosfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXML;
import model.Data;
import model.Tag;
import model.Photo;
import java.time.format.DateTimeFormatter;

public class DisplayController {

    @FXML ImageView imageDisplay;
    @FXML Label displayCaption;
    @FXML Label dateLabel;
    @FXML VBox displayContainer;
    @FXML ScrollPane scrollContainer;
    
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

    public void exitDisplay(ActionEvent event) {
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
