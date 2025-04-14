package model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.io.IOException;
import java.time.*;
import java.net.URI;

public class Photo implements Serializable {
    private LocalDateTime date;
    private String caption;
    private String pathName;
    transient private Image picture;
    private Set<Tag> tags = new HashSet<>();

    public Photo(String caption, String pathName) {
        this.date = setDateFromPath(pathName);
        this.caption = caption;
        this.pathName = pathName;
        this.picture = new Image(pathName);
    }

    private LocalDateTime setDateFromPath(String pathName) {
        try {
            Path path = Paths.get(URI.create(pathName));
            FileTime fileTime = Files.getLastModifiedTime(path);
            return LocalDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());
        } catch (IOException | IllegalArgumentException e) {
            return LocalDateTime.now(); // fallback if failed
        }
    }

    public String getCaption() {
        return caption;
    }

    public String getPathName() {
        return pathName;
    }

    public Image getPicture() {
        if (picture == null) {
            picture = new Image(pathName);
        }
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
        this.picture = new Image(pathName);
        this.date = setDateFromPath(pathName); // refresh date if path changes
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

    public String toString() {
        return caption + " | " + pathName + " | " + date + " | " + tags;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Photo photo = (Photo) obj;
        return pathName.equals(photo.pathName);
    }
}
