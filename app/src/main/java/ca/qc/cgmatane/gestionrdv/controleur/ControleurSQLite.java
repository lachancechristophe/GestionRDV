package ca.qc.cgmatane.gestionrdv.controleur;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControleurSQLite extends SQLiteOpenHelper {
    private static ControleurSQLite instance;

    public static ControleurSQLite getInstance(Context contexte){
        if(instance == null) instance = new ControleurSQLite(contexte);
        return instance;
    }
    public static ControleurSQLite getInstance()
    {
        return instance;
    }
    public ControleurSQLite(Context contexte){
        super(contexte, "GestionRDV", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table evenement " +
            "(id INTEGER PRIMARY KEY, nom TEXT, description TEXT, nom_endroit TEXT, moment TEXT, latitude DOUBLE, longitude DOUBLE);";
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }

}







