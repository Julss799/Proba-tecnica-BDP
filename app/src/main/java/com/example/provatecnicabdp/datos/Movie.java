package com.example.provatecnicabdp.datos;

import java.io.Serializable;

public class Movie implements Serializable {
    private String name;
    private String releaseDate;
    private String genre;
    private String imageUrl;

    public Movie( String name, String releaseDate, String genre, String imageUrl) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}


