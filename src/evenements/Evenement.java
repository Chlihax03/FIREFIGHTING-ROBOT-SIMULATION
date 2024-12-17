package evenements;

import classes.*;

public abstract class Evenement implements Comparable<Evenement> {
    private long date;
    protected DonneesSimulation donnees;

    public Evenement(long date, DonneesSimulation donnees) {
        this.date = date;
        this.donnees = donnees;
    }

    public long getDate() {
        return date;
    }

    public abstract void execute();

    public abstract long getDuree(); 

    @Override
    public int compareTo(Evenement other) {
        if (this.date > other.date)
            return 1;
        if (this.date < other.date)
            return -1;
        return 0;
    }

    @Override 
    public String toString() {
        String typeEvent = "";
        if (this instanceof Deplacer) {
            typeEvent += "Deplacer";
        } else if (this instanceof DeverserEau) {
            typeEvent += "DeverserEau";
        } else if (this instanceof RemplirEau) {
            typeEvent += "RemplirEau";
        }
        return "Evenement " + typeEvent + " à la date " + this.getDate();
    }
}