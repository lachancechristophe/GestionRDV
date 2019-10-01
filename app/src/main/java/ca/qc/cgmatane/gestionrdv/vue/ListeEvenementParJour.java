package ca.qc.cgmatane.gestionrdv.vue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ca.qc.cgmatane.gestionrdv.R;
import ca.qc.cgmatane.gestionrdv.modele.EvenementDAO;

public class ListeEvenementParJour extends AppCompatActivity {

    public static final int ACTIVITE_AJOUTER_EVENEMENT = 1;
    public static final int ACTIVITE_MODIFIER_EVENEMENT = 2;

    public static EvenementDAO baseDeDonnee;

    protected ListView vueListeEvenementsListeEvenements;
    protected TextView vueListeEvenementsTexteDateChoisie;

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
        String dateChoisie = (String) parametres.get("date");

        vueListeEvenementsTexteDateChoisie = (TextView) findViewById(R.id.vue_liste_evenements_texte_heure_choisie);
        vueListeEvenementsListeEvenements = (ListView)findViewById(R.id.vue_liste_evenements_liste_affichage);

        intentionNaviguerAjouterEvenement = new Intent(this,AjouterEvenement.class);


        Button vueCalendrierActionNaviguerAjouterEvenement;
        vueCalendrierActionNaviguerAjouterEvenement =
            (Button) findViewById(R.id.vue_liste_evenements_par_jour_bouton_ajouter);

        vueCalendrierActionNaviguerAjouterEvenement.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(intentionNaviguerAjouterEvenement,ListeEvenementParJour.ACTIVITE_AJOUTER_EVENEMENT);
                }
            }
        );

        vueListeEvenementsTexteDateChoisie.setText("Evenements le " + dateChoisie);
        // TODO
        //afficherEvenementsDuJour();
    }

    protected void afficherEvenementsDuJour(Date jourChoisi){

        //TODO Get les events et les transformer en arraylist de hashmap string string
        ArrayList<HashMap<String,String>> listeEvenements = new ArrayList<HashMap<String,String>>(); //accesseurEvenement.getTousEvenements();

        SimpleAdapter adapteurVueBibliothequeListeEvenementss = new SimpleAdapter(
                this,
                listeEvenements,
                android.R.layout.two_line_list_item,
                // http://stackoverflow.com/questions/3663745/what-is-android-r-layout-simple-list-item-1
                new String[] {"TitreAuteur", "Lire avant"}, // l'indice utilisé dans les hashmap
                new int[] {android.R.id.text1, android.R.id.text2});

        vueListeEvenementsListeEvenements.setAdapter(adapteurVueBibliothequeListeEvenementss);
    }
}
