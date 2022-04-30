package GestionDeSpectacles.Spectacle;

import GestionDeSpectacles.Seance.Seance;
import GestionDeSpectacles.Seance.SeanceTheatre;
import GestionDeSpectacles.Spectacle.Spectacle;

import java.util.*;

public class PieceTheatre extends Spectacle {
    private String pieceNomMetteurEnScene;
    private int pieceNbEntracte;
    private static int increment = 1000;

    public PieceTheatre(String titre, String metteurEnScene, int entracte, String... nom) {
        super(increment++, titre, nom);
        this.pieceNomMetteurEnScene = metteurEnScene;
        this.pieceNbEntracte = entracte;
    }

    @Override
    public String toString() {
        return "N°" + this.getSpectacleNumero() + ": " + this.getSpectacleTitre() + " de " +
                this.pieceNomMetteurEnScene + ", Nombre d'entractes " + this.pieceNbEntracte + '\n';
    }

    public String getPieceNomMetteurEnScene() {
        return this.pieceNomMetteurEnScene;
    }

    /**
     * @param st
     * @return vrai si la pièce a été ajouté sinon faux
     */
    public boolean ajouterSeanceTheatre(SeanceTheatre st) {
        int jour = st.getSeanceActuelle().getCreneauJourSemaine();
        if (this.getSpectacleLesSeances().get(jour) == null) {
            SortedSet<Seance> ensemble = new TreeSet<>();
            ensemble.add(st);
            this.getSpectacleLesSeances().put(jour, ensemble);
            return true;
        }
        return false;
    }

}
