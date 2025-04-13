package photosfx;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Data;
import model.Tag;

public class PhotoOptionsController {
    

    public void addTag(ActionEvent event)
    {
        //create popup to list current tags to select from and add new tags
        ChoiceDialog<String> choice = new ChoiceDialog<>("create new tag", Tag.tagTypes);
        choice.getItems().add("create new tag");
        choice.setTitle("Add a Tag");
        choice.setHeaderText("Select a tag to add to the photo or create a new one");
        Optional<String> result = choice.showAndWait();
        if (result.isPresent()) {
            if (result.get().equals("create new tag")) {
                TextInputDialog newTagDialog = new TextInputDialog("new tag name");
                newTagDialog.setTitle("Create a new tag");
                Optional<String> newTagResult = newTagDialog.showAndWait();
                if (newTagResult.isPresent()) {
                    Tag.tagTypes.add(newTagResult.get());
                }
            } else {
                TextInputDialog dialog = new TextInputDialog("new tag value");
                dialog.setTitle("Set the value for the tag " + result.get());
                Optional<String> tagValueResult = dialog.showAndWait();
                if (tagValueResult.isPresent()) {
                    Tag tag = new Tag(result.get(), tagValueResult.get());
                    Data.getCurrentPhoto().addTag(tag);
                }
            }
        }
    }

    public void removeTag(ActionEvent event) {
        //create popup to list current tags to select from and remove
        if(Data.getCurrentPhoto().getTags().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Tags");
            alert.setHeaderText("No tags to remove");
            alert.setContentText("This photo has no tags to remove.");
            alert.showAndWait();
            return;
        }
        ChoiceDialog<String> choice = new ChoiceDialog<>("remove tag", Data.getCurrentPhoto().getTagsAsString());
        choice.setTitle("Remove a Tag");
        choice.setHeaderText("Select a tag to remove from the photo");
        Optional<String> result = choice.showAndWait();
        if (result.isPresent()) {
            for (Tag tag : Data.getCurrentPhoto().getTags()) {
                if (tag.toString().equals(result.get())) {
                    Data.getCurrentPhoto().removeTag(tag);
                    break;
                }
            }
        }
    }

    public void displayPhoto(ActionEvent event) {
        try {
            Data.setCurrentFXML(Data.DISPLAYFXML);
            Parent root = FXMLLoader.load(getClass().getResource("display.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void captionPhoto(ActionEvent event) {
        VBox pictureContainer = (VBox) ((Button) event.getSource()).getParent().getParent();
        Label captionLabel = (Label) pictureContainer.getChildren().get(1);
        TextInputDialog dialog = new TextInputDialog(captionLabel.getText());
        dialog.setTitle("Caption Photo");
        Optional<String> result = dialog.showAndWait();
        while(!result.isPresent() || result.get().equals("")) {
            dialog.setContentText("Caption is invalid, try again");
            result = dialog.showAndWait();
        }
        Data.getCurrentPhoto().setCaption(result.get());
        captionLabel.setText(result.get());
        
    }
}
