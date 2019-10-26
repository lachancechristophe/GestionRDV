package ca.qc.cgmatane.gestionrdv.vue;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.qc.cgmatane.gestionrdv.R;
import ca.qc.cgmatane.gestionrdv.modele.Evenement;
import ca.qc.cgmatane.gestionrdv.modele.EvenementDAO;

public class ListeEvenementParJour extends AppCompatActivity {

    public static final int ACTIVITE_AJOUTER_EVENEMENT = 1;
    public static final int ACTIVITE_MODIFIER_EVENEMENT = 2;

    protected String dateChoisie;

    protected ListView vueListeEvenementsListeEvenements;
    protected TextView vueListeEvenementsTexteDateChoisie;

    protected Intent intentionNaviguerAjouterEvenement;
    protected Intent intentionNaviguerModifierEvenement;

    protected AlarmManager gestionnaireAlertes;
    protected HashMap<Integer, PendingIntent> listeIntentionsLatentesAlertes;

    protected EvenementDAO accesseurEvenement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EvenementDAO.getInstance().rafraichirBD();
        setContentView(R.layout.vue_liste_evenement_par_jour);

        this.accesseurEvenement =EvenementDAO.getInstance();

        Bundle parametres = this.getIntent().getExtras();
        dateChoisie = parametres.getString("date");

        vueListeEvenementsTexteDateChoisie = (TextView) findViewById(R.id.vue_liste_evenements_texte_date_choisie);
        vueListeEvenementsTexteDateChoisie.setText("Date choisie: " + dateChoisie);
        vueListeEvenementsListeEvenements = (ListView)findViewById(R.id.vue_liste_evenements_liste_affichage);

        intentionNaviguerAjouterEvenement = new Intent(this, CarteAjouter.class);


        Button vueCalendrierActionNaviguerAjouterEvenement;
        vueCalendrierActionNaviguerAjouterEvenement =
            (Button) findViewById(R.id.vue_liste_evenements_par_jour_bouton_ajouter);

        vueCalendrierActionNaviguerAjouterEvenement.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intentionNaviguerAjouterEvenement.putExtra("date", dateChoisie);
                    startActivityForResult(intentionNaviguerAjouterEvenement,ListeEvenementParJour.ACTIVITE_AJOUTER_EVENEMENT);
                }
            }
        );

        //vueListeEvenementsListeEvenements.setOnIt

        vueListeEvenementsListeEvenements.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                public void onItemClick(
                    AdapterView<?> parent,
                    View vue,
                    int positionDansAdapteur,
                    long positionItem)
                {

                    ListView vueListeEvenementsListeEvenementsOnClick = (ListView)vue.getParent();

                    @SuppressWarnings("unchecked")
                    HashMap<String, String> evenement =
                        (HashMap<String, String>)vueListeEvenementsListeEvenementsOnClick.getItemAtPosition((int)positionItem);
                    intentionNaviguerModifierEvenement =
                        new Intent(ListeEvenementParJour.this, ModifierEvenement.class);

                    intentionNaviguerModifierEvenement.putExtra("id_evenement", evenement.get("id"));
                    startActivityForResult(intentionNaviguerModifierEvenement,ListeEvenementParJour.ACTIVITE_MODIFIER_EVENEMENT);
                }
            }
        );

        vueListeEvenementsListeEvenements.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View vue,
                                           int positionDansAdapteur,
                                           long positionItem) {

                ListView vueListeEvenementsListeEvenementsOnLongClick = (ListView)vue.getParent();

                @SuppressWarnings("unchecked")
                HashMap<String, String> evenement =
                    (HashMap<String, String>)vueListeEvenementsListeEvenementsOnLongClick.getItemAtPosition((int)positionItem);

                accesseurEvenement.effacerEvenement(Integer.valueOf(evenement.get("id")));
                finish();
                startActivity(getIntent());
                return true;
            }
        });


        vueListeEvenementsTexteDateChoisie.setText("Evenements le " + dateChoisie);
    }

    @Override
    public void onResume() {
        super.onResume();

        afficherEvenementsDuJour(dateChoisie);

        annulerToutesAlarmes();
        definirToutesAlarmes();
    }

    private void definirToutesAlarmes() {
        if(listeIntentionsLatentesAlertes == null) listeIntentionsLatentesAlertes = new HashMap<>();

        for(Evenement event : EvenementDAO.getInstance().getTousEvenements()) {
            if(event.getMoment().before(new Date())) continue;

            Intent intentionAlerte = new Intent(this, Alerte.class);

            intentionAlerte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentionAlerte.putExtra("id", event.getId());
            int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
            PendingIntent intentionLatente = PendingIntent
                    .getActivity(this, uniqueInt, intentionAlerte, 0);
            listeIntentionsLatentesAlertes.put(event.getId(), intentionLatente);
            gestionnaireAlertes = (AlarmManager)getSystemService(ALARM_SERVICE);
            gestionnaireAlertes.setExact(AlarmManager.RTC_WAKEUP,
                    event.getMoment().getTime(), intentionLatente);
        }
    }

    private void annulerToutesAlarmes() {
        if(listeIntentionsLatentesAlertes == null) return;
        for(Map.Entry<Integer, PendingIntent> paire : listeIntentionsLatentesAlertes.entrySet()){
            PendingIntent intentionLatente = paire.getValue();

            gestionnaireAlertes.cancel(intentionLatente);
            intentionLatente.cancel();
        }
        listeIntentionsLatentesAlertes.clear();

    }

    protected void afficherEvenementsDuJour(String jourChoisi){
        EvenementDAO.getInstance().rafraichirBD(this);
        //TODO Get les events et les transformer en arraylist de hashmap string string
        List<HashMap<String,String>> listeEvenements = EvenementDAO.getInstance().recupererListeEvenementParJourPourAdapteur(jourChoisi, this); //accesseurEvenement.getTousEvenements();

        SimpleAdapter adapteurVueBibliothequeListeEvenements = new SimpleAdapter(
                this,
                listeEvenements,
                android.R.layout.two_line_list_item,
                new String[] {"nom", "description"}, // l'indice utilisé dans les hashmap
                new int[] {android.R.id.text1, android.R.id.text2});

        vueListeEvenementsListeEvenements.setAdapter(adapteurVueBibliothequeListeEvenements);
    }
}
