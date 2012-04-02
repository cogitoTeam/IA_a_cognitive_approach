package moteurDeRecherche;



import java.util.*;
import java.io.*;

import structure.Atome;
import structure.BaseFaits;
import structure.Regle;
import structure.Substitution;
import structure.Terme;
/**
 * Cette classe est constituée d'une base de règles et d'une base de faits. À l'aide
 * de ses méthodes de saturation, elle agit comme un moteur d'inférence qui sert de :
 * 1)calculer l'ensemble de tous les faits déductibles par chaînage avant
 * 2)calculer les réponses à une requête de la forme "trouver tous les x1...xn qui vérifient P", où
 * P est une conjonction d'atomes, et où x1...xn sont les variables apparaissant dans P.
 */
/**
 * @author namrata10
 *
 */
public class BaseConnaissances {

	private BaseFaits BF;// base de faits
	private ArrayList<Regle> BR;// base de règles
	private String nomFichierSource;
	private boolean saturee = false;

//Les constructeurs de la classe	
	/**
	 * Constructeur vide
	 */
	public BaseConnaissances() {
		super();
		BF = new BaseFaits();
		;
		BR = new ArrayList<Regle>();
		nomFichierSource = null;
	}

	/**
	 * Constructeur par copie
	 */
	public BaseConnaissances(BaseConnaissances k) {
		super();
		BF = new BaseFaits(k.getBF());
		BR = new ArrayList<Regle>(k.getBR());
		nomFichierSource = k.nomFichierSource;
	}

	/**
	 * Constructeur à paramètres :
	 * 
	 * @param BF
	 *            une instance de BaseFaits
	 * @param BR
	 *            une ArrayList d'instances de Regle
	 */
	public BaseConnaissances(BaseFaits BF, ArrayList<Regle> BR) {
		super();
		this.BF = BF;
		this.BR = BR;
		nomFichierSource = null;// on se sert de ça pour créer la base
								// propositionnalisée
	}

	/**
	 * Constructeur à paramètre :
	 * 
	 * @param nomFichier
	 *            le nom d'un fichier texte à partir duquel la base sera créée
	 * @throws IOException
	 */
	public BaseConnaissances(String nomFichier) throws IOException {
		nomFichierSource = nomFichier;
		BufferedReader lectureFichier = new BufferedReader(new FileReader(
				nomFichier));
		System.out
				.println("CHARGEMENT D'UNE BASE DE CONNAISSANCES à partir du fichier : "
						+ nomFichier);

		// création de la base de faits (utilise les méthodes de la classe
		// BaseFaits)
		String t = lectureFichier.readLine(); // 1e ligne du fichier contient
												// les faits
		BF = new BaseFaits(t);

		// détermination de la taille de BR
		t = lectureFichier.readLine(); // 2e ligne du fichier indique le nombre
										// de règles
		int n = Integer.parseInt(t);

		// création de la base de règles (utilise les méthodes de la classe
		// Regle)
		BR = new ArrayList<Regle>(n);
		for (int i = 0; i < n; i++) {
			t = lectureFichier.readLine(); // les lignes suivantes du fichier
											// sont les règles
			BR.add(new Regle(t, "Règle " + (i + 1)));
		}
		lectureFichier.close();
	}

//Les getters de la classe	
	public BaseFaits getBF() {
		return BF;
	}

	public ArrayList<Regle> getBR() {
		return BR;
	}

	public String getNomFichierSource() {
		return nomFichierSource;
	}

