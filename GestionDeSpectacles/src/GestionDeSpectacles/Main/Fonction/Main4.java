package GestionDeSpectacles.Main.Fonction;

import GestionDeSpectacles.Gestion.GestionProgrammationSemaine;
import GestionDeSpectacles.Horaire.Creneau;
import GestionDeSpectacles.Main.Main;

import java.util.Scanner;

/**
 * Ajouter séance film
 */
public class Main4 {
    public static GestionProgrammationSemaine main(GestionProgrammationSemaine GPS) {
        Scanner in = new Scanner(System.in);
        int film, salle, jour, heure, minute;

        try {
            System.out.print(GPS.lesFilms());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return GPS;
        }

        System.out.println("Choississez le numéro du film auquel vous voulez ajouter une séance (q pour quitter): ");
        while (true) {
            if (in.hasNextInt()) {
                film = in.nextInt();
                if (!GPS.existeFilm(film)) {
                    System.out.println("Le film n'existe pas.");
                    return main(GPS);
                }
                break;
            } else {
                if (in.nextLine().equals("q")) return GPS;
            }
        }
        try {
            System.out.println(GPS.lesSallesFilm());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return GPS;
        }

        System.out.print("Choississez le numéro de la salle auquel vous voulez ajouter une séance (q pour quitter): ");
        while (true) {
            if (in.hasNextInt()) {
                salle = in.nextInt();
                if (!GPS.existeSalleFilm(salle)) {
                    System.out.println("La salle n'existe pas.");
                    return main(GPS);
                }
                break;
            } else {
                if (in.nextLine().equals("q")) return GPS;
            }
        }

        System.out.print("Jour de la séance (1 à 7) (q pour quitter): ");
        while (true) {
            if (in.hasNextInt()) {
                jour = in.nextInt();
                if (jour >= 1 && jour <= 7) break;
                else System.out.println("Le jour doit être compris entre 1 et 7.");
            } else {
                if (in.nextLine().equals("q")) return GPS;
            }
        }

        System.out.print("Heure de la séance (0 à 23) (q pour quitter): ");
        while (true) {
            if (in.hasNextInt()) {
                heure = in.nextInt();
                if (heure >= 0 && heure <= 23) break;
                else System.out.println("L'heure doit être comprise entre 0 et 23.");
            } else {
                if (in.nextLine().equals("q")) return GPS;
            }
        }

        System.out.print("Minute de la séance (0 à 59) (q pour quitter): ");
        while (true) {
            if (in.hasNextInt()) {
                minute = in.nextInt();
                if (minute >= 0 && minute <= 59) break;
                else System.out.println("Les minutes doit être comprises entre 0 et 59.");
            } else {
                if (in.nextLine().equals("q")) return GPS;
            }
        }
        System.out.println();
        System.out.println("Film: " + film);
        System.out.println("Salle: " + salle);
        System.out.println("Jour: " + jour);
        System.out.print("Horaire: " + heure + "h");
        String norme = String.valueOf(minute);
        if (norme.length() == 1) System.out.print("0");
        System.out.print(minute);
        System.out.println();
        String sortie;
        do {
            System.out.print("Confirmez-vous vos données ? (o/n)");
            sortie = in.next();
            if (sortie.equals("n")) return main(GPS);
        } while (!sortie.equals("o"));
        System.out.println();
        try {
            GPS.ajouterSeanceFilm(film, jour, new Creneau.Horaire(heure * 60 + minute), salle);
        } catch (NullPointerException | IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return main(GPS);
        }
        try {
            System.out.print(GPS.lesSeancesFilm(film));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        Main.entrerPourContinuer();
        return GPS;
    }
}
