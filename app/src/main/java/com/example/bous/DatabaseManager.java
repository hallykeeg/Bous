package com.example.bous;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String name = "bous.db";
    private static final Integer version = 1;

    private static final String utilisateurTable = "CREATE TABLE IF NOT EXISTS utilisateur(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT(15) NOT NULL," +
            "password TEXT(50) NOT NULL)";

    private static final String sourceRevenusTable = "CREATE TABLE IF NOT EXISTS source_revenus(id_source_revenus INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT(50) NOT NULL)";

    private static final String dettesTable = "CREATE TABLE IF NOT EXISTS dettes(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT(50) NOT NULL," +
            "individu TEXT(50) NOT NULL, montant REAL  NOT NULL, date_remboursement TEXT(50)) ";

    private static final String creancesTable = "CREATE TABLE IF NOT EXISTS creances(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT(50) NOT NULL," +
            "individu TEXT(50) NOT NULL, montant REAL  NOT NULL, date_remboursement TEXT(50))";

    private static final String revenusTable = "CREATE TABLE IF NOT EXISTS revenus(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT(50) NOT NULL,epargne_montant REAL NOT NULL, montant REAL NOT NULL," +
            "id_source_revenus INTEGER NOT NULL," +
            "FOREIGN KEY (id_source_revenus)" +
            " REFERENCES source_revenus (id_source_revenus) )";

    private static final String epargneTable = "CREATE TABLE IF NOT EXISTS epargne(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT(50) NOT NULL, montant REAL NOT NULL," +
            "operation TEXT(3) NOT NULL)";
    private static final String objetDepensesTable = "CREATE TABLE IF NOT EXISTS objet_depenses(id_objet_depenses INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT(50) NOT NULL)";

    private static final String depensesTable = "CREATE TABLE IF NOT EXISTS depenses(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT(50) NOT NULL, montant REAL NOT NULL," +
            "id_objet_depenses INTEGER NOT NULL," +
            "FOREIGN KEY(id_objet_depenses) " +
            "REFERENCES objet_depenses (id_objet_depenses) )";


    public DatabaseManager(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilisateurTable);
        db.execSQL(objetDepensesTable);
        db.execSQL(sourceRevenusTable);
        db.execSQL(revenusTable);
        db.execSQL(depensesTable);
        db.execSQL(epargneTable);
        db.execSQL(dettesTable);
        db.execSQL(creancesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertUtilisateur(Utilisateur utilisateur) {
        long state;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", utilisateur.getUsername());
        contentValues.put("password", utilisateur.getPassword());
        state = db.insert("utilisateur", null, contentValues);
        return state;
    }

    public long insertSourceRevenus(String nom) {
        long state;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT COUNT(id_source_revenus) AS card FROM source_revenus WHERE nom =? ";
        String[] params = new String[]{nom};
        final Cursor cursor = db.rawQuery(sql, params);
        cursor.moveToFirst();
        int card = cursor.getInt(cursor.getColumnIndex("card"));
        if (card == 0) {
            ContentValues cv = new ContentValues();
            cv.put("nom", nom);
            state = db.insert("source_revenus", null, cv);
        } else {
            state = 409;
        }

        return state;
    }

    public long insertRevenus(Revenus revenus) {
        long state;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", revenus.getDate());
        contentValues.put("montant", revenus.getMontant());
        contentValues.put("id_source_revenus", revenus.getIdSource());
        contentValues.put("epargne_montant", revenus.getEpargneMontant());
        state = db.insert("revenus", null, contentValues);
        return state;
    }

    public long insertEpargne(Epargne epargne) {
        long state;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date", epargne.getDate());
        cv.put("montant", epargne.getMontant());
        cv.put("operation", epargne.getOperation());
        state = db.insert("epargne", null, cv);
        return state;
    }

    public long insertDettes(Dettes dettes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long state;
        cv.put("date", dettes.getDate());
        cv.put("individu", dettes.getIndividu());
        cv.put("montant", dettes.getMontant());
//        cv.put("date_remboursement", dettes.getDateRemboursement());
//        cv.put("operation",dettes.getOperation());
        state = db.insert("dettes", null, cv);
        return state;


    }

    public long insertCreances(Creances creances) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long state;
        cv.put("date", creances.getDate());
        cv.put("individu", creances.getIndividu());
        cv.put("montant", creances.getMontant());
//        cv.put("date_remboursement", creances.getDateRemboursement());
//        cv.put("operation",creances.getOperation());
        state = db.insert("creances", null, cv);
        return state;


    }


    public long insertDepenses(Depenses dp) {
        SQLiteDatabase db = this.getWritableDatabase();
        long state;
        ContentValues cv = new ContentValues();
        cv.put("date", dp.getDate());
        cv.put("montant", dp.getMontant());
        cv.put("id_objet_depenses", dp.getIdObjet());
        state = db.insert("depenses", null, cv);
        return state;
    }

    public long insertObjetDepenses(String nom) {
        long state;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", nom);
        state = db.insert("objet_depenses", null, contentValues);
        return state;
    }

    //Fonction qui retourne un arraylist d'objets dettes
    public ArrayList<Dettes> selectDettesByMonth(String debut, String fin) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Dettes> arrayList = new ArrayList<>();
        Dettes d;
        int id;
        String date, individu;
        float montant;
        String[] params = new String[]{debut, fin};
        String sql = "SELECT * FROM dettes WHERE date_remboursement IS NULL AND date BETWEEN ? AND ?";
        final Cursor cursor = db.rawQuery(sql, params);
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            date = cursor.getString(cursor.getColumnIndex("date"));
            individu = cursor.getString(cursor.getColumnIndex("individu"));
            montant = cursor.getFloat(cursor.getColumnIndex("montant"));
            d = new Dettes(id, date, individu, montant);
            arrayList.add(d);
        }
        cursor.close();
        return arrayList;

    }

    public ArrayList<Creances> selectCreancesByMonth(String debut, String fin) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Creances> arrayList = new ArrayList<>();
        Creances c;
        int id;
        String date, individu;
        float montant;
        String[] params = new String[]{debut, fin};
        String sql = "SELECT * FROM creances WHERE date_remboursement IS NULL AND date BETWEEN ? AND ?";
        final Cursor cursor = db.rawQuery(sql, params);
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            date = cursor.getString(cursor.getColumnIndex("date"));
            individu = cursor.getString(cursor.getColumnIndex("individu"));
            montant = cursor.getFloat(cursor.getColumnIndex("montant"));
            c = new Creances(id, date, individu, montant);
            arrayList.add(c);
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Revenus> selectRevenus(String debut, String fin) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Revenus> arrayList = new ArrayList<>();
        Revenus revenus;
        int id, id_source;
        float montant;
        float epargneMontant;
        String date;
        String[] params = new String[]{debut, fin};
        String sql = "SELECT * FROM revenus WHERE date BETWEEN ? AND ?";
        final Cursor cursor = db.rawQuery(sql, params);
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            date = cursor.getString(cursor.getColumnIndex("date"));
            epargneMontant = cursor.getFloat(cursor.getColumnIndex("epargne_montant"));
            montant = cursor.getFloat(cursor.getColumnIndex("montant"));
            id_source = cursor.getInt(cursor.getColumnIndex("id_source_revenus"));
            revenus = new Revenus(date, montant, id_source, epargneMontant, id);
            arrayList.add(revenus);

        }
        cursor.close();
        return arrayList;
    }

    /*--!

    Pour trouver la balance: Somme(Revenus.montant)-Somme(revenus.epargne_montant)+somme(epargne.montant where operation=out)-somme(depenses.montant)
     */
    public float getBalanceCourante() {
        float balance, sommeRevenus, sommeEpargne, sommeEpargneVersRevenus, sommeDepenses;
        SQLiteDatabase database = this.getReadableDatabase();
        //Somme revenus
        String sql = "SELECT SUM(montant) AS somme FROM revenus";
        Cursor cursor1 = database.rawQuery(sql, null);
        cursor1.moveToFirst();
        sommeRevenus = cursor1.getFloat(cursor1.getColumnIndex("somme"));
        cursor1.close();

        //somme epargne

        String req = "SELECT SUM(epargne_montant) as somme_epargne FROM revenus";
        Cursor cursor2 = database.rawQuery(req, null);
        cursor2.moveToFirst();
        sommeEpargne = cursor2.getFloat(cursor2.getColumnIndex("somme_epargne"));
        cursor2.close();

        //somme epargne vers revenus(retrait epargne)

        String requete = "SELECT SUM(montant) AS montant FROM epargne WHERE operation=?";
        String[] param = new String[]{"OUT"};
        Cursor cursor3 = database.rawQuery(requete, param);
        cursor3.moveToFirst();
        sommeEpargneVersRevenus = cursor3.getFloat(cursor3.getColumnIndex("montant"));
        cursor3.close();

        //somme depenses
        String r = "SELECT SUM(montant) AS montant FROM depenses";
        Cursor cursor4 = database.rawQuery(r, null);
        cursor4.moveToFirst();
        sommeDepenses = cursor4.getFloat(cursor4.getColumnIndex("montant"));
        cursor4.close();
        balance = sommeRevenus - sommeEpargne + sommeEpargneVersRevenus - sommeDepenses;
        if (balance < 0) {
            balance = 0;
        }
        return balance;
    }

    public float getEpargneSolde() {
        float solde, IN, OUT;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String req = "SELECT SUM(montant) AS montant FROM epargne WHERE operation=?";
        String[] params = new String[]{"IN"};
        Cursor cursor1 = sqLiteDatabase.rawQuery(req, params);
        cursor1.moveToFirst();
        IN = cursor1.getFloat(cursor1.getColumnIndex("montant"));

        String requete = "SELECT SUM(montant) AS somme FROM epargne WHERE operation=?";
        String[] parametres = new String[]{"OUT"};
        Cursor cursor2 = sqLiteDatabase.rawQuery(requete, parametres);
        cursor2.moveToFirst();
        OUT = cursor2.getFloat(cursor2.getColumnIndex("somme"));

        solde = IN - OUT;
        if (solde < 0) {
            solde = 0;
        }
        cursor1.close();
        cursor2.close();
        return solde;
    }
}
