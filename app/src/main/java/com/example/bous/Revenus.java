package com.example.bous;

public class Revenus {
    private String date;
    private Float montant;
    private int idSource;
    private Float epargneMontant;
    private int id;

    public Revenus(String date, Float montant, int idSource, Float epargneMontant) {
        this.date = date;
        this.montant = montant;
        this.idSource = idSource;
        this.epargneMontant = epargneMontant;
    }

    public Revenus(String date, Float montant, int idSource, Float epargneMontant, int id) {
        this.date = date;
        this.montant = montant;
        this.idSource = idSource;
        this.epargneMontant = epargneMontant;
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

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public int getIdSource() {
        return idSource;
    }

    public void setIdSource(int idSource) {
        this.idSource = idSource;
    }

    public Float getEpargneMontant() {
        return epargneMontant;
    }

    public void setEpargneMontant(Float epargneMontant) {
        this.epargneMontant = epargneMontant;
    }
}
