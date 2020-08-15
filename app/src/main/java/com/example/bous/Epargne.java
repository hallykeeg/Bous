package com.example.bous;

public class Epargne {
    private String date;
    private Float montant;
    private String operation;

    public Epargne(String date, Float montant, String operation) {
        this.date = date;
        this.montant = montant;
        this.operation = operation;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
