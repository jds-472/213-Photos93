package model;

import java.util.HashSet;
import java.util.Set;

public class User {
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
}
