package GestionDeSpectacles.Main.Fonction;

import GestionDeSpectacles.Gestion.GestionProgrammationSemaine;
import GestionDeSpectacles.Main.Main;

import java.util.Scanner;

/**
 * Ajouter film
 */
public class Main1 {
    public static GestionProgrammationSemaine main(GestionProgrammationSemaine GPS) {
        Scanner in = new Scanner(System.in);
        String titre = "", reali = "", sortie;
        int duree = 0;

        System.out.println("Afin d'ajouter un film, vous aurez besoin d'un titre, d'un réalisateur et d'une durée.");

        System.out.print("Quel est le titre du film ? (q pour quitter) ");
        if (in.hasNext()) titre = in.nextLine();
        if (titre.equals("q")) return GPS;

        System.out.print("Quel est le nom du réalisateur du film ? (q pour quitter) ");
        if (in.hasNext()) reali = in.nextLine();
        if (reali.equals("q")) return GPS;

        while (duree < 1) {
            System.out.print("Quel est la durée du film en minute ? (q pour quitter) ");
            if (in.hasNextInt()) {
                duree = in.nextInt();
                if (duree < 1) System.out.println("La durée du film doit être supérieur à 1 minute !");
            } else {
                sortie = in.nextLine();
                if (sortie.equals("q")) return GPS;
                else System.out.println("Entrée incorrecte.");
            }
        }
        System.out.println();
        System.out.print("Film : " + titre);
        System.out.print(", Réalisateur : " + reali);
        System.out.println(", Duree : " + duree);
        do {
            System.out.print("Confirmez-vous vos données ? (o/n)");
            sortie = in.next();
        } while (!(sortie.equals("o") || sortie.equals("n")));
        if (sortie.equals("n")) return main(GPS);
        try {
            GPS.ajouterFilm(titre, reali, duree);
            System.out.print(GPS.lesFilms());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        Main.entrerPourContinuer();
        return GPS;
    }
}
