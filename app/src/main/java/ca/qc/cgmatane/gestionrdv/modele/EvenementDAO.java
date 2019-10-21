package ca.qc.cgmatane.gestionrdv.modele;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import ca.qc.cgmatane.gestionrdv.controleur.*;

public class EvenementDAO {
    protected List<Evenement> listeEvenement;
    private ControleurSQLite accesseurBaseDeDonnees;
    private static EvenementDAO instance = null;
    //private Date date = null;
    public static EvenementDAO getInstance(){
        if(instance == null) instance = new EvenementDAO();
        return instance;
    }

    public EvenementDAO(){

        accesseurBaseDeDonnees = ControleurSQLite.getInstance();
        listeEvenement = new ArrayList<Evenement>();
    }

    public void rafraichirBD(Context context){
        this.accesseurBaseDeDonnees = ControleurSQLite.getInstance(context);
    }

    public Integer getNbEvenements(){
        String COMPTER_EVENEMENTS = "SELECT COUNT() FROM evenement";

        accesseurBaseDeDonnees = ControleurSQLite.getInstance();
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(COMPTER_EVENEMENTS, null);
        for(curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()){
            return curseur.getInt(0);
        }

        return 0;
    }

    public List<Evenement> getTousEvenements(){
        //ArrayList<Evenement> tousEvenements = new ArrayList<>();

        String LISTER_EVENEMENTS = "SELECT * FROM evenement";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_EVENEMENTS, null);
        this.listeEvenement.clear();
        Evenement evenement;

        int indexId = curseur.getColumnIndex("id");
        int indexNom = curseur.getColumnIndex("nom");
        int indexDescription = curseur.getColumnIndex("description");
        int indexNomEndroit = curseur.getColumnIndex("nom_endroit");
        int indexMoment = curseur.getColumnIndex("moment");
        int indexLatitute = curseur.getColumnIndex("latitude");
        int indexLongitude = curseur.getColumnIndex("longitude");

        for(curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()){
            int id = curseur.getInt(indexId);
            String nom = curseur.getString(indexNom);
            String description = curseur.getString(indexDescription);
            String nomEndroit = curseur.getString(indexNomEndroit);
            String moment = curseur.getString(indexMoment);
            double latitude = curseur.getDouble(indexLatitute);
            double longitude = curseur.getDouble(indexLongitude);
            LatLng position = new LatLng(latitude,longitude);
            SimpleDateFormat formater = new SimpleDateFormat("d/M/yyyy HH:mm");
            Date date = null;
            try {
                date = formater.parse(moment);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            evenement = new Evenement(id,nom,description,nomEndroit,position,date);
            this.listeEvenement.add(evenement);


        }

        return listeEvenement;
    }

    public List<Evenement> getEvenementsParJour(String dateRecherche, Context context){
        rafraichirBD(context);
        String lendemain = recupererlendemain(dateRecherche);
        //String LISTER_EVENEMENTS = "SELECT * FROM evenement WHERE moment LIKE '" + dateRecherche + "%' OR moment LIKE '" + lendemain + "%'";
        String LISTER_EVENEMENTS = "SELECT * FROM evenement WHERE moment >= '" + dateRecherche + "' AND moment <= '"+lendemain+"'";
        //String LISTER_EVENEMENTS = "SELECT * FROM evenement";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_EVENEMENTS, null);

        this.listeEvenement.clear();
        Evenement evenement;

        int indexId = curseur.getColumnIndex("id");
        int indexNom = curseur.getColumnIndex("nom");
        int indexDescription = curseur.getColumnIndex("description");
        int indexNomEndroit = curseur.getColumnIndex("nom_endroit");
        int indexMoment = curseur.getColumnIndex("moment");
        int indexLatitute = curseur.getColumnIndex("latitude");
        int indexLongitude = curseur.getColumnIndex("longitude");

        for(curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()){

            int id = curseur.getInt(indexId);
            String nom = curseur.getString(indexNom);
            String description = curseur.getString(indexDescription);
            String nomEndroit = curseur.getString(indexNomEndroit);
            String moment = curseur.getString(indexMoment);
            moment = recupererDatePourCalendarView(moment);
            Date date = new Date(moment);
            double latitude = curseur.getDouble(indexLatitute);
            double longitude = curseur.getDouble(indexLongitude);
            LatLng position = new LatLng(latitude,longitude);
            evenement = new Evenement(id,nom,description,nomEndroit,position,date);

            this.listeEvenement.add(evenement);


        }

        return listeEvenement;
    }

