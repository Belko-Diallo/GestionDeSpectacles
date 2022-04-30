package GestionDeSpectacles.Spectacle;

import GestionDeSpectacles.Horaire.Creneau;
import GestionDeSpectacles.Seance.Seance;

import java.util.*;

public abstract class Spectacle {
    private int spectacleNumero;
    private String spectacleTitre;
    private Set<String> spectacleNomsInterpretes = new HashSet<>();
    private SortedMap<Integer, SortedSet<Seance>> spectacleLesSeances = new TreeMap<>();

    public Spectacle(int numero, String titre, String... nom) {
        this.spectacleNumero = numero;
        this.spectacleTitre = titre;
        this.spectacleNomsInterpretes.addAll(Arrays.asList(nom));
    }

    /**
     * @return un String sur toutes les séances du spectacles.
     */
    public String toStringSeance() {
        String s = "Séances : \n";
        Collection<SortedSet<Seance>> lesEnsSeances = this.getSpectacleLesSeances().values();
        for (SortedSet<Seance> ensSeance : lesEnsSeances)
            for (Seance laSeance : ensSeance) s += laSeance.toString() + '\n';
        return s;
    }

    @Override
    public abstract String toString();

    public String toStringInterprete() {
        String s = this.toString() + "Interprète : ";
        for (String ens : this.spectacleNomsInterpretes) s += ens + " - ";
        return s;
    }

    public int getSpectacleNumero() {
        return this.spectacleNumero;
    }

    public String getSpectacleTitre() {
        return spectacleTitre;
    }

    public void setSpectacleNomsInterpretes(String interpretes) {
        if (!this.spectacleNomsInterpretes.add(interpretes)) throw new IllegalArgumentException("Existe déjà!");
    }

    public SortedMap<Integer, SortedSet<Seance>> getSpectacleLesSeances() {
        return this.spectacleLesSeances;
    }

    /**
     * Recherche de séance seulement pour les films.
     *
     * @param jour
     * @param hd
     * @return la seance trouvée si elle existe sinon null.
     */
    public Seance rechercheSeance(int jour, Creneau.Horaire hd) {
        SortedSet<Seance> lesSeances = this.getSpectacleLesSeances().get(jour);
        for (Seance laSeance : lesSeances) {
            if (laSeance.getSeanceActuelle().getCreneauDebut().getMinutes() == hd.getMinutes()) return laSeance;
        }
        return null;
    }

    /**
     * Recherche de séance seulement pour les pièces de théâtre.
     *
     * @param jour
     * @return la seance trouvée si elle existe sinon null.
     */
    public List<Seance> rechercheSeance(int jour) {
        if (!this.getSpectacleLesSeances().containsKey(jour)) return null;
        Set<Seance> ens = this.getSpectacleLesSeances().get(jour);
        return new ArrayList<>(ens);
    }

    /**
     * @return la somme des taux de remplissage des séances est la divise par le nombre total de séances.
     */
    public double tauxMoyenRemplissage() {
        int compteur = 0;
        double taux = 0;
        Collection<SortedSet<Seance>> lesSeances = this.spectacleLesSeances.values();
        for (SortedSet<Seance> ensSeances : lesSeances) {
            for (Seance laSeance : ensSeances) {
                compteur++;
                taux += laSeance.getTauxRemplissage();
            }
        }
        return taux / compteur;
    }

    /**
     * @return la somme du chiffre d'affaire de toutes les séances du spectacles.
     */
    public double chiffreAffaire() {
        int chiffreAffaire = 0;
        Collection<SortedSet<Seance>> lesSeances = this.spectacleLesSeances.values();
        for (SortedSet<Seance> ensSeances : lesSeances) {
            for (Seance laSeance : ensSeances) {
                chiffreAffaire += laSeance.getChiffreAffaire();
            }
        }
        return chiffreAffaire;
    }
}
