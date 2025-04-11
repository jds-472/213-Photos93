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

public class DisplayController {

    @FXML ImageView imageDisplay;
    @FXML Label displayCaption;

    private Album fromAlbum;
    
    public void setPhoto(Photo photo, Album from) {
        this.fromAlbum = from;
        Image image = photo.getPicture();
        imageDisplay.setImage(image);
        displayCaption.setText(photo.getCaption());
    }

    public void exitDisplay(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("stock.fxml"));
            Parent root = loader.load();
            StockController stockController = loader.getController();
            stockController.setAlbum(fromAlbum);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
