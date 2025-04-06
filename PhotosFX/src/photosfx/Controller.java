package photosfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Controller {

    @FXML
    private Label label;
    private Image stock0;
    private Image stock1;
    private Image stock2;
    private Image stock3;
    private Image stock4;
    private ArrayList<Image> stock = new ArrayList<>(java.util.Arrays.asList(stock0, stock1, stock2, stock3, stock4));
    private ArrayList<Image> images = new ArrayList<>();

    public void initialize() {
        label.setText("Stock");
    }
}