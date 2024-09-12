package org.example.server.logic.song;

public class Song {
    private String title;
    private String artist;
    private String imageUrl;

    public Song() {
    }

    public Song(String artist, String title, String imageUrl) {
        this.artist = artist;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
