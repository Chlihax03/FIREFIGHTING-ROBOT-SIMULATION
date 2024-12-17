package evenements;

import classes.*;
import robots.*;

public class Deplacer extends Evenement {
    private Robot robot;
    private Case destination;
    private DonneesSimulation donnees;

    public Deplacer(long date, Robot robot, Case destination, DonneesSimulation donnees) {
        super(date, donnees);
        this.robot = robot;
        this.destination = destination;
        this.donnees = donnees;
    }

    public Robot getRobot() {
        return this.robot;
    }

    @Override
    public void execute() {
    try {
        robot.setPosition(this.destination);
        robot.setVitesse(robot.getVitesseNature(this.destination.getNatureTerrain()));
    } catch (NullPointerException e) {
        System.out.println("Destination invalide.");
    }
    }
        

    @Override
    public long getDuree() {
        Carte carte = this.donnees.getCarte();
        int vitesse1 = robot.getVitesse();
        int vitesse2 = robot.getVitesseNature(this.destination.getNatureTerrain());
        double vitesseMoyenne = ((double)(vitesse1 + vitesse2)) / (3.6*2.0); 
        // On divise par 3.6 pour convertir la vitesse de km/h à m/s
        return (long)Math.ceil(((double)carte.getTailleCases()) /vitesseMoyenne);
    }

    @Override
    public String toString() {
        return("Deplacement de " + robot + "vers " + " a la date : " + this.getDate());
    }
}

