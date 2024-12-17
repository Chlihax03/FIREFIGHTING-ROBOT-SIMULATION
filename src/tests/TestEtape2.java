package tests;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import classes.*;
import dessin.*;
import lecteur.*;
import robots.*;
import simulation.*;
import evenements.*;

import java.awt.Color;
import gui.GUISimulator;

public class TestEtape2 {
    public static void main(String[] args) {
        // Nom du fichier de données
        String fichierDonnees = "cartes/carteSujet.map";
        Case[][] matrice = new Case[1][1];
        Carte carte1 = new Carte(matrice, 1000);

        // Crée une instance de DonneesSimulation où les données seront stockées
        DonneesSimulation donnees = new DonneesSimulation(carte1);

        try {
            // Appel de la méthode de stockage pour lire le fichier et remplir les données
            StockageDonnees.stock(fichierDonnees, donnees);


            GUISimulator gui = new GUISimulator(1000, 1000, Color.BLACK);

            DessineDonnees.drawDonnees(donnees, gui);
            
            // Scenario 0 (KO)
            Robot robot = donnees.getRobots().get(0);

            Case position = robot.getPosition();
            int colonne = position.getColonne();
            int ligne = position.getLigne();

            
            Case dest1 = donnees.getCarte().getCase(ligne-1, colonne);
            Case dest2 = donnees.getCarte().getCase(ligne-2, colonne);
            Case dest3 = donnees.getCarte().getCase(ligne-3, colonne);
            Case dest4 = donnees.getCarte().getCase(ligne-4, colonne);

            Evenement ev1 = new Deplacer(1, robot, dest1, donnees);
            Evenement ev2 = new Deplacer(2, robot, dest2, donnees);
            Evenement ev3 = new Deplacer(3, robot, dest3, donnees);
            Evenement ev4 = new Deplacer(4, robot, dest4, donnees);


            Scenario scenario = new Scenario();
            scenario.ajouteEvenement(ev1);
            scenario.ajouteEvenement(ev2);
            scenario.ajouteEvenement(ev3);
            scenario.ajouteEvenement(ev4);

            // Scenario 1 (OK)
            Robot robot1 = donnees.getRobots().get(1);

            Case position1 = robot1.getPosition();
            int colonne1 = position1.getColonne();
            int ligne1 = position1.getLigne();
            // Deplacement vers le nord (case (5,5))
            Case newdest1 = donnees.getCarte().getCase(ligne1-1, colonne1);
            // Intervention sur la case ou il se trouve
            scenario.ajouteEvenement(new Deplacer(5, robot1, newdest1, donnees));
            scenario.ajouteEvenement(new DeverserEau(6, robot1, Math.min(robot1.getCapacite(),donnees.getIncendie(newdest1).getIntensite()) , donnees));
            int newIntensite = Math.abs(robot1.getCapacite()-donnees.getIncendie(newdest1).getIntensite());
            scenario.ajouteEvenement(new DiminuerIntensite(7, donnees, donnees.getIncendie(newdest1), newIntensite));
            // Deplacement 2 fois vers l'ouest
            Case newdest2 = donnees.getCarte().getCase(ligne1-1, colonne1-1);
            Case newdest3 = donnees.getCarte().getCase(ligne1-1, colonne1-2);
            scenario.ajouteEvenement(new Deplacer(7, robot1, newdest2, donnees));
            scenario.ajouteEvenement(new Deplacer(8, robot1, newdest3, donnees));
            // Remplissage du reservoir du robot
            scenario.ajouteEvenement(new RemplirEau(9, robot1, donnees));
            // Deplacement 2 fois vers l'est
            Case newdest4 = donnees.getCarte().getCase(ligne1-1, colonne1-1);
            Case newdest5 = donnees.getCarte().getCase(ligne1-1, colonne1);
            scenario.ajouteEvenement(new Deplacer(10, robot1, newdest4, donnees));
            scenario.ajouteEvenement(new Deplacer(11, robot1, newdest5, donnees));
            // Intervention sur la case ou il se trouve
            scenario.ajouteEvenement(new Deplacer(12, robot1, newdest5, donnees));
            scenario.ajouteEvenement(new DeverserEau(13, robot1, Math.min(robot1.getCapacite(),donnees.getIncendie(newdest1).getIntensite()) , donnees));
            int newIntensite1 = newIntensite - Math.min(robot1.getCapacite(),newIntensite);
            scenario.ajouteEvenement(new DiminuerIntensite(13, donnees, donnees.getIncendie(newdest1), newIntensite1));

            
            new Simulateur(fichierDonnees, scenario, gui, donnees, true);
            System.out.println("Apres les 4 premiers clics sur suivant se déclenche le scenario OK.");


        } catch (FileNotFoundException e) {
            System.out.println("Le fichier " + fichierDonnees + " est introuvable.");
        } catch (DataFormatException e) {
            System.out.println("Erreur de format des données : " + e.getMessage());
        }
    }
}
