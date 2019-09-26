package ca.qc.cgmatane.gestionrdv.modele;

import android.database.Cursor;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    private void rafraichirBD(){
        this.accesseurBaseDeDonnees = ControleurSQLite.getInstance();
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

    public List<Evenement> getEvenementsParJour(String dateRecherche){
        String LISTER_EVENEMENTS = "SELECT * FROM evenement WHERE moment = " + dateRecherche;
        rafraichirBD();
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
    public List<Evenement> getEvenementsParJour(Date moment){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
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
            evenement = new Evenement(id,nom,description,nomEndroit,position,moment);
            this.listeEvenement.add(evenement);


        }

        return listeEvenement;
    }

    public void ajouterEvenement(Evenement evenement){
        Date moment = evenement.getMoment();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String date = dateFormat.format(moment);
        Double latitude = evenement.getPointGPS().latitude;
        Double longitude = evenement.getPointGPS().longitude;
        String AJOUTER_EVENEMENT = "insert into evenement(nom, description, nom_endroit, moment, latitude, longitude) VALUES("
                + evenement.getNom() + "','" + evenement.getDescription()
                +"', '" +evenement.getNom_endroit()+"', '" + moment +"', '" + latitude +"', '" + longitude + "')";
        accesseurBaseDeDonnees.getWritableDatabase().execSQL(AJOUTER_EVENEMENT);
    }

    public List<HashMap<String,String>> recupererListeEvenementPourAdapteur(){
        List<HashMap<String,String>> listeEvenementPourAdapteur = new ArrayList<HashMap<String, String>>();
        getTousEvenements();

        for(Evenement evenement:listeEvenement){

            listeEvenementPourAdapteur.add(evenement.obtenirEvenementPourAdapteur());


        }
        return listeEvenementPourAdapteur;
    }

    public List<HashMap<String,String>> recupererListeEvenementParJourPourAdapteur(String jour){
        //System.out.println("HEEEEEEEEEEEEEYAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");



        List<HashMap<String,String>> listeEvenementPourAdapteur = new ArrayList<HashMap<String, String>>();
        getEvenementsParJour(jour);

        for(Evenement evenement:listeEvenement){

            listeEvenementPourAdapteur.add(evenement.obtenirEvenementPourAdapteur());


        }
        return listeEvenementPourAdapteur;
    }

    public void modifierEvenement(Evenement rdv){
        //TODO: Update -> DB
    }

    public void effacerEvenement(Evenement rdv){
        //TODO: Remove from DB
    }


}
