package model;

import java.util.HashSet;
import java.util.Set;
import java.io.*;

/**
 * The {@code User} class represents a user that contains a collection of albums.
 * It provides functionality to manage albums and retrieve user details.
 * 
 * <p>This class implements {@link Serializable} to allow serialization of album objects.
 * 
 * <p>Features of the {@code Album} class include:
 * <ul>
 *   <li>Adding and removing albums</li>
 *   <li>Managing instance fields</li>
 * </ul>
 * 
 * @author [Joseph Scarpulla and Roger Ramirez]
 * @version 1.0
 */
public class User implements Serializable{
    private String name;
    // private String password;
    private Set<Album> albums = new HashSet<>();

    /**
     * Constructs a {@code User} with the specified name.
     * @param name the name of the user
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the user.
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the Set of albums of the user.
     * @return the Set of albums of the user
     */
    public Set<Album> getAlbums() {
        return albums;
    }

    /**
     * Retrieves the Set of albums of the user as a Set of Strings.
     * @return the Set of albums of the user as a Set of Strings
     */
    public Set<String> getAlbumsAsString(){
        Set<String> albumStrings = new HashSet<>();
        for (Album album : albums) {
            albumStrings.add(album.toString());
        }
        return albumStrings;
    }

    /**
     * Retrieves the album with the specified name.
     * @param name the name of the album to retrieve
     * @return the album with the specified name, or null if not found
     */
    public Album getAlbum(String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }

    /**
     * Adds an album to the user's collection.
     * @param album the album to add
     */
    public void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * Removes an album from the user's collection.
     * @param album the album to remove
     */
    public void removeAlbum(Album album) {
        albums.remove(album);
    }

    /**
     * Returns the string representation of the user, which is its name.
     * @return the name of the user
     */
    @Override
    public String toString() {
        return name;
    }
}
