package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Photos extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/photosfx/login.fxml"));
        primaryStage.setTitle("RU Photo Store");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        User stock = new User("stock");
        User.users.add(stock);

        Set<Photo> photos = new HashSet<>();
        photos.add(new Photo("getDate", "pacman", new File("../data/pacmanstock.png").toURI().toString()));
        photos.add(new Photo("getDate", "blinky", new File("../data/blinkystock.png").toURI().toString()));
        photos.add(new Photo("getDate", "pinky", new File("../data/pinkystock.png").toURI().toString()));
        photos.add(new Photo("getDate", "inky", new File("../data/inkystock.png").toURI().toString()));
        photos.add(new Photo("getDate", "clyde", new File("../data/clydestock.png").toURI().toString()));

        stock.addAlbum(new Album("stock", photos));
    
        launch(args);
    }
}