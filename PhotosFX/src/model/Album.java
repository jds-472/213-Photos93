package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDateTime;

public class Album implements Serializable{
    private String name;
    private Set<Photo> photos = new HashSet<>();

    public Album(String name) {
        this.name = name;
    }

    public Album(String name, Set<Photo> photos) {
        this.name = name;
        this.photos = photos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public String toString() {
        return name;
    }

    public int getPhotoCount(){
        return photos.size();
    }

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