    public void ajouterEvenement(Evenement evenement, Context context){

        Date moment = evenement.getMoment();
        DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy HH:mm");
        String date = dateFormat.format(moment);
        Double latitude = evenement.getPointGPS().latitude;
        Double longitude = evenement.getPointGPS().longitude;
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("insert into evenement(nom, description, nom_endroit, moment, latitude, longitude) VALUES(?,?,?,?,?,?)");
        query.bindString(1, evenement.getNom());
        query.bindString(2, evenement.getDescription());
        query.bindString(3, evenement.getNom_endroit());
        query.bindString(4, date);
        query.bindDouble(5, latitude);
        query.bindDouble(6, longitude);

        query.execute();



    }

    public void modifierEvenement(Evenement evenement){


        Date moment = evenement.getMoment();
        DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy HH:mm");
        String dateModifier = dateFormat.format(moment);
        Double latitude = evenement.getPointGPS().latitude;
        Double longitude = evenement.getPointGPS().longitude;
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("UPDATE evenement SET nom = ?, description = ?, nom_endroit = ?, moment = ?, latitude  = ?, longitude  = ? WHERE id = ? ");
        query.bindString(1, evenement.getNom());
        query.bindString(2, evenement.getDescription());
        query.bindString(3, evenement.getNom_endroit());
        query.bindString(4, dateModifier);
        query.bindDouble(5, latitude);
        query.bindDouble(6, longitude);
        query.bindDouble(7, evenement.getId());


        query.execute();
        System.out.println(query.toString()+dateModifier);
        /*ContentValues cv = new ContentValues();
        cv.put("nom", evenement.getNom());
        cv.put("description", evenement.getDescription());
        cv.put("nom_endroit", evenement.getNom_endroit());
        cv.put("moment", dateModifier);
        cv.put("latitude", latitude);
        cv.put("longitude", longitude);
        accesseurBaseDeDonnees.getWritableDatabase().update("evenement", cv, "id=" + evenement.getId(), null  );*/
    }
    public List<HashMap<String,String>> recupererListeEvenementPourAdapteur(){
        List<HashMap<String,String>> listeEvenementPourAdapteur = new ArrayList<HashMap<String, String>>();
        getTousEvenements();

        for(Evenement evenement:listeEvenement){

            listeEvenementPourAdapteur.add(evenement.obtenirEvenementPourAdapteur());
        }
        return listeEvenementPourAdapteur;
    }

    public List<HashMap<String,String>> recupererListeEvenementParJourPourAdapteur(String jour, Context context){

        List<HashMap<String,String>> listeEvenementPourAdapteur = new ArrayList<HashMap<String, String>>();
        List<Evenement> listeEvenementDuJour = getEvenementsParJour(jour, context);

        for(Evenement evenement : listeEvenementDuJour){
            listeEvenementPourAdapteur.add(evenement.obtenirEvenementPourAdapteur());

        }
        return listeEvenementPourAdapteur;
    }

    public Evenement chercherEvenementParIdEvenement(int id_evenement){

        for (Evenement evenementRecherche:this.listeEvenement) {
            if(evenementRecherche.getId() == id_evenement) return evenementRecherche;
        }
        return null;
    }

    public void effacerEvenement(Integer id_evenement){
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("DELETE FROM evenement WHERE id = ? ");
        query.bindDouble(1, id_evenement);

        query.execute();
        System.out.println("Deleted id: " + id_evenement);
    }

    private String recupererDatePourCalendarView(String moment){

        final String FORMAT_FR = "dd/MM/yyyy HH:mm";
        final String FORMAT_US = "yyyy/MM/dd HH:mm";

        String ancienneDateString = moment;
        String nouvelleDateString;

        SimpleDateFormat formatNouvelleDate = new SimpleDateFormat(FORMAT_FR);
        Date date = null;
        try {
            date = formatNouvelleDate.parse(ancienneDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatNouvelleDate.applyPattern(FORMAT_US);
        nouvelleDateString = formatNouvelleDate.format(date);

        return nouvelleDateString;
    }

    private String recupererlendemain(String date){
        String FORMAT= "d/M/yyyy";

        SimpleDateFormat formatDate = new SimpleDateFormat(FORMAT);
        Date date1 = null;
        try {
            date1 = formatDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        Date lendemain;
        Calendar c = Calendar.getInstance();
        c.setTime(date1);


        c.add(Calendar.DATE, 1);
        lendemain = c.getTime();


        String strDate = formatDate.format(lendemain);

        return strDate;
    }


}
