package photosfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class StockController {

    @FXML
    private Label label;
    private Image stock0;
    private Image stock1;
    private Image stock2;
    private Image stock3;
    private Image stock4;
    private ArrayList<Image> stock = new ArrayList<>(java.util.Arrays.asList(stock0, stock1, stock2, stock3, stock4));
    private ArrayList<Image> images = new ArrayList<>();
    private HBox photoOptions = new HBox(5);

    public void initialize() {
        label.setText("Stock");
    }

    public void logOut(ActionEvent event) {
        // save stuff to disk
        try {
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void quit(ActionEvent event) {
        // save stuff to disk
        System.exit(0);
    }
}