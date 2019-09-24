package ca.qc.cgmatane.gestionrdv.vue;

import android.os.Bundle;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;

import ca.qc.cgmatane.gestionrdv.R;

public class ListeEvenementParJour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_evenement_par_jour);

        Bundle parametres = this.getIntent().getExtras();
        String dateChoisit = (String) parametres.get("date");

        TextView titre = (TextView) findViewById(R.id.textView);

        titre.setText("Evenements le "+dateChoisit);
    }
}
