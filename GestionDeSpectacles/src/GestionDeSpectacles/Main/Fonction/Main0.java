package GestionDeSpectacles.Main.Fonction;

import GestionDeSpectacles.Gestion.GestionProgrammationSemaine;
import GestionDeSpectacles.Main.Main;

/**
 * Réinitialiser Programmation Semaine
 */
public class Main0 {
    public static GestionProgrammationSemaine main(GestionProgrammationSemaine GPS) {
        GPS.reinitialiserProgrammation();
        System.out.println("Programmation réinitialisée.");
        Main.entrerPourContinuer();
        return GPS;
    }
}
