package GestionDeSpectacles.Horaire;

public class Creneau implements Comparable<Creneau> {
    private int creneauJourSemaine;
    private Horaire creneauDebut;
    private Horaire creneauFin;

    public Creneau(int jour, Horaire debut, int duree) {
        this.creneauJourSemaine = jour;
        this.creneauDebut = debut;
        this.creneauFin = new Horaire(debut.getMinutes() + duree);
    }

    public int getCreneauJourSemaine() {
        return creneauJourSemaine;
    }

    public String getCreneauJour() {
        if (this.creneauJourSemaine == 1) return "Lundi(1)";
        if (this.creneauJourSemaine == 2) return "Mardi(2)";
        if (this.creneauJourSemaine == 3) return "Mercredi(3)";
        if (this.creneauJourSemaine == 4) return "Jeudi(4)";
        if (this.creneauJourSemaine == 5) return "Vendredi(5)";
        if (this.creneauJourSemaine == 6) return "Samedi(6)";
        if (this.creneauJourSemaine == 7) return "Dimanche(7)";
        return null;
    }

    public Horaire getCreneauDebut() {
        return creneauDebut;
    }

    public Horaire getCreneauFin() {
        return creneauFin;
    }

    /**
     * @return le String sous format: "Jour(numéro jour) de 00h00 à 01h00"
     */
    public String toString() {
        int minutedeb = this.creneauDebut.getHoraireMinutes();
        int minutefin = this.creneauFin.getHoraireMinutes();
        String s = "";
        s += this.getCreneauJour() + " de " + this.creneauDebut.getHoraireHeures() + "h";
        if (String.valueOf(minutedeb).length() == 1) s += "0";
        s += this.creneauDebut.getHoraireMinutes() + " à " + this.creneauFin.getHoraireHeures() + "h";
        if (String.valueOf(minutefin).length() == 1) s += "0";
        s += this.creneauFin.getHoraireMinutes();
        return s;
    }

    /**
     * @param o doit être un créneau
     * @return vrai si le jour,l'horaire de début et de fin sont égales respectivement à ceux de o sinon retourne faux
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creneau c = (Creneau) o;
        return (this.creneauJourSemaine == c.creneauJourSemaine &&
                this.creneauDebut.getMinutes() == c.creneauDebut.getMinutes() &&
                this.creneauFin.getMinutes() == c.creneauFin.getMinutes());
    }

    /**
     * Classe Horaire
     * classe interne static
     * elle n'a pas besoin de la classe créneau
     */
    public static class Horaire {
        private int horaireMinutes;

        /**
         * Seul l'horaire sous forme de minute est stockée : 01h20 donne 1*60+20 = 80 minutes
         *
         * @param minutes = heure*60+minute, minutes supérieur ou égale 0
         */
        public Horaire(int minutes) {
            this.horaireMinutes = minutes;
        }

        /**
         * @return seulement les minutes de l'horaire
         */
        public int getHoraireMinutes() {
            return this.horaireMinutes % 60;
        }

        /**
         * Passage à 00h00 quand l'horaire dépasse/égale à 24h00
         * à noter la méthode ne gère en aucun cas la différence des jours quand on repasse à 00h00
         * donc pour un spectacle de plus de 24h l'heure de fin pourra être ambigue
         *
         * @return seulement les heures de l'horaire
         */
        public int getHoraireHeures() {
            if (this.horaireMinutes / 60 > 23) return this.horaireMinutes / 60 - 24;
            return this.horaireMinutes / 60;
        }

        /**
         * @return l'ensemble des minutes d'une horaire
         */
        public int getMinutes() {
            return this.horaireMinutes;
        }
    }

    /**
     * @param c doit être un créneau
     * @return -1 si this inférieur c, 1 si this supérieur c sinon 0
     */
    @Override
    public int compareTo(Creneau c) {
        if (c == null) throw new NullPointerException("Créneau null");
        return Integer.compare(this.creneauDebut.getMinutes(), c.creneauDebut.getMinutes());
    }
}
