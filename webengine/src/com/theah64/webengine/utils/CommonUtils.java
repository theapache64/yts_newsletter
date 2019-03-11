package com.theah64.webengine.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by theapache64 on 1/10/17.
 * You're a mean one
 * You really are a heel
 * You're as cuddly as a cactus
 * You're as charming as an eel
 * Mr. Grinch, you're a bad banana
 * (Mr. Grinch), with the greasy black peel
 * You're a vile one
 * You got termites in your smile
 * You have all the tender sweetness
 * Of a seasick crocodile (Mr. Grinch)
 * You're a foul one
 * Friends, you don't have none
 * I wouldn't touch you with a thirty-nine-and-a-half foot pole!
 * You're a monster
 * Your heart's an empty hole
 * You're a goner
 * You got garlic in your soul
 * You got garlic in your soul
 * All them smiles, homie
 * I turn to frowns
 * All them decorations
 * I tear 'em down
 * You can ask Max, I don't play around
 * Ayo (eww)
 * Who is this mean fellow
 * With his skin all green and his teeth all yellow? (eww)
 * What you so mad for?
 * Halloween come around and we ain't knockin' at your door, mane
 * Mr. Grinch you're a bad banana
 * You're gonna spoil everybody with your bad attitude
 * (Spoil everybody)
 * Mr. Grinch
 * La-la-la-la
 * Who is this mean fellow
 * With his skin all green and his teeth all yellow?
 * (La-la-la-la)
 * What you so mad for?
 * Halloween come around and we ain't knockin' at your door
 * Bad banana
 */
public class CommonUtils {
    public static String getProper(int size, String singular, String plural) {
        return size <= 1 ? singular : plural;
    }

    public static boolean isJSONValid(String jsonInString, String message) throws Request.RequestException {

        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            throw new Request.RequestException(message + ":" + e.getMessage());
        }
    }
}