	public boolean isSaturee() {
		return saturee;
	}

// Les méthodes qui caractérisent les fonctionnalitées de la classe		
	/**
	 * Méthode qui permet de vider la base de faits de la base de connaissances courante
	 * On note qu'elle indique que la base n'est plus saturée
	 */
	public void viderBaseFaits() {
		BF = new BaseFaits();
		saturee = false;
	}
	/**
	 * Méthode qui permet d'ajouter un nouveau fait à la base de faits 
	 * de la base de connaissances courante.
	 * On note qu'elle indique que la base n'est plus saturée
	 * @param fait le fait (un atome) à ajouter 
	 */
	public void ajouterNouveauFait(Atome fait) {
		BF.ajouterNouveauFait(fait);
		saturee = false;
	}
//La méthode toString de la classe
	public String toString() {
		String BRs = "Nombre de règles : " + BR.size()
				+ "\nListe des règles : \n";
		for (int i = 0; i < BR.size(); i++) {
			BRs += "\t" + BR.get(i) + "\n";
		}
		return "\n  Base de faits\n" + BF + "\n  Base de règles\n" + BRs;
	}

	

// Les méthodes pour se ramener à la logique des propositions
		
	/**
	 * Propositionnalise la base de connaissances : 1) génère l'ensemble de
	 * substitutions des variables de chaque règle 2) crée des nouvelles règles
	 * à partir de ces substitutions 3) retourne la nouvelle base de
	 * connaissances
	 */
	private BaseConnaissances propositionnalisation() throws IOException {
		// déclaration et initialisation des variables de la méthode
		BaseConnaissances ordre0 = new BaseConnaissances();
		Regle regleCourante;
		Substitutions substitutions;
		Substitution s = new Substitution();
	
		// les nouvelles règles sont crées en traitant les entrées du fichier
		// texte :
	
		BufferedReader lectureFichier = new BufferedReader(new FileReader(
				nomFichierSource));
		String t = lectureFichier.readLine(); // lecture de la base de faits
		ordre0.BF = new BaseFaits(t); // la base de faits reste la même
	
		t = lectureFichier.readLine(); // ne fait rien : on veut passer à la
										// ligne suivante
	
		// boucle qui génère l'ensemble de règles complètement instanciées pour
		// chaque règle
		for (int i = 0; i < BR.size(); i++) // i = nombre de règles
		{
			regleCourante = BR.get(i);
			ArrayList<Terme> termesVariables = new ArrayList<Terme>();
			ArrayList<Terme> termesConstantes = new ArrayList<Terme>(
					ordre0.BF.getEnsembleTermes());// on va ajouter les
													// constantes de BR à ça
	
			// création de la liste de variables et de constantes (y compris
			// celles de la base de règles)
			for (int index = 0; index < regleCourante.getEnsembleTermes()
					.size(); index++) {
				Terme termeCourant = regleCourante.getEnsembleTermes().get(
						index);
				if (termeCourant.isConstante()) {
					if (!ordre0.BF.termeExiste(termeCourant))
						termesConstantes.add(termeCourant);
				} else
					termesVariables.add(termeCourant);
			}
	
			/*
			 * initialise l'ensemble des substitutions des variables de la règle
			 * courante par les constantes de la base de faits
			 */
			substitutions = new Substitutions(termesVariables, termesConstantes);
			// la méthode de la classe Substitutions qui génère l'ensemble des
			// substitutions
			substitutions.getSubstitutions(s);
			t = lectureFichier.readLine(); // lecture de la règle courante
	
			/*
			 * boucle qui ajoute les règles complètement instanciées à la
			 * nouvelle base de connaissances en traitant la String t selon la
			 * substitution générée par la méthode replace de la classe
			 * Substitution
			 */
			for (int j = 0; j < substitutions.S.size(); j++) {
				Regle temp = new Regle(substitutions.S.get(j).replace(t),
						regleCourante.getNom() + "." + (j + 1));
				ordre0.BR.add(temp);
			}
		}
		System.out.println("AFFICHAGE DE LA BASE PROPOSITIONNALISÉE :" + ordre0);
		return ordre0;
	}

