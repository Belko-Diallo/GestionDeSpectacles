package GestionDeSpectacles.Gestion;

import GestionDeSpectacles.Horaire.Creneau;
import GestionDeSpectacles.Salle.Salle;
import GestionDeSpectacles.Salle.SalleTheatre;
import GestionDeSpectacles.Seance.Seance;
import GestionDeSpectacles.Seance.SeanceFilm;
import GestionDeSpectacles.Seance.SeanceTheatre;
import GestionDeSpectacles.Spectacle.Film;
import GestionDeSpectacles.Spectacle.PieceTheatre;
import GestionDeSpectacles.Spectacle.Spectacle;

import java.util.*;

public class GestionProgrammationSemaine implements IProgrammationSemaine {

    private Map<Integer, Salle> lesSalles = new TreeMap<>();
    private Map<Integer, SalleTheatre> lesSallesTheatre = new TreeMap<>();
    private Map<Integer, Film> lesFilms = new TreeMap<>();
    private Map<Integer, PieceTheatre> lesPieces = new TreeMap<>();

    public GestionProgrammationSemaine() {
        Salle s1 = new Salle("Alpha", 50, 10);
        Salle s2 = new Salle("Beta", 50, 10);
        Salle s3 = new Salle("Gamma", 50, 10);
        Salle s4 = new Salle("Theta", 50, 10);
        SalleTheatre st1 = new SalleTheatre("Herbrand", 25, 20, 10, 30);
        SalleTheatre st2 = new SalleTheatre("Lauriche", 25, 20, 10, 30);
        SalleTheatre st3 = new SalleTheatre("Albert", 25, 20, 10, 30);
        SalleTheatre st4 = new SalleTheatre("Camille", 25, 20, 10, 30);
        this.lesSalles.put(s1.getSalleNumero(), s1);
        this.lesSalles.put(s2.getSalleNumero(), s2);
        this.lesSalles.put(s3.getSalleNumero(), s3);
        this.lesSalles.put(s4.getSalleNumero(), s4);
        this.lesSallesTheatre.put(st1.getSalleNumero(), st1);
        this.lesSallesTheatre.put(st2.getSalleNumero(), st2);
        this.lesSallesTheatre.put(st3.getSalleNumero(), st3);
        this.lesSallesTheatre.put(st4.getSalleNumero(), st4);
    }

    /**
     * Recherche un film existant ayant pour titre et pour réalisateur ceux passés en paramètre
     *
     * @param titre
     * @param realisateur
     * @return le film trouvé ou null si aucun film trouvé
     */
    public Film rechercherFilm(String titre, String realisateur) {
        Collection<Film> lesFilms = this.lesFilms.values();
        for (Film film : lesFilms)
            if (film.getFilmNomRealisateur().equals(realisateur) && film.getSpectacleTitre().equals(titre)) return film;
        return null;
    }

    /**
     * Recherche un film ayant pour titre et pour réalisateur ceux passés en paramètre.
     * Si aucun film trouvé, crée le film et l'ajoute sinon lève une exception
     *
     * @param titre
     * @param realisateur
     * @param duree
     * @throws IllegalArgumentException Le film existe déjà
     */
    @Override
    public void ajouterFilm(String titre, String realisateur, int duree) {
        if (this.rechercherFilm(titre, realisateur) != null) throw new IllegalArgumentException("Le film existe déjà.");
        Film f = new Film(titre, realisateur, duree);
        this.lesFilms.put(f.getSpectacleNumero(), f);
    }

    /**
     * Ajoute l'interprète passé en paramètre au spectacle correspondant au paramètre numSpectacle s'il existe sinon lève une exception
     *
     * @param numSpectacle
     * @param interprete
     * @throws IllegalArgumentException Spectacle inexistant
     */
    @Override
    public void ajouterInterprete(int numSpectacle, String interprete) {
        if (this.getSpectacle(numSpectacle) == null) throw new IllegalArgumentException("Spectacle inéxistant.");
        if (this.estUnFilm(numSpectacle)) this.lesFilms.get(numSpectacle).setSpectacleNomsInterpretes(interprete);
        else this.lesPieces.get(numSpectacle).setSpectacleNomsInterpretes(interprete);
    }

