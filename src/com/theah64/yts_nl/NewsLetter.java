package com.theah64.yts_nl;

import com.theah64.yts_api.models.YtsMovie;

import java.util.List;

/**
 * Created by theapache64 on 1/10/17.
 */
public class NewsLetter {

    private final String html;

    public NewsLetter(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public static class Builder {

        private final StringBuilder sb = new StringBuilder();

        public Builder() {
            sb
                    .append("YTS Newsletter")
                    .append("\n---------------");
        }

        /**
         * private final String title, imdbId, imageUrl, year, imdbRating, genre, ytsUrl;
         *
         * @param newMovies
         * @return
         */
        public Builder addMovies(List<YtsMovie> newMovies) {
            for (final YtsMovie ytsMovie : newMovies) {
                sb.append("\nTitle:").append(ytsMovie.getTitle());
                sb.append("\nImage:").append(ytsMovie.getImageUrl());
                sb.append("\nYear:").append(ytsMovie.getYear());
                sb.append("\nGenre:").append(ytsMovie.getGenre());
                sb.append("\nYts URL:").append(ytsMovie.getYtsUrl());
                sb.append("\n----------------------------------");
            }
            return null;
        }

        public NewsLetter build() {
            sb.append("\nA Github project");
            return new NewsLetter(sb.toString());
        }
    }
}
