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
import model.User;


public class LoginController {

    @FXML
    private TextField userField;

    public void initialize() {
        Data.setCurrentFXML(Data.LOGINFXML);
    }

    public void userFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            enter(new ActionEvent(userField, null));
        }
    }

    public void enter(ActionEvent event) {
        String username = userField.getText().trim();
        Parent root = null;
        if (!username.isEmpty()) {
            if (username.equals("admin")) {
                Data.setCurrentFXML(Data.ADMINFXML);
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
                Data.setCurrentFXML(Data.USERFXML);
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
