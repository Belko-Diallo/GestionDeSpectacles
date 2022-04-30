package GestionDeSpectacles.Main.Fonction;

import GestionDeSpectacles.Gestion.GestionProgrammationSemaine;
import GestionDeSpectacles.Main.Main;

import java.util.Scanner;

/**
 * Ajouter Interprète
 */
public class Main3 {
    public static GestionProgrammationSemaine main(GestionProgrammationSemaine GPS) {
        Scanner in = new Scanner(System.in);
        int choix;
        String interprete, sortie;
        int nbErreur = 0;

        try {
            System.out.print(GPS.lesFilms());
        } catch (RuntimeException e) {
            nbErreur++;
        }
        try {
            System.out.print(GPS.lesPieces());
        } catch (RuntimeException e) {
            nbErreur++;
        }
        if (nbErreur == 2) {
            System.out.println("Il n'existe aucun film ni pièce de théâtre.");
            Main.entrerPourContinuer();
            return GPS;
        }
        System.out.print("Entrez le nom de l'interprète à ajouter (q pour quitter) : ");
        interprete = in.nextLine();
        if (interprete.equals("q")) return GPS;

        System.out.println("Choississez le numéro du spectacle auquel vous voulez ajouter un interprète.(q pour quitter)");
        while (true) {
            if (in.hasNextInt()) {
                choix = in.nextInt();
                try {
                    GPS.ajouterInterprete(choix, interprete);
                    System.out.println(GPS.lesInterpretes(choix));
                } catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    Main.entrerPourContinuer();
                    return main(GPS);
                }
                break;
            } else {
                sortie = in.nextLine();
                if (sortie.equals("q")) return GPS;
            }
        }
        Main.entrerPourContinuer();
        return GPS;
    }
}
