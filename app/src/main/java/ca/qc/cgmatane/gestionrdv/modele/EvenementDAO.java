package ca.qc.cgmatane.gestionrdv.modele;

import java.util.ArrayList;

public class EvenementDAO {
    public EvenementDAO instance;
    public EvenementDAO getInstance(){
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
