package photosfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;


import java.util.ArrayList;

public class StockController {

    //@FXML
    @FXML private Label label;
    @FXML private ImageView stock0;
    @FXML private ImageView stock1;
    @FXML private ImageView stock2;
    @FXML private ImageView stock3;
    @FXML private ImageView stock4;
    @FXML private Button photoOption0;
    @FXML private Button photoOption1;
    @FXML private Button photoOption2;
    @FXML private Button photoOption3;
    @FXML private HBox photoOptions;
    @FXML private VBox leftBox;
    private ArrayList<ImageView> stock = new ArrayList<>(java.util.Arrays.asList(stock0, stock1, stock2, stock3, stock4));
    //private ArrayList<Image> Images = new ArrayList<>();

    public void initialize() {
        label.setText("Stock");
    }

    public void slideShow(ActionEvent event) {
        leftBox.getChildren().remove(photoOptions);
        leftBox.getChildren().addAll(photoOptions);
        // try {
        //     Parent root = FXMLLoader.load(getClass().getResource("slideshow.fxml"));
        //     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //     stage.setScene(new Scene(root));
        //     stage.show();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
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