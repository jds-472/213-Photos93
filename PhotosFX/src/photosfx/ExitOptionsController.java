package photosfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Data;

/**
 * The ExitOptionsController class is responsible for managing the exit options in the PhotosFX application.
 * It provides functionalities for going back to the previous screen, logging out, and quitting the application.
 * 
 * <p>This class interacts with the application's data layer to save data and manage the current user and album state.
 * 
 * <p>Key functionalities include:
 * <ul>
 *  <li>Going back to the previous screen</li>
 * <li>Logging out the current user</li>
 * <li>Quitting the application</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */
public class ExitOptionsController {

    /**
     * Handles the action of going back to the previous screen based on the current FXML context.
     * It transitions to the appropriate FXML file and resets the current user and album state.
     * @param event the action event triggered by the user
     */
    public void goBack (ActionEvent event) {
        switch (Data.getCurrentFXML()) {
            case Data.USERFXML, Data.ADMINFXML:
                transitionFXML(event, "login.fxml");
                Data.setCurrentUser(null);
                break;
            case Data.LOGINFXML:
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Cannot go back from login screen");
                alert.showAndWait();
                break;
            case Data.ALBUMFXML, Data.SEARCHFXML:
                transitionFXML(event, "user.fxml");
                Data.setCurrentAlbum(null);
                break;
            case Data.DISPLAYFXML, Data.SLIDESHOWFXML:
                transitionFXML(event, "album.fxml");
                Data.setCurrentPhoto(null);
                break;
            default:
                break;
        }
    }

    /**
     * Handles the action of logging out the current user.
     * It saves the data and transitions to the login screen.
     * @param event the action event triggered by the user
     */
    public void logOut(ActionEvent event) {
        Data.saveData();
        Data.setCurrentUser(null);
        Data.setCurrentAlbum(null);
        Data.setCurrentPhoto(null);
        transitionFXML(event, "login.fxml");
    }

    /**
     * Handles the action of quitting the application.
     * It saves the data and exits the application.
     * @param event the action event triggered by the user
     */
    public void quit(ActionEvent event) {
        Data.saveData();
        System.exit(0);
    }

    /**
     * Transitions to the specified FXML file and sets the current FXML context.
     * @param event the action event triggered by the user
     * @param fxmlS the FXML file to transition to
     */
    private void transitionFXML(ActionEvent event, String fxmlS)
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlS));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
