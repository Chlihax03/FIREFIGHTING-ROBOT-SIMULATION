package dessin;

import classes.*;
import gui.GUISimulator;
import gui.ImageElement;
import helper.Helper;

public class DessineCase {
    public static void drawCase(Case ccase, GUISimulator gui, Carte carte) {
        int cote = Helper.transform(carte.getNbColonnes());
        int centre_x = ccase.getColonne() * cote;
        int centre_y = ccase.getLigne() * cote;
        String imagePath = "";
        switch (ccase.getNatureTerrain()) {
            case EAU:
                imagePath = "photos/eau.png";
                break;
            case FORET:
                imagePath = "photos/foret.png";
                break;
            case ROCHE:
                imagePath = "photos/roche.png";
                break;
            case TERRAIN_LIBRE:
                imagePath = "photos/terrainlibre.png";
                break;
            case HABITAT:
                imagePath = "photos/habitat.png";
                break;
        }
        gui.addGraphicalElement(
                new ImageElement(
                        centre_x, // position x
                        centre_y, // position y
                        imagePath, // chemin de l'image
                        cote, // largeur
                        cote, // hauteur
                        gui // ImageObserver
                ));
    }
}