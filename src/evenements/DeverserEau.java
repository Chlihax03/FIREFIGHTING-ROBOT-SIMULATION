package evenements;

import classes.*;
import robots.*;

public class DeverserEau extends Evenement {
    private Robot robot;
    private int VolumeEauàDeverser;
    
    public DeverserEau(long date, Robot robot, int VolumeEauàDeverser, DonneesSimulation donnees) {
        super(date, donnees);
        this.robot = robot;
        this.VolumeEauàDeverser = VolumeEauàDeverser;
    }

    public int getVolumeEauàDeverser() {
        return VolumeEauàDeverser;
    }

    Robot geRobot() {
        return robot;
    }

    @Override
    public long getDuree() {
        return this.robot.getTempsDeversement(VolumeEauàDeverser);
    }

    @Override
    public void execute() {
        try {
            this.robot.deverserEau(VolumeEauàDeverser);
        } catch (NullPointerException e) {
        }
    }
}
