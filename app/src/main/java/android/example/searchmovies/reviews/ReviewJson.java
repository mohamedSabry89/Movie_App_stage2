package android.example.searchmovies.reviews;

import android.content.Context;
import android.example.searchmovies.database.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ReviewJson {
    public static Review[] parseReviewJson(Context context, String json) throws JSONException {

        final String RESULT = "results";
        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String ID = "movie_id";

        JSONObject reviewJson = new JSONObject(json);
        JSONArray result = reviewJson.getJSONArray(RESULT);
        Review[] movieReviews = new Review[(result.length())];

        for (int i = 0; i < result.length(); i++) {

            Review review = new Review();

            String author = result.getJSONObject(i).optString(AUTHOR);
            String content = result.getJSONObject(i).optString(CONTENT);

            review.setAuthor(author);
            review.setTheReview(content);

            movieReviews[i] = review;

        }
        return movieReviews;
    }
}
