package photosfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Photo;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

public class DisplayController {

    @FXML ImageView imageDisplay;
    @FXML Label displayCaption;
    
    public void setPhoto(Photo photo) {
        Image image = photo.getPicture();
        imageDisplay.setImage(image);
        displayCaption.setText(photo.getCaption());
    }

    public void exitDisplay(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("stock.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
