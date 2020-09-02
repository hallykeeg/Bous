package com.example.bous;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    private static DatabaseManager databaseManager = null;
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

    //singleton pattern

    public static synchronized DatabaseManager getDatabaseManager(@Nullable Context context) {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager(context);
        }
        return databaseManager;
    }

    private DatabaseManager(@Nullable Context context) {
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
        float solde = getEpargneSolde();
        if (epargne.getMontant() > solde) {
            state = 404;
        } else {
            ContentValues cv = new ContentValues();
            cv.put("date", epargne.getDate());
            cv.put("montant", epargne.getMontant());
            state = db.insert("epargne", null, cv);
        }

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
        //EVITER LA DUPLICATION D'UN MEME OBJET
        String sql = "SELECT COUNT(id_objet_depenses) AS card FROM objet_depenses WHERE nom=?";
        String[] params = new String[]{nom};
        final Cursor cursor = db.rawQuery(sql, params);
        cursor.moveToFirst();
        int card = cursor.getInt(cursor.getColumnIndex("card"));
        if (card == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nom", nom);
            state = db.insert("objet_depenses", null, contentValues);
        } else {
            state = 409;
        }

        return state;
    }

    //Fonction qui retourne un arraylist d'objets dettes
    public ArrayList<CustomModel> selectDettes() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CustomModel> arrayList = new ArrayList<>();
        int id;
        String date, individu;
        float montant;
        String sql = "SELECT * FROM dettes WHERE date_remboursement IS NULL ORDER BY date DESC";
        final Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            date = cursor.getString(cursor.getColumnIndex("date"));
            individu = cursor.getString(cursor.getColumnIndex("individu"));
            montant = cursor.getFloat(cursor.getColumnIndex("montant"));
            arrayList.add(new CustomModel(id, date, individu, montant));
        }
        cursor.close();
        return arrayList;

    }

    public ArrayList<CustomModel> selectCreances() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CustomModel> arrayListCustom = new ArrayList<>();
        int id;
        String date, individu;
        float montant;

        String sql = "SELECT * FROM creances WHERE date_remboursement IS NULL ORDER BY date DESC";
        final Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            date = cursor.getString(cursor.getColumnIndex("date"));
            individu = cursor.getString(cursor.getColumnIndex("individu"));
            montant = cursor.getFloat(cursor.getColumnIndex("montant"));
            arrayListCustom.add(new CustomModel(id, date, individu, montant));
        }
        cursor.close();
        return arrayListCustom;
    }

    public ArrayList<CustomModel> selectRevenusByMonth(String debut, String fin) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CustomModel> arrayList = new ArrayList<>();

        int id, id_source;
        float montant;
        float epargneMontant;
        String date;
        String[] params = new String[]{debut, fin};
        String sql = "SELECT * FROM revenus WHERE date BETWEEN ? AND ? ORDER BY date DESC";
        final Cursor cursor = db.rawQuery(sql, params);
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            date = cursor.getString(cursor.getColumnIndex("date"));
            montant = cursor.getFloat(cursor.getColumnIndex("montant"));
            id_source = cursor.getInt(cursor.getColumnIndex("id_source_revenus"));
            //trouver le nom associe a l'id_source
            String requete = "SELECT nom FROM source_revenus WHERE id_source_revenus=?";
            String[] clef = new String[]{String.valueOf(id_source)};
            Cursor cursorFindSource = db.rawQuery(requete, clef);
            cursorFindSource.moveToFirst();
            String source = cursorFindSource.getString(cursorFindSource.getColumnIndex("nom"));
            cursorFindSource.close();

            arrayList.add(new CustomModel(id, date, source, montant));

        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<CustomModel> selectDepensesByMonth(String debut, String fin) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CustomModel> arrayList = new ArrayList<>();
        int id, id_objet;
        float montant;
        String date;
        String[] params = new String[]{debut, fin};
        String sql = "SELECT * FROM depenses WHERE date BETWEEN ? AND ? ORDER BY date DESC";
        final Cursor cursor = db.rawQuery(sql, params);
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            date = cursor.getString(cursor.getColumnIndex("date"));
            montant = cursor.getFloat(cursor.getColumnIndex("montant"));
            id_objet = cursor.getInt(cursor.getColumnIndex("id_objet_depenses"));
            //trouver le nom associe a l'id
            String requete = "SELECT nom FROM objet_depenses WHERE id_objet_depenses=?";
            String[] clef = new String[]{String.valueOf(id_objet)};
            Cursor cursorFindObject = db.rawQuery(requete, clef);
            cursorFindObject.moveToFirst();
            String objetDepenses = cursorFindObject.getString(cursorFindObject.getColumnIndex("nom"));
            cursorFindObject.close();
            arrayList.add(new CustomModel(id, date, objetDepenses, montant));
        }
        cursor.close();
        return arrayList;
    }

    /*--!

    Pour trouver la balance: Somme(Revenus.montant)-Somme(revenus.epargne_montant)+somme(epargne.montant where operation=out)-somme(depenses.montant)
     */
    public float getBalanceCourante() {
        float balance = 0, sommeRevenus = 0, sommeEpargne = 0, sommeEpargneVersRevenus = 0, sommeDepenses = 0, sommeCreancesOut = 0, sommeCreancesIn = 0, sommeDettesOut = 0, sommeDettesIn = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        //Somme revenus
        String sql = "SELECT SUM(montant) AS somme FROM revenus";
        Cursor cursor1 = database.rawQuery(sql, null);

        if (cursor1.moveToFirst()) {
            sommeRevenus = cursor1.getFloat(cursor1.getColumnIndex("somme"));
        }
        cursor1.close();

        //somme epargne

        String req = "SELECT SUM(epargne_montant) as somme_epargne FROM revenus";
        Cursor cursor2 = database.rawQuery(req, null);
        if (cursor2.moveToFirst()) {
            sommeEpargne = cursor2.getFloat(cursor2.getColumnIndex("somme_epargne"));
        }
        cursor2.close();

        //somme epargne vers revenus(retrait epargne)

        String requete = "SELECT SUM(montant) AS montant FROM epargne";

        Cursor cursor3 = database.rawQuery(requete, null);
        if (cursor3.moveToFirst()) {
            sommeEpargneVersRevenus = cursor3.getFloat(cursor3.getColumnIndex("montant"));
        }
        cursor3.close();

        //somme depenses
        String r = "SELECT SUM(montant) AS montant FROM depenses";
        Cursor cursor4 = database.rawQuery(r, null);
        if (cursor4.moveToFirst()) {
            sommeDepenses = cursor4.getFloat(cursor4.getColumnIndex("montant"));
        }
        cursor4.close();
        //somme creance OUT-> sortie d;argent
        String requeteCreance = "SELECT SUM(montant) as montant FROM creances WHERE date_remboursement IS NULL";
        Cursor cursorCreanceOut = database.rawQuery(requeteCreance, null);
        if (cursorCreanceOut.moveToFirst()) {
            sommeCreancesOut = cursorCreanceOut.getFloat(cursorCreanceOut.getColumnIndex("montant"));
        }
        cursorCreanceOut.close();

        //somme creance IN->Rentree d'argent

        requeteCreance = "SELECT SUM(montant) as montant FROM creances WHERE date_remboursement IS NOT NULL";
        Cursor cursorCreanceIn = database.rawQuery(requeteCreance, null);

        if (cursorCreanceIn.moveToFirst()) {
            sommeCreancesIn = cursorCreanceIn.getFloat(cursorCreanceIn.getColumnIndex("montant"));
        }
        cursorCreanceIn.close();

        //somme dette OUT: sortie d'argent(m peye det mwen)
        String requeteDettes = "SELECT SUM(montant) AS montant FROM dettes WHERE date_remboursement IS NOT NULL";
        Cursor cursorDettesOut = database.rawQuery(requeteDettes, null);
        if (cursorDettesOut.moveToFirst()) {
            sommeDettesOut = cursorDettesOut.getFloat(cursorDettesOut.getColumnIndex("montant"));
        }
        cursorDettesOut.close();

        //somme dette IN:rentree d'argent (kob m prete)
        requeteDettes = "SELECT SUM(montant) AS montant FROM dettes WHERE date_remboursement IS NULL";
        Cursor cursorDettesIn = database.rawQuery(requeteDettes, null);
        if (cursorDettesIn.moveToFirst()) {
            sommeDettesIn = cursorDettesIn.getFloat(cursorDettesIn.getColumnIndex("montant"));
        }
        cursorDettesIn.close();

        balance = sommeRevenus - sommeEpargne + sommeEpargneVersRevenus - sommeDepenses + sommeDettesIn - sommeDettesOut + sommeCreancesIn - sommeCreancesOut;
        if (balance < 0) {
            balance = 0;
        }
        return balance;
    }

    public float getEpargneSolde() {
        float solde = 0, IN = 0, OUT = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        String req = "SELECT SUM(epargne_montant) as somme_epargne FROM revenus";
        Cursor cursor2 = database.rawQuery(req, null);
        if (cursor2.moveToFirst()) {
            IN = cursor2.getFloat(cursor2.getColumnIndex("somme_epargne"));
        }
        cursor2.close();


        String requete = "SELECT SUM(montant) AS somme FROM epargne";

        Cursor cursorOut = database.rawQuery(requete, null);
        if (cursorOut.moveToFirst()) {
            OUT = cursorOut.getFloat(cursorOut.getColumnIndex("somme"));
        }
        solde = IN - OUT;
        if (solde < 0) {
            solde = 0;
        }
        cursorOut.close();
        cursor2.close();
        return solde;
    }

    public long DettesPaid(int id, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id=?";
        ContentValues cv = new ContentValues();
        cv.put("date_remboursement", date);
        String[] whereParams = new String[]{String.valueOf(id)};
        long resultat = db.update("dettes", cv, whereClause, whereParams);
        return resultat;
    }

    public long CreancesPaid(int id, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id=?";
        ContentValues cv = new ContentValues();
        cv.put("date_remboursement", date);
        String[] whereParams = new String[]{String.valueOf(id)};
        long resultat = db.update("creances", cv, whereClause, whereParams);
        return resultat;
    }

    //Selectionner les objets de depenses
    public ArrayList<ObjetDepenses> selectObjetDepenses() {
        ArrayList<ObjetDepenses> arrayList = new ArrayList<>();
        int id;
        String nom;
        SQLiteDatabase database = this.getReadableDatabase();
        String requeteObjetDepenses = "SELECT id_objet_depenses AS id, nom FROM objet_depenses ORDER BY nom";
        Cursor cursorObjetDepenses = database.rawQuery(requeteObjetDepenses, null);
        while (cursorObjetDepenses.moveToNext()) {
            id = cursorObjetDepenses.getInt(cursorObjetDepenses.getColumnIndex("id"));
            nom = cursorObjetDepenses.getString(cursorObjetDepenses.getColumnIndex("nom"));
            ObjetDepenses objetDepenses = new ObjetDepenses(id, nom);
            arrayList.add(objetDepenses);
        }
        cursorObjetDepenses.close();
        return arrayList;
    }

    //selection des sources de revenus
    public ArrayList<SourceRevenus> selectSourceRevenus() {
        ArrayList<SourceRevenus> arrayList = new ArrayList<>();
        int id;
        String nom;
        SQLiteDatabase database = this.getReadableDatabase();
        String requeteSourceRevenus = "SELECT id_source_revenus AS id,nom FROM source_revenus ORDER BY nom";
        Cursor cursorSourceRevenus = database.rawQuery(requeteSourceRevenus, null);
        while (cursorSourceRevenus.moveToNext()) {
            id = cursorSourceRevenus.getInt(cursorSourceRevenus.getColumnIndex("id"));
            nom = cursorSourceRevenus.getString(cursorSourceRevenus.getColumnIndex("nom"));
            SourceRevenus sourceRevenus = new SourceRevenus(id, nom);
            arrayList.add(sourceRevenus);
        }
        cursorSourceRevenus.close();
        return arrayList;
    }

    public String selectObjetDepenseById(String id) {
        SQLiteDatabase database = this.getReadableDatabase();
        String requete = "SELECT nom FROM objet_depenses WHERE id_objet_depenses=?";
        String[] params = new String[]{id};
        Cursor cursor = database.rawQuery(requete, params);
        cursor.moveToFirst();
        String nom = cursor.getString(cursor.getColumnIndex("nom"));
        cursor.close();
        return nom;
    }

    public String selectSourceRevenusById(String id) {
        SQLiteDatabase database = this.getReadableDatabase();
        String requete = "SELECT nom FROM source_revenus WHERE id_source_revenus=?";
        String[] params = new String[]{id};
        Cursor cursor = database.rawQuery(requete, params);
        cursor.moveToFirst();
        String nom = cursor.getString(cursor.getColumnIndex("nom"));
        cursor.close();
        return nom;
    }
}
