package chemins;

import classes.*;
import robots.*;
import helper.*;

import java.util.Objects;

public class RobotIncendieKey {
    private Robot robot;
    private Case positionIncendie;

    public RobotIncendieKey(Robot robot, Case positionIncendie) {
        this.robot = robot;
        this.positionIncendie = positionIncendie;
    }

    public Robot getRobot() {
        return robot;
    }

    public Case getPositionIncendie() {
        return positionIncendie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotIncendieKey that = (RobotIncendieKey) o;
        return Objects.equals(robot, that.robot) &&
               Objects.equals(positionIncendie, that.positionIncendie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(robot, positionIncendie);
    }

    @Override
    public String toString() {
        return "RobotIncendieKey{" +
               "robot=" + robot +
               ", positionIncendie=" + positionIncendie +
               '}';
    }
}
