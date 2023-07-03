package com.example.provatecnicabdp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    Button btnCartellera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCartellera = findViewById(R.id.btnCartellera);

        btnCartellera.setOnClickListener(view -> {
            AnarACartellera();
        });
    }

    private void AnarACartellera() {
        llegirJson();
        Intent intent = new Intent(this, LlistaCines.class);
        startActivity(intent);
    }

    private void llegirJson() {
        try {
            // Primer llegirem el json com a String
            InputStream inputStream = getAssets().open("cines.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String content = new String(buffer, "UTF-8");

            //Despres el convertirem a un JSONArray
            JSONArray contentArray = new JSONArray(content);

            for (int i = 0; i < contentArray.length(); i++) {
                //Convertim cada posicio de l'array a un objecte JSON
                JSONObject cinemaObject = contentArray.getJSONObject(i);

                // Accedeim a les dades del cine
                int cinemaId = cinemaObject.getInt("id");
                String cinemaName = cinemaObject.getString("name");
                String cinemaAddress = cinemaObject.getString("address");

                // Crear una instancia de MovieDataSource
                DatabaseManage databaseManage = new DatabaseManage(this);

                // Obrir la base de dades
                databaseManage.open();

                // Inserir les dades a la base de dades
                long cinemaRowId = databaseManage.insertCinema(cinemaId, cinemaName, cinemaAddress);

                // Accedim a la llista de pel·licules
                JSONArray moviesArray = cinemaObject.getJSONArray("peliculas");

                for (int j = 0; j < moviesArray.length(); j++) {
                    JSONObject movieObject = moviesArray.getJSONObject(j);

                    // Accedir al genere de la pel·licula
                    JSONObject genreObject = movieObject.getJSONObject("gendre");
                    int genreId = genreObject.getInt("id");
                    String genreName = genreObject.getString("name");
                    String genreDescription = genreObject.getString("description");

                    long genreRowId = databaseManage.insertGenre(genreId, genreName, genreDescription);

                    // Accedir a les dades de la pel·licula
                    int movieId = movieObject.getInt("id");
                    String movieName = movieObject.getString("name");
                    String releaseDate = movieObject.getString("releaseDate");
                    String poster = movieObject.getString("poster");

                    long movieRowId = databaseManage.insertMovie(movieId, movieName, releaseDate, poster, (int) genreRowId);
                    long movieCineRowId = databaseManage.insertCinemaMovie((int) cinemaRowId, (int) movieRowId);

                    // Accedir als personatges de la pel·licula
                    JSONArray charactersArray = movieObject.getJSONArray("characters");

                    for (int k = 0; k < charactersArray.length(); k++) {
                        JSONObject characterObject = charactersArray.getJSONObject(k);

                        // Accedir a les dades del personatge
                        int characterId = characterObject.getInt("id");
                        String characterName = characterObject.getString("name");

                        // Accedir a les dades de l'actor
                        JSONObject actorObject = characterObject.getJSONObject("actor");
                        int actorId = actorObject.getInt("id");
                        String actorName = actorObject.getString("name");
                        String actorDescription = actorObject.getString("description");
                        String actorBirthDate = actorObject.getString("birthDate");
                        String actorFoto = actorObject.getString("foto");

                        long actorRowId = databaseManage.insertActor(actorId, actorName, actorDescription, actorBirthDate, actorFoto);

                        long characterRowId = databaseManage.insertCharacter(characterId, (int) movieRowId, characterName, (int) actorRowId);
                    }
                }

                // Tancar la base de dades
                databaseManage.close();
            }

        } catch (IOException | JSONException | SQLException e) {
            e.printStackTrace();
        }
    }


}