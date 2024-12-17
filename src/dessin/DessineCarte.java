package dessin;

import gui.GUISimulator;
import classes.*;

public class DessineCarte {
    public static void drawCarte(Carte carte, GUISimulator gui) {
        for (int i =0; i<carte.getNbLignes(); i++) {
            for (int j=0; j<carte.getNbColonnes(); j++) {
                DessineCase.drawCase(carte.getCase(i, j), gui, carte);
            }
        }
    }
}