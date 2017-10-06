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
                    .append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\" \"http://www.w3.org/TR/REC-html40/loose.dtd\"> <html style=\"margin: 0 auto; padding: 0;\"> <head> </head> <body style=\"background-color: #171717; margin: 0 auto; padding: 0;\" bgcolor=\"#171717\"> <div id=\"header\" style=\"width: 100%; height: 80px; border-bottom-width: 1px; border-bottom-color: #2f2f2f; border-bottom-style: solid; background-color: #1d1d1d; margin: 0 auto; padding: 0;\"> <img id=\"yts_logo\" src=\"yts_logo.png\" style=\"margin: 0 auto; padding: 22px;\"> </div> <div id=\"body\" style=\"text-align: center; color: white; height: 158px; margin: 0 auto; padding: 50px;\" align=\"center\"> <p style=\"font-weight: 600; color: #4a4a4a; font-size: 20px; font-family: 'Roboto', sans-serif; margin: 0 auto; padding: 0;\">20 new movies found</p> <br style=\"margin: 0 auto; padding: 0;\"> <br style=\"margin: 0 auto; padding: 0;\"> <!--Movies--> <div id=\"movies\" style=\"margin: 0 auto; padding: 0;\">");
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
            return this;
        }

        public NewsLetter build() {
            sb.append("\nA Github project");
            return new NewsLetter(sb.toString());
        }
    }
}
