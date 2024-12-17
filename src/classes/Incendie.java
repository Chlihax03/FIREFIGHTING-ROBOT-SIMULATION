package classes;

public class Incendie {
    private Case position;
    private int intensite;

    public Incendie(Case position, int intensite) {
        this.position = position;
        this.intensite = intensite;
    }

    public Case getPosition(){
        return this.position;
    }

    public int getIntensite(){
        return this.intensite;
    }

    public void setIntensite(int newIntensite) {
        this.intensite = newIntensite;
    }

    @Override
    public String toString() {
        return "Incendie en " + position.toString() + " d'intensité " + this.intensite ;
    }
}
