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

/**
 * The {@code Photo} class represents a photo that contains a collection of tags.
 * It provides functionality to manage tags, retrieve photo details, and compare photos with each other.
 * 
 * <p>This class implements {@link Serializable} to allow serialization of album objects.
 * 
 * <p>Features of the {@code Album} class include:
 * <ul>
 *   <li>Adding and removing tags</li>
 *   <li>Retrieving the total number of tags</li>
 *   <li>Managing the fields and setting the Date</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */
public class Photo implements Serializable {
    private LocalDateTime date;
    private String caption;
    private String pathName;
    transient private Image picture;
    private Set<Tag> tags = new HashSet<>();

    /**
     * Constructs a {@code Photo} with the specified caption and path name.
     *
     * @param caption  the caption of the photo
     * @param pathName the path name of the photo
     */
    public Photo(String caption, String pathName) {
        this.date = setDateFromPath(pathName);
        this.caption = caption;
        this.pathName = pathName;
        this.picture = new Image(pathName);
    }

    /**
     * Sets the date of the photo based on the last modified time of the file at the given path.
     *
     * @param pathName the path name of the photo
     * @return the date of the photo
     */
    private LocalDateTime setDateFromPath(String pathName) {
        try {
            Path path = Paths.get(URI.create(pathName));
            FileTime fileTime = Files.getLastModifiedTime(path);
            return LocalDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());
        } catch (IOException | IllegalArgumentException e) {
            return LocalDateTime.now(); // fallback if failed
        }
    }

    /**
     * Retrieves the caption of the photo.
     * 
     * @return the caption of the photo
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Retrieves the path name of the photo.
     * 
     * @return the path name of the photo
     */
    public String getPathName() {
        return pathName;
    }

    /**
     * Retrieves the image of the photo.
     * 
     * @return the image of the photo
     */
    public Image getPicture() {
        if (picture == null) {
            picture = new Image(pathName);
        }
        return picture;
    }

    /**
     * Retrieves the set of tags associated with the photo.
     * 
     * @return the set of tags associated with the photo
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Retrieves the set of tags associated with the photo as a string.
     * 
     * @return the tags associated with the photo as a string
     */
    public Set<String> getTagsAsString() {
        Set<String> tagStrings = new HashSet<>();
        for (Tag tag : tags) {
            tagStrings.add(tag.toString());
        }
        return tagStrings;
    }

    /**
     * Retrieves the date of the photo.
     * 
     * @return the date of the photo
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the caption of the photo.
     * 
     * @param caption the caption to set for the photo
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Sets the path name of the photo.
     * 
     * @param pathName the path name to set for the photo
     */
    public void setPathName(String pathName) {
        this.pathName = pathName;
        this.picture = new Image(pathName);
        this.date = setDateFromPath(pathName); // refresh date if path changes
    }

    /**
     * Sets the image of the photo.
     * 
     * @param picture the image to set for the photo
     */
    public void setPicture(Image picture) {
        this.picture = picture;
    }

    /**
     * Adds a tag to this photo's set of tags.
     * 
     * @param tag the tag to be added to the set
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Removes a tag from this photo's set of tags.
     * 
     * @param tag the tag to be removed from the set
     */
    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    /**
     * Converts a photo to a string representation.
     * 
     * @return a string representation of the photo
     */
    public String toString() {
        return caption + " | " + pathName + " | " + date + " | " + tags;
    }

    /**
     * Checks if two photos are equal based on their path names.
     * 
     * @param obj the object to compare with
     * @return true if the photos are equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Photo photo = (Photo) obj;
        return pathName.equals(photo.pathName);
    }
}