	/**
	 * Méthode de saturation de la base propositionnalisée par le chaînage avant
	 * 
	 * @return k la base de connaissances saturée
	 * @throws IOException
	 */
	private BaseConnaissances saturationPropositionnelle() throws IOException {
		// déclaration et initialisation des variables
		BaseConnaissances k = new BaseConnaissances(propositionnalisation());
		ArrayList<Atome> aTraiter = new ArrayList<Atome>(k.BF.getListeAtomes());// copie
																				// la
																				// base
																				// de
																				// faits
	
		Regle regleCourante;
		Atome atomeCourant;
	
		// création du tableau qui stocke la taille de l'hypothèse de chaque
		// règle
		ArrayList<Integer> compteurR = new ArrayList<Integer>();// stocke la
																// taille de
																// l'hypothèse
																// de chaque
																// règle
		for (int n = 0; n < k.BR.size(); n++)
			compteurR.add(k.BR.get(n).getHypothese().size());
	
		// Debut de l'algorithme de chaînage avant
		while (aTraiter.size() != 0) {
	
			atomeCourant = aTraiter.remove(0);
	
			/*
			 * Pour toute règle de BR, 1)compare l'atome courant avec les atomes
			 * de son hypothèse 2) si chaque atome de l'hypothèse est incluse
			 * dans la base de faits, ajoute la conclusion à la base de faits
			 */
			for (int i = 0; i < k.BR.size(); i++) {
				regleCourante = k.BR.get(i);
				for (int j = 0; j < regleCourante.getHypothese().size(); j++)
					if (regleCourante.getHypothese().get(j)
							.equalsA(atomeCourant))
						compteurR.set(i, compteurR.get(i) - 1);// décrémente
																// compteurR
																// correspondant
																// à
																// regleCourante
				if (compteurR.get(i) == 0)
					if (!k.BF.atomeExiste(regleCourante.getConclusion())) {
						aTraiter.add(regleCourante.getConclusion());// ajout de
																	// la
																	// conclusion
																	// à la
																	// liste
																	// d'atomes
																	// à traiter
						k.BF.ajouterNouveauFait(regleCourante.getConclusion());// ajout
																				// de
																				// la
																				// conclusion
																				// à
																				// la
																				// base
																				// de
																				// faits
					}
			}
		}
		return k;
	}

	public BaseConnaissances saturationOrdre0() throws IOException {
		BaseConnaissances saturee = saturationPropositionnelle();
		return saturee;
	}

	
// Les méthodes pour se ramener à la logique des propositions
	
	/**
	 * Méthode de saturation << premier ordre >> de la base de faits
	 * par le chaînage avant
	 * 
	 * @return k la base de connaissances saturée
	 * @throws IOException
	 */
	public BaseConnaissances saturationOrdre1() throws IOException {
		if (saturee)
			return this; // evite le calcul de saturation au cas où la base est
							// déjà saturée
		
		// déclaration et initialisation des variables
		BaseConnaissances k = new BaseConnaissances(this);
		ArrayList<Atome> nouveaux; //liste qui stocke les nouveaux faits
		Homomorphismes s;
		boolean fin = false; //indique fin de l'algorithme de chaînage avant
		Atome temp;
	
		// Debut de l'algorithme de chaînage avant
		while (!fin) {
			nouveaux = new ArrayList<Atome>(); //initialisement vide
	
			for (Regle r : k.BR) {
				//initialise les homomorphismes de l'hypothèse de r dans la base de faits
				s = new Homomorphismes(r.getHypothese(), k.BF);
				if (s.existeHomomorphisme()) {
					//calcule les homomorphismes de l'hypothèse de r dans la base de faits
					for (Substitution hom : s.getHomomorphismes()) {
						//pour chaque homomorphisme hom, considère la
						//substitution de la conclusion de r
						temp = r.getConclusion().appliquerSubstitution(hom);
						if (!k.BF.atomeExiste(temp) && !nouveaux.contains(temp))
							nouveaux.add(temp);//si cette substitution est un nouveau fait,
											   //ajoute ce fait à la liste de nouveaux faits	
					}
				}
			}
			if (nouveaux.size() == 0)  //condition de fin d'algorithme
				fin = true;
			else
				k.BF.ajouterNouveauxFaits(nouveaux); //sinon on continue avec la nouvelle 
													 //base de faits	
		}
		k.saturee = true; //indique que la base est saturée (pour éviter de recalculer
						  //au cas où la base reste la même
		return k;
	}

	
	/**
	 * Méthode de saturation << premier ordre >> de la base de faits
	 * par le chaînage avant en exploitant le graphe de dépendances des règles 
	 * (et des faits)
	 * 
	 * @return k la base de connaissances saturée
	 * @throws IOException
	 */
	public BaseConnaissances saturationOrdre1Exploite() throws IOException {
		if (saturee)
			return this; // evite le calcul de saturation au cas où la base est
							// déjà saturée
		
		// déclaration et initialisation des variables
		BaseConnaissances k = new BaseConnaissances(this);
		GDR gdr = new GDR(k); 
		gdr.calculeGDR(); //calcule le graphe de dépendances des règles 
		  				  //(et des faits)
		
		//algorithme de saturation avec affichage des éléments qui illustrent
		//l'exploitation du graphe de dépendances des règles (et des faits)
		System.out.println(gdr); 
		System.out.println("--> Début de l'algorithme de chaînage avant");
		for (int i = 0; i < BF.getListeAtomes().size(); i++) {
			System.out.print("\n  Fait considéré : "
					+ BF.getListeAtomes().get(i));
			//appel à la fonction récursive qui calcule des nouveaux faits  
			//en appliquant les successeurs des faits (puis règles) considérés
			calculeNouveauxFaitsRec(gdr.getGraphe().get(i), k.BF); 
													 
														
		}
		System.out.println("\n--> Fin de l'algorithme de chaînage avant");
		k.saturee = true; //indique que la base est saturée
		return k;
	}

