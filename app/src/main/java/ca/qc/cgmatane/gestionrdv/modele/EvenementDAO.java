package ca.qc.cgmatane.gestionrdv.modele;

import java.util.ArrayList;
import java.util.Date;

public class EvenementDAO {
    public static EvenementDAO instance;
    public static EvenementDAO getInstance(){
        if(instance == null) instance = new EvenementDAO();
        return instance;
    }

    public EvenementDAO(){

    }

    public ArrayList<Evenement> getTousEvenements(){
        ArrayList<Evenement> tousEvenements = new ArrayList<>();

        //TODO: Fetch and return all evenements from DB

        return tousEvenements;
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
