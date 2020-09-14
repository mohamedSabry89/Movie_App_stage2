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
<<<<<<< HEAD
=======
import android.widget.ScrollView;
>>>>>>> 2929748... after updating xml
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

<<<<<<< HEAD
=======
    ScrollView scrollView;

>>>>>>> 2929748... after updating xml
    private static final int DEFAULT_POSITION = 0;
    TextView tvTitle, tvOverview, tvDate, tvRate, tvReview, tvContent;
    ImageView imPoster;
    ToggleButton button;
    AppDatabase appDatabase;
    Movie movie;
    String moviePoster, title, overView, rate, date;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.movies_details);
=======
        setContentView(R.layout.movies_datails);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
>>>>>>> 2929748... after updating xml

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        recyclerView = findViewById(R.id.recycler_review);
        recyclerView2 = findViewById(R.id.trailer);

        tvReview = findViewById(R.id.review_author);
        tvContent = findViewById(R.id.review_content);

<<<<<<< HEAD

=======
>>>>>>> 2929748... after updating xml
        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvDate = findViewById(R.id.tv_date);
        tvRate = findViewById(R.id.tv_rate);
        imPoster = findViewById(R.id.imageView);
        button = findViewById(R.id.favorite);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("get_data");

        assert movie != null;
        id = movie.getId();

        title = movie.getTitle();
        overView = movie.getOverview();
        date = movie.getDate();
        rate = movie.getAverage();
        moviePoster = movie.getPoster();
        id = movie.getId();

        task = new getReviewUrl();
        task.execute();

        trailerTask = new getTrailerUrl();
        trailerTask.execute();

        Picasso.get().load(moviePoster).fit().centerInside().into(imPoster);
        tvTitle.setText(title);
        tvDate.setText(date);
        tvRate.setText(rate);
        tvOverview.setText(overView);

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
            appDatabase.movieDao().insert(movie);
            Log.d("the movie ID added  " + id, "   good");

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


}