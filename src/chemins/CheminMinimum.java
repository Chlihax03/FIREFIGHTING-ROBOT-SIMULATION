package chemins;

import java.util.*;

import classes.*;
import robots.*;

public class CheminMinimum {
    
    public List<Case> trouverCheminMinimum(Carte carte, Case depart, Case arrivee, Robot robot) {
        int nbLignes = carte.getNbLignes();
        int nbColonnes = carte.getNbColonnes();
        
        // Initialisation
        Map<Case, Double> temps = new HashMap<>();  // On utilise maintenant le temps au lieu de la distance
        Map<Case, Case> predecesseurs = new HashMap<>();
        PriorityQueue<Case> frontiere = new PriorityQueue<>((c1, c2) -> {
            Double temps1 = temps.get(c1);
            Double temps2 = temps.get(c2);
            return Double.compare(temps1 != null ? temps1 : Double.MAX_VALUE, 
                                temps2 != null ? temps2 : Double.MAX_VALUE);
        });
        
        // Initialiser les temps à l'infini pour toutes les cases
        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonnes; colonne++) {
                Case case_ = carte.getCase(ligne, colonne);
                NatureTerrain nature_de_la_case = case_.getNatureTerrain();
                String type_robot = robot.getClass().getSimpleName();
                
                // Vérifier si le robot peut accéder à cette case selon son type
                boolean caseAccessible = false;
                switch (type_robot) {
                    case "RobotDrone":
                        caseAccessible = true;
                        break;
                    case "RobotRoues":
                        caseAccessible = (nature_de_la_case == NatureTerrain.TERRAIN_LIBRE || 
                                        nature_de_la_case == NatureTerrain.HABITAT);
                        break;
                    case "RobotPattes":
                        caseAccessible = (nature_de_la_case != NatureTerrain.EAU);
                        break;
                    case "RobotChenille":
                        caseAccessible = (nature_de_la_case != NatureTerrain.EAU && 
                                        nature_de_la_case != NatureTerrain.ROCHE);
                        break;
                    default:
                        caseAccessible = true;
                }
                
                temps.put(case_, caseAccessible ? Double.MAX_VALUE : null);
            }
        }
        
        // Initialiser la case de départ
        temps.put(depart, 0.0);
        frontiere.offer(depart);
        
        while (!frontiere.isEmpty()) {
            Case caseActuelle = frontiere.poll();
            
            // Vérifier que la case courante est accessible
            if (temps.get(caseActuelle) == null) continue;
            
            // Si on a atteint l'arrivée, on a trouvé le chemin minimum
            if (caseActuelle.equals(arrivee)) {
                return reconstituerChemin(predecesseurs, arrivee);
            }
            
            // Traiter les voisins
            for (Direction dir : Direction.values()) {
                if (carte.voisinExiste(caseActuelle, dir)) {
                    Case voisin = carte.getVoisin(caseActuelle, dir);
                    if (temps.get(voisin) == null) continue; // Ignore les cases inaccessibles
                    
                    // Calculer le temps nécessaire pour aller à la case voisine
                    // On prend la moyenne des vitesses entre la case actuelle et la case voisine
                    int vitesseActuelle = robot.getVitesseNature(caseActuelle.getNatureTerrain());
                    int vitesseVoisin = robot.getVitesseNature(voisin.getNatureTerrain());
                    
                    // Si l'une des vitesses est 0, le déplacement est impossible
                    if (vitesseActuelle == 0 || vitesseVoisin == 0) continue;
                    
                    // Calcul du temps moyen pour parcourir la distance entre les deux cases
                    double vitesseMoyenne = (vitesseActuelle + vitesseVoisin) / (3.6*2.0); // en m/s
                    double tempsDeplacement = carte.getTailleCases() / vitesseMoyenne; // en secondes
                    
                    double nouveau_temps = temps.get(caseActuelle) + tempsDeplacement;
                    
                    // Si le nouveau temps est plus court, on met à jour
                    if (nouveau_temps < temps.get(voisin)) {
                        temps.put(voisin, nouveau_temps);
                        predecesseurs.put(voisin, caseActuelle);
                        // Pour s'assurer que la case est réévaluée avec son nouveau temps
                        frontiere.remove(voisin);
                        frontiere.offer(voisin);
                    }
                }
            }
        }
        
        // Si on sort de la boucle, c'est qu'on n'a pas trouvé de chemin
        return null;
    }
    
    private static List<Case> reconstituerChemin(Map<Case, Case> predecesseurs, Case arrivee) {
        List<Case> chemin = new ArrayList<>();
        Case case_ = arrivee;
        
        while (case_ != null) {
            chemin.add(0, case_);
            case_ = predecesseurs.get(case_);
        }
        
        return chemin;
    }
}