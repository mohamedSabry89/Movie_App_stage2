package android.example.searchmovies.utils;


import android.content.Context;
import android.example.searchmovies.database.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public final class  JsonUtils {
    public static Movie[] parseMovieJson(Context context, String json) throws JSONException {

        final String IMG_URL = "https://image.tmdb.org/t/p/w185";

        final String RESULT = "results";
        final String TITLE = "title";
        final String POSTER = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";
        final String ID = "movie_id";

        JSONObject movieJson = new JSONObject(json);
        JSONArray result = movieJson.getJSONArray(RESULT);
        Movie[] movieResults = new Movie[result.length()];

        for (int i = 0; i < result.length(); i++) {
            String title, poster_path, overview, vote_average, release_date;
            int id;

            Movie movie = new Movie();

            poster_path = result.getJSONObject(i).optString(POSTER);
            title = result.getJSONObject(i).optString(TITLE);
            overview = result.getJSONObject(i).optString(OVERVIEW);
            vote_average = result.getJSONObject(i).optString(VOTE_AVERAGE);
            release_date = result.getJSONObject(i).optString(RELEASE_DATE);
            id = result.getJSONObject(i).optInt(ID);

            movie.setPoster(IMG_URL + poster_path);
            movie.setTitle(title);
            movie.setOverview(overview);
            movie.setAverage(vote_average);
            movie.setDate(release_date);
            movie.setId(id);
            movieResults[i] = movie;

        }

        return movieResults;
    }
}
