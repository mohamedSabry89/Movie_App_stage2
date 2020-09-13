package android.example.searchmovies.trailers;

import android.content.Context;
import android.example.searchmovies.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private Context context;
    private Trailer[] trailerList;

    public TrailerAdapter(Context context, Trailer[] trailerList) {
        this.context = context;
        this.trailerList = trailerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.video_list, parent, false);
        TrailerAdapter.ViewHolder viewHolder = new TrailerAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.ViewHolder holder, int position) {
        final Trailer trailer = trailerList[position];

        TextView tvTrailer = holder.trailerTextView.findViewById(R.id.link_trailer);

        tvTrailer.setText(trailer.getVideoName());

    }

    @Override
    public int getItemCount() {
        if (trailerList == null) {
            return 0;
        }
        return trailerList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView trailerTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerTextView = itemView.findViewById(R.id.link_trailer);
        }
    }
}
