package android.example.searchmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movies_Table WHERE id = :id")
    LiveData<Movie> getAllMovies(int id);

    @Update()
    void update(Movie m);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie m);

    @Delete()
    void delete(Movie m);

    @Query("SELECT * FROM Movies_Table ")
    LiveData<List<Movie>> getAllMovies();

}
