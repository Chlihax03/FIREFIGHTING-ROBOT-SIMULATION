package classes;

import java.awt.Color;

import gui.GUISimulator;
import gui.Rectangle;

public class Case{
    private int ligne;
    private int colonne;
    private NatureTerrain nature;

    public Case(int ligne, int colonne, NatureTerrain nature){
        this.ligne = ligne;
        this.colonne = colonne;
        this.nature = nature;
    }

    public int getLigne(){
        return this.ligne;
    }

    public int getColonne(){
        return this.colonne;
    }

    public NatureTerrain getNatureTerrain(){
        return this.nature;
    }
    public void setNature(NatureTerrain nature){
        this.nature = nature;
    }
    public String toString(){
        return "position: (" + this.ligne + ","+ this.colonne + ")  nature:"+ this.nature;
    }

    /**
    * Vérifie si la case actuelle est voisine d'une autre case spécifiée.
    *
    * Deux cases sont considérées comme voisines si elles se touchent horizontalement ou verticalement.
    *
    * @param src la case à comparer avec la case actuelle
    * @return true si la case actuelle est voisine de la case spécifiée, false sinon
    */
    public boolean estVoisin(Case src) {
        
        return (Math.abs(this.getLigne() - src.getLigne()) == 1 && this.getColonne() == src.getColonne()) || 
        (Math.abs(this.getColonne() - src.getColonne()) == 1 && this.getLigne() == src.getLigne());
    }

    public boolean possedeVoisindeType(NatureTerrain nature, Carte tab) {
        for (Direction dir : Direction.values()) {
            try {
                Case voisin = tab.getVoisin(this, dir);
                if (voisin != null && voisin.getNatureTerrain() == nature) {
                    return true;
                }
            } catch (IllegalArgumentException e){
                // on ignore l'exception s'il n'y a pas de voisin
            }
        }
        return false;
    }

    public boolean estEgal(Case other) {
        return this.ligne == other.ligne && this.colonne == other.colonne;
    }

    public void draw(GUISimulator gui, Color BackGroundColor, Carte carte, Case ccase) {
        int cote = carte.getTailleCases();
        int centre_x = ccase.getColonne()*cote + cote/2;
        int centre_y = ccase.getLigne()*cote + cote/2;
        Color caseColor = Color.BLACK; // valeur arbitraire pour l'initialisation

        switch (ccase.getNatureTerrain()) {
            case EAU :
                caseColor = new Color(51, 204, 255); // light blue
                break;
            case FORET :
                caseColor = Color.GREEN;
                break;
            case ROCHE :
                caseColor = Color.LIGHT_GRAY;
                break;
            case TERRAIN_LIBRE:
                caseColor = new Color(102, 51, 0); // marron
                break;
            case HABITAT :
                caseColor = new Color(222, 184, 135);
                break;
        }

        gui.addGraphicalElement(new Rectangle(centre_x, centre_y, Color.WHITE, caseColor, cote, cote));
    }
}