module jsrr.photos {
    requires javafx.controls;
    requires javafx.fxml;

    opens jsrr.photos to javafx.fxml;
    exports jsrr.photos;
}
