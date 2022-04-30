package GestionDeSpectacles.Main.Fonction;

import GestionDeSpectacles.Gestion.GestionProgrammationSemaine;
import GestionDeSpectacles.Horaire.Creneau;
import GestionDeSpectacles.Main.Main;

import java.util.Scanner;

/**
 * Vendre place film
 */
public class Main6 {
    public static GestionProgrammationSemaine main(GestionProgrammationSemaine GPS) {
        Scanner in = new Scanner(System.in);
        int placeTR = 0, placeTN = 0, film, jour = -1, heure = -1, minute = -1, placeDispo;
        String choix, sortie;

        try {
            System.out.println(GPS.lesFilms());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return GPS;
        }

        System.out.println("Veuillez choisir le numéro du film que vous aimeriez voir (q pour quitter)");
        while (true) {
            if (in.hasNextInt()) {
                film = in.nextInt();
                if (!GPS.existeFilm(film)) {
                    System.out.println("Le film n'existe pas.");
                    Main.entrerPourContinuer();
                    return main(GPS);
                }
                break;
            } else {
                if (in.nextLine().equals("q")) return GPS;
            }
        }
        try {
            String sf = GPS.lesSeancesFilm(film);
            if (sf == null) {
                System.out.println("Aucune séance programmée pour ce film.");
                Main.entrerPourContinuer();
                return main(GPS);
            }
            System.out.println("Veuillez choisir l'horaire et le jour du film qui vous conviendront le mieux.");
            System.out.print(sf);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return main(GPS);
        }
        while (jour < 1 || jour > 7) {
            System.out.print("Jour de séance (q pour quitter): ");
            if (in.hasNextInt()) {
                jour = in.nextInt();
                if (jour < 1 || jour > 7) System.out.println("Le jour est compris entre 1 et 7.");
            } else {
                if (in.nextLine().equals("q")) return GPS;
            }
        }
        while (heure < 0 || heure > 23) {
            System.out.print("Heure de séance (q pour quitter): ");
            if (in.hasNextInt()) {
                heure = in.nextInt();
                if (heure < 0 || heure > 23) System.out.println("L'heure est comprise entre 0 et 23.");
            } else {
                if (in.nextLine().equals("q")) return GPS;
            }
        }
        while (minute < 0 || minute > 59) {
            System.out.print("Minutes de séance (q pour quitter): ");
            if (in.hasNextInt()) {
                minute = in.nextInt();
                if (minute < 0 || minute > 59) System.out.println("Les minutes sont comprises entre 0 et 59.");
            } else {
                if (in.nextLine().equals("q")) return GPS;
            }
        }

        try {
            placeDispo = GPS.getNbPlacesDispo(film, jour, heure, minute);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return main(GPS);
        }
        if (placeDispo == 0) {
            System.out.println("La séance est complète, veuillez choisir une autre séance s'il vous plaît.");
            Main.entrerPourContinuer();
            return main(GPS);
        }
        System.out.println("Tarif réduit : famille nombreuse(+5 places), étudiant, personnes agées(60+)");
        System.out.println("Sous présentation d'un justificatif.");
        System.out.println("Souhaitez-vous prendre des places à tarif réduit (q pour quitter) ? (o/n)");
        do {
            choix = in.nextLine();
            if (choix.equals("q")) return GPS;
        } while (!(choix.equals("n") || choix.equals("o")));
        System.out.println("Place disponible :" + placeDispo);
        if (choix.equals("o")) {
            System.out.println("Combien voulez-vous de places à tarif réduit ? (q pour quitter)");
            while (true) {
                if (in.hasNextInt()) placeTR = in.nextInt();
                else if (in.nextLine().equals("q")) return GPS;
                if (placeTR < 0 || placeTR > placeDispo)
                    System.out.println("Nombre de places disponibles : 0 à " + placeDispo);
                else break;
            }
            placeDispo -= placeTR;
            if ((placeDispo == 0)) {
                System.out.println("La séance est complète, veuillez choisir une autre séance s'il vous plaît.");
                Main.entrerPourContinuer();
                return main(GPS);
            }
            System.out.println("Place disponible :" + placeDispo);
        }
        System.out.println("Combien de places à tarif normal voulez-vous ? (q pour quitter)");
        while (true) {
            if (in.hasNextInt()) placeTN = in.nextInt();
            else if (in.nextLine().equals("q")) return GPS;
            if (placeTN < 0 || placeTN > placeDispo)
                System.out.println("Nombre de places disponibles : 0 à " + placeDispo);
            else break;
        }
        double total = 0;
        try {
            total = GPS.totalPlaceAPayer(film, jour, new Creneau.Horaire(heure * 60 + minute), placeTN, placeTR);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Récapitulatif");
        System.out.println("Total à payer : " + total + "0€");
        System.out.println("Payé ? (o/n)");
        do {
            sortie = in.nextLine();
            if (sortie.equals("o")) {
                try {
                    GPS.vendrePlaceFilmTN(film, jour, new Creneau.Horaire(heure * 60 + minute), placeTN);
                    GPS.vendrePlaceFilmTR(film, jour, new Creneau.Horaire(heure * 60 + minute), placeTR);
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println(e.getMessage());
                    Main.entrerPourContinuer();
                    return main(GPS);
                }
                System.out.println("Vous venez de payer " + total + "0€");
                System.out.println("Bonne séance !");
                Main.entrerPourContinuer();
                return GPS;
            }
        } while (!sortie.equals("n"));
        return GPS;
    }
}
