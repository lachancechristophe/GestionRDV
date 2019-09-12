package ca.qc.cgmatane.gestionrdv.controleur;

public class ControleurSQLite {
    private ControleurSQLite instance;

    public ControleurSQLite getInstance(){
        if(instance == null) instance = new ControleurSQLite();
        return instance;
    }

    public ControleurSQLite(){

    }
}
