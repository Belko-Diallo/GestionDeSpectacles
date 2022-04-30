package GestionDeSpectacles.Salle;

public class SalleTheatre extends Salle {
    private int salleTheatreNbFauteuils;
    private double salleTheatreTarifFauteuils;

    public SalleTheatre(String nom, int nbPlace, double tarif, int nbFauteuils, int tarifFauteuils) {
        super(nom, nbPlace, tarif);
        this.salleTheatreNbFauteuils = nbFauteuils;
        this.salleTheatreTarifFauteuils = tarifFauteuils;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + this.salleTheatreNbFauteuils + " fauteuils, " +
                this.salleTheatreTarifFauteuils + "€/fauteuil\n";
    }

    public int getSalleTheatreNbFauteuils() {
        return this.salleTheatreNbFauteuils;
    }

    public double getSalleTheatreTarifFauteuils() {
        return this.salleTheatreTarifFauteuils;
    }

}
