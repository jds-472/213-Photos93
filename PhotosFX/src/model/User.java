package model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String name;
    // private String password;
    private Set<Album> albums = new HashSet<>();
    public static Set<User> users = new HashSet<>();

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public static User getUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
}
