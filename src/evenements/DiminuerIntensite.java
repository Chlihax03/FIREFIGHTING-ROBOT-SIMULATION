package evenements;

import classes.DonneesSimulation;
import classes.Incendie;

public class DiminuerIntensite extends Evenement {
    private long date;
    private DonneesSimulation donneesSimulation;
    private Incendie incendie;
    private int newIntensite;

    public DiminuerIntensite(long date, DonneesSimulation donnees, Incendie incendie, int newIntensite) {
        super(date, donnees);
        this.incendie = incendie;
        this.newIntensite = newIntensite;
    }

    public Incendie getIncendie() {
        return this.incendie;
    }

    @Override
    public void execute() {
        this.incendie.setIntensite(newIntensite);
    }

    @Override
    public long getDuree() {
        return 1; // c'est un évènement instantanné, on ajoute 1 pour éviter le chevauchement avec d'autres évènements
    }
}
