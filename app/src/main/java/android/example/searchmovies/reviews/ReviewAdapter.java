package android.example.searchmovies.reviews;

import android.content.Context;
import android.example.searchmovies.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context;
    private List<Review> reviewList = new ArrayList<>();

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.review_list, parent, false);
        ReviewAdapter.ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {

        final Review review = reviewList.get(position);

        TextView TextViewAuthor = holder.authorTextView.findViewById(R.id.review_author);
        TextView TextViewReview = holder.reviewTextView.findViewById(R.id.review_content);

        TextViewAuthor.setText(review.getAuthor());
        TextViewReview.setText(review.getTheReview());

    }

    @Override
    public int getItemCount() {
        if (reviewList == null) {
            return 0;
        }
        return reviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView reviewTextView;
        TextView authorTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            authorTextView = itemView.findViewById(R.id.review_author);
            reviewTextView = itemView.findViewById(R.id.review_content);
        }
    }
}

