package android.example.searchmovies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetWortUtils {

    final static String URL = "http://api.themoviedb.org/3/movie/";
    final static String PARAM_API_KEY = "api_key";
    final static String api_key = "";
    final static String PARAM_LANGUAGE = "language";
    final static String language = "en-US";
    final static String REVIEW = "reviews";
    final static String TRAILER = "";

    public static URL buildUrl(String movieSearchQuery) {
        Uri buildUri = Uri.parse(URL).buildUpon()
                .appendEncodedPath(movieSearchQuery)
                .appendQueryParameter(PARAM_API_KEY, api_key)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL reviewUrl(int id) {
        Uri reviewUri = Uri.parse(URL).buildUpon()
                .appendEncodedPath(String.valueOf(id))
                .appendEncodedPath(REVIEW)
                .appendQueryParameter(PARAM_API_KEY, api_key)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .build();

        URL url = null;
        try {
            url = new URL(reviewUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

