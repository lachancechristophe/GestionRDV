package ca.qc.cgmatane.gestionrdv.modele;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ca.qc.cgmatane.gestionrdv.controleur.*;

public class EvenementDAO {
    protected List<Evenement> listeEvenement;
    private ControleurSQLite accesseurBaseDeDonnees;
    private static EvenementDAO instance = null;
    private Date date = null;
    public static EvenementDAO getInstance(){
        if(instance == null) instance = new EvenementDAO();
        return instance;
    }

    public EvenementDAO(){

        this.accesseurBaseDeDonnees = ControleurSQLite.getInstance();
        listeEvenement = new ArrayList<Evenement>();
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
            int id = curseur.getInt((indexId));
            String nom = curseur.getString(indexNom);
            String description = curseur.getString(indexDescription);
            String nomEndroit = curseur.getString(indexNomEndroit);
            String moment = curseur.getString(indexMoment);
            double latitude = curseur.getDouble(indexLatitute);
            double longitude = curseur.getDouble(indexLongitude);
            LatLng position = new LatLng(latitude,longitude);
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");

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


    public List<Evenement> getEvenementsParJour(Date moment){
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String dateRecherche = dateFormat.format(moment);
        String LISTER_EVENEMENTS = "SELECT * FROM evenement WHERE moment = " + dateRecherche;
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
            double latitude = curseur.getDouble(indexLatitute);
            double longitude = curseur.getDouble(indexLongitude);
            LatLng position = new LatLng(latitude,longitude);
            evenement = new Evenement(id,nom,description,nomEndroit,position,date);
            this.listeEvenement.add(evenement);


        }

        return listeEvenement;
    }

    public List<Evenement> getEvenementsParJour(String dateRecherche){
        String LISTER_EVENEMENTS = "SELECT * FROM evenement WHERE moment = " + dateRecherche;
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
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");

        try {

            date = formater.parse(dateRecherche);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        for(curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()){
            int id = curseur.getInt(indexId);
            String nom = curseur.getString(indexNom);
            String description = curseur.getString(indexDescription);
            String nomEndroit = curseur.getString(indexNomEndroit);
            double latitude = curseur.getDouble(indexLatitute);
            double longitude = curseur.getDouble(indexLongitude);
            LatLng position = new LatLng(latitude,longitude);
            evenement = new Evenement(id,nom,description,nomEndroit,position,date);
            this.listeEvenement.add(evenement);


        }

        return listeEvenement;
    }

    public void ajouterEvenement(Evenement evenement){
        Date moment = evenement.getMoment();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String dateInserer = dateFormat.format(moment);
        Double latitude = evenement.getPointGPS().latitude;
        Double longitude = evenement.getPointGPS().longitude;
        String AJOUTER_EVENEMENT = "insert into evenement(nom, description, nom_endroit, moment, latitude, longitude) VALUES("
                + evenement.getNom() + "','" + evenement.getDescription()
                +"', '" +evenement.getNom_endroit()+"', '" + dateInserer +"', '" + latitude +"', '" + longitude + "')";
        accesseurBaseDeDonnees.getWritableDatabase().execSQL(AJOUTER_EVENEMENT);
    }

    public void modifierEvenement(Evenement evenement){
        Date moment = evenement.getMoment();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String dateModifier = dateFormat.format(moment);
        Double latitude = evenement.getPointGPS().latitude;
        Double longitude = evenement.getPointGPS().longitude;
        ContentValues cv = new ContentValues();
        cv.put("nom", evenement.getNom());
        cv.put("description", evenement.getDescription());
        cv.put("nom_endroit", evenement.getNom_endroit());
        cv.put("moment", dateModifier);
        cv.put("latitude", latitude);
        cv.put("longitude", longitude);
        accesseurBaseDeDonnees.getWritableDatabase().update("utilisateur", cv, "id=" + evenement.getId(), null  );

    }

    public Evenement chercherEvenementParIdEvenement(int id_evenement){

        for (Evenement evenementRecherche:this.listeEvenement) {
            if(evenementRecherche.getId() == id_evenement) return evenementRecherche;
        }
        return null;
    }

    public void effacerEvenement(Evenement rdv){
        //TODO: Remove from DB
    }


}
