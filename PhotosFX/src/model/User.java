package model;

import java.util.HashSet;
import java.util.Set;
import java.io.*;

public class User implements Serializable{
    private String name;
    // private String password;
    private Set<Album> albums = new HashSet<>();

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Album getAlbum(String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void removeAlbum(Album album) {
        albums.remove(album);
    }

    public String toString() {
        return name;
    }
}
