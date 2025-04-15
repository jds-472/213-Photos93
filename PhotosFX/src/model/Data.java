package model;

import java.util.HashSet;
import java.util.Set;
import java.io.*;

/**
 The {@code Data} class holds static fields and metohds used by the whole program.
 It provides functionality to set the current User, Album, and Photo, as well as to save and load data with Serialization.
 It also provides methods to manage the list of users in the system.
 
 <p>Features of the {@code Album} class include:
 <ul>
   <li>Accessing and setting the current fields</li>
   <li>Adding and removing User from the package wide list</li>
   <li>Saving and loading data with serialization</li>
 </ul>
 
 @author [Joseph Scarpulla and Roger Ramirez]
 @version 1.0
 */
public class Data {
    private Data(){}

    private static User currentUser = null;
    private static Album currentAlbum = null;
    private static Photo currentPhoto = null;
    private static int currentFXML = 0;
    private static Set<User> users = new HashSet<>();

    public static final int LOGINFXML = 0;
    public static final int ADMINFXML = 1;
    public static final int USERFXML = 2;
    public static final int ALBUMFXML = 3;
    public static final int DISPLAYFXML = 4;
    public static final int SLIDESHOWFXML = 5;
    public static final int SEARCHFXML = 6;

    public static final String storeFile = "users.ser";
    public static final String storeDir = "src" + File.separator + "model" + File.separator + "store";

    /**
    Returns the current user of the system. 

     @return the current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user of the system.
     * 
     * @param currentUser the user to set as the current user
     */
    public static void setCurrentUser(User currentUser) {
        Data.currentUser = currentUser;
    }

    /**
     * Returns the current album of the system.
     * 
     * @return the current album
     */
    public static Album getCurrentAlbum() {
        return currentAlbum;
    }

    /**
     * Sets the current album of the system.
     * 
     * @param currentAlbum the album to set as the current album
     */
    public static void setCurrentAlbum(Album currentAlbum) {
        Data.currentAlbum = currentAlbum;
    }

    /**
     * Returns the current photo of the system.
     * 
     * @return the current photo
     */
    public static Photo getCurrentPhoto() {
        return currentPhoto;
    }

    /**
     * Sets the current photo of the system.
     * 
     * @param currentPhoto the photo to set as the current photo
     */
    public static void setCurrentPhoto(Photo currentPhoto) {
        Data.currentPhoto = currentPhoto;
    }

    /**
     * Returns the current FXML of the system.
     * 
     * @return the current FXML
     */
    public static int getCurrentFXML() {
        return currentFXML;
    }

    /**
     * Sets the current FXML of the system.
     * 
     * @param currentFXML the FXML to set as the current FXML
     */
    public static void setCurrentFXML(int currentFXML) {
        Data.currentFXML = currentFXML;
    }

    /**
     * Returns the user with the specified name.
     * 
     * @param name the name of the user to retrieve
     * @return the user with the specified name, or null if not found
     */
    public static User getUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a user to the list of users.
     * 
     * @param user the user to add
     */
    public static void addUser(User user) {
        users.add(user);
    }

    /**
     * Removes a user from the list of users.
     * 
     * @param user the user to remove
     */
    public static void removeUser(User user) {
        users.remove(user);
    }

    /**
     * Returns the set of users in the system.
     * 
     * @return the set of users
     */
    public static Set<User> getUsers() {
        return users;
    }

    /**
     * Saves the data to a file using serialization.
     *
     */
    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the data from a file using deserialization.
     *
     */
    public static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile))) {
            Object obj = ois.readObject();
            if (obj instanceof Set<?>) {
                users = (Set<User>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
