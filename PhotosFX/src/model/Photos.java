/**
 * The @code Photos class is the main entry point of the application.
 * It initializes the JavaFX application and sets up the primary stage.
 * It also can set up an inital user and album.
 * 
 * <p>This class implements {@link Application} to allow JavaFX application functionality.
 * 
 * <p>Features of the {@code Photos} class include:
 * <ul>
 *  <li>Starting the JavaFX application</li>
 *  <li>Loading data</li>
 *  <li>Setting up the primary stage</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */

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

    /**
     * Starts the JavaFX application and sets up the primary stage.
     * 
     * @param primaryStage the primary stage for this application
     * @throws Exception if an error occurs during the loading of the FXML file
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/photosfx/login.fxml"));
        primaryStage.setTitle("RU Photo Store");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Data.saveData();
            e.consume();
            System.exit(0);
        });
    }

    /**
     * The main method to launch the JavaFX application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // User stock = new User("stock");
        // Data.addUser(stock);

        // Set<Photo> photos = new HashSet<>();
        // photos.add(new Photo("pacman", new File("../data/pacmanstock.png").toURI().toString()));
        // photos.add(new Photo("blinky", new File("../data/blinkystock.png").toURI().toString()));
        // photos.add(new Photo("pinky", new File("../data/pinkystock.png").toURI().toString()));
        // photos.add(new Photo("inky", new File("../data/inkystock.png").toURI().toString()));
        // photos.add(new Photo("clyde", new File("../data/clydestock.png").toURI().toString()));

        // stock.addAlbum(new Album("stock", photos));
        Data.loadData();
        
        launch(args);
    }
}