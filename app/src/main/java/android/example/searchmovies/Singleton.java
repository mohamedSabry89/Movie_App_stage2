package android.example.searchmovies;

import android.example.searchmovies.database.Movie;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private static List<Movie> mMovies = null;

    public static List<Movie> getInstance() {
        if (mMovies == null)
            mMovies = new ArrayList<>();
        return mMovies;
    }

    private Singleton() {
    }
}
