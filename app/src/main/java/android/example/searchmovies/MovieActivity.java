package android.example.searchmovies;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.example.searchmovies.database.AppDatabase;
import android.example.searchmovies.database.Movie;
import android.example.searchmovies.reviews.Review;
import android.example.searchmovies.reviews.ReviewAdapter;
import android.example.searchmovies.reviews.ReviewJson;
import android.example.searchmovies.trailers.Trailer;
import android.example.searchmovies.trailers.TrailerAdapter;
import android.example.searchmovies.trailers.TrailerJson;
import android.example.searchmovies.utils.JsonUtils;
import android.example.searchmovies.utils.NetWortUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


public class MovieActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;

    private RecyclerView.LayoutManager rvLayout;

    public ReviewAdapter reviewAdapter;
    public Review[] review;

    public TrailerAdapter trailerAdapter;
    public Trailer[] trailers;

    public Context context;
    public getReviewUrl task;
    public getTrailerUrl trailerTask;

    // Constant for default task id to be used when not in favourite
    private static final int DEFAULT_TASK_ID = -1;

    TextView tvTitle, tvOverview, tvDate, tvRate, tvReview, tvContent;
    ImageView imPoster;
    ToggleButton button;
    AppDatabase appDatabase;
    Movie movie;
    String moviePoster, title, overView, rate, date;
    int id;
    public Boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_details);

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        recyclerView = findViewById(R.id.recycler_review);
        recyclerView2 = findViewById(R.id.trailer);

        tvReview = findViewById(R.id.review_author);
        tvContent = findViewById(R.id.review_content);

        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvDate = findViewById(R.id.tv_date);
        tvRate = findViewById(R.id.tv_rate);
        imPoster = findViewById(R.id.imageView);
        button = findViewById(R.id.favorite);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("get_data");
        id = intent.getIntExtra("get_id", DEFAULT_TASK_ID);

        title = movie.getTitle();
        overView = movie.getOverview();
        date = movie.getDate();
        rate = movie.getAverage();
        moviePoster = movie.getPoster();
        // id = movie.getId();

        task = new getReviewUrl();
        task.execute();

        trailerTask = new getTrailerUrl();
        trailerTask.execute();

        Picasso.get().load(moviePoster).fit().centerInside().into(imPoster);
        tvTitle.setText(title);
        tvDate.setText(date);
        tvRate.setText(rate);
        tvOverview.setText(overView);
        isFavorite();
        // checkFavorite();

       /* if (id != DEFAULT_TASK_ID) {
            AddTaskViewModelFactory factory = new AddTaskViewModelFactory(appDatabase, id);
            final AddTaskViewModel viewModel = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);
            viewModel.getTask().observe(this, new Observer<Movie>() {
                @Override
                public void onChanged(Movie movie) {
                    viewModel.getTask().removeObserver(this);
                    button.setTextOn("Added");
                    button.getResources().getColor(R.color.colorGreen);
                }
            });
        }*/

        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    compoundButton.setBackgroundColor(ContextCompat.getColor(compoundButton.getContext(), R.color.colorGreen));
                    addToFavourite();
                } else {
                    compoundButton.setBackgroundColor(ContextCompat.getColor(compoundButton.getContext(), R.color.colorGrey));
                    deleteFromFavourite();
                }
            }
        });

    }

    public void addToFavourite() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (!isFavorite) {
                appDatabase.movieDao().insert(movie);
                Log.d("the movie ID added  " + id, "   good");
            }
        });
    }

    public void deleteFromFavourite() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            appDatabase.movieDao().delete(movie);
            Log.d("the movie ID deleted  " + id, "   bad");

        });
    }

    @SuppressLint("StaticFieldLeak")
    public class getReviewUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String urlSearchResult = null;
            try {
                URL searchUrl = NetWortUtils.reviewUrl(id);
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
                review = ReviewJson.parseReviewJson(MovieActivity.this, s);
                rvLayout = new LinearLayoutManager(MovieActivity.this);
                recyclerView.setLayoutManager(rvLayout);
                reviewAdapter = new ReviewAdapter(MovieActivity.this, review);
                recyclerView.setAdapter(reviewAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class getTrailerUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String urlSearchResult = null;
            try {
                URL searchUrl = NetWortUtils.trailerUrl(id);
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
                trailers = TrailerJson.parseReviewJson(MovieActivity.this, s);
                rvLayout = new LinearLayoutManager(MovieActivity.this);
                recyclerView2.setLayoutManager(rvLayout);
                trailerAdapter = new TrailerAdapter(MovieActivity.this, trailers);
                recyclerView2.setAdapter(trailerAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* public void checkFavorite() {
        AppExecutors.getInstance().diskIO().execute(() -> {

            appDatabase.movieDao().getAllMovies(id);

            if (appDatabase.movieDao().check(id) != 0) {
                button.setTextOn("Added");
                button.getResources().getColor(R.color.colorGreen);
            }
            Log.d("the movie ID deleted  " + id, "   bad");
        });
    } */

    public void isFavorite() {
        AddTaskViewModelFactory factory = new AddTaskViewModelFactory(appDatabase, id);
        final AddTaskViewModel viewModel = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);
        LiveData<Movie> favorites = viewModel.getTask();
        favorites.observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie result) {
                favorites.removeObserver(this);
                if (result == null) {
                    isFavorite = false;
                    button.setChecked(false);
                } else if (movie.getId() == result.getId() && !button.isChecked()) {
                    isFavorite = true;
                    button.setChecked(true);
                } else {
                    isFavorite = true;
                    button.setChecked(true);
                }
            }
        });
    }

}
