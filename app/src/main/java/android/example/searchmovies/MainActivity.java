package android.example.searchmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.example.searchmovies.database.AppDatabase;
import android.example.searchmovies.database.Movie;
import android.example.searchmovies.database.ViewModel;
import android.example.searchmovies.utils.JsonUtils;
import android.example.searchmovies.utils.NetWortUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Movie> movie = new ArrayList();
    private RecyclerView recyclerView;
    public MoviesRecyclerAdapter adapter;
    private RecyclerView.LayoutManager rvLayout;
    public ImageView imageView;
    public Context context;
    public MovieQueryTask task;
    public String sortBy = "popular";

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task = new MovieQueryTask();
        task.execute("popular");

        recyclerView = findViewById(R.id.recycler_view);
        imageView = findViewById(R.id.movies_img);

        rvLayout = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(rvLayout);

        adapter = new MoviesRecyclerAdapter(context, movie);
        recyclerView.setAdapter(adapter);

    }

    @SuppressLint("StaticFieldLeak")
    public class MovieQueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String urlSearchResult = null;
            try {
                URL searchUrl = NetWortUtils.buildUrl(urls[0]);
                urlSearchResult = NetWortUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return urlSearchResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                movie = Arrays.asList(JsonUtils.parseMovieJson(MainActivity.this, s));
                new GridLayoutManager(MainActivity.this, 2);
                recyclerView.setLayoutManager(rvLayout);
                adapter = new MoviesRecyclerAdapter(MainActivity.this, movie);
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();

        if (itemSelected == R.id.popular) {
            sortBy = "popular";
            task.cancel(true);
            task = new MovieQueryTask();
            task.execute(sortBy);
            Toast.makeText(this, "Searching By Popular", Toast.LENGTH_SHORT).show();
        } else if (itemSelected == R.id.best_rate) {
            sortBy = "top_rated";
            task.cancel(true);
            task = new MovieQueryTask();
            task.execute(sortBy);
            Toast.makeText(this, "Searching By Best Rating", Toast.LENGTH_SHORT).show();
        } else if (itemSelected == R.id.favorite){
            getFavorites();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getFavorites() {
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> results) {
                adapter.setMovies(results);
            }
        });
    }
}
