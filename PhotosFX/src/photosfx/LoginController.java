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
        String user = userField.getText().trim();
        Parent root = null;
        if (!user.isEmpty()) {
            if (user.equals("stock")) {
                try {
                    root = FXMLLoader.load(getClass().getResource("stock.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (user.equals("admin")) {
                try {
                    root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    root = FXMLLoader.load(getClass().getResource("user.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }  
    }
}
