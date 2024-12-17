package dessin;

import gui.GUISimulator;
import gui.Text;
import classes.*;
import robots.*;
import helper.Helper;
import java.awt.Color;

public class DessineRobot {
    public static void drawRobot(Robot robot, GUISimulator gui, Carte carte) {
        Color robotColor = null;
        // Définir la couleur en fonction du type de robot
        if (robot instanceof RobotDrone) {
            robotColor = new Color(0, 191, 255); // Bleu ciel pour le drone
        } else if (robot instanceof RobotChenille) {
            robotColor = new Color(139, 69, 19); // Marron pour les chenilles
        } else if (robot instanceof RobotPattes) {
            robotColor = new Color(34, 139, 34); // Vert pour les pattes
        } else if (robot instanceof RobotRoues) {
            robotColor = new Color(255, 69, 0); // Rouge-orange pour les roues
        } else {
            robotColor = Color.GRAY; // Couleur par défaut
        }

        int caseSize = Helper.transform(carte.getNbColonnes());
        int centre_x = robot.getPosition().getColonne() * caseSize + caseSize / 2;
        int centre_y = robot.getPosition().getLigne() * caseSize + caseSize / 2;

        // Crée un cercle pour représenter le robot
        gui.addGraphicalElement(new gui.Oval(
                centre_x,
                centre_y,
                robotColor,
                robotColor,
                caseSize / 2));

        // Ajoute le symbole du type de robot
        Text symbolText = new Text(
                centre_x,
                centre_y,
                Color.WHITE, // Texte blanc pour contraster avec le fond coloré
                getRobotSymbol(robot));
        gui.addGraphicalElement(symbolText);
    }

    private static String getRobotSymbol(Robot robot) {
        if (robot instanceof RobotDrone) {
            return "D";
        } else if (robot instanceof RobotChenille) {
            return "Ch";
        } else if (robot instanceof RobotPattes) {
            return "P";
        } else if (robot instanceof RobotRoues) {
            return "R";
        }
        return "?";
    }

}
