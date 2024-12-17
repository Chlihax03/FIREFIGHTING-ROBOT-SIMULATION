package robots;

import classes.*;

public abstract class Robot{
    protected Case position;
    protected int volumeEau;
    private int vitesse;
    protected int capacite;
    protected double tempsRemplissageParLitre; // en s/l
    protected double tempsDeversementParLitre; // en s/l
    private long date;

    public Robot(Case position, int vitesse) {
        this.position = position;
        this.vitesse = vitesse;
        this.date = 0;
    }

    public int getVitesse(){
        return this.vitesse;
    };

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public abstract int getVitesseNature(NatureTerrain nature);

    public void setVitesse(int vitesse){
        this.vitesse = vitesse;
    };

    public Case getPosition(){
        return this.position;
    }


    public abstract int defaultvitesse(NatureTerrain terrain);

    public int getVolumeEau(){
        return this.volumeEau;
    }

    public int getCapacite(){
        return this.capacite;
    }
    
    public abstract void setPosition(Case position);

    public void setVolumeEau(int Volume) {
        if (this.volumeEau > Volume) {
        }
        this.volumeEau = Volume;
    }

    public void deverserEau(int volumeEauDeverse) {
        this.volumeEau -= volumeEauDeverse;
    }

    public abstract void remplirReservoir(Carte carte);

    public int getTempsRemplissage(int Nblitres) {
        return (int)((double)Nblitres * this.tempsRemplissageParLitre);
    }

    public int getTempsDeversement(int NbLitres) {
        return (int)((double)NbLitres * tempsDeversementParLitre);
    }
}
