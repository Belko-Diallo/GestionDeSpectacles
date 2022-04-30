package GestionDeSpectacles.Main;

import GestionDeSpectacles.Gestion.GestionProgrammationSemaine;
import GestionDeSpectacles.Main.Fonction.*;

import java.util.Scanner;

/**
 * Menu principal
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Maison des Arts et de la Culture de la ville de Tarascon !");
        GestionProgrammationSemaine GPS = new GestionProgrammationSemaine();
        Scanner in = new Scanner(System.in);
        int choix = -1;
        do {
            menu();
            if (in.hasNextInt()) {
                choix = in.nextInt();
                switch (choix) {
                    case 0:
                        Main0.main(GPS);
                        break;// réinitialisation
                    case 1:
                        Main1.main(GPS);
                        break;// ajouter film
                    case 2:
                        Main2.main(GPS);
                        break;// ajouter pièce
                    case 3:
                        Main3.main(GPS);
                        break;// ajouter interprète
                    case 4:
                        Main4.main(GPS);
                        break;//ajouter séance film;
                    case 5:
                        Main5.main(GPS);
                        break;//ajouter séance théâtre;
                    case 6:
                        Main6.main(GPS);
                        break;//vendre place film;
                    case 7:
                        Main7.main(GPS);
                        break;//vendre place théâtre;
                    case 8:
                        Main8.main(GPS);
                        break;//chiffre affaire/ remplissage spectacle;
                    case 9:
                        break;
                }
            } else {
                in.next();
            }
        } while (choix != 9);
        System.out.println("Au revoir, nous vous remercions !");
    }

    /**
     * Affiche le menu principal.
     */
    public static void menu() {
        System.out.println();
        System.out.println("Réinitialiser programmation (0)");
        System.out.println("Ajouter un film (1)");
        System.out.println("Ajouter une pièce de théâtre (2)");
        System.out.println("Ajouter un nom d'interprète à un spectacle (3)");
        System.out.println("Ajouter une séance pour un film (4)");
        System.out.println("Ajouter une séance pour une pièce de théâtre (5)");
        System.out.println("Vendre des places pour un film (6)");
        System.out.println("Vendre des places pour une pièce de théâtre (7)");
        System.out.println("Consulter le chiffre d'affaires et le taux de remplissage d'un spectacle (8)");
        System.out.println("Quitter le programme (9)");
        System.out.println();
    }

    /**
     * Méthode placée avant le retour au menu afin de pouvoir lire les dernières lignes affichées.
     */
    public static void entrerPourContinuer() {
        Scanner in = new Scanner(System.in);
        System.out.print("Tapez entrer pour continuer : ");
        while (true) {
            if (in.hasNextLine()) break;
        }
    }
}