package com.example.bous;

public class SourceRevenus {
    private String nom;
    private int id;

    public SourceRevenus(int id, String nom) {
        this.nom = nom;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
