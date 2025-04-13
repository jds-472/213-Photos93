package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
}
