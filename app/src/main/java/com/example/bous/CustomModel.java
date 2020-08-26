package com.example.bous;

public class CustomModel {
    private String date, objet;
    private float montant;
    int id;

    public CustomModel(int id, String date, String objet, float montant) {
        this.date = date;
        this.objet = objet;
        this.montant = montant;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }
}
