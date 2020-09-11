package android.example.searchmovies;

import android.example.searchmovies.database.AppDatabase;
import android.example.searchmovies.database.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

class AddTaskViewModel extends ViewModel {
    private LiveData<Movie> task;

    public AddTaskViewModel(AppDatabase database, int taskId) {
        task = database.movieDao().getAllMovies(taskId);
    }

    public LiveData<Movie> getTask() {
        return task;
    }
}
