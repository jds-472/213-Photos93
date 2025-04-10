package model;

import javafx.scene.image.Image;
import java.util.HashSet;
import java.util.Set;

public class Photo {
    private String date;
    private String caption;
    private String pathName;
    private Image picture;
    private Set<String> tags = new HashSet<>();

    public Photo(String date, String caption, String pathName) {
        this.date = date;
        this.caption = caption;
        this.pathName = pathName;
        this.picture = new Image(pathName);
    }

    public String getDate() {
        return date;
    }

    public String getCaption() {
        return caption;
    }

    public String getPathName() {
        return pathName;
    }

    public Image getPicture() {
        return picture;
    }

    public Set<String> getTags() {
        return tags;
    }

}
