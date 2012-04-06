package ac.analysis.inferenceEngine;

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
public class KnowledgeBase {

	private FactBase BF;// base de faits
	private ArrayList<Rule> BR;// base de r�gles
	private String sourceFilename;
	private boolean isSaturated = false;

//Les constructeurs de la classe	
	/**
	 * Constructeur vide
	 */
	public KnowledgeBase() {
		super();
		BF = new FactBase();
		;
		BR = new ArrayList<Rule>();
		sourceFilename = null;
	}

	/**
	 * Constructeur par copie
	 */
	public KnowledgeBase(KnowledgeBase k) {
		super();
		BF = new FactBase(k.getFB());
		BR = new ArrayList<Rule>(k.getRB());
		sourceFilename = k.sourceFilename;
	}

	/**
	 * Constructeur � param�tres :
	 * 
	 * @param BF
	 *            une instance de BaseFaits
	 * @param BR
	 *            une ArrayList d'instances de Regle
	 */
	public KnowledgeBase(FactBase BF, ArrayList<Rule> BR) {
		super();
		this.BF = BF;
		this.BR = BR;
		sourceFilename = null;// on se sert de �a pour cr�er la base
								// propositionnalis�e
	}

	/**
	 * Constructeur � param�tre :
	 * 
	 * @param nomFichier
	 *            le nom d'un fichier texte � partir duquel la base sera cr��e
	 * @throws IOException
	 */
	public KnowledgeBase(String nomFichier) throws IOException {
		sourceFilename = nomFichier;
		BufferedReader lectureFichier = new BufferedReader(new FileReader(
				nomFichier));
		System.out
				.println("CHARGEMENT D'UNE BASE DE CONNAISSANCES � partir du fichier : "
						+ nomFichier);

		// cr�ation de la base de faits (utilise les m�thodes de la classe
		// BaseFaits)
		String t = lectureFichier.readLine(); // 1e ligne du fichier contient
												// les faits
		BF = new FactBase(t);

		// d�termination de la taille de BR
		t = lectureFichier.readLine(); // 2e ligne du fichier indique le nombre
										// de r�gles
		int n = Integer.parseInt(t);

		// cr�ation de la base de r�gles (utilise les m�thodes de la classe
		// Regle)
		BR = new ArrayList<Rule>(n);
		for (int i = 0; i < n; i++) {
			t = lectureFichier.readLine(); // les lignes suivantes du fichier
											// sont les r�gles
			BR.add(new Rule(t, "R�gle " + (i + 1)));
		}
		lectureFichier.close();
	}

//Les getters de la classe	
	public FactBase getFB() {
		return BF;
	}

	public ArrayList<Rule> getRB() {
		return BR;
	}

	public String getSourceFilename() {
		return sourceFilename;
	}

