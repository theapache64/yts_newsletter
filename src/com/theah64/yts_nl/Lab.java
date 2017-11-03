package com.theah64.yts_nl;

import com.theah64.webengine.exceptions.MailException;
import com.theah64.webengine.utils.MailHelper;
import com.theah64.yts_api.models.YtsMovie;
import com.theah64.yts_nl.misc.NewsLetter;
import com.theah64.yts_nl.misc.SecretConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 6/10/17.
 */
public class Lab {
    public static void main(String[] args) {
        try {
            List<YtsMovie> movieList = new ArrayList<>();

            movieList.add(new YtsMovie("Abc", "dfgdfg", "https://yts.ag/assets/images/movies/despicable_me_3_2017/medium-cover.jpg", "2017", "6.5", "A,V,C", "http://google.com"));
            MailHelper.init(SecretConstants.GMAIL_USERNAME, SecretConstants.GMAIL_PASSWORD);
            MailHelper.sendMail("theapache64@gmail.com", "YTS Newsletter",
                    new NewsLetter.Builder(10)
                            .addMovies(movieList)
                            .build().getHtml(), "YTS Newsletter"
            );
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
