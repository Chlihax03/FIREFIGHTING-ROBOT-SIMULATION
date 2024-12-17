package helper;

import java.util.List;
import evenements.*;

public class Helper {

    public static long CalculDureeEvents(List<Evenement> events) {
        long res = 0;
        for (Evenement event : events) {
            res += event.getDuree();
        }
        return res;
    }
    
    public static int transform(int input) {
        return 1000/(input+2);
    }

}
