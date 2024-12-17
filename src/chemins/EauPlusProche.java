package chemins;

import classes.*;
import robots.*;
import evenements.*;
import helper.*;

import java.util.HashMap;
import java.util.List;

public class EauPlusProche {
    public static Case eauPlusProche(Robot robot, Case positionIncendie, List<Case> casesEau, DonneesSimulation donnees) {
        Case positionRemplissageEau = donnees.getCasesEau().get(0);  
        long tempsMin = Long.MAX_VALUE;

        for (Case positionEau : casesEau) {
            if (robot instanceof RobotDrone) {
                List<Evenement> eauPlusProche = DeplacerRobotMin.DeplacementMin(positionIncendie, positionEau, robot, donnees, 0);
                if (!eauPlusProche.isEmpty()) {
                    long temps = Helper.CalculDureeEvents(eauPlusProche);
                    if (temps < tempsMin) {
                        tempsMin = temps;
                        positionRemplissageEau = positionEau;
                    }
                }
            } else { 
                for (Direction dir : Direction.values()) {
                    Case voisinEau = donnees.getCarte().getVoisin(positionEau, dir);
                    if (voisinEau != null && voisinEau.getNatureTerrain() != NatureTerrain.EAU) {
                        List<Evenement> eauPlusProche = DeplacerRobotMin.DeplacementMin(positionIncendie, voisinEau, robot, donnees, 0);
                        if (!eauPlusProche.isEmpty()) {
                            long temps = Helper.CalculDureeEvents(eauPlusProche);
                            if (temps < tempsMin) {
                                tempsMin = temps;
                                positionRemplissageEau = voisinEau;
                            }
                        }
                    }
                }
            }
        }

        return positionRemplissageEau;
    }


    public static HashMap<RobotIncendieKey, Case> creerMapIncendieEau(List<Incendie> Incendies, List<Case> casesEau, Robot robot, DonneesSimulation donnees) {
        HashMap<RobotIncendieKey, Case> incendieVersEauMap = new HashMap<>();

        for (Incendie Incendie : Incendies) {
            Case caseEauProche;
            Case positionIncendie = Incendie.getPosition();

            caseEauProche = EauPlusProche.eauPlusProche(robot, positionIncendie, casesEau, donnees);
            if (caseEauProche != null) {
                RobotIncendieKey key = new RobotIncendieKey(robot, positionIncendie);
                incendieVersEauMap.put(key, caseEauProche);
            }
        }

        return incendieVersEauMap;
    }
}

    
