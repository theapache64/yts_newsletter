package com.theah64.yts_nl.models;

/**
 * Created by theapache64 on 7/10/17.
 */
public class LetterSent {
    private final int recipientsCount, movieCount;

    public LetterSent(int recipientsCount, int movieCount) {
        this.recipientsCount = recipientsCount;
        this.movieCount = movieCount;
    }

    public int getRecipientsCount() {
        return recipientsCount;
    }

    public int getMovieCount() {
        return movieCount;
    }
}
