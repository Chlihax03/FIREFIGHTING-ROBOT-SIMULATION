package classes;

public class Carte{
    private Case[][] matriceCase;
    private int tailleCases;

    public Carte(Case[][] matriceCase, int tailleCases ){
        this.matriceCase = matriceCase;
        this.tailleCases = tailleCases;
    }

    public int getNbLignes(){
        return this.matriceCase.length;
    }

    public int getNbColonnes(){
        return this.matriceCase[0].length;
    }

    public int getTailleCases(){
        return this.tailleCases;
    }
    
    public Case getCase(int ligne, int colonne) {
        if (ligne >= 0 && ligne < getNbLignes() && colonne >= 0 && colonne < getNbColonnes()) {
            return this.matriceCase[ligne][colonne];
        } else {
            return null;
        }
    }
    
    public void setCase(int ligne, int colonne, Case uneCase){
        this.matriceCase[ligne][colonne] = uneCase;
    }
    public boolean voisinExiste(Case src, Direction dir ) {
	    switch(dir) {
            case NORD:
    	        return !(src.getLigne() <= 0 );
	        case SUD:
                return !( src.getLigne() >= this.getNbLignes()-1 );
            case EST:
    	        return !( src.getColonne() >= this.getNbColonnes()-1 );
            case OUEST:
      	        return !( src.getColonne() <= 0 );
            default:
                return false;
        }
    }

    public Case getVoisin(Case src, Direction dir) {
	    if(voisinExiste(src,dir)){
		    switch (dir) {
		        case NORD:
			        return this.matriceCase[src.getLigne()-1][src.getColonne()];
		        case SUD:
	                return this.matriceCase[src.getLigne()+1][src.getColonne()];
	            case EST:
		            return this.matriceCase[src.getLigne()][src.getColonne()+1];
	            case OUEST:
		            return this.matriceCase[src.getLigne()][src.getColonne()-1];
                default:
                    return src;
	            }
	    }
        else{
            return null;
        }
    }

    public void printCarte() {
        for (int i = 0; i < matriceCase.length; i++) {
            for (int j = 0; j < matriceCase[i].length; j++) {
                if (matriceCase[i][j] != null) {
                    System.out.println(matriceCase[i][j].toString());
                } else {
                    System.out.println("Case vide (" + i + "," + j + ")");
                }
            }
        }
    }

}