package dessin;

import java.awt.*;
import gui.GUISimulator;
import gui.Text;
import helper.Helper;
import gui.ImageElement;

import classes.*;

public class DessineIncendie {
    public static void drawIncendie(Incendie incendie, GUISimulator gui, Carte carte) {
        if (incendie.getIntensite() != 0) {
            // Taille de la case pour représenter l'incendie
            int intensitySize = Helper.transform(carte.getNbColonnes());

            // Coordonnées de la case où se trouve l'incendie
            int centre_x = incendie.getPosition().getColonne() * intensitySize;
            int centre_y = incendie.getPosition().getLigne() * intensitySize;

            // chemin vers l'image utilisée pour modéliser une incendie
            String imagePath = "photos/fire.png";

            // Ajoute l'image du feu en utilisant ImageElement
            gui.addGraphicalElement(
                    new ImageElement(
                            centre_x, // position x
                            centre_y, // position y
                            imagePath, // chemin de l'image
                            intensitySize, // largeur
                            intensitySize, // hauteur
                            gui // ImageObserver
                    ));

            // Affiche l'intensité sous forme de texte
            gui.addGraphicalElement(
                    new Text(
                            centre_x + intensitySize / 2,
                            centre_y + intensitySize / 2,
                            Color.BLACK,
                            "" + incendie.getIntensite()));
        }
    }
}