	public boolean isSaturated() {
		return isSaturated;
	}

// Les m�thodes qui caract�risent les fonctionnalit�es de la classe		
	/**
	 * M�thode qui permet de vider la base de faits de la base de connaissances courante
	 * On note qu'elle indique que la base n'est plus satur�e
	 */
	public void clearFactBase() {
		BF = new FactBase();
		isSaturated = false;
	}
	/**
	 * M�thode qui permet d'ajouter un nouveau fait � la base de faits 
	 * de la base de connaissances courante.
	 * On note qu'elle indique que la base n'est plus satur�e
	 * @param fait le fait (un atome) � ajouter 
	 */
	public void addNewFact(Atom fait) {
		BF.addNewFact(fait);
		isSaturated = false;
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
		
	

	
// Les m�thodes pour se ramener � la logique des propositions
	
	/**
	 * M�thode de saturation << premier ordre >> de la base de faits
	 * par le cha�nage avant
	 * 
	 * @return k la base de connaissances satur�e
	 * @throws IOException
	 */
	public KnowledgeBase saturation_FOL() throws IOException {
		if (isSaturated)
			return this; // evite le calcul de saturation au cas o� la base est
							// d�j� satur�e
		
		// d�claration et initialisation des variables
		KnowledgeBase k = new KnowledgeBase(this);
		ArrayList<Atom> nouveaux; //liste qui stocke les nouveaux faits
		Homomorphisms s;
		boolean fin = false; //indique fin de l'algorithme de cha�nage avant
		Atom temp;
	
		// Debut de l'algorithme de cha�nage avant
		while (!fin) {
			nouveaux = new ArrayList<Atom>(); //initialisement vide
	
			for (Rule r : k.BR) {
				//initialise les homomorphismes de l'hypoth�se de r dans la base de faits
				s = new Homomorphisms(r.getPremise(), k.BF);
				if (s.existsHomomorphismTest()) {
					//calcule les homomorphismes de l'hypoth�se de r dans la base de faits
					for (Substitution hom : s.getHomomorphisms()) {
						//pour chaque homomorphisme hom, consid�re la
						//substitution de la conclusion de r
						temp = r.getConclusion().applySubtitution(hom);
						if (!k.BF.atomExistsTest(temp) && !nouveaux.contains(temp))
							nouveaux.add(temp);//si cette substitution est un nouveau fait,
											   //ajoute ce fait � la liste de nouveaux faits	
					}
				}
			}
			if (nouveaux.size() == 0)  //condition de fin d'algorithme
				fin = true;
			else
				k.BF.addNewFacts(nouveaux); //sinon on continue avec la nouvelle 
													 //base de faits	
		}
		k.isSaturated = true; //indique que la base est satur�e (pour �viter de recalculer
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
	public KnowledgeBase optimizedSaturation_FOL() throws IOException {
		if (isSaturated)
			return this; // evite le calcul de saturation au cas o� la base est
							// d�j� satur�e
		
		// d�claration et initialisation des variables
		KnowledgeBase k = new KnowledgeBase(this);
		RuleDependencyGraph ruleDependencyGraph = new RuleDependencyGraph(k); 
		ruleDependencyGraph.calculeGDR(); //calcule le graphe de d�pendances des r�gles 
		  				  //(et des faits)
		
		//algorithme de saturation avec affichage des �l�ments qui illustrent
		//l'exploitation du graphe de d�pendances des r�gles (et des faits)
		System.out.println(ruleDependencyGraph); 
		System.out.println("--> D�but de l'algorithme de cha�nage avant");
		for (int i = 0; i < BF.getAtomList().size(); i++) {
			System.out.print("\n  Fait consid�r� : "
					+ BF.getAtomList().get(i));
			//appel � la fonction r�cursive qui calcule des nouveaux faits  
			//en appliquant les successeurs des faits (puis r�gles) consid�r�s
			computeNewFactsRec(ruleDependencyGraph.getGraphe().get(i), k.BF); 
													 
														
		}
		System.out.println("\n--> Fin de l'algorithme de cha�nage avant");
		k.isSaturated = true; //indique que la base est satur�e
		return k;
	}

	/**
	 * M�thode r�cursive qui calcule les nouveaux faits g�n�r�s par des successeurs
	 * (de faits ou de r�gles selon le cas) pass�s en param�tre 
	 * @param successeurs La liste de successeurs � consid�rer
	 * @param faits La base de faits courante (les nouveux faits y seront ajout�s)
	 */
	private void computeNewFactsRec(ArrayList<Rule> successeurs, FactBase faits) {
		ArrayList<Rule> successeursNew;
		// Debut de l'algorithme qui exploite le graphe de
		// d�pendance des r�gles
		for (Rule r : successeurs) {
			//Affiche l'ordre dans lequel les r�gles sont consid�r�e
			System.out.print("\n\tR�gle consid�r�e : " + r);
			Homomorphisms s = new Homomorphisms(r.getPremise(), faits);
			if (s.existsHomomorphismTest())
				for (Substitution hom : s.getHomomorphisms()) {
					Atom temp = r.getConclusion().applySubtitution(hom);
					if (!faits.atomExistsTest(temp)) {
						faits.addNewFact(temp);
						System.out.print("\n\tNouveau fait ajout� : " + temp
								+ "\n  �tape suivante : successeurs de "
								+ r.getName());
						successeursNew = new ArrayList<Rule>(
								computeSuccessors(r));
						//appel r�cursive qui parcourt le graphe en profondeur
						computeNewFactsRec(successeursNew, faits); 
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
	private ArrayList<Rule> computeSuccessors(Rule r) {
		RuleDependencyGraph g = new RuleDependencyGraph(this);
		int n = Integer.parseInt(r.getName().substring(6))
				+ BF.getAtomList().size() - 1;

		g.calculeGDR();
		return g.getGraphe().get(n);
	}

//Test de la classe
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		KnowledgeBase k = new KnowledgeBase("RuleBase");
		System.out.println(k);
//		KnowledgeBase kSaturee = k.saturationOrdre0();
	//	System.out.println(kSaturee);

		k = k.optimizedSaturation_FOL();
		System.out.println("\n\nBase de Faits satur�e par homomorphismes:\n\n"
				+ k.BF);

	}
}
