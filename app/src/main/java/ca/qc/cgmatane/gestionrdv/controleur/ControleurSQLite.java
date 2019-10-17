package ca.qc.cgmatane.gestionrdv.controleur;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControleurSQLite extends SQLiteOpenHelper {
    private static ControleurSQLite instance;

    public static ControleurSQLite getInstance(Context contexte){
        instance = new ControleurSQLite(contexte);
        return instance;
    }
    public static ControleurSQLite getInstance()
    {
        return instance;
    }
    public ControleurSQLite(Context contexte){
        super(contexte, "gestionrdv", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table evenement " +
            "(id INTEGER PRIMARY KEY, nom TEXT, description TEXT, nom_endroit TEXT, moment TEXT, latitude DOUBLE, longitude DOUBLE);";
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        String DELETE = "delete from evenement where 1 = 1";
        db.execSQL(DELETE);

        String INSERT_1 = "insert into evenement(nom, description, nom_endroit, moment, latitude, longitude) VALUES('Reunion', 'Reunion pour projet X', 'Cegep Matane', '3/10/2019 12:40', 48.840677, -67.497457)";
        String INSERT_2 = "insert into evenement(nom, description, nom_endroit, moment, latitude, longitude) VALUES('Rendez-Vous', 'Rendez-Vous pour abandonner le Cegep', 'Cegep Matane', '24/11/2019 12:00', 48.840677, -67.497457)";
        String INSERT_3 = "insert into evenement(nom, description, nom_endroit, moment, latitude, longitude) VALUES('Competition', 'Tournoi national du lancer de patate', 'Cegep Matane', '4/4/2020 12:00', 48.840677, -67.497457)";
        String INSERT_4 = "insert into evenement(nom, description, nom_endroit, moment, latitude, longitude) VALUES('Nomwqeasfsadgashedfg','adfgasdfgadfg', 'Endroitadfadfgasdfgasdfgasdfgasfgasdfgasfgasfgdgadgsdfgsgadfg', '16/10/2019 12:20', 48.846482782542125, -67.50114396214485)";


        db.execSQL(INSERT_1);
        db.execSQL(INSERT_2);
        db.execSQL(INSERT_3);
        db.execSQL(INSERT_4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }

}







