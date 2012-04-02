package ac.analysis.moteurDeRecherche;

import java.util.*;
import java.io.*;

import ac.analysis.structure.*;


/**
 * Cette classe est constitu�e d'une base de r�gles et d'une base de faits. � l'aide
 * de ses m�thodes de saturation, elle agit comme un moteur d'inf�rence qui sert de :
 * 1)calculer l'ensemble de tous les faits d�ductibles par cha�nage avant
 * 2)calculer les r�ponses � une requ�te de la forme "trouver tous les x1...xn qui v�rifient P", o�
 * P est une conjonction d'atomes, et o� x1...xn sont les variables apparaissant dans P.
 */
/**
 * @author namrata10
 *
 */
public class BaseConnaissances {

	private BaseFaits BF;// base de faits
	private ArrayList<Regle> BR;// base de r�gles
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
	 * Constructeur � param�tres :
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
		nomFichierSource = null;// on se sert de �a pour cr�er la base
								// propositionnalis�e
	}

	/**
	 * Constructeur � param�tre :
	 * 
	 * @param nomFichier
	 *            le nom d'un fichier texte � partir duquel la base sera cr��e
	 * @throws IOException
	 */
	public BaseConnaissances(String nomFichier) throws IOException {
		nomFichierSource = nomFichier;
		BufferedReader lectureFichier = new BufferedReader(new FileReader(
				nomFichier));
		System.out
				.println("CHARGEMENT D'UNE BASE DE CONNAISSANCES � partir du fichier : "
						+ nomFichier);

		// cr�ation de la base de faits (utilise les m�thodes de la classe
		// BaseFaits)
		String t = lectureFichier.readLine(); // 1e ligne du fichier contient
												// les faits
		BF = new BaseFaits(t);

		// d�termination de la taille de BR
		t = lectureFichier.readLine(); // 2e ligne du fichier indique le nombre
										// de r�gles
		int n = Integer.parseInt(t);

		// cr�ation de la base de r�gles (utilise les m�thodes de la classe
		// Regle)
		BR = new ArrayList<Regle>(n);
		for (int i = 0; i < n; i++) {
			t = lectureFichier.readLine(); // les lignes suivantes du fichier
											// sont les r�gles
			BR.add(new Regle(t, "R�gle " + (i + 1)));
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

// Les m�thodes qui caract�risent les fonctionnalit�es de la classe		
	/**
	 * M�thode qui permet de vider la base de faits de la base de connaissances courante
	 * On note qu'elle indique que la base n'est plus satur�e
	 */
	public void viderBaseFaits() {
		BF = new BaseFaits();
		saturee = false;
	}
	/**
	 * M�thode qui permet d'ajouter un nouveau fait � la base de faits 
	 * de la base de connaissances courante.
	 * On note qu'elle indique que la base n'est plus satur�e
	 * @param fait le fait (un atome) � ajouter 
	 */
	public void ajouterNouveauFait(Atome fait) {
		BF.ajouterNouveauFait(fait);
		saturee = false;
	}
//La m�thode toString de la classe
	public String toString() {
		String BRs = "Nombre de r�gles : " + BR.size()
				+ "\nListe des r�gles : \n";
		for (int i = 0; i < BR.size(); i++) {
			BRs += "\t" + BR.get(i) + "\n";
		}
		return "\n  Base de faits\n" + BF + "\n  Base de r�gles\n" + BRs;
	}

	

// Les m�thodes pour se ramener � la logique des propositions
		
	/**
	 * Propositionnalise la base de connaissances : 1) g�n�re l'ensemble de
	 * substitutions des variables de chaque r�gle 2) cr�e des nouvelles r�gles
	 * � partir de ces substitutions 3) retourne la nouvelle base de
	 * connaissances
	 */
	private BaseConnaissances propositionnalisation() throws IOException {
		// d�claration et initialisation des variables de la m�thode
		BaseConnaissances ordre0 = new BaseConnaissances();
		Regle regleCourante;
		Substitutions substitutions;
		Substitution s = new Substitution();
	
		// les nouvelles r�gles sont cr�es en traitant les entr�es du fichier
		// texte :
	
		BufferedReader lectureFichier = new BufferedReader(new FileReader(
				nomFichierSource));
		String t = lectureFichier.readLine(); // lecture de la base de faits
		ordre0.BF = new BaseFaits(t); // la base de faits reste la m�me
	
		t = lectureFichier.readLine(); // ne fait rien : on veut passer � la
										// ligne suivante
	
		// boucle qui g�n�re l'ensemble de r�gles compl�tement instanci�es pour
		// chaque r�gle
		for (int i = 0; i < BR.size(); i++) // i = nombre de r�gles
		{
			regleCourante = BR.get(i);
			ArrayList<Terme> termesVariables = new ArrayList<Terme>();
			ArrayList<Terme> termesConstantes = new ArrayList<Terme>(
					ordre0.BF.getEnsembleTermes());// on va ajouter les
													// constantes de BR � �a
	
			// cr�ation de la liste de variables et de constantes (y compris
			// celles de la base de r�gles)
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
			 * initialise l'ensemble des substitutions des variables de la r�gle
			 * courante par les constantes de la base de faits
			 */
			substitutions = new Substitutions(termesVariables, termesConstantes);
			// la m�thode de la classe Substitutions qui g�n�re l'ensemble des
			// substitutions
			substitutions.getSubstitutions(s);
			t = lectureFichier.readLine(); // lecture de la r�gle courante
	
			/*
			 * boucle qui ajoute les r�gles compl�tement instanci�es � la
			 * nouvelle base de connaissances en traitant la String t selon la
			 * substitution g�n�r�e par la m�thode replace de la classe
			 * Substitution
			 */
			for (int j = 0; j < substitutions.S.size(); j++) {
				Regle temp = new Regle(substitutions.S.get(j).replace(t),
						regleCourante.getNom() + "." + (j + 1));
				ordre0.BR.add(temp);
			}
		}
		System.out.println("AFFICHAGE DE LA BASE PROPOSITIONNALIS�E :" + ordre0);
		return ordre0;
	}

	/**
	 * M�thode de saturation de la base propositionnalis�e par le cha�nage avant
	 * 
	 * @return k la base de connaissances satur�e
	 * @throws IOException
	 */
	private BaseConnaissances saturationPropositionnelle() throws IOException {
		// d�claration et initialisation des variables
		BaseConnaissances k = new BaseConnaissances(propositionnalisation());
		ArrayList<Atome> aTraiter = new ArrayList<Atome>(k.BF.getListeAtomes());// copie
																				// la
																				// base
																				// de
																				// faits
	
		Regle regleCourante;
		Atome atomeCourant;
	
		// cr�ation du tableau qui stocke la taille de l'hypoth�se de chaque
		// r�gle
		ArrayList<Integer> compteurR = new ArrayList<Integer>();// stocke la
																// taille de
																// l'hypoth�se
																// de chaque
																// r�gle
		for (int n = 0; n < k.BR.size(); n++)
			compteurR.add(k.BR.get(n).getHypothese().size());
	
		// Debut de l'algorithme de cha�nage avant
		while (aTraiter.size() != 0) {
	
			atomeCourant = aTraiter.remove(0);
	
			/*
			 * Pour toute r�gle de BR, 1)compare l'atome courant avec les atomes
			 * de son hypoth�se 2) si chaque atome de l'hypoth�se est incluse
			 * dans la base de faits, ajoute la conclusion � la base de faits
			 */
			for (int i = 0; i < k.BR.size(); i++) {
				regleCourante = k.BR.get(i);
				for (int j = 0; j < regleCourante.getHypothese().size(); j++)
					if (regleCourante.getHypothese().get(j)
							.equalsA(atomeCourant))
						compteurR.set(i, compteurR.get(i) - 1);// d�cr�mente
																// compteurR
																// correspondant
																// �
																// regleCourante
				if (compteurR.get(i) == 0)
					if (!k.BF.atomeExiste(regleCourante.getConclusion())) {
						aTraiter.add(regleCourante.getConclusion());// ajout de
																	// la
																	// conclusion
																	// � la
																	// liste
																	// d'atomes
																	// � traiter
						k.BF.ajouterNouveauFait(regleCourante.getConclusion());// ajout
																				// de
																				// la
																				// conclusion
																				// �
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

	
// Les m�thodes pour se ramener � la logique des propositions
	
	/**
	 * M�thode de saturation << premier ordre >> de la base de faits
	 * par le cha�nage avant
	 * 
	 * @return k la base de connaissances satur�e
	 * @throws IOException
	 */
	public BaseConnaissances saturationOrdre1() throws IOException {
		if (saturee)
			return this; // evite le calcul de saturation au cas o� la base est
							// d�j� satur�e
		
		// d�claration et initialisation des variables
		BaseConnaissances k = new BaseConnaissances(this);
		ArrayList<Atome> nouveaux; //liste qui stocke les nouveaux faits
		Homomorphismes s;
		boolean fin = false; //indique fin de l'algorithme de cha�nage avant
		Atome temp;
	
		// Debut de l'algorithme de cha�nage avant
		while (!fin) {
			nouveaux = new ArrayList<Atome>(); //initialisement vide
	
			for (Regle r : k.BR) {
				//initialise les homomorphismes de l'hypoth�se de r dans la base de faits
				s = new Homomorphismes(r.getHypothese(), k.BF);
				if (s.existeHomomorphisme()) {
					//calcule les homomorphismes de l'hypoth�se de r dans la base de faits
					for (Substitution hom : s.getHomomorphismes()) {
						//pour chaque homomorphisme hom, consid�re la
						//substitution de la conclusion de r
						temp = r.getConclusion().appliquerSubstitution(hom);
						if (!k.BF.atomeExiste(temp) && !nouveaux.contains(temp))
							nouveaux.add(temp);//si cette substitution est un nouveau fait,
											   //ajoute ce fait � la liste de nouveaux faits	
					}
				}
			}
			if (nouveaux.size() == 0)  //condition de fin d'algorithme
				fin = true;
			else
				k.BF.ajouterNouveauxFaits(nouveaux); //sinon on continue avec la nouvelle 
													 //base de faits	
		}
		k.saturee = true; //indique que la base est satur�e (pour �viter de recalculer
						  //au cas o� la base reste la m�me
		return k;
	}

	
	/**
	 * M�thode de saturation << premier ordre >> de la base de faits
	 * par le cha�nage avant en exploitant le graphe de d�pendances des r�gles 
	 * (et des faits)
	 * 
	 * @return k la base de connaissances satur�e
	 * @throws IOException
	 */
	public BaseConnaissances saturationOrdre1Exploite() throws IOException {
		if (saturee)
			return this; // evite le calcul de saturation au cas o� la base est
							// d�j� satur�e
		
		// d�claration et initialisation des variables
		BaseConnaissances k = new BaseConnaissances(this);
		GDR gdr = new GDR(k); 
		gdr.calculeGDR(); //calcule le graphe de d�pendances des r�gles 
		  				  //(et des faits)
		
		//algorithme de saturation avec affichage des �l�ments qui illustrent
		//l'exploitation du graphe de d�pendances des r�gles (et des faits)
		System.out.println(gdr); 
		System.out.println("--> D�but de l'algorithme de cha�nage avant");
		for (int i = 0; i < BF.getListeAtomes().size(); i++) {
			System.out.print("\n  Fait consid�r� : "
					+ BF.getListeAtomes().get(i));
			//appel � la fonction r�cursive qui calcule des nouveaux faits  
			//en appliquant les successeurs des faits (puis r�gles) consid�r�s
			calculeNouveauxFaitsRec(gdr.getGraphe().get(i), k.BF); 
													 
														
		}
		System.out.println("\n--> Fin de l'algorithme de cha�nage avant");
		k.saturee = true; //indique que la base est satur�e
		return k;
	}

	/**
	 * M�thode r�cursive qui calcule les nouveaux faits g�n�r�s par des successeurs
	 * (de faits ou de r�gles selon le cas) pass�s en param�tre 
	 * @param successeurs La liste de successeurs � consid�rer
	 * @param faits La base de faits courante (les nouveux faits y seront ajout�s)
	 */
	private void calculeNouveauxFaitsRec(ArrayList<Regle> successeurs, BaseFaits faits) {
		ArrayList<Regle> successeursNew;
		// Debut de l'algorithme qui exploite le graphe de
		// d�pendance des r�gles
		for (Regle r : successeurs) {
			//Affiche l'ordre dans lequel les r�gles sont consid�r�e
			System.out.print("\n\tR�gle consid�r�e : " + r);
			Homomorphismes s = new Homomorphismes(r.getHypothese(), faits);
			if (s.existeHomomorphisme())
				for (Substitution hom : s.getHomomorphismes()) {
					Atome temp = r.getConclusion().appliquerSubstitution(hom);
					if (!faits.atomeExiste(temp)) {
						faits.ajouterNouveauFait(temp);
						System.out.print("\n\tNouveau fait ajout� : " + temp
								+ "\n  �tape suivante : successeurs de "
								+ r.getNom());
						successeursNew = new ArrayList<Regle>(
								calculeSuccesseurs(r));
						//appel r�cursive qui parcourt le graphe en profondeur
						calculeNouveauxFaitsRec(successeursNew, faits); 
					}
				}
		}
		System.out.print("\n  Retour � l'�tape pr�c�dente");
	}

	/**
	 * M�thode qui calcule les successeurs d'une r�gle pass�e en param�tre
	 * @param r La r�gle dont on veut calculer les successeurs
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
		System.out.println("\n\nBase de Faits satur�e par homomorphismes:\n\n"
				+ kSaturee.BF);

	}
}
