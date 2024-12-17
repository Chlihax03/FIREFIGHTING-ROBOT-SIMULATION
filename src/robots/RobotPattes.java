package robots;

import classes.*;

public class RobotPattes extends Robot {
    public RobotPattes(Case position, int vitesse) {
        super(position, vitesse);
        this.capacite = Integer.MAX_VALUE; // capacité infinie
        this.volumeEau = Integer.MAX_VALUE;
        this.tempsRemplissageParLitre = 0.0; // n'effectue jamais le remplissage
        this.tempsDeversementParLitre = 1.0 / 10.0; // vide 10l en une seconde.
        if (position.getNatureTerrain() == NatureTerrain.EAU) {
            throw new IllegalArgumentException("Le robot à Pattes ne peut pas se rendre sur de l'eau.\n");
        }
    }

    @Override
    public int getVitesseNature(NatureTerrain nature) {
        if (nature == NatureTerrain.EAU) {
            return 0; // Ne peut pas se déplacer sur l'eau
        } else if (nature == NatureTerrain.ROCHE) {
            return 10; // Réduction de la vitesse sur les rochers
        } else {
            return this.getVitesse();
        }
    }

    public int defaultvitesse(NatureTerrain terrain) {
        if (terrain == NatureTerrain.EAU) {
            return 0; // Ne peut pas de déplacer sur l'eau
        } else if (terrain == NatureTerrain.ROCHE) {
            return 10; // km/h
        }
        // Pour les autres natures de terrain
        return 60; // km/h
    }

    public void setPosition(Case position) {
        try {
            if (position == null) {
                throw new IllegalArgumentException("La nouvelle position est invalide.");
            }
            if (position.getNatureTerrain() == NatureTerrain.EAU) {
                throw new IllegalArgumentException("Le robot à pattes ne peut pas se rendre sur de l'eau.");
            }

            // Si les conditions sont satisfaites, mise à jour de la position
            this.position = position;

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    public void remplirReservoir(Carte tab) {
        // Le robot à pattes ne se remplit jamais.
    }
}
