package GestionDeSpectacles.Seance;

import GestionDeSpectacles.Horaire.Creneau;
import GestionDeSpectacles.Salle.Salle;

public class SeanceFilm extends Seance {
    private Salle seanceFilmSalle;
    private int seanceFilmNbPlacesVenduesTarifReduit = 0;

    public SeanceFilm(Creneau c, Salle salle) {
        super(c);
        this.seanceFilmSalle = salle;
    }

    @Override
    public String toString() {
        return this.getSeanceActuelle().toString() + ", " + this.seanceFilmSalle.getSalleNom() + "(" +
                this.seanceFilmSalle.getSalleNumero() + "), Tarif standard : " +
                this.seanceFilmSalle.getSalleTarifPlace() + "0€/place," +
                " Tarif réduit : " + this.seanceFilmSalle.getSalleTarifPlaceReduit() + "0€/place";
    }

    public Salle getSeanceFilmSalle() {
        return this.seanceFilmSalle;
    }

    /**
     * Retourne le nombre de places disponibles
     *
     * @return PlaceTotal - placeVenduesTarifReduit - placeVenduesTarifNormal
     */
    public int getPlaceDispo() {
        return this.seanceFilmSalle.getSalleNbPlace() - (this.seanceFilmNbPlacesVenduesTarifReduit + this.getSeanceNbPlacesVenduesTarifNormal());
    }

    /**
     * @return (nbPlaceVenduesTarifNormal + nbPlacesVenduesTarifReduit) / placeTotale * 100
     */
    @Override
    public double getTauxRemplissage() {
        return ((double) (this.getSeanceNbPlacesVenduesTarifNormal() + this.seanceFilmNbPlacesVenduesTarifReduit)
                / this.seanceFilmSalle.getSalleNbPlace() * 100);
    }

    /**
     * @return placeVenduesTarifNormal * tarifNormal + placeVenduesTarifReduit * tarifReduit
     */
    @Override
    public double getChiffreAffaire() {
        int placeNormal = this.getSeanceNbPlacesVenduesTarifNormal();
        int placeReduit = this.seanceFilmNbPlacesVenduesTarifReduit;
        double tarifNormal = this.seanceFilmSalle.getSalleTarifPlace();
        double tarifReduit = this.seanceFilmSalle.getSalleTarifPlaceReduit();
        return (placeNormal * tarifNormal + placeReduit * tarifReduit);
    }

    public void vendrePlaceTarifReduit(int nbPlace) {
        this.seanceFilmNbPlacesVenduesTarifReduit += nbPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SeanceFilm sf = (SeanceFilm) o;
        return this.seanceFilmNbPlacesVenduesTarifReduit == sf.seanceFilmNbPlacesVenduesTarifReduit &&
                this.seanceFilmSalle.equals(sf.seanceFilmSalle);
    }

}
