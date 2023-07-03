package com.example.provatecnicabdp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.provatecnicabdp.datos.Actor;
import com.example.provatecnicabdp.datos.Character;
import com.example.provatecnicabdp.datos.Cinema;
import com.example.provatecnicabdp.datos.Movie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManage {
    private static SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DatabaseManage(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Obte tots els personatges d'una pel·licula
    public static List<Character> getCharactersFromMovie(Movie movie) {
        List<Character> characters = new ArrayList<>();

        String[] projection = {"id", "name", "actor_id"};
        String selection = "movie_id = (SELECT id FROM movies WHERE name = ?)";
        String[] selectionArgs = {movie.getName()};
        Cursor cursor = database.query("characters", projection, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int actorId = cursor.getInt(cursor.getColumnIndexOrThrow("actor_id"));

            Actor actor = getActorById(actorId);
            Character character = new Character(id, name, actor);
            characters.add(character);
        }

        return characters;
    }

    // Obte un actor per identificador
    public static Actor getActorById(int actorId) {

        String[] projection = {"id", "name", "description", "birthDate", "foto"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(actorId)};
        Cursor cursor = database.query("actors", projection, selection, selectionArgs, null, null, null);

        Actor actor = null;

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String birthDate = cursor.getString(cursor.getColumnIndexOrThrow("birthDate"));
            String foto = cursor.getString(cursor.getColumnIndexOrThrow("foto"));

            actor = new Actor(actorId, name, description, birthDate, foto);
        }

        return actor;
    }

    public void close() {
        dbHelper.close();
    }

    // Insereix un cinema a la base de dades
    public long insertCinema(int id, String name, String address) {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("address", address);

        return database.insert("cinema", null, values);
    }

    // Insereix una pel·licula a la base de dades
    public long insertMovie(int movieId, String movieName, String releaseDate, String poster, int genreId) {

        // Verifica si la pel·licula ja existeix a la base de dades
        String selectQuery = "SELECT * FROM movies WHERE id = " + movieId;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            // La pel·licula ja existeix a la base de dades, retorna el rowId corresponent
            int rowIdIndex = cursor.getColumnIndex("id");
            long movieRowId = cursor.getLong(rowIdIndex);
            cursor.close();
            return movieRowId;
        } else {
            // La pel·licula no existeix a la base de dades, insereix-la i retorna el nou rowId
            ContentValues values = new ContentValues();
            values.put("id", movieId);
            values.put("name", movieName);
            values.put("releaseDate", releaseDate);
            values.put("poster", poster);
            values.put("genre_id", genreId);

            long movieRowId = database.insert("movies", null, values);
            return movieRowId;
        }
    }

    // Insereix un cinema i una pel·licula relacionats a la base de dades
    public long insertCinemaMovie(int cinemaId, int movieId) {
        ContentValues values = new ContentValues();
        values.put("cinema_id", cinemaId);
        values.put("movie_id", movieId);

        return database.insert("cinema_movies", null, values);
    }

    // Insereix un genere a la base de dades
    public long insertGenre(int id, String name, String description) {

        // Verifica si el genere ja existeix a la base de dades
        String selectQuery = "SELECT * FROM genres WHERE id = " + id;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            // El genere ja existeix a la base de dades, retorna el rowId corresponent
            int rowIdIndex = cursor.getColumnIndex("id");
            long genreRowId = cursor.getLong(rowIdIndex);
            cursor.close();
            return genreRowId;
        } else {
            // El genere no existeix a la base de dades, insereix-lo i retorna el nou rowId
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("name", name);
            values.put("description", description);

            return database.insert("genres", null, values);
        }
    }

    // Insereix un personatge a la base de dades
    public long insertCharacter(int id, int movieId, String name, int actorId) {
        // Verifica si el personatge ja existeix a la base de dades
        String selectQuery = "SELECT * FROM characters WHERE id = " + id;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            // El personatge ja existeix a la base de dades, retorna el rowId corresponent
            int rowIdIndex = cursor.getColumnIndex("id");
            long characterRowId = cursor.getLong(rowIdIndex);
            cursor.close();
            return characterRowId;
        } else {
            // El personatge no existeix a la base de dades, insereix-lo i retorna el nou rowId
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("movie_id", movieId);
            values.put("name", name);
            values.put("actor_id", actorId);

            long characterRowId = database.insert("characters", null, values);
            return characterRowId;
        }
    }

    // Insereix un actor a la base de dades
    public long insertActor(int id, String name, String description, String birthDate, String foto) {
        // Verifica si l'actor ja existeix a la base de dades
        String selectQuery = "SELECT * FROM actors WHERE id = " + id;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            // L'actor ja existeix a la base de dades, retorna el rowId corresponent
            int rowIdIndex = cursor.getColumnIndex("id");
            long actorRowId = cursor.getLong(rowIdIndex);
            cursor.close();
            return actorRowId;
        } else {
            // L'actor no existeix a la base de dades, insereix-lo i retorna el nou rowId
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("name", name);
            values.put("description", description);
            values.put("birthDate", birthDate);
            values.put("foto", foto);

            long actorRowId = database.insert("actors", null, values);
            return actorRowId;
        }
    }

    // Obte tots els cinemes de la base de dades
    public static List<Cinema> getAllCinemas() {
        List<Cinema> cinemas = new ArrayList<>();
        String[] projection = {"id", "name", "address"};
        String tableName = "cinema";

        Cursor cursor = database.query(tableName, projection, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));

                Cinema cinema = new Cinema(id, name, address);
                cinemas.add(cinema);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return cinemas;
    }

    // Obte les pel·licules d'un cinema a partir de l'ID del cinema
    public List<Movie> getMovieForCinema(int cinemaId) {
        List<Movie> movies = new ArrayList<>();

        String moviesTable = "movies";
        String cinemaMoviesTable = "cinema_movies";
        String genresTable = "genres";
        String[] projection = {"m.name", "m.releaseDate", "m.poster", "g.name AS genre"};
        String selection = "cm.cinema_id = ?";
        String[] selectionArgs = {String.valueOf(cinemaId)};
        String joinStatement = "m.id = cm.movie_id"; // Condicio de junta per a la taula movies i cinema_movies
        String joinStatementGenres = "m.genre_id = g.id"; // Condicio de junta per a la taula movies i genres
        String orderBy = "m.name ASC";

        String rawQuery = "SELECT " + TextUtils.join(", ", projection) +
                " FROM " + moviesTable + " AS m" +
                " INNER JOIN " + cinemaMoviesTable + " AS cm ON " + joinStatement +
                " INNER JOIN " + genresTable + " AS g ON " + joinStatementGenres +
                " WHERE " + selection +
                " ORDER BY " + orderBy;

        Cursor cursor = database.rawQuery(rawQuery, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Llegeix les dades del cursor i crea un objecte Movie
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String releaseDate = cursor.getString(cursor.getColumnIndex("releaseDate"));
                @SuppressLint("Range") String poster = cursor.getString(cursor.getColumnIndex("poster"));
                @SuppressLint("Range") String genre = cursor.getString(cursor.getColumnIndex("genre"));

                Movie movie = new Movie(name, releaseDate, genre, poster);

                // Afegeix l'objecte Movie a la llista de pel·licules
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return movies;
    }
}
