package ca.qc.cgmatane.gestionrdv.vue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.qc.cgmatane.gestionrdv.R;
import ca.qc.cgmatane.gestionrdv.modele.Evenement;
import ca.qc.cgmatane.gestionrdv.modele.EvenementDAO;

public class ModifierEvenement extends AppCompatActivity {

    public static final String HEURE_FORMAT = "hh:mm";
    public static final String DATE_FORMAT = "d/M/yyyy";
    public static final String MOMENT_FORMAT = "d/M/yyyy hh:mm";
    public static final int ACTIVITE_MODIFIER_EMPLACEMENT = 1;


    protected EvenementDAO accesseurEvenement;
    protected Evenement evenement;

    protected EditText vueModifierEvenementChampNom;
    protected EditText vueModifierEvenementChampDescription;
    protected EditText vueModifierEvenementChampNomEndroit;
    protected TextView vueModifierEvenementChampEchance;
    protected CalendarView vueModifierEvenementChampDate;
    protected Date moment;

    protected Intent intentionNaviguerModifierEmplacement;

    private String echanceChoisie;
    private String dateChoisie;


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



        //Recuperation des valeurs de l'evenement a modifier et affichage dans les champs
        vueModifierEvenementChampNom.setText(evenement.getNom());
        vueModifierEvenementChampDescription.setText(evenement.getDescription());
        vueModifierEvenementChampNomEndroit.setText(evenement.getNom_endroit());
        moment = evenement.getMoment();
        SimpleDateFormat format_date = new SimpleDateFormat(DATE_FORMAT);
        dateChoisie = format_date.format(moment);
        System.out.println(dateChoisie);
        long momentEnMilliseconds = moment.getTime();
        vueModifierEvenementChampDate.setDate(momentEnMilliseconds);

        SimpleDateFormat format_echance = new SimpleDateFormat(HEURE_FORMAT);
        echanceChoisie = format_echance.format(moment);
        vueModifierEvenementChampEchance.setText(echanceChoisie);




        vueModifierEvenementChampDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                dateChoisie = ""+dayOfMonth+"/"+month+"/"+year;

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

        Button vueModifierEvenementActionRetour =
            (Button) findViewById(R.id.vue_modifier_evenement_action_retour);

        vueModifierEvenementActionRetour.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    naviguerRetourListeEvenementParJour();
                }
            }
        );

        intentionNaviguerModifierEmplacement = new Intent(this, CarteAjouter.class);

        Button vueModifierEvenementActionModifierEmplacement =
            (Button) findViewById(R.id.vue_modifier_evenement_action_modifier_emplacement);

        vueModifierEvenementActionModifierEmplacement.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    intentionNaviguerModifierEmplacement =
                        new Intent(ModifierEvenement.this, CarteModifier.class);
                    intentionNaviguerModifierEmplacement.putExtra("id_evenement", evenement.getId());
                    startActivityForResult(intentionNaviguerModifierEmplacement,ModifierEvenement.ACTIVITE_MODIFIER_EMPLACEMENT);
                }
            }
        );


    }

    private void enregistrerEvenement() {


        evenement.setNom(vueModifierEvenementChampNom.getText().toString());
        evenement.setDescription(vueModifierEvenementChampDescription.getText().toString());
        evenement.setNom_endroit(vueModifierEvenementChampNomEndroit.getText().toString());
        echanceChoisie = vueModifierEvenementChampEchance.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat(MOMENT_FORMAT);
        String date = dateChoisie+" "+echanceChoisie;
        try {
            moment = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(moment);
        System.out.println(dateChoisie+" "+echanceChoisie);
        evenement.setMoment(moment);


        accesseurEvenement.modifierEvenement(evenement);

        naviguerRetourListeEvenementParJour();
    }
    private void naviguerRetourListeEvenementParJour() {
        this.finish();
    }

}
