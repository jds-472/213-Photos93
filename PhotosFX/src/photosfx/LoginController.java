package photosfx;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Data;

/**
 * The LoginController class is responsible for managing the login interface in the PhotosFX application.
 * It handles user input, validates usernames, and transitions to the appropriate screen based on the user's role.
 * 
 * <p>This class interacts with the application's data layer to retrieve user information and manage the current user state.
 * 
 * <p>FXML elements such as {@code TextField} are used to capture user input.
 * 
 * <p>Key functionalities include:
 * <ul>
 *  <li>Validating user input</li>
 *  <li>Transitioning to the admin or user interface based on the username</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */
public class LoginController {

    @FXML
    private TextField userField;

    /**
     * Initializes the LoginController by setting the current FXML context.
     * This method is called automatically by the JavaFX framework when the FXML file is loaded.
     */
    public void initialize() {
        Data.setCurrentFXML(Data.LOGINFXML);
    }

    /**
     * Handles the key press event for the username field.
     * If the Enter key is pressed, it triggers the enter method.
     * 
     * @param keyEvent the key event triggered by the user
     */
    public void userFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            enter(new ActionEvent(userField, null));
        }
    }

    /**
     * Handles the action of entering the application.
     * Validates the username and transitions to the appropriate screen based on the user's role.
     * 
     * @param event the action event triggered by the user
     */
    public void enter(ActionEvent event) {
        String username = userField.getText().trim();
        Parent root = null;
        if (!username.isEmpty()) {
            if (username.equals("admin")) {
                try {
                    root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
            else if (Data.getUsers().contains(Data.getUser(username))) {
                Data.setCurrentUser(Data.getUser(username));
                try {
                    root = FXMLLoader.load(getClass().getResource("user.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User Error");
                alert.setHeaderText("User does not exist, please create a new user");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Error");
            alert.setHeaderText("Username cannot be empty");
            alert.showAndWait();
        }
    }
}
