package photosfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Data;

public class ExitOptionsController {

    public void goBack (ActionEvent event) {
        switch (Data.getCurrentFXML()) {
            case Data.USERFXML, Data.ADMINFXML:
                transitionFXML(event, "login.fxml", Data.LOGINFXML);
                Data.setCurrentUser(null);
                break;
            case Data.LOGINFXML:
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Cannot go back from login screen");
                alert.showAndWait();
                break;
            case Data.ALBUMFXML, Data.SEARCHFXML:
                transitionFXML(event, "user.fxml", Data.USERFXML);
                Data.setCurrentAlbum(null);
                break;
            case Data.DISPLAYFXML, Data.SLIDESHOWFXML:
                transitionFXML(event, "album.fxml", Data.ALBUMFXML);
                Data.setCurrentPhoto(null);
                break;
            default:
                break;
        }
    }

    public void logOut(ActionEvent event) {
        Data.saveData();
        Data.setCurrentFXML(Data.LOGINFXML);
        Data.setCurrentUser(null);
        Data.setCurrentAlbum(null);
        Data.setCurrentPhoto(null);
        transitionFXML(event, "login.fxml", Data.LOGINFXML);
    }

    public void quit(ActionEvent event) {
        Data.saveData();
        System.exit(0);
    }

    private void transitionFXML(ActionEvent event, String fxml, int FXML)
    {
        try {
            Data.setCurrentFXML(FXML);
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
