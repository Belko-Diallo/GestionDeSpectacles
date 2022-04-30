package GestionDeSpectacles.Salle;

import GestionDeSpectacles.Horaire.Creneau;

import java.util.*;

public class Salle {
    private int salleNumero;
    private String salleNom;
    private int salleNbPlace;
    private double salleTarifPlace;
    private SortedMap<Integer, SortedSet<Creneau>> lesCreneauxOccupes = new TreeMap<>();
    private static int increment;

    public Salle(String nom, int nbPlace, double tarif) {
        this.salleNumero = (increment += 10);
        this.salleNom = nom;
        this.salleNbPlace = nbPlace;
        this.salleTarifPlace = tarif;
    }

    @Override
    public String toString() {
        return "N°" + this.salleNumero + ": " + this.salleNom + ", " + this.salleNbPlace +
                " places, " + this.salleTarifPlace + "€/place";
    }

    public String getSalleNom() {
        return this.salleNom;
    }

    public int getSalleNumero() {
        return salleNumero;
    }

    public int getSalleNbPlace() {
        return salleNbPlace;
    }

    public double getSalleTarifPlace() {
        return salleTarifPlace;
    }

    public SortedMap<Integer, SortedSet<Creneau>> getLesCreneauxOccupes() {
        return lesCreneauxOccupes;
    }

    public double getSalleTarifPlaceReduit() {
        return this.salleTarifPlace * 0.6;
    }

    /**
     * @param c ne doit pas être null.
     * @return vrai si this.lesCreneauxOccupes ne contient pas le creneau c(le jour doit aussi correspondre) sinon faux.
     */
    public boolean estDisponible(Creneau c) {
        int jour = c.getCreneauJourSemaine();
        int debThis = c.getCreneauDebut().getMinutes();
        int finThis = c.getCreneauFin().getMinutes();
        int debC1, finC1;
        if (!this.pasDeCreneauCeJour(jour)) {
            Set<Creneau> ensemble = this.lesCreneauxOccupes.get(jour);
            for (Creneau creneau : ensemble) {
                debC1 = creneau.getCreneauDebut().getMinutes();
                finC1 = creneau.getCreneauFin().getMinutes();
                if (debThis > debC1 && debThis < finC1 || finThis > debC1 && finThis < finC1) return false;
            }
        }
        return true;
    }

    /**
     * @param c ne doit pas être null.
     * @return vrai si c a pu être ajouté à l'ensemble de creneaux concernés sinon retourne faux.
     */
    public boolean ajouterCreneau(Creneau c) {
        int jour = c.getCreneauJourSemaine();
        if (this.estDisponible(c)) {
            if (this.pasDeCreneauCeJour(jour)) {
                SortedSet<Creneau> ensemble = new TreeSet<>();
                ensemble.add(c);
                this.lesCreneauxOccupes.put(jour, ensemble);
            } else {
                SortedSet<Creneau> ensemble = this.lesCreneauxOccupes.get(jour);
                ensemble.add(c);
                this.lesCreneauxOccupes.put(jour, ensemble);
            }
            return true;
        }
        return false;
    }

    /**
     * @param jour
     * @return vrai si le jour n'est une clé à la map lesCreneauxOccupes sinon faux.
     */
    public boolean pasDeCreneauCeJour(int jour) {
        return !this.lesCreneauxOccupes.containsKey(jour);
    }

}