    @Override
    public PieceTheatre rechercherPiece(String titre, String metteurEnScene) {
        Collection<PieceTheatre> lesPieces = this.lesPieces.values();
        for (PieceTheatre piece : lesPieces)
            if (piece.getPieceNomMetteurEnScene().equals(metteurEnScene) && piece.getSpectacleTitre().equals(titre))
                return piece;
        return null;
    }

    /**
     * Recherche une pièce de théâtre ayant pour titre et pour metteur en scène ceux passés en paramètre.
     * Si aucune pièce trouvée, crée la pièce et l'ajoute sinon lève une exception
     *
     * @param titre
     * @param metteurEnScene
     * @param nbEntractes
     * @throws IllegalArgumentException La pièce existe déjà
     */
    @Override
    public void ajouterPiece(String titre, String metteurEnScene, int nbEntractes) {
        if (this.rechercherPiece(titre, metteurEnScene) != null)
            throw new IllegalArgumentException("La pièce existe déjà.");
        PieceTheatre pt = new PieceTheatre(titre, metteurEnScene, nbEntractes);
        this.lesPieces.put(pt.getSpectacleNumero(), pt);
    }

    /**
     * Crée et ajoute la séance au film correspondant à idFilm s'il existe
     * et si la salle est disponible sur le créneau défini par les paramètres jour et début et la durée du film.
     * Il faut que l'heure de l'horaire de fin calculée soit compris entre 0 et 23 et les minutes entre 0 et 59.
     * Lève une exception si aucune salle ne correspond ) idSalle
     * Ajoute également le créneau à la salle correspondante
     *
     * @param idFilm
     * @param jour
     * @param debut
     * @param idSalle
     * @throws IllegalArgumentException Film inexistant
     * @throws IllegalArgumentException Salle inexistante
     * @throws IllegalStateException    Créneau indisponible pour dans cette salle
     */
    @Override
    public void ajouterSeanceFilm(int idFilm, int jour, Creneau.Horaire debut, int idSalle) {
        if (!this.existeFilm(idFilm)) throw new IllegalArgumentException("Film inéxistant.");
        if (!this.existeSalleFilm(idSalle)) throw new IllegalArgumentException("Salle inéxistante.");
        Creneau c = new Creneau(jour, debut, this.lesFilms.get(idFilm).getFilmDuree());
        if (!this.lesSalles.get(idSalle).estDisponible(c))
            throw new IllegalStateException("Créneau indisponible pour cette salle.");
        this.lesSalles.get(idSalle).ajouterCreneau(c);
        Film spec = (Film) this.getSpectacle(idFilm);
        spec.ajouterSeanceFilm(new SeanceFilm(c, this.lesSalles.get(idSalle)));
    }

    /**
     * Teste l'existence d'une séance pour la pièce de théâtre correspondant à idPiece si elle existe.
     *
     * @param idPiece
     * @param jour
     * @return
     * @throws IllegalArgumentException Pièce inexistante
     */
    @Override
    public boolean existeSeanceCeJour(int idPiece, int jour) {
        if (!this.existePiece(idPiece)) throw new IllegalArgumentException("Pièce inéxistante.");
        return this.getSpectacle(idPiece).getSpectacleLesSeances().containsKey(jour);
    }

