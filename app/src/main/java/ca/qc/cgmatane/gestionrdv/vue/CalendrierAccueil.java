package ca.qc.cgmatane.gestionrdv.vue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import ca.qc.cgmatane.gestionrdv.R;

public class CalendrierAccueil extends AppCompatActivity {
    static final public int ACTIVITE_LISTE_EVENEMENT_PAR_JOUR = 1;
    static final public String DATE_FORMAT = "yyyy/M/d";


    private String dateChoisit;
    private CalendarView vueCalendrierActionChoixJour;

    protected Intent intentionNaviguerListeEvenementParJour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_calendrier_accueil);

        vueCalendrierActionChoixJour =(CalendarView) findViewById(R.id.vue_calendrier_action_choix_jour);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        dateChoisit= formatter.format(date);

        vueCalendrierActionChoixJour.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                i1++;
                dateChoisit = ""+i+"/"+i1+"/"+i2;
            }
        });

        intentionNaviguerListeEvenementParJour = new Intent(this,ListeEvenementParJour.class);

        Button vueCalendrierActionNaviguerListeEvenementParJour;
        vueCalendrierActionNaviguerListeEvenementParJour =
            (Button) findViewById(R.id.vue_calendrier_action_naviguer_liste_evenement_par_jour);

        vueCalendrierActionNaviguerListeEvenementParJour.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intentionNaviguerListeEvenementParJour.putExtra("date",dateChoisit);
                    startActivityForResult(intentionNaviguerListeEvenementParJour,CalendrierAccueil.ACTIVITE_LISTE_EVENEMENT_PAR_JOUR);
                }
            }
        );
    }
}
