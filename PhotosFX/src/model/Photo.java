package model;

import javafx.scene.image.Image;
import java.util.HashSet;
import java.util.Set;

public class Photo {
    private String date;
    private String caption;
    private String pathName;
    private Image picture;
    private Set<Tag> tags = new HashSet<>();

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

    public Set<Tag> getTags() {
        return tags;
    }

    public Set<String> getTagsAsString() {
        Set<String> tagStrings = new HashSet<>();
        for (Tag tag : tags) {
            tagStrings.add(tag.toString());
        }
        return tagStrings;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }



}
