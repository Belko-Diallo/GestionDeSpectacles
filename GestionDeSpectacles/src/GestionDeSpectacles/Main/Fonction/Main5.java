package GestionDeSpectacles.Main.Fonction;

import GestionDeSpectacles.Gestion.GestionProgrammationSemaine;
import GestionDeSpectacles.Horaire.Creneau;
import GestionDeSpectacles.Main.Main;

import java.util.Scanner;

/**
 * Ajouter séance théâtre
 */
public class Main5 {
    public static GestionProgrammationSemaine main(GestionProgrammationSemaine GPS) {
        Scanner in = new Scanner(System.in);
        String sortie;
        int piece, salle, jour, heure, minute;

        try {
            System.out.println(GPS.lesPieces());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return GPS;
        }

        System.out.println("Choississez le numéro de la pièce de théâtre auquel vous voulez ajouter une séance (q pour quitter): ");
        while (true) {
            if (in.hasNextInt()) {
                piece = in.nextInt();
                if (!GPS.existePiece(piece)) {
                    System.out.println("La pièce de théâtre n'existe pas.");
                    return main(GPS);
                }
                break;
            } else {
                sortie = in.nextLine();
                if (sortie.equals("q")) return GPS;
            }
        }
        try {
            System.out.print(GPS.lesSallesTheatre());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return GPS;
        }

        System.out.print("Choississez le numéro de la salle auquel vous voulez ajouter une séance (q pour quitter): ");
        while (true) {
            if (in.hasNextInt()) {
                salle = in.nextInt();
                if (!GPS.existeSalleTheatre(salle)) {
                    System.out.println("La salle de théâtre n'existe pas.");
                    Main.entrerPourContinuer();
                    return main(GPS);
                }
                break;
            } else {
                sortie = in.nextLine();
                if (sortie.equals("q")) return GPS;
            }
        }

        System.out.print("Jour de la séance (1 à 7) (q pour quitter): ");
        while (true) {
            if (in.hasNextInt()) {
                jour = in.nextInt();
                if (jour >= 1 && jour <= 7) break;
                else System.out.println("Le jour doit être compris entre 1 et 7.");
            } else {
                sortie = in.nextLine();
                if (sortie.equals("q")) return GPS;
            }
        }

        System.out.print("Heure de la séance (0 à 23) (q pour quitter): ");
        while (true) {
            if (in.hasNextInt()) {
                heure = in.nextInt();
                if (heure >= 0 && heure <= 23) break;
                else System.out.println("L'heure doit être comprise entre 0 et 23.");
            } else {
                sortie = in.next();
                if (sortie.equals("q")) return GPS;
            }
        }

        System.out.print("Minute de la séance (0 à 59) (q pour quitter): ");
        while (true) {
            if (in.hasNextInt()) {
                minute = in.nextInt();
                if (minute >= 0 && minute <= 59) break;
                else System.out.println("Les minutes doit être comprises entre 0 et 59.");
            } else {
                sortie = in.next();
                if (sortie.equals("q")) return GPS;
            }
        }
        System.out.println();
        System.out.println("Pièce: " + piece);
        System.out.println("Salle: " + salle);
        System.out.println("Jour: " + jour);
        System.out.print("Horaire: " + heure + "h");
        String norme = String.valueOf(minute);
        if (norme.length() == 1) System.out.print("0");
        System.out.print(minute);
        System.out.println();
        do {
            System.out.print("Confirmez-vous vos données ? (o/n)");
            sortie = in.next();
            if (sortie.equals("n")) return main(GPS);
        } while (!sortie.equals("o"));
        System.out.println();
        try {
            GPS.ajouterSeanceTheatre(piece, jour, new Creneau.Horaire(heure * 60 + minute), salle);
        } catch (NullPointerException | IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return main(GPS);
        }
        try {
            System.out.print(GPS.lesSeancesTheatre(piece));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        Main.entrerPourContinuer();
        return GPS;
    }
}
