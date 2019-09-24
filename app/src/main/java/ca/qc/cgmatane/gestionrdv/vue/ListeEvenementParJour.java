package ca.qc.cgmatane.gestionrdv.vue;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

import ca.qc.cgmatane.gestionrdv.R;
import ca.qc.cgmatane.gestionrdv.modele.EvenementDAO;

public class ListeEvenementParJour extends AppCompatActivity {

    public static final int ACTIVITE_AJOUTER_EVENEMENT = 1;
    public static final int ACTIVITE_MODIFIER_EVENEMENT = 2;

    public static EvenementDAO baseDeDonnee;

    protected ListView vueBibliothequeListeEvenements;

    protected Intent intentionNaviguerAjouterEvenement;
    protected Intent intentionNaviguerModifierEvenement;
    protected Intent intentionNaviguerAlerte;

    protected EvenementDAO accesseurEvenement;

    /* TODO Gérer les alarmes
    protected AlarmManager gestionnaireAlertes;
    protected HashMap<Integer, PendingIntent> listeIntentionsLatentesAlertes;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_evenement_par_jour);

        Bundle parametres = this.getIntent().getExtras();
        String dateChoisit = (String) parametres.get("date");

        TextView titre = (TextView) findViewById(R.id.vue_liste_evenements_texte_heure_choisie);

        titre.setText("Evenements le "+dateChoisit);
    }
}
