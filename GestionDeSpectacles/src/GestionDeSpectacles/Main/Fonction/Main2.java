package GestionDeSpectacles.Main.Fonction;

import GestionDeSpectacles.Gestion.GestionProgrammationSemaine;
import GestionDeSpectacles.Main.Main;

import java.util.Scanner;

/**
 * Ajouter pièce de théâtre
 */
public class Main2 {
    public static GestionProgrammationSemaine main(GestionProgrammationSemaine GPS) {
        Scanner in = new Scanner(System.in);
        String titre = "", metteur = "", sortie;
        int nbEntracte = 0;

        System.out.println("Afin d'ajouter une pièce de théâtre, vous aurez besoin d'un titre, d'un metteur en scène et d'un nombre d'entractes.");

        System.out.print("Quel est le titre de la pièce de théâtre ? (q pour quitter) ");
        if (in.hasNext()) titre = in.nextLine();
        if (titre.equals("q")) return GPS;

        System.out.print("Quel est le nom du metteur en scène de la pièce de théâtre ? (q pour annuler) ");
        if (in.hasNext()) metteur = in.nextLine();
        if (metteur.equals("q")) return GPS;

        while (nbEntracte < 1) {
            System.out.print("Quel est le nombre d'entractes de la pièce de théâtre ? (q pour quitter) ");
            if (in.hasNextInt()) {
                nbEntracte = in.nextInt();
                if (nbEntracte < 1) System.out.println("La pièce de théâtre doit au moins contenir une entracte !");
            } else {
                sortie = in.nextLine();
                if (sortie.equals("q")) return GPS;
                else System.out.println("Entrée incorrecte.");
            }
        }
        System.out.println();
        System.out.print("Pièce : " + titre);
        System.out.print(", Metteur en scène : " + metteur);
        System.out.println(", Nombre d'entractes : " + nbEntracte);
        do {
            System.out.print("Confirmez-vous vos données ? (o/n)");
            sortie = in.next();
            if (sortie.equals("n")) return main(GPS);
        } while (!sortie.equals("o"));
        try {
            GPS.ajouterPiece(titre, metteur, nbEntracte);
            System.out.print(GPS.lesPieces());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        Main.entrerPourContinuer();
        return GPS;
    }
}
