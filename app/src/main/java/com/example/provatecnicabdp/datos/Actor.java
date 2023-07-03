package com.example.provatecnicabdp.datos;

public class Actor {
    private int id;
    private String name;
    private String description;
    private String birthDate;
    private String foto;

    public Actor(int id, String name, String description, String birthDate, String foto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.birthDate = birthDate;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getFoto() {
        return foto;
    }
}
