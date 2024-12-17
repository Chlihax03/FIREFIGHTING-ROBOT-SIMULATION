package tests;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import chemins.DeplacerRobotMin;
import classes.DonneesSimulation;
import dessin.DessineDonnees;
import evenements.Evenement;

import java.awt.Color;
import robots.*;
import chemins.*;
import classes.*;
import lecteur.*;
import evenements.*;
import dessin.*;
import simulation.*;
import java.util.List;

import gui.GUISimulator;

public class TestEtape3 {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        // Nom du fichier de données
        String fichierDonnees = "cartes/spiralOfMadness-50x50.map";
        Case[][] matrice = new Case[1][1];
        Carte carte1 = new Carte(matrice, 1000);

        // Crée une instance de DonneesSimulation où les données seront stockées
        DonneesSimulation donnees = new DonneesSimulation(carte1);

        try {
            // Appel de la méthode de stockage pour lire le fichier et remplir les données
            StockageDonnees.stock(fichierDonnees, donnees);

            Color BackGroundColor = Color.BLACK;

            GUISimulator gui = new GUISimulator(1000, 1000, BackGroundColor);

            DessineDonnees.drawDonnees(donnees, gui);

            Robot robot = donnees.getRobots().get(1);

            List<Evenement> deplacements = DeplacerRobotMin.DeplacementMin(robot.getPosition(), donnees.getCase(3, 40), robot, donnees, 1);
            
            Scenario scenario = new Scenario();
            for (Evenement event : deplacements) {
                scenario.ajouteEvenement(event);
            }

            new Simulateur(fichierDonnees, scenario, gui,donnees, false);

        } catch (FileNotFoundException e) {
            System.out.println("Le fichier " + fichierDonnees + " est introuvable.");
        } catch (DataFormatException e) {
            System.out.println("Erreur de format des données : " + e.getMessage());
        }
    }
}
