package robots;

import classes.*;

public class RobotDrone extends Robot {

    public RobotDrone(Case position, int vitesse) {
        super(position, vitesse);
        this.capacite = 10000;
        this.volumeEau = 10000;
        this.tempsRemplissageParLitre = 1800.0 / (double) capacite; // 30 min pour remplissage complet
        this.tempsDeversementParLitre = 30.0 / (double) capacite; // vide le reservoir en 30 secondes
    }

    @Override
    public int getVitesseNature(NatureTerrain nature) {
        return this.getVitesse();
    }

    public int defaultvitesse(NatureTerrain terrain) {
        return 100;
    }

    public void setPosition(Case position) {
        try {
            if (position == null) {
                throw new IllegalArgumentException("La nouvelle position est invalide.");
            }
            this.position = position;
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void remplirReservoir(Carte carte) {
        if (this.getPosition().getNatureTerrain() == NatureTerrain.EAU) {
            this.setVolumeEau(10000);
        } else {
            throw new IllegalArgumentException(
                    "Remplissage échoué! Nécéssité de présence sur une case contenant de l'eau.\n");
        }
    }
}
