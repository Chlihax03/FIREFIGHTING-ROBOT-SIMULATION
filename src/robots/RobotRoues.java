package robots;

import classes.*;

public class RobotRoues extends Robot {
    public RobotRoues(Case position, int vitesse) {
        super(position, vitesse);
        this.capacite = 5000;
        this.volumeEau = 5000;
        this.tempsRemplissageParLitre = 600.0 / (double) capacite; // 10 min pour remplissage complet
        this.tempsDeversementParLitre = 5.0 / 100.0; // vide 100L en 5 secondes
        if (position.getNatureTerrain() != NatureTerrain.TERRAIN_LIBRE
                && position.getNatureTerrain() != NatureTerrain.HABITAT) {
            throw new IllegalArgumentException(
                    "Le robot à roues ne peut se déplacer que sur du terrain libre ou habitat.\n");
        }
    }

    @Override
    public int getVitesseNature(NatureTerrain nature) {
        if (nature == NatureTerrain.TERRAIN_LIBRE || nature == NatureTerrain.HABITAT) {
            return this.getVitesse();
        } else {
            return 0; // Ne peut pas se déplacer sur les autres terrains
        }
    }

    public int defaultvitesse(NatureTerrain terrain) {
        if (terrain == NatureTerrain.TERRAIN_LIBRE || terrain == NatureTerrain.HABITAT) {
            return 80; // km/h
        } else {
            throw new IllegalArgumentException("Le robot à roues ne peut pas se déplacer sur le terrain " + terrain);
        }
    }

    public void setPosition(Case position) {
        try {
            if (position == null) {
                throw new IllegalArgumentException("La nouvelle position est invalide.");
            }

            if (position.getNatureTerrain() != NatureTerrain.TERRAIN_LIBRE
                    && position.getNatureTerrain() != NatureTerrain.HABITAT) {
                throw new IllegalArgumentException(
                        "Le robot à roues ne peut se déplacer que sur du terrain libre ou habitat.");
            }

            // Si toutes les conditions sont remplies, on peut mettre à jour la position
            this.position = position;

        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void remplirReservoir(Carte tab) {
        if (this.getPosition().possedeVoisindeType(NatureTerrain.EAU, tab)) {
            this.setVolumeEau(5000);
        } else {
            throw new IllegalArgumentException(
                    "Remplissage échoué! Nécéssité de présente à côté d'une case contenant de l'eau.\n");
        }
    }

    public int getTempsRemplissage(int nbLitres) {
        return nbLitres * 600 / 5000;
    }

    public int getTempsDeversement(int nbLitres) {
        return nbLitres * 5 / 100;
    }
}
