package com.example.provatecnicabdp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.provatecnicabdp.adapters.CharacterAdapter;
import com.example.provatecnicabdp.datos.Character;
import com.example.provatecnicabdp.datos.Movie;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.List;

public class DetailsMovie extends AppCompatActivity {

    private ImageView imgMoviePoster;
    private TextView txtMovieName;
    private TextView txtReleaseDate;
    private TextView txtGenre;

    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        DatabaseManage databaseManage = new DatabaseManage(this);
        try {
            databaseManage.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Obte referències als components de la interficie d'usuari
        imgMoviePoster = findViewById(R.id.imgMoviePoster);
        txtMovieName = findViewById(R.id.txtMovieName);
        txtReleaseDate = findViewById(R.id.txtReleaseDate);
        txtGenre = findViewById(R.id.txtGenre);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        // Assigna els valors de la pel·licula als components de la interficie d'usuari
        Picasso.get().load(movie.getImageUrl()).into(imgMoviePoster);
        txtMovieName.setText(movie.getName());
        txtReleaseDate.setText(movie.getReleaseDate());
        txtGenre.setText(movie.getGenre());

        List<Character> characters = DatabaseManage.getCharactersFromMovie(movie);

        RecyclerView recyclerViewCharacters = findViewById(R.id.recyclerViewCharacters);
        recyclerViewCharacters.setLayoutManager(new LinearLayoutManager(this));
        CharacterAdapter characterAdapter = new CharacterAdapter(characters);
        recyclerViewCharacters.setAdapter(characterAdapter);

        databaseManage.close();

    }
}