package com.theah64.yts_api.models;

/**
 * Created by theapache64 on 1/10/17.
 */
public class YtsMovie {
    private final String title, imdbId, imageUrl, year, imdbRating, genre, ytsUrl;

    public YtsMovie(String title, String imdbId, String imageUrl, String year, String imdbRating, String genre, String ytsUrl) {
        this.title = title;
        this.imdbId = imdbId;
        this.imageUrl = imageUrl;
        this.year = year;
        this.imdbRating = imdbRating;
        this.genre = genre;
        this.ytsUrl = ytsUrl;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getYear() {
        return year;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getGenre() {
        return genre;
    }

    public String getYtsUrl() {
        return ytsUrl;
    }

    @Override
    public String toString() {
        return "YtsMovie{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", year='" + year + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", genre='" + genre + '\'' +
                ", ytsUrl='" + ytsUrl + '\'' +
                '}';
    }
}
