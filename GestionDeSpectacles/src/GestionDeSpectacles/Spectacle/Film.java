package GestionDeSpectacles.Spectacle;

import GestionDeSpectacles.Seance.Seance;
import GestionDeSpectacles.Seance.SeanceFilm;

import java.util.SortedSet;
import java.util.TreeSet;

public class Film extends Spectacle {
    private String filmNomRealisateur;
    private int filmDuree;
    private static int increment = 100;

    public Film(String titre, String realisateur, int duree, String... nom) {
        super(increment++, titre, nom);
        this.filmNomRealisateur = realisateur;
        this.filmDuree = duree;
    }

    @Override
    public String toString() {
        return "N°" + this.getSpectacleNumero() + ": " + this.getSpectacleTitre() + " de " +
                this.filmNomRealisateur + " , " + this.filmDuree + "min \n";
    }

    public String getFilmNomRealisateur() {
        return filmNomRealisateur;
    }

    public int getFilmDuree() {
        return this.filmDuree;
    }

    /**
     * @param sf
     * @return vrai si le film a été ajouté sinon faux
     */
    public boolean ajouterSeanceFilm(SeanceFilm sf) {
        int jour = sf.getSeanceActuelle().getCreneauJourSemaine();
        if (this.getSpectacleLesSeances().get(jour) == null) {
            SortedSet<Seance> ensemble = new TreeSet<>();
            ensemble.add(sf);
            this.getSpectacleLesSeances().put(jour, ensemble);
            return true;
        }
        return this.getSpectacleLesSeances().get(jour).add(sf);
    }

}
