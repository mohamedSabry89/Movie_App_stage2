package android.example.searchmovies;


import android.content.Intent;
import android.example.searchmovies.database.AppDatabase;
import android.example.searchmovies.database.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;


public class MovieActivity extends AppCompatActivity {

    private static final int DEFAULT_POSITION = 0;
    TextView tvTitle, tvOverview, tvDate, tvRate;
    ImageView imPoster;
    Button button;
    AppDatabase appDatabase;
    Movie movie;
    String moviePoster, title, overView, rate, date;
    int id = movie.getId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_details);

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvDate = findViewById(R.id.tv_date);
        tvRate = findViewById(R.id.tv_rate);
        imPoster = findViewById(R.id.imageView);
        button = findViewById(R.id.favorite);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("get_data");
        int position = intent.getIntExtra("get_id", id);

        title = movie.getTitle();
        overView = movie.getOverview();
        date = movie.getDate();
        rate = movie.getAverage();
        moviePoster = movie.getPoster();
        id = movie.getId();

        Picasso.get().load(moviePoster).fit().centerInside().into(imPoster);
        tvTitle.setText(title);
        tvDate.setText(date);
        tvRate.setText(rate);
        tvOverview.setText(overView);

        movie = new Movie(moviePoster, title, overView, rate, date, id);

        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {

                if () {
                    view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorGreen));
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        appDatabase.movieDao().insert(movie);
                    });
                } else if () {
                    view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorGrey));
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        appDatabase.movieDao().delete(movie);
                    });
                }
            }
        });
    }
}