    /**
     * Crée et ajoute la séance à la pièce correspondant à idPiece s'il existe
     * et s'il n'y a pas déjà un créneau défini pour cette salle ce jour là.
     * Pour toute les pièces, on définira un créneau d'une durée de 3h. Si en ajoutant 3 heures à l'horaire de début, on passe au jour suivant (h supérieur ou égale 24) il faut ramener l'heure entre 0 et 23.
     * Lève une exception si aucune salle ne correspond à idSalle
     * Ajoute également le créneau à la salle
     *
     * @param idPiece
     * @param jour
     * @param debut
     * @param idSalle
     * @throws IllegalArgumentException Pièce inexistante
     * @throws IllegalArgumentException Salle inexistante
     * @throws IllegalStateException    Créneau indisponible pour dans cette salle
     */
    @Override
    public void ajouterSeanceTheatre(int idPiece, int jour, Creneau.Horaire debut, int idSalle) {
        if (!this.existePiece(idPiece)) throw new IllegalArgumentException("Pièce inéxistante.");
        if (!this.existeSalleTheatre(idSalle)) throw new IllegalArgumentException("Salle inéxistante.");
        Creneau c = new Creneau(jour, debut, 180);
        if (!this.lesSallesTheatre.get(idSalle).estDisponible(c))
            throw new IllegalStateException("Créneau indisponible pour cette salle.");
        if (this.existeSeanceCeJour(idPiece, jour))
            throw new IllegalStateException("La pièce de théâtre a déjà été programmé ce jour.");
        this.lesSallesTheatre.get(idSalle).ajouterCreneau(c);
        PieceTheatre spec = (PieceTheatre) this.getSpectacle(idPiece);
        spec.ajouterSeanceTheatre(new SeanceTheatre(c, this.lesSallesTheatre.get(idSalle)));
    }

    /**
     * Retourne le chiffre d'affaires du spectacle correspondant au numéro passé en paramètre s'il existe
     *
     * @param numSpectacle
     * @return
     * @throws IllegalArgumentException Spectacle inexistant
     */
    @Override
    public double chiffreAffaires(int numSpectacle) {
        if (this.getSpectacle(numSpectacle) == null) throw new IllegalArgumentException("Spectacle inéxistant.");
        return this.getSpectacle(numSpectacle).chiffreAffaire();
    }

    /**
     * Retourne le taux de remplissage du spectacle correspondant au numéro passé en paramètre s'il existe
     *
     * @param numSpectacle
     * @return
     * @throws IllegalArgumentException Spectacle inexistant
     */
    @Override
    public double getTauxRemplissage(int numSpectacle) {
        if (this.getSpectacle(numSpectacle) == null) throw new IllegalArgumentException("Spectacle inéxistant.");
        return this.getSpectacle(numSpectacle).tauxMoyenRemplissage();
    }

    /**
     * Vend le nombre de places à tarif normal passé en paramètre pour le film correspondant à idFilm s'il existe
     * et pour la séance correspondant au jour et à l'horaire de début passés en paramètre à condition qu'il y ait assez de places disponibles
     *
     * @param idFilm
     * @param jour
     * @param debut
     * @param nbPlacesTN
     * @throws IllegalArgumentException Film inexistant
     * @throws IllegalArgumentException Séance inexistant
     * @throws IllegalArgumentException Pas assez de places disponibles
     */
    @Override
    public void vendrePlaceFilmTN(int idFilm, int jour, Creneau.Horaire debut, int nbPlacesTN) {
        if (!this.existeFilm(idFilm)) throw new IllegalArgumentException("Film inéxistant.");
        if (!this.existeSeanceFilm(idFilm, jour, debut.getHoraireHeures(), debut.getHoraireMinutes()))
            throw new IllegalArgumentException("Séance inéxistante.");
        Seance sf = this.lesFilms.get(idFilm).rechercheSeance(jour, debut);
        if (sf.getPlaceDispo() < nbPlacesTN) throw new IllegalArgumentException("Places restantes insuffisantes.");
        sf.vendrePlaceTarifNormal(nbPlacesTN);
    }

