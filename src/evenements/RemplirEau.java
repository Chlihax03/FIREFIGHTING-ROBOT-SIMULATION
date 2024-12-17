package evenements;

import classes.*;
import robots.*;

public class RemplirEau extends Evenement {
    private Robot robot;
    private int volumeEauARemplir;

    public RemplirEau(long date, Robot robot, DonneesSimulation donnees) {
        super(date, donnees);
        this.robot = robot;
        this.volumeEauARemplir = robot.getCapacite() - robot.getVolumeEau();
    }

    public Robot getRobot() {
        return robot;
    }

    public int getVolumeEauARemplir() {
        return volumeEauARemplir;
    }

    @Override
    public void execute() {
        robot.remplirReservoir(this.donnees.getCarte());
    }

    @Override
    public long getDuree() {
        return this.robot.getTempsRemplissage(volumeEauARemplir);
    }
}
