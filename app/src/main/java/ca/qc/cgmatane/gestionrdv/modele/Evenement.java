package ca.qc.cgmatane.gestionrdv.modele;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Evenement {
    private String nom;
    private String description;
    private String nom_endroit;
    private LatLng pointGPS;
    private Date moment;

    public Evenement(String nom, String description, String nom_endroit, LatLng pointGPS, Date moment) {
        this.nom = nom;
        this.description = description;
        this.nom_endroit = nom_endroit;
        this.pointGPS = pointGPS;
        this.moment = moment;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom_endroit() {
        return nom_endroit;
    }

    public void setNom_endroit(String nom_endroit) {
        this.nom_endroit = nom_endroit;
    }

    public LatLng getPointGPS() {
        return pointGPS;
    }

    public void setPointGPS(LatLng pointGPS) {
        this.pointGPS = pointGPS;
    }

    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }
}
