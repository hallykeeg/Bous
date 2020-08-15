package com.example.bous;

public class Depenses {
    private String date;
    private Float montant;
    private int idObjet;

    public Depenses(String date, Float montant, int idObjet) {
        this.date = date;
        this.montant = montant;
        this.idObjet = idObjet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public int getIdObjet() {
        return idObjet;
    }

    public void setIdObjet(int idObjet) {
        this.idObjet = idObjet;
    }
}
