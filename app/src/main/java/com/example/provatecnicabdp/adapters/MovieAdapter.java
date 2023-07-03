package com.example.provatecnicabdp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.provatecnicabdp.LlistaMovies;
import com.example.provatecnicabdp.R;
import com.example.provatecnicabdp.datos.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> movies;
    private Context context;

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    // Crea una nova instancia de ViewHolder inflant el layout corresponent
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    // Vincula les dades de la pel·licula amb els elements de la interficie
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.txtMovieName.setText(movie.getName());
        holder.txtReleaseDate.setText(movie.getReleaseDate());
        holder.txtGenre.setText(movie.getGenre());

        Glide.with(context)
                .load(movie.getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgMoviePoster);

        // Configura l'accio que es realitzara quan es faci clic en una pel·licula de la llista
        holder.itemView.setOnClickListener(v -> ((LlistaMovies) context).showDetailsMovie(movie));
    }

    // Retorna el nombre d'elements de la llista de pel·licules
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // Classe ViewHolder que conté les referencies als elements de la interficie d'un item de la llista
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgMoviePoster;
        public TextView txtMovieName;
        public TextView txtReleaseDate;
        public TextView txtGenre;

        public ViewHolder(View itemView) {
            super(itemView);
            imgMoviePoster = itemView.findViewById(R.id.imgMoviePoster);
            txtMovieName = itemView.findViewById(R.id.txtMovieName);
            txtReleaseDate = itemView.findViewById(R.id.txtReleaseDate);
            txtGenre = itemView.findViewById(R.id.txtGenre);
        }
    }

    // Actualitza la llista de pel·licules
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}
