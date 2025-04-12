package photosfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Photo;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import model.Album;
import model.Data;

public class DisplayController {

    @FXML ImageView imageDisplay;
    @FXML Label displayCaption;
    
    public void initialize() {
        Image image = Data.getCurrentPhoto().getPicture();
        imageDisplay.setImage(image);
        displayCaption.setText(Data.getCurrentPhoto().getCaption());
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
