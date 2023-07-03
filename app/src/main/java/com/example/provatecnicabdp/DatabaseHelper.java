package com.example.provatecnicabdp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cines.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Metode que s'executa quan es crea la base de dades per primera vegada
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la taula "cinema"
        String createCinemaTable = "CREATE TABLE cinema (id INTEGER PRIMARY KEY, name TEXT, address TEXT)";
        db.execSQL(createCinemaTable);

        // Crear la taula "movies"
        String createMoviesTable = "CREATE TABLE movies (id INTEGER PRIMARY KEY, name TEXT, releaseDate TEXT, poster TEXT, genre_id INTEGER, FOREIGN KEY (genre_id) REFERENCES genres(id))";
        db.execSQL(createMoviesTable);

        // Crear la taula intermitja "cinema_movies"
        String createCinemaMoviesTable = "CREATE TABLE cinema_movies (cinema_id INTEGER, movie_id INTEGER, FOREIGN KEY (cinema_id) REFERENCES cinema(id), FOREIGN KEY (movie_id) REFERENCES movies(id))";
        db.execSQL(createCinemaMoviesTable);

        // Crear la taula "genres"
        String createGenresTable = "CREATE TABLE genres (id INTEGER PRIMARY KEY, name TEXT, description TEXT)";
        db.execSQL(createGenresTable);

        // Crear la taula "characters"
        String createCharactersTable = "CREATE TABLE characters (id INTEGER PRIMARY KEY, movie_id INTEGER, name TEXT, actor_id INTEGER, FOREIGN KEY (movie_id) REFERENCES movies(id), FOREIGN KEY (actor_id) REFERENCES actors(id))";
        db.execSQL(createCharactersTable);

        // Crear la taula "actors"
        String createActorsTable = "CREATE TABLE actors (id INTEGER PRIMARY KEY, name TEXT, description TEXT, birthDate TEXT, foto TEXT)";
        db.execSQL(createActorsTable);
    }

    // Metode que s'executa quan es fa un upgrade de la base de dades
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar les taules existents
        db.execSQL("DROP TABLE IF EXISTS cinema");
        db.execSQL("DROP TABLE IF EXISTS movies");
        db.execSQL("DROP TABLE IF EXISTS cinema_movies");
        db.execSQL("DROP TABLE IF EXISTS genres");
        db.execSQL("DROP TABLE IF EXISTS characters");
        db.execSQL("DROP TABLE IF EXISTS actors");

        // Crear les taules novament
        onCreate(db);
    }
}
