package photosfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The SearchController class is responsible for managing the search functionality in the PhotosFX application.
 * It allows users to search for photos based on date ranges and tags.
 * 
 * <p>This class interacts with the application's data layer to retrieve photos and manage the search results.
 * 
 * <p>FXML elements such as {@code DatePicker}, {@code ChoiceBox}, {@code TextField}, and {@code TilePane} are used to capture user input and display search results.
 * 
 * <p>Key functionalities include:
 * <ul>
 *   <li>Searching for photos by date range</li>
 *   <li>Searching for photos by tags</li>
 *   <li>Creating a new album from search results</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */
public class SearchController {

    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;

    @FXML private ChoiceBox<String> tagType1;
    @FXML private ChoiceBox<String> tagType2;
    @FXML private TextField tagValue1;
    @FXML private TextField tagValue2;

    @FXML private ChoiceBox<String> tagLogic;

    @FXML private TilePane resultsPane;

    private List<Photo> results = new ArrayList<>();

    /**
     * Initializes the SearchController by setting the current FXML context and populating the tag type and logic choice boxes.
     * This method is called automatically by the JavaFX framework when the FXML file is loaded.
     */
    public void initialize() {
        Data.setCurrentFXML(Data.SEARCHFXML);
        tagLogic.getItems().addAll("AND", "OR");
        tagLogic.getSelectionModel().selectFirst();
    
        tagType1.getItems().addAll(Tag.tagTypes);
        tagType2.getItems().addAll(Tag.tagTypes);
    
        tagType1.getSelectionModel().selectFirst();
        tagType2.getSelectionModel().selectFirst(); // optional
    }

    /**
     * Searches for photos based on the selected date range.
     * If no date range is selected, it returns without performing the search
     * @param event the action event triggered by the user
     */
    public void searchByDate(ActionEvent event) {
        results.clear();
        resultsPane.getChildren().clear();

        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        if (start == null || end == null) return;

        LocalDateTime startDT = start.atStartOfDay();
        LocalDateTime endDT = end.atTime(23, 59, 59);

        for (Album album : Data.getCurrentUser().getAlbums()) {
            for (Photo photo : album.getPhotos()) {
                LocalDateTime taken = photo.getDate();
                if (!results.contains(photo) && (taken.isEqual(startDT) || taken.isAfter(startDT)) && (taken.isEqual(endDT) || taken.isBefore(endDT))) {
                    results.add(photo);
                }
            }
        }

        displayResults();
    }

    /**
     * Searches for photos based on the selected tags and logic.
     * If no tags are selected, it returns without performing the search.
     * @param event the action event triggered by the user
     */
    public void searchByTags(ActionEvent event) {
        results.clear();
        resultsPane.getChildren().clear();

        String t1 = tagType1.getValue();
        String v1 = tagValue1.getText().trim();
        String t2 = tagType2.getValue();
        String v2 = tagValue2.getText().trim();
        String logic = tagLogic.getValue();

        boolean isSingle = t2.isEmpty() || v2.isEmpty();

        for (Album album : Data.getCurrentUser().getAlbums()) {
            for (Photo photo : album.getPhotos()) {
                boolean hasFirst = photo.getTags().contains(new Tag(t1, v1));
                boolean hasSecond = photo.getTags().contains(new Tag(t2, v2));

                if (!results.contains(photo)) {
                    if (isSingle && hasFirst) {
                        results.add(photo);
                    } else if ("AND".equals(logic) && hasFirst && hasSecond) {
                        results.add(photo);
                    } else if ("OR".equals(logic) && (hasFirst || hasSecond)) {
                        results.add(photo);
                    }
                }
            }
        }

        displayResults();
    }

    /**
     * Displays the search results in the results pane.
     * Each photo is displayed with its image and caption.
     */
    private void displayResults() {
        for (Photo photo : results) {
            ImageView imageView = new ImageView(photo.getPicture());
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            VBox container = new VBox(imageView, new Label(photo.getCaption()));
            container.setAlignment(Pos.CENTER);
            resultsPane.getChildren().add(container);
        }
    }

    /**
     * Creates a new album from the search results.
     * If no results are found, it returns without performing the action.
     * If the album name is empty or already exists, an alert is shown to the user.
     * @param event the action event triggered by the user
     */
    public void createAlbumFromResults(ActionEvent event) {
        if (results.isEmpty()) return;

        TextInputDialog dialog = new TextInputDialog("New Search Album");
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Enter album name:");
        Optional<String> nameResult = dialog.showAndWait();

        nameResult.ifPresent(name -> {
            if (name.trim().isEmpty()) return;
            if (Data.getCurrentUser().getAlbum(name) != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Album name already exists.");
                alert.showAndWait();
                return;
            }
            Album newAlbum = new Album(name);
            for (Photo photo : results) {
                newAlbum.getPhotos().add(photo);
            }
            Data.getCurrentUser().addAlbum(newAlbum);
        });
    }
}