    /**
     * Vend le nombre de places à tarif réduit passé en paramètre pour le film correspondant à idFilm s'il existe
     * et pour la séance correspondant au jour et à l'horaire de début passés en paramètre à condition qu'il y ait assez de places disponibles
     *
     * @param idFilm
     * @param jour
     * @param debut
     * @param nbPlacesTR
     * @throws IllegalArgumentException Film inexistant
     * @throws IllegalArgumentException Séance inexistant
     * @throws IllegalArgumentException Pas assez de places disponibles
     */
    @Override
    public void vendrePlaceFilmTR(int idFilm, int jour, Creneau.Horaire debut, int nbPlacesTR) {
        if (!this.existeFilm(idFilm)) throw new IllegalArgumentException("Film inéxistant.");
        if (!this.existeSeanceFilm(idFilm, jour, debut.getHoraireHeures(), debut.getHoraireMinutes()))
            throw new IllegalArgumentException("Séance inéxistante.");
        SeanceFilm sf = (SeanceFilm) this.lesFilms.get(idFilm).rechercheSeance(jour, debut);
        if (sf.getPlaceDispo() < nbPlacesTR) throw new IllegalArgumentException("Places restantes insuffisante.");
        sf.vendrePlaceTarifReduit(nbPlacesTR);
    }

    /**
     * Vend le nombre de places à tarif normal passé en paramètre pour la pièce correspondant à idPiece s'elle existe
     * et pour la séance correspondant au jour passé en paramètre à condition qu'il y ait assez de places disponibles
     *
     * @param idPiece
     * @param jour
     * @param nbPlacesTN
     * @throws IllegalArgumentException Film inexistant
     * @throws IllegalArgumentException Séance inexistant
     * @throws IllegalArgumentException Pas assez de places disponibles
     */
    @Override
    public void vendrePlacePieceTN(int idPiece, int jour, int nbPlacesTN) {
        if (!this.existePiece(idPiece)) throw new IllegalArgumentException("Pièces inéxistant.");
        if (!this.existeSeanceCeJour(idPiece, jour)) throw new IllegalArgumentException("Séance inéxistante.");
        List<Seance> l = this.lesPieces.get(idPiece).rechercheSeance(jour);
        if (l == null) throw new NullPointerException("Aucune séance ce jour.");
        if (l.size() > 1) throw new IllegalArgumentException("Il ne peut y avoir 2 séances.");
        SeanceTheatre st = (SeanceTheatre) l.get(0);
        if (st.getPlaceDispo() < nbPlacesTN) throw new IllegalArgumentException("Place restante insuffisante.");
        st.vendrePlaceTarifNormal(nbPlacesTN);
    }

    /**
     * Vend le nombre de places à tarif fauteuils passé en paramètre pour la pièce correspondant à idPiece s'elle existe
     * et pour la séance correspondant au jour passé en paramètre à condition qu'il y ait assez de places disponibles
     *
     * @param idPiece
     * @param jour
     * @param nbFauteuils
     * @throws IllegalArgumentException Film inexistant
     * @throws IllegalArgumentException Séance inexistant
     * @throws IllegalArgumentException Pas assez de places disponibles
     */
    @Override
    public void vendrePlaceFauteuilPiece(int idPiece, int jour, int nbFauteuils) {
        if (!this.existePiece(idPiece)) throw new IllegalArgumentException("Pièces inéxistant.");
        if (!this.existeSeanceCeJour(idPiece, jour)) throw new IllegalArgumentException("Séance inéxistante.");
        List<Seance> l = this.lesPieces.get(idPiece).rechercheSeance(jour);
        if (l == null) throw new NullPointerException("Aucune séance ce jour.");
        if (l.size() > 1) throw new IllegalArgumentException("Il ne peut y avoir 2 séances.");
        SeanceTheatre st = (SeanceTheatre) l.get(0);
        if (st.getFauteuilDispo() < nbFauteuils) throw new IllegalArgumentException("Places restantes insuffisante.");
        st.vendrePlaceFauteuil(nbFauteuils);
    }

    /**
     * @return les films sous forme d'une chaîne de caractères
     */
    @Override
    public String lesFilms() {
        String s = "\n Films \n";
        Collection<Film> ens = this.lesFilms.values();
        if (ens.isEmpty()) throw new RuntimeException("Aucun film.");
        for (Film film : ens) s += film.toString();
        return s;
    }

