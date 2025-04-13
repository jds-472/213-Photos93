package model;

public class Data {
    private Data(){}

    private static User currentUser = null;
    private static Album currentAlbum = null;
    private static Photo currentPhoto = null;
    private static int currentFXML = 0;

    public static final int LOGINFXML = 0;
    public static final int ADMINFXML = 1;
    public static final int USERFXML = 2;
    public static final int ALBUMFXML = 3;
    public static final int DISPLAYFXML = 4;
    public static final int SLIDESHOWFXML = 5;


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
}
