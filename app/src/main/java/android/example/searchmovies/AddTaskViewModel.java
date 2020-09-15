package android.example.searchmovies;

import android.content.Context;
import android.example.searchmovies.database.AppDatabase;
import android.example.searchmovies.database.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

class AddTaskViewModel extends ViewModel {

    private LiveData<Movie> task;
    MoviesRecyclerAdapter movieAdapter;

    public AddTaskViewModel() {
        movieAdapter = new MoviesRecyclerAdapter(null, (List<Movie>) task);
    }

    public AddTaskViewModel(AppDatabase database, int taskId) {
        task = database.movieDao().getAllMovies(taskId);
    }

    public LiveData<Movie> getTask() {
        return task;
    }
}