    /**
     * @return les pièces de théâtre sous forme d'une chaîne de caractères
     */
    @Override
    public String lesPieces() {
        String s = "\n Pièces de Théâtres \n";
        Collection<PieceTheatre> lesPieces = this.lesPieces.values();
        if (lesPieces.isEmpty()) throw new RuntimeException("Aucune pièce de théâtre.");
        for (PieceTheatre piece : lesPieces) s += piece.toString();
        return s;
    }

    /**
     * @return les salles de film sous forme d'une chaîne de caractères
     */
    @Override
    public String lesSallesFilm() {
        String s = "\n Salles de Films \n";
        Collection<Salle> lesSalles = this.lesSalles.values();
        if (lesSalles.isEmpty()) throw new RuntimeException("Aucune salle.");
        for (Salle salle : lesSalles) s += salle.toString() + '\n';
        return s;
    }

    /**
     * @return les salles de théâtre sous forme d'une chaîne de caractères
     */
    @Override
    public String lesSallesTheatre() {
        String s = "\n Salles de Théâtre \n";
        Collection<SalleTheatre> lesTheatres = this.lesSallesTheatre.values();
        if (lesTheatres.isEmpty()) throw new RuntimeException("Aucun théâtre.");
        for (SalleTheatre theatre : lesTheatres) s += theatre.toString();
        return s;
    }

    /**
     * retourne les séances de la pièce de théâtre correspondant à idPiece si elle existe sinon lève une exception
     *
     * @param idPiece
     * @return
     * @throws IllegalArgumentException Pièce inexistante
     */
    @Override
    public String lesSeancesTheatre(int idPiece) {
        if (!this.existePiece(idPiece)) throw new IllegalArgumentException("Pièce inéxistante.");
        PieceTheatre piece = (PieceTheatre) this.getSpectacle(idPiece);
        return piece.toString() + piece.toStringSeance() + "\n";
    }

    /**
     * retourne les séances du film correspondant à idFilm s'il existe sinon lève une exception
     *
     * @param idFilm
     * @return
     * @throws IllegalArgumentException Film inexistant
     */
    @Override
    public String lesSeancesFilm(int idFilm) {
        if (!this.existeFilm(idFilm)) throw new IllegalArgumentException("Film inéxistant.");
        Film film = (Film) this.getSpectacle(idFilm);
        return film.toString() + film.toStringSeance() + "\n";
    }

    /**
     * Retourne le nombre de places standard disponibles pour le spectacle correspondant au numéro passé en paramètre s'il existe
     * et à la séance correspondant au jour et à l'horaire de début passés en paramètre si elle existe
     *
     * @param numSpectacle
     * @param jour
     * @param heures
     * @param minutes
     * @return
     * @throws IllegalArgumentException Spectacle inexistant
     * @throws IllegalArgumentException Séance inexistante pour ce spectacle
     */
    @Override
    public int getNbPlacesDispo(int numSpectacle, int jour, int heures, int minutes) {
        if (this.getSpectacle(numSpectacle) == null) throw new IllegalArgumentException("Spectacle inexistant.");
        Spectacle spec = this.getSpectacle(numSpectacle);
        Seance s = spec.rechercheSeance(jour, new Creneau.Horaire(heures * 60 + minutes));
        if (s == null) throw new IllegalArgumentException("Séance inéxistante.");
        return s.getPlaceDispo();
    }

    /**
     * @param idFilm
     * @return true si idFilm correspond à un film et false sinon
     */
    @Override
    public boolean existeFilm(int idFilm) {
        return this.lesFilms.containsKey(idFilm);
    }

    /**
     * @param idPiece
     * @return true si idPièce correspond à une pièce de théâtre et false sinon
     */
    @Override
    public boolean existePiece(int idPiece) {
        return this.lesPieces.containsKey(idPiece);
    }

