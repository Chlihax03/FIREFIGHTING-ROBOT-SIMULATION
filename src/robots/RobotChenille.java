package robots;

import classes.*;

public class RobotChenille extends Robot {
    public RobotChenille(Case position, int vitesse) {
        super(position, vitesse);
        this.capacite = 2000;
        this.volumeEau = 2000;
        this.tempsRemplissageParLitre = 300.0 / (double) capacite; // 5 min pour remplissage complet
        this.tempsDeversementParLitre = 8.0 / 100.0;
        if (position.getNatureTerrain() == NatureTerrain.EAU || position.getNatureTerrain() == NatureTerrain.ROCHE) {
            throw new IllegalArgumentException(
                    "Le robot à chenille ne peut pas se rendre sur de l’eau ou du rocher..\n");
        }
    }

    @Override
    public int getVitesseNature(NatureTerrain nature) {
        if (nature == NatureTerrain.FORET) {
            return this.getVitesse() / 2; // Réduction de 50% en forêt
        } else if (nature == NatureTerrain.EAU || nature == NatureTerrain.ROCHE) {
            return 0; // Ne peut pas se déplacer sur l'eau ou les rochers
        } else {
            return this.getVitesse();
        }
    }

    public int defaultvitesse(NatureTerrain terrain) {
        int res;
        if (terrain == NatureTerrain.TERRAIN_LIBRE || terrain == NatureTerrain.HABITAT
                || terrain == NatureTerrain.FORET) {
            res = 60; // km/h
        } else {
            throw new IllegalArgumentException("Le robot à roues ne peut pas se déplacer sur le terrain " + terrain);
        }
        if (terrain == NatureTerrain.FORET) {
            res /= 2;
        }
        return res;
    }

    public void setPosition(Case position) {

        try {
            if (position == null) {
                throw new IllegalArgumentException("La nouvelle position est invalide.");
            }

            if (position.getNatureTerrain() == NatureTerrain.EAU
                    || position.getNatureTerrain() == NatureTerrain.ROCHE) {
                throw new IllegalArgumentException(
                        "Le robot à chenilles ne peut pas se rendre sur de l’eau ou du rocher.");
            }
            this.position = position;

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    public void remplirReservoir(Carte tab) {
        if (this.getPosition().possedeVoisindeType(NatureTerrain.EAU, tab)) {
            this.setVolumeEau(this.capacite);
        } else {
            throw new IllegalArgumentException(
                    "Remplissage échoué! Nécéssité de présente à côté d'une case contenant de l'eau.\n");
        }
    }
}
