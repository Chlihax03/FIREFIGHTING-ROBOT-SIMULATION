package evenements;

import java.util.List;
import java.util.PriorityQueue;

public class Scenario {
    private PriorityQueue<Evenement> evenements;

    public Scenario() {
        this.evenements = new PriorityQueue<>();
    }

    public PriorityQueue<Evenement> getEvenements() {
        return evenements;
    }

    public void ajouteEvenement(Evenement e) {
        if (e != null) {
            this.evenements.add(e);
        }
        else {
            System.out.println("Evenement vide, non ajouté au scénario.");
        }
    }

    public void ajouteEvenements(List<Evenement> events) {
        for (Evenement e : events) {
            ajouteEvenement(e);
        }
    }

    public String toString() {
        String res = "";
        for (Evenement event : this.evenements) {
            res += event.toString();
            res += "\n";
        }
        return res;
    }
}
