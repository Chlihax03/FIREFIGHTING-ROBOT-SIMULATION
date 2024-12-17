package classes;

import java.util.ArrayList;
import java.util.List;

import robots.*;


public class DonneesSimulation {
    private Carte carte;
    private List<Incendie> incendies;
    private List<Robot> robots;
    private List<Case> casesEau;

    public DonneesSimulation(Carte carte) {
        this.carte = carte;
        this.incendies = new ArrayList<>();
        this.robots = new ArrayList<>();
        this.casesEau = new ArrayList<>();
    }

    public Carte getCarte() { 
        return carte; 
    }

    public Case getCase(int ligne, int colonne) {
        return carte.getCase(ligne, colonne);
    }

    public List<Incendie> getIncendies() { 
        return incendies;
    }

    public List<Case> getCasesEau() {
        return casesEau;
    }

    public void setCasesEau(List<Case> cases) {
        for (Case position : cases) {
            this.casesEau.add(position);
        }
    }

    public Incendie getIncendie(Case position) {
        for (Incendie incendie : incendies) {
            if (incendie.getPosition().estEgal(position)) {
                return incendie;
            }
        }
        System.out.println("Aucun incendie trouvé à la position : " + position);
        return null; // Retourne null s'il n'y a pas d'incendie dans la position donnée
    }

    public List<Robot> getRobots() { 
        return robots; 
    }

    public void setCarte(Carte carte){
        this.carte = carte;
    }

    public void setIncendies(List<Incendie> incendies){
        this.incendies = incendies;
    }

    public void setRobots(List<Robot> robots){
        this.robots = robots;
    }
    
    public void ajouteIncendie(Incendie incendie) {
        incendies.add(incendie);
    }
    
    public void ajouteRobot(Robot robot) {
        robots.add(robot);
    }
}