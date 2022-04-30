package GestionDeSpectacles.Main.Fonction;

import GestionDeSpectacles.Gestion.GestionProgrammationSemaine;
import GestionDeSpectacles.Main.Main;

import java.util.Scanner;

/**
 * Chiffre d'affaires et taux de remplissage
 */
public class Main8 {
    public static GestionProgrammationSemaine main(GestionProgrammationSemaine GPS) {
        Scanner in = new Scanner(System.in);
        int numSpectacle, nbErreur = 0;
        double chiffre = 0, taux = 0;

        try {
            System.out.print(GPS.lesFilms());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            nbErreur++;
        }
        try {
            System.out.print(GPS.lesPieces());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            nbErreur++;
        }
        if (nbErreur == 2) {
            System.out.println("Retour au menu principal.");
            Main.entrerPourContinuer();
            return GPS;
        }

        System.out.print("Choississez le numéro du spectacle pour consulter ses statistiques (q pour quitter):");
        while (true) {
            if (in.hasNextInt()) {
                numSpectacle = in.nextInt();
                try {
                    chiffre = GPS.chiffreAffaires(numSpectacle);
                    taux = GPS.getTauxRemplissage(numSpectacle);
                } catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            } else {
                String sortie = in.nextLine();
                if (sortie.equals("q")) return GPS;
            }
        }
        System.out.println("Le chiffre d'affaire est de " + chiffre + "0€ pour un taux de remplissage de " + taux + "%.");
        Main.entrerPourContinuer();
        return GPS;
    }
}
