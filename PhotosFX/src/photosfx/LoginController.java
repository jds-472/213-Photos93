package photosfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


public class LoginController {

    @FXML
    private TextField userField;
    private StockController stockController = new StockController();

    public void enter(ActionEvent event) {
        if (event.getSource() == null) {
            System.out.println("Event source is null");
            return;
        }
        if (!userField.getText().isEmpty() && userField.getText().equals("stock")) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("stock.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                //stockController.initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}