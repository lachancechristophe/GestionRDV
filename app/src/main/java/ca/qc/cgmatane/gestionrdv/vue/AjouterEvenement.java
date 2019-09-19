package ca.qc.cgmatane.gestionrdv.vue;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import ca.qc.cgmatane.gestionrdv.R;
import ca.qc.cgmatane.gestionrdv.modele.Evenement;
import ca.qc.cgmatane.gestionrdv.modele.EvenementDAO;

public class AjouterEvenement extends AppCompatActivity {

    protected EditText vueAjouterEvenementChampNom;
    protected EditText vueAjouterEvenementChampDescription;
    protected EditText vueAjouterEvenementChampNomEndroit;
    protected TextView vueAjouterEvenementTexteHeure;
    protected CalendarView vueAjouterEvenementChampDate;
    protected LatLng pointGPS;
    protected Date echeance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajouter_evenement);
        //TODO: Passer le point GPS en extra


        echeance = new Date();
        echeance.setHours(12);
        echeance.setMinutes(0);

        Button vueAjouterEvenementActionValider = (Button)findViewById(R.id.vue_ajouter_evenement_action_valider);
        vueAjouterEvenementActionValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enregistrerEvenement();
            }
        });

        Button vueAjouterEvenementActionRetour = (Button)findViewById(R.id.vue_ajouter_evenement_action_retour);
        vueAjouterEvenementActionRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naviguerRetourListeEvenements();
            }
        });

        Button vueAjouterEvenementActionChoisirHeure = (Button)findViewById(R.id.vue_ajouter_evenement_action_choisir_heure);
        vueAjouterEvenementActionChoisirHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ChoisirHeure");
                showDialog(2);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 2) {
            return new TimePickerDialog(this, observateurTemps,
                    12, 0, false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener observateurTemps = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int heure, int minutes){
            echeance.setHours(heure);
            echeance.setMinutes(minutes);
            echeance.setSeconds(0);
            vueAjouterEvenementTexteHeure.setText(
                    android.text.format.DateFormat.format("hh:mm a", echeance));
            System.out.println(echeance);
        }
    };

    private void enregistrerEvenement(){
        System.out.println(echeance);
        EvenementDAO accesseurEvenements = EvenementDAO.getInstance();

        Evenement event = new Evenement(
                vueAjouterEvenementChampNom.getText().toString(),
                vueAjouterEvenementChampDescription.getText().toString(),
                vueAjouterEvenementChampNomEndroit.getText().toString(),
                pointGPS,
                echeance);
        accesseurEvenements.ajouterEvenement(event);

        naviguerRetourListeEvenements();
    }

    private void  naviguerRetourListeEvenements(){
        this.finish();
    }

}
