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
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.control.*;

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

    private Map<VBox, Photo> photoMap = new HashMap<>(); //Map for storing the photo and its corresponding VBox

    //I have no idea how to get the objects from the output stream yet so I'm just gonna initalize the photos to the stock
    public StockController() {
    }

    public void initialize() {
        photos.add(new Photo("getDate", "pacman", getClass().getResource("/data/pacmanstock.png").toExternalForm()));
        photos.add(new Photo("getDate", "blinky", getClass().getResource("/data/blinkystock.png").toExternalForm()));
        photos.add(new Photo("getDate", "pinky", getClass().getResource("/data/pinkystock.png").toExternalForm()));
        photos.add(new Photo("getDate", "inky", getClass().getResource("/data/inkystock.png").toExternalForm()));
        photos.add(new Photo("getDate", "clyde", getClass().getResource("/data/clydestock.png").toExternalForm()));
        label.setText("Stock");
        leftBox.getChildren().clear();
        rightBox.getChildren().clear();
        for (int i = 0; i < photos.size(); i++) {
            ImageView stock = new ImageView(photos.get(i).getPicture());
            stock.setFitHeight(200);
            stock.setFitWidth(200);
            VBox pictureContainer = new VBox(stock, new Label(photos.get(i).getCaption()));
            pictureContainer.spacingProperty().setValue(5);
            pictureContainer.alignmentProperty().setValue(javafx.geometry.Pos.CENTER);
            pictureContainer.setOnMouseClicked(this::showOptions);
            photoMap.put(pictureContainer, photos.get(i));
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

    public void addTag(ActionEvent event)
    {
        //create popup to list current tags to select from and add new tags
        VBox pictureContainer = (VBox) ((Button) event.getSource()).getParent().getParent();
        Photo photo = photoMap.get(pictureContainer);
    }

    public void captionPhoto(ActionEvent event) {
        VBox pictureContainer = (VBox) ((Button) event.getSource()).getParent().getParent();
        Label captionLabel = (Label) pictureContainer.getChildren().get(1);
        Photo photo = photoMap.get(pictureContainer);
        TextInputDialog dialog = new TextInputDialog(captionLabel.getText());
        dialog.setTitle("Caption Photo");
        Optional<String> result = dialog.showAndWait();
        while(!result.isPresent() || result.get().equals("")) {
            dialog.setContentText("Caption is invalid, try again");
            result = dialog.showAndWait();
        }
        photo.setCaption(result.get());
        captionLabel.setText(photo.getCaption());
        
    }

    public void displayPhoto(ActionEvent event) {
        VBox pictureContainer = (VBox) ((Button) event.getSource()).getParent().getParent();
        Photo photo = photoMap.get(pictureContainer);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("display.fxml"));
            Parent root = loader.load();
            DisplayController displayController = loader.getController();
            displayController.setPhoto(photo);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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