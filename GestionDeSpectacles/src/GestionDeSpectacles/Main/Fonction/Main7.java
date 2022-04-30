package GestionDeSpectacles.Main.Fonction;

import GestionDeSpectacles.Gestion.GestionProgrammationSemaine;
import GestionDeSpectacles.Main.Main;

import java.util.Scanner;

/**
 * Vendre place de théâtre
 */
public class Main7 {
    public static GestionProgrammationSemaine main(GestionProgrammationSemaine GPS) {
        Scanner in = new Scanner(System.in);
        int nbFauteuil = 0, nbPlace = 0, piece, jour = -1, placeDispo, fauteuilDispo;
        String sortie;

        try {
            System.out.print(GPS.lesPieces());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return GPS;
        }

        System.out.println("Veuillez choisir le numéro de la pièce de théâtre que vous aimeriez voir (q pour quitter)");
        while (true) {
            if (in.hasNextInt()) {
                piece = in.nextInt();
                if (!GPS.existePiece(piece)) {
                    System.out.println("La pièce de théâtre n'existe pas.");
                    Main.entrerPourContinuer();
                    return main(GPS);
                }
                break;
            } else {
                if (in.next().equals("q")) return GPS;
            }
        }
        try {
            String st = GPS.lesSeancesTheatre(piece);
            if (st == null) {
                System.out.println("Aucune séance programmée pour cette pièce de théâtre.");
                Main.entrerPourContinuer();
                return main(GPS);
            }
            System.out.println("Veuillez choisir le jour de la pièce de théâtre qui vous conviendrait le mieux.");
            System.out.print(st);
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
                if (in.next().equals("q")) return GPS;
            }
        }

        try {
            placeDispo = GPS.getNbPlacesDispo(piece, jour);
            fauteuilDispo = GPS.getNbFauteuilsDispo(piece, jour);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return main(GPS);
        }
        if (placeDispo == 0 && fauteuilDispo == 0) {
            System.out.println("La séance est complète, veuillez choisir une autre séance s'il vous plaît.");
            return main(GPS);
        }
        String choix = "";
        if (fauteuilDispo > 0) {
            System.out.println("Place premium : Fauteuils grand luxe.");
            System.out.println("Souhaitez-vous prendre des places grand confort (q pour quitter) ? (o/n)");
            do {
                choix = in.next();
                if (choix.equals("q")) return GPS;
            } while (!(choix.equals("n") || choix.equals("o")));
        }
        if (choix.equals("o")) {
            System.out.println("Fauteuil disponible :" + fauteuilDispo);
            System.out.println("Combien voulez-vous de fauteuils ? (q pour quitter)");
            while (true) {
                if (in.hasNextInt()) {
                    nbFauteuil = in.nextInt();
                } else {
                    sortie = in.next();
                    if (sortie.equals("q")) return GPS;
                }
                if (nbFauteuil < 0 || nbFauteuil > fauteuilDispo)
                    System.out.println("Nombre de fauteuils disponibles : 0 à " + fauteuilDispo);
                else break;
            }
        }
        if ((placeDispo == 0)) {
            System.out.println("Les places standards ont toutes été vendues.");
            System.out.println("Veuillez, si vous le voulez choisir un autre jour de prestation.");
            if (fauteuilDispo > 0) {
                System.out.println("Nous proposons des fauteuils grand confort à bon prix pour ce même spectacle.");
                System.out.println("Fauteuil disponible : " + fauteuilDispo);
            }
            Main.entrerPourContinuer();
            return main(GPS);
        }
        System.out.println("Place disponible :" + placeDispo);
        System.out.println("Combien de places voulez-vous ? (q pour quitter)");
        while (true) {
            if (in.hasNextInt()) {
                nbPlace = in.nextInt();
            } else {
                if (in.nextLine().equals("q")) return GPS;
            }
            if (nbPlace < 0 || nbPlace > placeDispo)
                System.out.println("Nombre de places disponibles : 0 à " + placeDispo);
            else break;
        }
        double total;
        try {
            total = GPS.totalPlaceAPayer(piece, jour, nbPlace, nbFauteuil);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            Main.entrerPourContinuer();
            return GPS;
        }
        System.out.println("Récapitulatif");
        System.out.println("Total à payer : " + total + "0€");
        System.out.println("Payé ? (o/n)");
        do {
            sortie = in.nextLine();
            if (sortie.equals("o")) {
                try {
                    GPS.vendrePlacePieceTN(piece, jour, nbPlace);
                    GPS.vendrePlaceFauteuilPiece(piece, jour, nbFauteuil);
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
