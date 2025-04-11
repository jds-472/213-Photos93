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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import model.Photo;
import java.util.ArrayList;

public class StockController {

    @FXML private Label label;
    @FXML private ImageView stock;
    @FXML private Button photoOption0;
    @FXML private Button photoOption1;
    @FXML private Button photoOption2;
    @FXML private Button photoOption3;
    @FXML private HBox photoOptions;
    @FXML private VBox pictureBox;
    @FXML private VBox leftBox;
    @FXML private VBox rightBox;
    // private ArrayList<ImageView> stocks = new ArrayList<>(java.util.Arrays.asList(stock));
    private ArrayList<Photo> photos = new ArrayList<>();

    //I have no idea how to get the objects from the output stream yet so I'm just gonna initalize the photos to the stock
    public StockController() {
    }

    public void initialize() {
        photos.add(new Photo("getDate", "pacman", "data/pacmanstock.png"));
        photos.add(new Photo("getDate", "blinky", ".\\..\\..\\data\\blinkystock.png"));
        photos.add(new Photo("getDate", "pinky", ".\\..\\..\\data\\pinkystock.png"));
        photos.add(new Photo("getDate", "inky", ".\\..\\..\\data\\inkystock.png"));
        photos.add(new Photo("getDate", "clyde", ".\\..\\..\\data\\clydestock.png"));
        label.setText("Stock");
        leftBox.getChildren().removeAll();
        rightBox.getChildren().removeAll();
        for (int i = 0; i < photos.size(); i++) {
            ImageView stock = new ImageView(photos.get(i).getPicture());
            stock.setFitHeight(200);
            stock.setFitWidth(200);
            stock.setOnMouseClicked(this::showOptions);
            VBox pictureContainer = new VBox(stock, new Label(photos.get(i).getCaption()));
            if (i % 2 == 0) {
                leftBox.getChildren().add(pictureContainer);
            } else {
                rightBox.getChildren().add(pictureContainer);
            }
        }
    }

    // private HBox createPhotoOptions() {
    //     HBox newPhotoOptions = new HBox(5);
    //     Button newbutton0 = new Button(photoOption0.getText());
    //     Button newbutton1 = new Button(photoOption1.getText());
    //     Button newbutton2 = new Button(photoOption2.getText());
    //     Button newbutton3 = new Button(photoOption3.getText());

    //     newbutton0.setOnAction(photoOption0.getOnAction());
    //     newbutton1.setOnAction(photoOption1.getOnAction());
    //     newbutton2.setOnAction(photoOption2.getOnAction());
    //     newbutton3.setOnAction(photoOption3.getOnAction());

    //     newPhotoOptions.getChildren().addAll(newbutton0, newbutton1, newbutton2, newbutton3);
    //     return newPhotoOptions;
    // }

    private void removePhotoOptions()
    {
        for (Node node : leftBox.getChildren()) {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;
                if (vbox.getChildren().contains(photoOptions)) {
                    vbox.getChildren().remove(photoOptions);
                    return; // Exit the loop once the old photoOptions is removed
                }
            }
        }
        for (Node node : rightBox.getChildren()) {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;
                if (vbox.getChildren().contains(photoOptions)) {
                    vbox.getChildren().remove(photoOptions);
                    return; // Exit the loop once the old photoOptions is removed
                }
            }
        }
    }

    public void showOptions(MouseEvent event)
    {
        removePhotoOptions();
        VBox pictureContainer = (VBox) event.getSource();
        pictureContainer.getChildren().add(photoOptions);
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