package ca.qc.cgmatane.gestionrdv.vue;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ca.qc.cgmatane.gestionrdv.R;

public class CarteAjouter extends FragmentActivity implements OnMapReadyCallback {

    public static final int ACTIVITE_AJOUTER_EVENEMENT = 1;

    private GoogleMap mMap;
    private int compteurTemp;
    private LatLng coordonnes;
    private String dateChoisie;
    private Marker markeur;
    private Button vueCarteActionNaviguerListe;
    private Button vueCarteActionNaviguerAjouterEvenement;
    private Intent intentionAjouterEvenement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateChoisie = getIntent().getExtras().getString("date");
        compteurTemp = 1;
        setContentView(R.layout.vue_carte_accueil);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        vueCarteActionNaviguerListe = (Button)findViewById(R.id.vue_carte_action_naviguer_liste);
        vueCarteActionNaviguerListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naviguerListeEvenements();
            }
        });

        vueCarteActionNaviguerAjouterEvenement = (Button)findViewById(R.id.vue_carte_action_naviguer_ajout_evenement);
        vueCarteActionNaviguerAjouterEvenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                naviguerAjouterEvenements();
            }
        });


        intentionAjouterEvenement = new Intent(this, AjouterEvenement.class);
    }

    protected void onActivityResult(int activite, int resultat, Intent donnees){
        switch(activite){

        }
    }

    private void naviguerListeEvenements(){
    }

    private void naviguerAjouterEvenements(){
        if(markeur == null) {
            Toast.makeText(
                    CarteAjouter.this,
                    "SVP désigner un endroit.",
                    Toast.LENGTH_LONG).show();
            try {
                wait(1000);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
            return;
        }
        intentionAjouterEvenement.putExtra("Position", markeur.getPosition());
        intentionAjouterEvenement.putExtra("date", dateChoisie);
        startActivityForResult(intentionAjouterEvenement, ACTIVITE_AJOUTER_EVENEMENT);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        LatLng matane = new LatLng(48.849, -67.528);
        mMap.addMarker(new MarkerOptions().position(matane).title("Marker in Matane"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(matane));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(matane, 12f));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                coordonnes = latLng;
                Toast.makeText(
                        CarteAjouter.this,
                        "Lat : " + latLng.latitude + " , "
                                + "Long : " + latLng.longitude,
                        Toast.LENGTH_LONG).show();

            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){
            @Override
            public void onMapLongClick(LatLng point) {
                if (markeur != null){markeur.remove();}
                markeur = mMap.addMarker(new MarkerOptions()
                        .position(point)
                        .title("Position de l'évènement")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                compteurTemp++;
            }


            //mMap.setOnInfoWindowClickListener(RegActivity.this);
        });





    }


}
