package com.example.provatecnicabdp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.provatecnicabdp.adapters.MovieAdapter;
import com.example.provatecnicabdp.datos.Movie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LlistaMovies extends AppCompatActivity {
    private RecyclerView recyclerViewMovies;
    private MovieAdapter movieAdapter;
    int cinemaId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_movies);

        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));

        // Obte el cinemaId de l'intent
        cinemaId = getIntent().getIntExtra("cinemaId", -1);

        List<Movie> movies = null;
        try {
            movies = getMoviesForCinema();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        movieAdapter = new MovieAdapter(movies, this);
        recyclerViewMovies.setAdapter(movieAdapter);
    }

    // Obtenir les pel·licules per a un cinema especific
    private List<Movie> getMoviesForCinema() throws SQLException {
        DatabaseManage databaseManage = new DatabaseManage(this);
        databaseManage.open();

        List<Movie> movies = (List<Movie>) databaseManage.getMovieForCinema(cinemaId);

        databaseManage.close();

        return movies;
    }

    // Mostra els detalls d'una pel·licula
    public void showDetailsMovie(Movie movie) {
        Intent intent = new Intent(this, DetailsMovie.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    // Mostra el dialeg de filtratge
    public void showFilterDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona un género")
                .setItems(R.array.generos_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedGenre = getResources().getStringArray(R.array.generos_array)[which];
                        // Aplica el filtre i actualitza l'adaptador
                        try {
                            applyGenreFilter(selectedGenre);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    // Aplica el filtre de genere a les pel·licules
    private void applyGenreFilter(String genre) throws SQLException {
        List<Movie> filteredMovies = new ArrayList<>();

        for (Movie movie : getMoviesForCinema()) {
            if (movie.getGenre().equals(genre)) {
                filteredMovies.add(movie);
            }
        }

        movieAdapter.setMovies(filteredMovies);
        movieAdapter.notifyDataSetChanged();
    }

}
