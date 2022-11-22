package ml.luxinfine.bot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlUtils {
    public static String execute(String url) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            return reader.readLine();
        } catch (IOException e) { return ""; }
    }
}
