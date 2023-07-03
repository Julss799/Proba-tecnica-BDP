package com.example.provatecnicabdp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.provatecnicabdp.adapters.CinemaAdapter;
import com.example.provatecnicabdp.datos.Cinema;

import java.sql.SQLException;
import java.util.List;

public class LlistaCines extends AppCompatActivity {
        RecyclerView recyclerViewCinemas;
        CinemaAdapter cinemaAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_llista_cines);

            recyclerViewCinemas = findViewById(R.id.recyclerViewCinemas);
            recyclerViewCinemas.setLayoutManager(new LinearLayoutManager(this));

            // Aqui obte les dades dels cinemes de la teva base de dades
            List<Cinema> cinemas = null;
            try {
                cinemas = obtindrecinemas();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            cinemaAdapter = new CinemaAdapter(cinemas,this);
            recyclerViewCinemas.setAdapter(cinemaAdapter);
        }

    // Obte la llista de cinemes de la base de dades
    private List<Cinema> obtindrecinemas() throws SQLException {
        DatabaseManage databaseManage = new DatabaseManage(this);
        databaseManage.open();

        List<Cinema> cinemas = (List<Cinema>) databaseManage.getAllCinemas();

        databaseManage.close();

        return cinemas;
    }

    // Mostra les pel·licules per a un cinema especific
    public void showMoviesForCinema(Cinema cinema) {
        Intent intent = new Intent(this, LlistaMovies.class);
        intent.putExtra("cinemaId", cinema.getId());
        startActivity(intent);
    }
}