    /**
     * Teste l'existance d'une séance pour le film dont l'idFilm est passé en paramètre s'il existe. Sinon lève une exception
     *
     * @param idFilm
     * @param jour
     * @param heures
     * @param minutes
     * @return true s'il existe une séance correspondant à un créneau défini par un jour et un horaire de début donné par heures et minutes et false sinon
     * @throws IllegalArgumentException Film inexistant
     */
    @Override
    public boolean existeSeanceFilm(int idFilm, int jour, int heures, int minutes) {
        if (!this.existeFilm(idFilm)) throw new IllegalArgumentException("Film inéxistant.");
        return (this.lesFilms.get(idFilm).rechercheSeance(jour, new Creneau.Horaire(heures * 60 + minutes)) != null);
    }

    /**
     * @param idSalle
     * @return true si idSalle correspond à une salle de film et false sinon
     */
    @Override
    public boolean existeSalleFilm(int idSalle) {
        return this.lesSalles.containsKey(idSalle);
    }

    /**
     * @param idSalle
     * @return true si idSalle correspond à une salle de film et false sinon
     */
    @Override
    public boolean existeSalleTheatre(int idSalle) {
        return this.lesSallesTheatre.containsKey(idSalle);
    }

    /**
     * Retourne la durée du film correspondant au paramètre s'il existe
     *
     * @param idFilm
     * @return
     * @throws IllegalArgumentException Film inexistant
     */
    @Override
    public int dureeFilm(int idFilm) {
        if (this.existeFilm(idFilm)) return this.lesFilms.get(idFilm).getFilmDuree();
        throw new IllegalArgumentException("Film inexistant.");
    }

    /**
     * Teste la disponibilité de la salle dont l'idSalle est passé en paramètre si elle existe
     *
     * @param jour
     * @param debut
     * @param duree
     * @param idSalle
     * @return Retourne true si la salle dont l'idSalle est passé en paramètre est disponible au créneau passé en paramètre sinon retourne false
     * @throws IllegalArgumentException Salle inexistante
     */
    @Override
    public boolean salleStandardDisponible(int jour, Creneau.Horaire debut, int duree, int idSalle) {
        if (this.existeSalleFilm(idSalle))
            return this.lesSalles.get(idSalle).estDisponible(new Creneau(jour, debut, duree));
        if (this.existeSalleTheatre(idSalle))
            return this.lesSallesTheatre.get(idSalle).estDisponible(new Creneau(jour, debut, duree));
        throw new IllegalArgumentException("Salle inéxistante.");
    }

    /**
     * Supprime les films et les pièces de théâtre de la programmation en cours.
     * Il faut également supprimer les créneaux occupés de chaque salle
     */
    @Override
    public void reinitialiserProgrammation() {
        this.lesPieces.clear();
        this.lesFilms.clear();
        Set<Integer> ens1 = this.lesSalles.keySet();
        Iterator it1 = ens1.iterator();
        while (it1.hasNext()) {
            int cle = (int) it1.next();
            this.lesSalles.get(cle).getLesCreneauxOccupes().clear();
        }
        Set<Integer> ens2 = this.lesSallesTheatre.keySet();
        Iterator it2 = ens2.iterator();
        while (it2.hasNext()) {
            int cle = (int) it2.next();
            this.lesSallesTheatre.get(cle).getLesCreneauxOccupes().clear();
        }
    }

    /**
     * Retourne le nombre de places de type fauteuil disponibles pour la pièce correspondant à idPiece s'elle existe
     * et s'il existe une séance le jour passé en paramètre
     *
     * @param idPiece
     * @param jour
     * @return
     * @throws IllegalArgumentException Pièce inexistante
     * @throws IllegalArgumentException Aucune séance ce jour;
     */
    @Override
    public int getNbFauteuilsDispo(int idPiece, int jour) {
        if (!this.existePiece(idPiece)) throw new IllegalArgumentException("Pièce innexistante");
        if (!this.existeSeanceCeJour(idPiece, jour)) throw new IllegalArgumentException("Aucun séance ce jour.");
        List<Seance> l = this.lesPieces.get(idPiece).rechercheSeance(jour);
        if (l == null) throw new NullPointerException("Aucune séance correspond.");
        if (l.size() > 1)
            throw new IllegalArgumentException("J'ai fait de la merde. Impossiblle d'avoir 2 séance de théâtre le même jour.");
        SeanceTheatre st = (SeanceTheatre) l.get(0);
        return st.getFauteuilDispo();
    }

