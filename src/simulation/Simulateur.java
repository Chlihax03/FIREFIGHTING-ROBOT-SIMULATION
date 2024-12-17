package simulation;

import gui.GUISimulator;
import gui.Simulable;
import dessin.*;
import classes.*;
import robots.*;
import evenements.*;

public class Simulateur implements Simulable {
    private long dateSimulation;
    private Scenario scenario;
    private GUISimulator gui;
    private DonneesSimulation donnees;
    private String fichier;
    private Boolean StratEvoluee;

    public Simulateur(String fichier, Scenario scenario, GUISimulator gui, DonneesSimulation donnees, Boolean StratEvoluee) {
        this.dateSimulation = 0;
        this.scenario = scenario;
        this.gui = gui;
        gui.setSimulable(this);
        this.donnees = donnees;
        this.fichier = fichier;
        this.StratEvoluee = StratEvoluee;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    private void incrementeDate() {
        this.dateSimulation++;
    }

    @Override
    public void next() {
        incrementeDate();
        if (simulationTerminee()) {
            System.out.println("Simulation terminée");
            return;
        }
        
        while (!this.scenario.getEvenements().isEmpty() && scenario.getEvenements().peek().getDate() <= dateSimulation) {
            Evenement e = scenario.getEvenements().poll();
            e.execute();
            
            // Si l'événement est de type Deplacer, afficher la position actuelle du robot
            if (e instanceof Deplacer) {
                ((Deplacer) e).getRobot();
            }
            // Mise à jour de l'affichage
            this.gui.reset();
            DessineDonnees.drawDonnees(donnees, this.gui);
        }
        
        
    }

    @Override
    public void restart() {
        LancerSimu.lancerSimu(fichier, this.gui, this.StratEvoluee);
        System.out.println("Restart");
    }

    private boolean simulationTerminee() {
        return this.scenario.getEvenements().isEmpty();
    }

    public long getDateSimulation() {
        return this.dateSimulation;
    }
} 