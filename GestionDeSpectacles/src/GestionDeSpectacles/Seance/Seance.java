package GestionDeSpectacles.Seance;

import GestionDeSpectacles.Horaire.Creneau;

public abstract class Seance implements Comparable<Seance> {
    private Creneau seanceActuelle;
    private int seanceNbPlacesVenduesTarifNormal = 0;

    public Seance(Creneau c) {
        this.seanceActuelle = c;
    }


    /**
     * @param seance lève une exception si nul.
     * @return le résultat de compareTo des créneaux des séances
     */
    @Override
    public int compareTo(Seance seance) {
        if (seance == null) throw new NullPointerException("La séance est nulle.");
        return this.seanceActuelle.compareTo(seance.seanceActuelle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seance seance = (Seance) o;
        return this.seanceNbPlacesVenduesTarifNormal == seance.seanceNbPlacesVenduesTarifNormal &&
                this.seanceActuelle.equals(seance.seanceActuelle);
    }

    public Creneau getSeanceActuelle() {
        return seanceActuelle;
    }

    public int getSeanceNbPlacesVenduesTarifNormal() {
        return seanceNbPlacesVenduesTarifNormal;
    }

    public void vendrePlaceTarifNormal(int nbPlace) {
        this.seanceNbPlacesVenduesTarifNormal += nbPlace;
    }

    public boolean egaliteSeance(Creneau c) {
        return this.seanceActuelle.equals(c);
    }

    public abstract double getTauxRemplissage();

    public abstract double getChiffreAffaire();

    public abstract int getPlaceDispo();

}
