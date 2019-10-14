package ca.qc.cgmatane.gestionrdv.vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

import ca.qc.cgmatane.gestionrdv.R;
import ca.qc.cgmatane.gestionrdv.modele.Evenement;
import ca.qc.cgmatane.gestionrdv.modele.EvenementDAO;

public class ModifierEvenement extends AppCompatActivity {

    public static final String HEURE_FORMAT = "HH:mm";


    protected EvenementDAO accesseurEvenement;
    protected Evenement evenement;

    protected EditText vueModifierEvenementChampNom;
    protected EditText vueModifierEvenementChampDescription;
    protected EditText vueModifierEvenementChampNomEndroit;
    protected TextView vueModifierEvenementChampEchance;
    protected CalendarView vueModifierEvenementChampDate;
    protected LatLng pointGPS;
    protected String echeance;
    protected Date moment;

    private String momentChoisi;

    //TODO : recuperer l'evenement
    //              +
    //       ecouter la modification de la date
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_evenement);

        Bundle parametres = this.getIntent().getExtras();
        String parametreId_evenement = (String) parametres.get("id_evenement");
        int idEvenement = Integer.parseInt(parametreId_evenement);

        this.accesseurEvenement =EvenementDAO.getInstance();

        evenement = accesseurEvenement.chercherEvenementParIdEvenement(idEvenement);


        vueModifierEvenementChampNom = (EditText) findViewById(R.id.vue_modifier_evenement_champ_nom);
        vueModifierEvenementChampDescription = (EditText) findViewById(R.id.vue_modifier_evenement_champ_description);
        vueModifierEvenementChampNomEndroit = (EditText) findViewById(R.id.vue_modifier_evenement_champ_nom_endroit);
        vueModifierEvenementChampEchance = (EditText) findViewById(R.id.vue_modifier_evenement_champ_echeance);
        vueModifierEvenementChampDate = (CalendarView) findViewById(R.id.vue_modifier_evenement_champ_date);


        //Recuperation des valeurs de l'evenement a modifiier et affichage dans les champs
        vueModifierEvenementChampNom.setText(evenement.getNom());
        vueModifierEvenementChampDescription.setText(evenement.getDescription());
        vueModifierEvenementChampNomEndroit.setText(evenement.getNom_endroit());
        moment = evenement.getMoment();

        Toast toast = Toast.makeText(this, String.valueOf(moment), Toast.LENGTH_LONG);
        toast.show();

        long momentEnMilliseconds = moment.getTime();
        vueModifierEvenementChampDate.setDate(momentEnMilliseconds);
        SimpleDateFormat format_echance = new SimpleDateFormat(HEURE_FORMAT);
        echeance = format_echance.format(moment);
        vueModifierEvenementChampEchance.setText(echeance);



        vueModifierEvenementChampDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                String dateChoisie = ""+year+"/"+month+"/"+dayOfMonth;
                moment = new Date(dateChoisie);
            }
        });

        Button vueModifierEvenementActionEnregistrer =
            (Button) findViewById(R.id.vue_modifier_evenement_action_enregistrer);

        vueModifierEvenementActionEnregistrer.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enregistrerEvenement();
                }
            }
        );


    }
    //TODO : recuperer les coordonnées dans pointGPS
    //                      +
    //       recuperer la modification du moment(date+heure)
    private void enregistrerEvenement() {


        evenement.setNom(vueModifierEvenementChampNom.getText().toString());
        evenement.setDescription(vueModifierEvenementChampDescription.getText().toString());
        evenement.setNom_endroit(vueModifierEvenementChampNomEndroit.getText().toString());
        //evenement.setPointGPS();
        evenement.setMoment(moment);


        accesseurEvenement.modifierEvenement(evenement);

        naviguerRetourListeEvenementParJour();
    }
    private void naviguerRetourListeEvenementParJour() {
        this.finish();
    }

}
