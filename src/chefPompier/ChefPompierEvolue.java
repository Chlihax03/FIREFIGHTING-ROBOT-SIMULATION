package chefPompier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classes.Case;
import classes.DonneesSimulation;
import classes.Incendie;
import evenements.Evenement;
import evenements.Scenario;
import intervention.EteindreIncendie;
import intervention.ResultatIntervention;
import robots.Robot;
import simulation.Simulateur;
import chemins.*;

public class ChefPompierEvolue {
    DonneesSimulation donnees;
    Simulateur simulateur;

    public ChefPompierEvolue(DonneesSimulation donnees, Simulateur simulateur) {
        this.donnees = donnees;
        this.simulateur = simulateur;
    }

    public Scenario AffecterRobotsEvolue() {
        List<Evenement> events = new ArrayList<>();
        List<Robot> robots = donnees.getRobots();
        List<Incendie> incendies = donnees.getIncendies();

        HashMap<Robot, HashMap<RobotIncendieKey, Case>> robotToIncendieVersEauMap = new HashMap<>();
        for (Robot robot : robots) {
            // Création de la HashMap pour ce robot
            HashMap<RobotIncendieKey, Case> incendieVersEauMap = EauPlusProche.creerMapIncendieEau(
                donnees.getIncendies(),
                donnees.getCasesEau(),
                robot,
                donnees
            );
        
            // Stocker la HashMap dans la map globale associée à ce robot
            robotToIncendieVersEauMap.put(robot, incendieVersEauMap);
        
            
        }


        // On récupère les positions initiales des robots
        Map<Robot, Case> positionsInitales = new HashMap<>();
        for (Robot robot: robots) {
            positionsInitales.put(robot, robot.getPosition());
        }

        for (Incendie incendie : incendies) {
            if (incendie.getIntensite() <= 0) {
                continue;  // Passer l'incendie si déjà éteint
            }
    
            // Trouver un robot disponible qui peut atteindre l'incendie
            Robot robotChoisi = null;
            ResultatIntervention resultat = null;

            EteindreIncendie pompier = new EteindreIncendie();
            Case positionRobot = null;

            // On recherche le robot avec le temps d'intervention minimal
            long tempsMin = Long.MAX_VALUE;
            for (Robot robot : robots) {
                HashMap<RobotIncendieKey, Case> incendieVersEauMap = robotToIncendieVersEauMap.get(robot);
                RobotIncendieKey key = new RobotIncendieKey(robot, incendie.getPosition());
                Case positionRemplissageEau = incendieVersEauMap.get(key);

                pompier = new EteindreIncendie();
                positionRobot = robot.getPosition();
                ResultatIntervention resultatCourant = pompier.intervenir(robot, positionRobot, incendie.getPosition(), positionRemplissageEau,donnees, robot.getDate());

                if (resultatCourant==null) {
                    continue; // on fait rien si le robot ne peut pas intervenir
                }

                // On choisi le robot qui terminera l'intervention le premier
                long dureeIntervention = pompier.getDureeEteindreIncendie();
                long tempsFinIntervention = robot.getDate() + dureeIntervention;
                if (tempsFinIntervention < tempsMin) {
                    tempsMin = tempsFinIntervention;
                    robotChoisi = robot;
                    resultat = resultatCourant;
                }
            }
            
            // Si aucun robot n'est trouvé pour éteindre cet incendie, passer au prochain incendie
            if (robotChoisi == null) {
                continue;
            }
            events.addAll(resultat.getEvents());
            robotChoisi.setPosition(resultat.getPositionFinale());
            robotChoisi.setDate(tempsMin + 1); // Ajouter 1 pour éviter chevauchements
        }

        // On rétablit le volume d'eau et la position de chaque robot
        for (Robot robot : robots) {
            robot.setVolumeEau(robot.getCapacite());
            robot.setPosition(positionsInitales.get(robot));
        }
    
        // Création du scénario et ajout des événements générés
        Scenario scenario = new Scenario();
        scenario.ajouteEvenements(events);
        return scenario;
    }
    
}


