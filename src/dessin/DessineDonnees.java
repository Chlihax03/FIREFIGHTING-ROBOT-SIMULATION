package dessin;

import gui.GUISimulator;

import classes.*;
import robots.*;

public class DessineDonnees {
    public static void drawDonnees(DonneesSimulation donnees, GUISimulator gui) {
        // affichage de la carte
        DessineCarte.drawCarte(donnees.getCarte(), gui);

        // Afichage des incendies 
        for (Incendie incendie : donnees.getIncendies()) {
            DessineIncendie.drawIncendie(incendie, gui, donnees.getCarte());
        }

        // Afichage des robots
        for (Robot robot : donnees.getRobots()) {
            DessineRobot.drawRobot(robot, gui, donnees.getCarte());
        }
    }
}