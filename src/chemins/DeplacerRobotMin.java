package chemins;

import java.util.ArrayList;
import java.util.List;

import classes.*;
import robots.*;
import evenements.*;

public class DeplacerRobotMin {
    public static List<Evenement> DeplacementMin(Case depart, Case arrivee, Robot robot, DonneesSimulation donnees, long date) {
        Carte carte = donnees.getCarte();
        CheminMinimum chemin = new CheminMinimum();
        List<Case> chemin_min = chemin.trouverCheminMinimum(carte, depart, arrivee, robot);
        List<Evenement> chemin_parcouru = new ArrayList<>();
        long date_ev = date;

        if (chemin_min == null || chemin_min.isEmpty()) {
            return chemin_parcouru;
        }

        for (int i = 0; i < chemin_min.size() - 1; i++) {
            Case case_depart = chemin_min.get(i);
            Case case_arrivee = chemin_min.get(i + 1);
            
            Evenement ev = new Deplacer(date_ev, robot, case_arrivee, donnees);
            long duree_ev = ev.getDuree();
            
            date_ev += duree_ev;
            chemin_parcouru.add(ev);
        }
        return chemin_parcouru;
    }
}
