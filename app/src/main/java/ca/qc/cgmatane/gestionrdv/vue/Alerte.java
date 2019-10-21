package ca.qc.cgmatane.gestionrdv.vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ca.qc.cgmatane.gestionrdv.R;
import ca.qc.cgmatane.gestionrdv.modele.Evenement;
import ca.qc.cgmatane.gestionrdv.modele.EvenementDAO;

public class Alerte extends AppCompatActivity {

    protected int id_evenement;

    protected Date dateEcheance;

    protected EditText vueAlerteChampNom;
    protected EditText vueAlerteChampDescription;
    protected EditText vueAlerteChampEcheance;
    protected TextView vueAlerteChampEndroit;

    protected EvenementDAO accesseurEvenement;
    protected Evenement evenement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_alerte);

        dateEcheance = new Date();

        Bundle parametres = this.getIntent().getExtras();
        id_evenement = parametres.getInt("id");

        Button vueAlerteActionValider = (Button)findViewById(R.id.vue_alerte_action_valider);
        vueAlerteActionValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marquerFait();
                naviguerRetourListeEvenements();
            }
        });

        Button vueAlerteActionRetour = (Button)findViewById(R.id.vue_alerte_action_retour);
        vueAlerteActionRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naviguerRetourListeEvenements();
            }
        });


        vueAlerteChampNom = (EditText)findViewById(R.id.vue_alerte_champ_nom);
        vueAlerteChampDescription = (EditText)findViewById(R.id.vue_alerte_champ_description);
        vueAlerteChampEndroit = (TextView)findViewById(R.id.vue_alerte_champ_nom_endroit);
        vueAlerteChampEcheance = (EditText)findViewById(R.id.vue_alerte_champ_echeance);

        accesseurEvenement = EvenementDAO.getInstance();
        evenement = accesseurEvenement.chercherEvenementParIdEvenement(id_evenement);

        vueAlerteChampNom.setText(evenement.getNom());
        vueAlerteChampDescription.setText(evenement.getDescription());
        vueAlerteChampEndroit.setText(evenement.getNom_endroit());
        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy hh:mm a");
        vueAlerteChampEcheance.setText(format.format(evenement.getMoment()));

    }

    private void naviguerRetourListeEvenements() {
        this.finish();
    }

    private void marquerFait() {
        accesseurEvenement.effacerEvenement(evenement);
    }
}
