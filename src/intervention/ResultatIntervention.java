package intervention;

import java.util.List;

import evenements.*;
import classes.*;

public class ResultatIntervention {
    private List<Evenement> events;
    private Case positionFinale;

    public ResultatIntervention(List<Evenement> events, Case positionFinale) {
        this.events = events;
        this.positionFinale = positionFinale;
    }

    public List<Evenement> getEvents() {
        return events;
    }

    public Case getPositionFinale() {
        return positionFinale;
    }
}
