package model;

public class Data {
    private Data(){}

    private static User currentUser = null;
    private static Album currentAlbum = null;
    private static Photo currentPhoto = null;

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
}
