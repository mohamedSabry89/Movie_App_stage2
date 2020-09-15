package android.example.searchmovies.database;

import android.app.Application;
import android.example.searchmovies.MoviesRecyclerAdapter;
import android.example.searchmovies.database.AppDatabase;
import android.example.searchmovies.database.Movie;
import android.example.searchmovies.database.MovieDao;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> mInstance;



    public ViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        if (mInstance == null) {
            mInstance = appDatabase.movieDao().getAllMovies();
        }
    }
        public LiveData<List<Movie>> getAllMovies() {
        return mInstance;
    }

}

