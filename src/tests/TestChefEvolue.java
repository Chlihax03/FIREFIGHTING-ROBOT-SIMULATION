package tests;

import simulation.*;

public class TestChefEvolue {
    public static void main(String[] args) {
        String fichier = args[0];
        LancerSimu.lancerSimu(fichier, null, true);
    }
}
