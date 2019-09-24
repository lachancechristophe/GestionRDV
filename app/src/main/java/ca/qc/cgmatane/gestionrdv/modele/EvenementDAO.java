package ca.qc.cgmatane.gestionrdv.modele;

import android.database.Cursor;

import com.google.android.gms.maps.model.LatLng;

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


        int indexNom = curseur.getColumnIndex("nom");
        int indexDescription = curseur.getColumnIndex("description");
        int indexNomEndroit = curseur.getColumnIndex("nom_endroit");
        int indexMoment = curseur.getColumnIndex("moment");
        int indexLatitute = curseur.getColumnIndex("latitude");
        int indexLongitude = curseur.getColumnIndex("longitude");



        for(curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()){
            String nom = curseur.getString(indexNom);
            String description = curseur.getString(indexDescription);
            String nomEndroit = curseur.getString(indexNomEndroit);
            String moment = curseur.getString(indexMoment);
            double latitude = curseur.getDouble(indexLatitute);
            double longitude = curseur.getDouble(indexLongitude);
            LatLng position = new LatLng(latitude,longitude);
            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {

                date = formater.parse(moment);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            evenement = new Evenement(nom,description,nomEndroit,position,date);
            this.listeEvenement.add(evenement);


        }
        
        return listeEvenement;


    }


    public ArrayList<Evenement> getEvenementsParJour(Date date){
        ArrayList<Evenement> evenements = new ArrayList<>();

        //TODO: Fetch and return all evenements from DB

        return evenements;
    }

    public void ajouterEvenement(Evenement rdv){
        //TODO: Insert into DB
    }

    public void modifierEvenement(Evenement rdv){
        //TODO: Update -> DB
    }

    public void effacerEvenement(Evenement rdv){
        //TODO: Remove from DB
    }


}