	/**
	 * Méthode récursive qui calcule les nouveaux faits générés par des successeurs
	 * (de faits ou de règles selon le cas) passés en paramètre 
	 * @param successeurs La liste de successeurs à considérer
	 * @param faits La base de faits courante (les nouveux faits y seront ajoutés)
	 */
	private void calculeNouveauxFaitsRec(ArrayList<Regle> successeurs, BaseFaits faits) {
		ArrayList<Regle> successeursNew;
		// Debut de l'algorithme qui exploite le graphe de
		// dépendance des règles
		for (Regle r : successeurs) {
			//Affiche l'ordre dans lequel les règles sont considérée
			System.out.print("\n\tRègle considérée : " + r);
			Homomorphismes s = new Homomorphismes(r.getHypothese(), faits);
			if (s.existeHomomorphisme())
				for (Substitution hom : s.getHomomorphismes()) {
					Atome temp = r.getConclusion().appliquerSubstitution(hom);
					if (!faits.atomeExiste(temp)) {
						faits.ajouterNouveauFait(temp);
						System.out.print("\n\tNouveau fait ajouté : " + temp
								+ "\n  Étape suivante : successeurs de "
								+ r.getNom());
						successeursNew = new ArrayList<Regle>(
								calculeSuccesseurs(r));
						//appel récursive qui parcourt le graphe en profondeur
						calculeNouveauxFaitsRec(successeursNew, faits); 
					}
				}
		}
		System.out.print("\n  Retour à l'étape précédente");
	}

	/**
	 * Méthode qui calcule les successeurs d'une règle passée en paramètre
	 * @param r La règle dont on veut calculer les successeurs
	 * @return La liste de successeurs de r
	 */
	private ArrayList<Regle> calculeSuccesseurs(Regle r) {
		GDR g = new GDR(this);
		int n = Integer.parseInt(r.getNom().substring(6))
				+ BF.getListeAtomes().size() - 1;

		g.calculeGDR();
		return g.getGraphe().get(n);
	}

//Test de la classe
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BaseConnaissances k = new BaseConnaissances("ex2.txt");
		System.out.println(k);
		BaseConnaissances kSaturee = k.saturationOrdre0();
		System.out.println(kSaturee);

		kSaturee = k.saturationOrdre1Exploite();
		System.out.println("\n\nBase de Faits saturée par homomorphismes:\n\n"
				+ kSaturee.BF);

	}
}
