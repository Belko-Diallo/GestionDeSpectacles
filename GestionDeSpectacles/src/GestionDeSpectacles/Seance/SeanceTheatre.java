package GestionDeSpectacles.Seance;

import GestionDeSpectacles.Horaire.Creneau;
import GestionDeSpectacles.Salle.SalleTheatre;

public class SeanceTheatre extends Seance {
    private SalleTheatre seanceTheatreSalleTheatre;
    private int seanceTheatreNbFauteuilsVendus = 0;

    public SeanceTheatre(Creneau c, SalleTheatre salle) {
        super(c);
        this.seanceTheatreSalleTheatre = salle;
    }

    /**
     * Retourne le nombre de places disponibles
     *
     * @return PlaceTotal - placeVenduesTarifReduit - placeVenduesTarifNormal
     */
    public int getPlaceDispo() {
        return (this.seanceTheatreSalleTheatre.getSalleNbPlace() - this.getSeanceNbPlacesVenduesTarifNormal());
    }

    public SalleTheatre getSeanceTheatreSalleTheatre() {
        return this.seanceTheatreSalleTheatre;
    }

    public void vendrePlaceFauteuil(int nbPlace) {
        this.seanceTheatreNbFauteuilsVendus += nbPlace;
    }

    /**
     * @return (nbPlaceVenduesTarifNormal + nbFauteuilsVendues) / placeTotale * 100
     */
    public double getTauxRemplissage() {
        return ((double) (this.getSeanceNbPlacesVenduesTarifNormal() + this.seanceTheatreNbFauteuilsVendus)
                / this.seanceTheatreSalleTheatre.getSalleNbPlace() * 100);
    }

    /**
     * @return placeVenduesTarifNormal * tarifNormal + fauteuilVenduesTarifReduit * fauteuilTarif
     */
    @Override
    public double getChiffreAffaire() {
        int placeNormal = this.getSeanceNbPlacesVenduesTarifNormal();
        int placeFauteuil = this.seanceTheatreNbFauteuilsVendus;
        double tarifNormal = this.getSeanceNbPlacesVenduesTarifNormal();
        double tarifFauteil = this.seanceTheatreSalleTheatre.getSalleTheatreTarifFauteuils();
        return (placeNormal * tarifNormal + placeFauteuil * tarifFauteil);
    }

    /**
     * Retourne le nombre de fauteuils disponibles
     *
     * @return fauteuilTotal - fauteuilsVendus
     */
    public int getFauteuilDispo() {
        return (this.seanceTheatreSalleTheatre.getSalleTheatreNbFauteuils() - this.seanceTheatreNbFauteuilsVendus);
    }

    @Override
    public String toString() {
        return this.getSeanceActuelle().toString() + ", " + this.seanceTheatreSalleTheatre.getSalleNom() +
                "(" + this.seanceTheatreSalleTheatre.getSalleNumero() + "), Tarif place : " +
                this.seanceTheatreSalleTheatre.getSalleTarifPlace() + "0€/place" + ", Tarif fauteuil : " +
                this.seanceTheatreSalleTheatre.getSalleTheatreTarifFauteuils() + "0€/fauteuil";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SeanceTheatre st = (SeanceTheatre) o;
        return seanceTheatreNbFauteuilsVendus == st.seanceTheatreNbFauteuilsVendus &&
                this.seanceTheatreSalleTheatre.equals(st.seanceTheatreSalleTheatre);
    }

}
