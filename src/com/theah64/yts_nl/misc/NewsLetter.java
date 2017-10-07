package com.theah64.yts_nl.misc;

import com.theah64.webengine.utils.CommonUtils;
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

        public Builder(int movieCount) {
            sb
                    .append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\" \"http://www.w3.org/TR/REC-html40/loose.dtd\"> <html style=\"margin: 0 auto; padding: 0;\"> <head> </head> <body style=\"background-color: #171717; margin: 0 auto; padding: 0;\" bgcolor=\"#171717\"> <div id=\"header\" style=\"width: 100%; height: 80px; border-bottom-width: 1px; border-bottom-color: #2f2f2f; border-bottom-style: solid; background-color: #1d1d1d; margin: 0 auto; padding: 0;\"> <img id=\"yts_logo\" src=\"http://theapache64.xyz:8080/yts_newsletter/yts_logo.png\" style=\"margin: 0 auto; padding: 22px;\"> </div> <div id=\"body\" style=\"text-align: center; color: white; margin: 0 auto; padding: 50px;\" align=\"center\"> <p style=\"font-weight: 600; color: #4a4a4a; font-size: 20px; font-family: 'Roboto', sans-serif; margin: 0 auto; padding: 0;\">MOVIE_COUNT_STRING</p> <br style=\"margin: 0 auto; padding: 0;\"> <br style=\"margin: 0 auto; padding: 0;\"> <!--Movies--> <div id=\"movies\" style=\"margin: 0 auto; padding: 0;\">".replace("MOVIE_COUNT_STRING", CommonUtils.getProper(movieCount, "One movie found", movieCount + " movies found")));
        }

        private final static String MOVIE_NODE = "<div class=\"movie\" style=\"width: 100%; height: 400px; margin: 0 auto; padding: 0;\"> <!--Poster--> <img class=\"poster\" src=\"MOVIE_IMAGE_URL\" style=\"border-radius: 10px; float: left; margin: 0 10px 0 auto; padding: 0; border: 3px solid white;\" align=\"left\"> <div class=\"movie_details\" style=\"text-align: left; margin: 0 auto; padding: 0;\" align=\"left\"> <p class=\"movie_name\" style=\"font-family: 'Roboto', sans-serif; font-weight: 600; font-size: 29px; margin: 0 auto; padding: 0;\">MOVIE_NAME</p> <p class=\"year\" style=\"font-family: 'Roboto', sans-serif; color: #b5b5b5; margin: 0 auto; padding: 0;\">MOVIE_YEAR</p> <p class=\"genres\" style=\"font-family: 'Roboto', sans-serif; margin: 0 auto; padding: 0;\">MOVIE_GENRES</p> <p class=\"imdb_rating\" style=\"font-family: 'Roboto', sans-serif; font-size: 72px; font-weight: 600; margin: 0 auto; padding: 0;\"><a target=\"_blank\" href=\"http://imdb.com/title/MOVIE_IMDB_ID/\" style=\"font-family: 'Roboto', sans-serif; text-decoration: none; color: white; margin: 0 auto; padding: 0;\"> <img src=\"http://theapache64.xyz:8080/yts_newsletter/logo-imdb.png\" style=\"margin: 0 auto; padding: 0;\"> MOVIE_IMDB_RATING</a></p> <p class=\"yts_link\" style=\"font-family: 'Roboto', sans-serif; margin: 0 auto; padding: 0;\"><a target=\"_blank\" class=\"yts_link\" href=\"MOVIE_YTS_URL\" style=\"font-family: 'Roboto', sans-serif; text-decoration: none; border-radius: 3px; -webkit-border-radius: 3px; -moz-border-radius: 3px; color: white !important; font-weight: 800; background-color: #6ac045; margin: 0 auto; padding: 1px 15px;\">View</a></p> </div> </div> <br style=\"margin: 0 auto; padding: 0;\">";


        /**
         * private final String title, imdbId, imageUrl, year, imdbRating, genre, ytsUrl;
         *
         * @param newMovies
         * @return
         */
        public Builder addMovies(List<YtsMovie> newMovies) {

            for (final YtsMovie ytsMovie : newMovies) {

                String movieNode = MOVIE_NODE;
                movieNode = movieNode.replace("MOVIE_IMAGE_URL", ytsMovie.getImageUrl());
                movieNode = movieNode.replace("MOVIE_NAME", ytsMovie.getTitle());
                movieNode = movieNode.replace("MOVIE_YEAR", ytsMovie.getYear());
                movieNode = movieNode.replace("MOVIE_IMDB_URL", ytsMovie.getImdbId());
                movieNode = movieNode.replace("MOVIE_GENRES", ytsMovie.getGenre());
                movieNode = movieNode.replace("MOVIE_YTS_URL", ytsMovie.getYtsUrl());
                movieNode = movieNode.replace("MOVIE_IMDB_RATING", ytsMovie.getImdbRating());
                sb.append(movieNode);
            }
            return this;
        }

        public NewsLetter build() {
            sb.append("</div> </div> <div id=\"footer\" style=\"width: 100%; text-align: center; border-top-color: #2f2f2f; border-top-width: 1px; border-top-style: solid; position: absolute; bottom: 0px; background-color: #1d1d1d; margin: 0 auto; padding: 0;\" align=\"center\"> <p id=\"credits\" style=\"font-family: 'Roboto', sans-serif; color: #565656; margin: 0 auto; padding: 10px;\"><a target=\"_blank\" href=\"http://github.com/theapache64/yts_newsletter\" style=\"font-family: 'Roboto', sans-serif; text-decoration: none; color: #565656; margin: 0 auto; padding: 0;\"> A Github Project</a></p> </div> </body> </html>");
            return new NewsLetter(sb.toString());
        }
    }
}
