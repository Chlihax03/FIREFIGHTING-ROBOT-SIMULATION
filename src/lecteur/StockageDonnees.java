package lecteur;

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

import classes.*;
import robots.*;



/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que stock les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class StockageDonnees {


    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.stock(fichierDonnees)
     * @param fichierDonnees nom du fichier à stock
     */
    public static void stock(String fichierDonnees, DonneesSimulation donnees) throws FileNotFoundException, DataFormatException {
        StockageDonnees lecteur = new StockageDonnees(fichierDonnees);
        List<Case> casesEau = new ArrayList<>();
        donnees.setCarte(lecteur.stockCarte(donnees.getCasesEau()));
        donnees.setCasesEau(casesEau);
        donnees.setIncendies(lecteur.stockIncendies());
        Carte carte = donnees.getCarte();
        donnees.setRobots(lecteur.stockRobots(carte));
        scanner.close();
    }


    // Tout le reste de la classe est prive!

    private static Scanner scanner;
    private List<Case> casesEau = new ArrayList<>();  // Liste pour stocker les cases de type EAU


    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a stock
     */
    private StockageDonnees(String fichierDonnees) throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    private Carte stockCarte(List<Case> casesEau) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();

            Case[][] matriceCases = new Case[nbLignes][nbColonnes];
            Carte carte = new Carte(matriceCases, tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    Case currentCase = stockCase(lig, col);
                    carte.setCase(lig, col, currentCase);
                    if (currentCase.getNatureTerrain() == NatureTerrain.EAU) {
                        casesEau.add(currentCase);  // Ajouter la case à la liste si elle est de type EAU
                    }
                }
            }
            return carte;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. Attendu: nbLignes nbColonnes tailleCases");
        }
    }

private Case stockCase(int lig, int col) throws DataFormatException {
    ignorerCommentaires();
    try {
        String chaineNature = scanner.next();
        
        NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

        verifieLigneTerminee();
        return new Case(lig, col, nature); // Création de l'objet Case

    } catch (NoSuchElementException e) {
        throw new DataFormatException("format de case invalide. "
                + "Attendu: nature altitude [valeur_specifique]");
    }
}



private List<Incendie> stockIncendies() throws DataFormatException {
    ignorerCommentaires();
    List<Incendie> incendies = new ArrayList<>();
    try {
        int nbIncendies = scanner.nextInt();
        for (int i = 0; i < nbIncendies; i++) {
            incendies.add(stockIncendie(i)); // Ajouter chaque incendie à la liste
        }
    } catch (NoSuchElementException e) {
        throw new DataFormatException("Format invalide. "
                + "Attendu: nbIncendies");
    }
    return incendies;
}

private Incendie stockIncendie(int i) throws DataFormatException {
    ignorerCommentaires();
    try {
        int lig = scanner.nextInt();
        int col = scanner.nextInt();
        Case case_incendie = new Case(lig, col, NatureTerrain.TERRAIN_LIBRE);
        int intensite = scanner.nextInt();
        if (intensite <= 0) {
            throw new DataFormatException("incendie " + i + " : nb litres pour eteindre doit être > 0");
        }
        verifieLigneTerminee();

        return new Incendie(case_incendie, intensite); // Création de l'objet Incendie

    } catch (NoSuchElementException e) {
        throw new DataFormatException("format d'incendie invalide. Attendu: ligne colonne intensite");
    }
}

private List<Robot> stockRobots(Carte carte) throws DataFormatException {
    ignorerCommentaires();
    List<Robot> robots = new ArrayList<>();
    try {
        int nbRobots = scanner.nextInt();
        for (int i = 0; i < nbRobots; i++) {
            robots.add(stockRobot(i, carte)); // Ajouter chaque robot à la liste
        }
    } catch (NoSuchElementException e) {
        throw new DataFormatException("Format invalide. Attendu: nbRobots");
    }
    return robots;
}

private Robot stockRobot(int i, Carte carte) throws DataFormatException {
    ignorerCommentaires();
    int lig = scanner.nextInt();
    int col = scanner.nextInt();
    Case case_robot = carte.getCase(lig, col);
    NatureTerrain nature = case_robot.getNatureTerrain();
    Case caseRobot = new Case(lig, col, nature);
    String type = scanner.next();
    String s = scanner.findInLine("(\\d+)");	
    int vitesse;
    if (s == null) {
        vitesse = 0;
    } else {
        vitesse = Integer.parseInt(s) ; // km/h
    }

    switch (type){
        case "DRONE":
            Robot robotadrone = new RobotDrone(caseRobot, vitesse);
            if (s == null){
                robotadrone.setVitesse(100);
            }
            return robotadrone;
        case "ROUES":
            Robot robotaroues = new RobotRoues(caseRobot, vitesse);
            if (s==null){
                robotaroues.setVitesse(80);
            }
            return robotaroues;
        case "PATTES":
            Robot robotapattes = new RobotPattes(caseRobot, vitesse);
            if (s == null || nature == NatureTerrain.ROCHE){
                robotapattes.setVitesse(10);
            }
            if (s == null|| nature != NatureTerrain.ROCHE){
                robotapattes.setVitesse(30);
            }
            return robotapattes;
        case "CHENILLES":
            Robot robotachenilles = new RobotChenille(caseRobot, vitesse);
            if (s == null){
                robotachenilles.setVitesse(60);
            }
            if (nature == NatureTerrain.FORET ) {
                int nv_vitesse = robotachenilles.getVitesse();
                robotachenilles.setVitesse(nv_vitesse);
            }
            return robotachenilles;
        default:
            throw new DataFormatException("Type de robot inconnu : " + type);
    }
}

    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * //verifie qu'il n'y a plus rien a stock sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