    /**
     * Retourne le nombre de places standard disponibles pour la pièce correspondant à idPiece s'elle existe
     * et s'il existe une séance le jour passé en paramètre
     *
     * @param idPiece
     * @param jour
     * @return
     * @throws IllegalArgumentException Pièce inexistante
     * @throws IllegalArgumentException Aucune séance ce jour;
     */
    @Override
    public int getNbPlacesDispo(int idPiece, int jour) {
        if (!this.existePiece(idPiece)) throw new IllegalArgumentException("Pièce innexistante");
        if (!this.existeSeanceCeJour(idPiece, jour)) throw new IllegalArgumentException("Aucun séance ce jour.");
        List<Seance> l = this.lesPieces.get(idPiece).rechercheSeance(jour);
        if (l == null) throw new NullPointerException("J'ai fait de la merde.2");
        if (l.size() > 1) throw new IllegalArgumentException("J'ai fait de la merde.");
        SeanceTheatre st = (SeanceTheatre) l.get(0);
        return st.getPlaceDispo();
    }

    /**
     * @param numSpectacle
     * @return true si le numéro du spectacle passé en paramètre correspond à un numéro de film et false sinon
     */
    @Override
    public boolean estUnFilm(int numSpectacle) {
        return this.existeFilm(numSpectacle);
    }

    /**
     * @param numSpectacle
     * @return true si le numéro du spectacle passé en paramètre correspond à un numéro de pièce de théâtre et false sinon
     */
    @Override
    public boolean estUnePiece(int numSpectacle) {
        return this.existePiece(numSpectacle);
    }

    /**
     * Retourne le spectacle correspondant au numéro passé en paramètre s'il existe et null sinon
     *
     * @param numSpectacle
     * @return
     */
    @Override
    public Spectacle getSpectacle(int numSpectacle) {
        if (this.estUnePiece(numSpectacle)) return this.lesPieces.get(numSpectacle);
        if (this.estUnFilm(numSpectacle)) return this.lesFilms.get(numSpectacle);
        return null;
    }

    /**
     * @param idFilm
     * @param jour
     * @param debut
     * @param tn     nombre de place à acheter à tarif normal
     * @param tr     nombre de place à acheter à tarif réduit
     * @return la somme à payer en fonction du nombre de places prises et leurs tarifs respectifs
     */
    public double totalPlaceAPayer(int idFilm, int jour, Creneau.Horaire debut, int tn, int tr) {
        SeanceFilm sf = (SeanceFilm) this.lesFilms.get(idFilm).rechercheSeance(jour, debut);
        if (sf == null) throw new NullPointerException("Séance inéxistante.");
        return sf.getSeanceFilmSalle().getSalleTarifPlace() * tn +
                sf.getSeanceFilmSalle().getSalleTarifPlaceReduit() * tr;
    }

    /**
     * @param idPiece
     * @param jour
     * @param place    nombre de place à acheter à tarif normal
     * @param fauteuil nombre de fauteuil à acheter
     * @return la somme à payer en fonction du nombre de places/fauteuil pris et leurs tarifs respectifs
     */
    public double totalPlaceAPayer(int idPiece, int jour, int place, int fauteuil) {
        List<Seance> l = this.lesPieces.get(idPiece).rechercheSeance(jour);
        if (l == null) throw new NullPointerException("Aucune séance.");
        if (l.size() > 1) throw new IllegalArgumentException("Il y a plus d'une séance théâtre.");
        SeanceTheatre st = (SeanceTheatre) l.get(0);
        return st.getSeanceTheatreSalleTheatre().getSalleTheatreTarifFauteuils() * fauteuil +
                st.getSeanceTheatreSalleTheatre().getSalleTarifPlace() * place;
    }

    /**
     * @param spectacle
     * @return le toString du spectacle et le toString des interprètes
     */
    public String lesInterpretes(int spectacle) {
        return this.getSpectacle(spectacle).toStringInterprete();
    }
}
