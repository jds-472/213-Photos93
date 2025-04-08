package photosfx;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class LoginController {

    @FXML
    private TextField userField;

    public void userFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            enter(new ActionEvent(userField, null));
        }
    }

    public void enter(ActionEvent event) {
        System.out.println("called");
        if (!userField.getText().isEmpty() && userField.getText().equals("stock")) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("stock.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}