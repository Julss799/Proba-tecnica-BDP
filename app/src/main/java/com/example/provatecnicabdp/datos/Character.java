package com.example.provatecnicabdp.datos;

public class Character {
    private int id;
    private String name;
    private Actor actor;

    public Character(int id, String name, Actor actor) {
        this.id = id;
        this.name = name;
        this.actor = actor;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Actor getActor() {
        return actor;
    }
}

