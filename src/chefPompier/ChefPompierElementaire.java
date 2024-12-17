package chefPompier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classes.*;
import robots.*;
import evenements.*;
import simulation.*;
import intervention.*;
import chemins.*;

public class ChefPompierElementaire {
    DonneesSimulation donnees;
    Simulateur simulateur;

    public ChefPompierElementaire(DonneesSimulation donnees, Simulateur simulateur) {
        this.donnees = donnees;
        this.simulateur = simulateur;
    }

    public Scenario AffecterRobotsElementaire() {
        List<Evenement> events = new ArrayList<>();
        List<Robot> robots = donnees.getRobots();
        List<Incendie> incendies = donnees.getIncendies();

        // On récupère les positions initiales des robots
        Map<Robot, Case> positionsInitiales = new HashMap<>();
        for (Robot robot : robots) {
            positionsInitiales.put(robot, robot.getPosition());
        }

        for (Incendie incendie : incendies) {
            if (incendie.getIntensite() <= 0) {
                continue; // Passer l'incendie si déjà éteint
            }

            // Trouver un robot disponible qui peut atteindre l'incendie
            Robot robotChoisi = null;
            ResultatIntervention resultat = null;

            EteindreIncendie pompier = new EteindreIncendie();
            Case positionRobot = null;

            for (Robot robot : robots) {
                pompier = new EteindreIncendie();
                positionRobot = robot.getPosition();

                for (Case positionEau : donnees.getCasesEau()) {
                    List<Evenement> events3 = new ArrayList<>();
                    Case positionRemplissageEau = positionEau;

                    if (robot instanceof RobotDrone) {
                        // Si le robot est un drone, il doit se déplacer directement sur une case d'eau
                        events3 = DeplacerRobotMin.DeplacementMin(incendie.getPosition(), positionEau, robot, donnees, 0);
                        positionRemplissageEau = positionEau;
                    } else {
                        // Si le robot n'est pas un drone, il doit aller sur une case adjacente à l'eau
                        for (Direction dir : Direction.values()) {
                            positionRemplissageEau = donnees.getCarte().getVoisin(positionEau, dir);
                            if (positionRemplissageEau != null) {
                                events3 = DeplacerRobotMin.DeplacementMin(incendie.getPosition(), positionRemplissageEau, robot, donnees, 0);
                                if (!events3.isEmpty()) {
                                    break;
                                }
                            }
                        }
                    }

                    resultat = pompier.intervenir(robot, positionRobot, incendie.getPosition(), positionRemplissageEau, donnees, robot.getDate());

                    // Si un résultat non nul est obtenu, ce robot peut éteindre l'incendie
                    if (resultat != null) {
                        robotChoisi = robot;
                        break;
                    }
                }

                if (robotChoisi != null) {
                    break; // Un robot a été trouvé pour cet incendie
                }
            }

            // Si aucun robot n'est trouvé pour éteindre cet incendie, passer au prochain incendie
            if (robotChoisi == null) {
                continue;
            }

            events.addAll(resultat.getEvents());
            robotChoisi.setPosition(resultat.getPositionFinale());
            robotChoisi.setDate(robotChoisi.getDate() + pompier.getDureeEteindreIncendie() + 1); // Ajouter 1 pour éviter chevauchements
        }

        // On rétablit le volume d'eau et la position de chaque robot
        for (Robot robot : robots) {
            robot.setVolumeEau(robot.getCapacite());
            robot.setPosition(positionsInitiales.get(robot));
        }

        // Création du scénario et ajout des événements générés
        Scenario scenario = new Scenario();
        scenario.ajouteEvenements(events);
        return scenario;
    }
}
