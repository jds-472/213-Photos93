/**
 * The {@code Album} class represents a photo album that contains a collection of photos.
 * It provides functionality to manage photos, retrieve album details, and determine
 * the earliest and latest dates of the photos in the album.
 * 
 * <p>This class implements {@link Serializable} to allow serialization of album objects.
 * 
 * <p>Features of the {@code Album} class include:
 * <ul>
 *   <li>Adding and removing photos</li>
 *   <li>Retrieving the total number of photos</li>
 *   <li>Getting the earliest and latest dates of photos</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */
package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDateTime;

public class Album implements Serializable{
    private String name;
    private Set<Photo> photos = new HashSet<>();

    /**
    Constructs an {@code Album} with the specified name.
    
    @param name the name of the album
    */
    public Album(String name) {
        this.name = name;
    }
    /**
    Constructs an {@code Album} with the specified name and a set of photos.

    @param name the name of the album
    @param photos the set of photos to initialize the album with
    */
    public Album(String name, Set<Photo> photos) {
        this.name = name;
        this.photos = photos;
    }
    /**
    Retrieves the name of the album.

    @return the name of the album
    */
    public String getName() {
        return name;
    }

    /**
    Sets the name of the album.
 
    @param name the new name of the album
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    Retrieves the set of photos in the album.
 
    @return the set of photos in the album
    */
    public Set<Photo> getPhotos() {
        return photos;
    }

    /**
    Returns the string representation of the album, which is its name.
 
    @return the name of the album
    */
    public String toString() {
        return name;
    }

    /**
    Retrieves the total number of photos in the album.
 
    @return the total number of photos in the album
    */
    public int getPhotoCount(){
        return photos.size();
    }

    /**
    Adds a photo to the album.
 
    @param photo the photo to be added to the album
    */
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    /**
    Removes a photo from the album.
 
    @param photo the photo to be removed from the album
    */
    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }

    /**
    Retrieves the earliest date of all photos in the album.
 
    @return the earliest date of the photos, or {@code null} if the album has no photos
    */
    public LocalDateTime getEarliestDate()
    {
        LocalDateTime earliest = null;
        for (Photo photo : photos) {
            if (earliest == null || photo.getDate().isBefore(earliest)) {
                earliest = photo.getDate();
            }
        }
        return earliest;
    }

    /**
    Retrieves the latest date of all photos in the album.
 
    @return the latest date of the photos, or {@code null} if the album has no photos
    */
    public LocalDateTime getLatestDate()
    {
        LocalDateTime latest = null;
        for (Photo photo : photos) {
            if (latest == null || photo.getDate().isAfter(latest)) {
                latest = photo.getDate();
            }
        }
        return latest;
    }
}
