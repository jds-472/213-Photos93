package model;

import java.util.HashSet;
import java.util.Set;
import java.io.*;

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
    final static long serialVersionUID = 1L;


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Data.currentUser = currentUser;
    }

    public static Album getCurrentAlbum() {
        return currentAlbum;
    }

    public static void setCurrentAlbum(Album currentAlbum) {
        Data.currentAlbum = currentAlbum;
    }

    public static Photo getCurrentPhoto() {
        return currentPhoto;
    }

    public static void setCurrentPhoto(Photo currentPhoto) {
        Data.currentPhoto = currentPhoto;
    }

    public static int getCurrentFXML() {
        return currentFXML;
    }

    public static void setCurrentFXML(int currentFXML) {
        Data.currentFXML = currentFXML;
    }

    public static User getUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }
    
    public static Set<User> getUsers() {
        return users;
    }

        public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile))) {
            Object obj = ois.readObject();
            if (obj instanceof Set<?>) {
                users = (Set<User>) obj;
            }
            for (User user : users) {
                System.out.println(user);
                for (Album album : user.getAlbums()) {
                    System.out.println("  " + album);
                    for (Photo photo : album.getPhotos()) {
                        System.out.println("    " + photo);
                        for (Tag tag : photo.getTags()) {
                            System.out.println("      Tag: " + tag);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
