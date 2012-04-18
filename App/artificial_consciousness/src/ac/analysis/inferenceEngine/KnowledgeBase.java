package ac.analysis.inferenceEngine;

import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

import ac.analysis.structure.*;
import ac.util.LinkedSet;


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

	/**
   * @param bF the bF to set
   */
  public void setBF(FactBase bF)
  {
    BF = bF;
  }
  
  private static final Logger LOGGER = Logger.getLogger(KnowledgeBase.class);


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
		LOGGER.debug("CHARGEMENT D'UNE BASE DE CONNAISSANCES à partir du fichier : "
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
			BR.add(new Rule(t, "Règle " + (i + 1)));
		}
		lectureFichier.close();
		LOGGER.debug("FIN DU CHARGEMENT DE LA BASE DE CONNAISSANCE");
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
	
	/**
   * M�thode qui permet d'ajouter un nouveau fait � la base de faits 
   * de la base de connaissances courante.
   * On note qu'elle indique que la base n'est plus satur�e
   * @param fait le fait (un atome) � ajouter 
   */
  public void addNewRule(Rule newRule) {
    BR.add(newRule);
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
			return this; // evite le calcul de saturation au cas où la base est
							// déjà saturée
		
		if(LOGGER.isDebugEnabled())
		  LOGGER.debug("DEBUT SATURATION KB");
		
		// déclaration et initialisation des variables
		KnowledgeBase k = new KnowledgeBase(this);
		RuleDependencyGraph ruleDependencyGraph = new RuleDependencyGraph(k); 
		ruleDependencyGraph.calculeGDR(); //calcule le graphe de d�pendances des r�gles 
		  				  //(et des faits)
		
		//algorithme de saturation avec affichage des éléments qui illustrent
		//l'exploitation du graphe de dépendances des règles (et des faits)
	  if(LOGGER.isDebugEnabled())
	    {
	      LOGGER.debug(ruleDependencyGraph.toString()); 
	      LOGGER.debug("--> Début de l'algorithme de chaînage avant");
	    }

	  for (int i = 0; i < BF.getAtomList().size(); i++) {
			//appel à la fonction récursive qui calcule des nouveaux faits  
			//en appliquant les successeurs des faits (puis règles) considérés
			computeNewFactsRec(ruleDependencyGraph.getGraphe().get(i), k.BF); 													 
		}
		
	  k.isSaturated = true; //indique que la base est saturée
		
		if(LOGGER.isDebugEnabled())
      LOGGER.debug("FIN SATURATION KB");
		
		return k;
	}

	/**
	 * Méthode récursive qui calcule les nouveaux faits générés par des successeurs
	 * (de faits ou de règles selon le cas) passés en paramètre 
	 * @param successeurs La liste de successeurs à considérer
	 * @param faits La base de faits courante (les nouveux faits y seront ajoutés)
	 */
	private void computeNewFactsRec(LinkedSet<Rule> successeurs, FactBase faits) {
		// Debut de l'algorithme qui exploite le graphe de
		// dépendance des régles

		if(LOGGER.isDebugEnabled())
      LOGGER.debug(successeurs);
		
		Rule r;
		while (!successeurs.isEmpty()) {   
		  r = successeurs.removeFirst();
		  
			//Affiche l'ordre dans lequel les règles sont considérée
		  if(LOGGER.isDebugEnabled())
	      LOGGER.debug("\n\tRègle considérée : " + r);

	    Homomorphisms s = new Homomorphisms(r.getPremise(), faits);
			if (s.existsHomomorphismTest())
				for (Substitution hom : s.getHomomorphisms()) {
					Atom temp = r.getConclusion().applySubtitution(hom);
					if (!faits.atomExistsTest(temp)) {
						faits.addNewFact(temp);
						 
						if(LOGGER.isDebugEnabled())
				        LOGGER.debug("\n\tNouveau fait ajouté : " + temp);
						
						successeurs.addAll(computeSuccessors(r));
					}
				}
			
			if(LOGGER.isDebugEnabled())
        LOGGER.debug(successeurs);
		}
	}

	/**
	 * M�thode qui calcule les successeurs d'une r�gle pass�e en param�tre
	 * @param r La r�gle dont on veut calculer les successeurs
	 * @return La liste de successeurs de r
	 */
	private LinkedSet<Rule> computeSuccessors(Rule r) {
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
