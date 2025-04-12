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

public class DisplayController {

    @FXML ImageView imageDisplay;
    @FXML Label displayCaption;
    @FXML Label currentTags;
    @FXML VBox displayContainer;
    @FXML ScrollPane scrollContainer;
    
    public void initialize() {
        //TODO: change the fxml to support tags and display tags as well as date once serializable is done (can do placeholder date if you feel like it)
        Image image = Data.getCurrentPhoto().getPicture();
        imageDisplay.setImage(image);
        displayCaption.setText(Data.getCurrentPhoto().getCaption());
        for (Tag tag : Data.getCurrentPhoto().getTags()) {
            displayContainer.getChildren().add(new Label(tag + ""));
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
