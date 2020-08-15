package com.example.bous;

public class Dettes {
    private String date;
    private String individu;
    private Float montant;
    private String dateRemboursement;
    private String operation = "IN";
    private int id;

//Quand operation = OUT : remboursement

    public Dettes(String date, String individu, Float montant) {
        this.date = date;
        this.individu = individu;
        this.montant = montant;
    }

    public Dettes(int id, String date, String individu, Float montant) {
        this.date = date;
        this.individu = individu;
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

    public String getIndividu() {
        return individu;
    }

    public void setIndividu(String individu) {
        this.individu = individu;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public String getDateRemboursement() {
        return dateRemboursement;
    }

    public void setDateRemboursement(String dateRemboursement) {
        this.dateRemboursement = dateRemboursement;
    }

}
