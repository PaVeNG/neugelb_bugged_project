package com.bugged.themoviedb.ui.base.MovieList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bugged.themoviedb.R;
import com.bugged.themoviedb.data.model.Movie;
import com.bugged.themoviedb.util.ConstantUtils;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> implements Filterable {
    private List<Movie> movies_list;
    private List<Movie> movieListFiltered;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Movie item);
    }

    private  OnItemClickListener listener;

    public MoviesAdapter(List<Movie> movies_list, Context mContext, OnItemClickListener listener) {
        this.movies_list = movies_list;
        this.mContext = mContext;
        this.listener = listener;
        movieListFiltered = movies_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bind(movieListFiltered.get(position), listener);
        holder.item_movie_release_date.setText(movieListFiltered.get(position).getRelease_date());
        holder.item_movie_title.setText(movieListFiltered.get(position).getTitle());
        Picasso.with(mContext).load(ConstantUtils.HOST_NAME1+movieListFiltered.get(position).getPoster_path()).into(holder.item_movie_poster);
        holder.item_movie_rating.setText(String.valueOf(movieListFiltered.get(position).getVote_average()));
    }

    @Override
    public int getItemCount() {
        return movieListFiltered.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_movie_release_date, item_movie_title, item_movie_rating;
        ImageView item_movie_poster;
        RelativeLayout card_view;
        ViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            item_movie_release_date = itemView.findViewById(R.id.item_movie_release_date);
            item_movie_title = itemView.findViewById(R.id.item_movie_title);
            item_movie_rating = itemView.findViewById(R.id.item_movie_rating);
            item_movie_poster = itemView.findViewById(R.id.item_movie_poster);
            card_view = itemView.findViewById(R.id.card_view);
        }

        public void bind(final Movie item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public void add(Movie movie) {
        movieListFiltered.add(movie);
        notifyItemInserted(movieListFiltered.size() - 1);
    }

    public void addAll(List<Movie> movies) {
        for (Movie movie : movies) {
            add(movie);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    movieListFiltered = movies_list;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie row : movies_list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                    }

                    movieListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = movieListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                movieListFiltered = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
