package android.example.searchmovies.trailers;

import android.content.Context;
import android.example.searchmovies.reviews.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class TrailerJson {
    public static Trailer[] parseReviewJson(Context context, String json) throws JSONException {

        final String RESULT = "results";
        final String KEY_VIDEO = "key";
        final String VIDEO_NAME = "name";
        final String SITE = "site";

        JSONObject trailerJson = new JSONObject(json);
        JSONArray result = trailerJson.getJSONArray(RESULT);
        Trailer[] movieTrailers = new Trailer[(result.length())];

        for (int i = 0; i < result.length(); i++) {

            Trailer trailer = new Trailer();

            String key = result.getJSONObject(i).optString(KEY_VIDEO);
            String name = result.getJSONObject(i).optString(VIDEO_NAME);
            String site = result.getJSONObject(i).optString(SITE);

            trailer.setVideoKey(key);
            trailer.setVideoName(name);
            trailer.setSite(site);

            movieTrailers[i] = trailer;

        }
        return movieTrailers;
    }
}
