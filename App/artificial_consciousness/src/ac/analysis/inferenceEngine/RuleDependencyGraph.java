/**
 * 
 */
package ac.analysis.inferenceEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ac.analysis.structure.*;
import ac.util.ArraySet;
import ac.util.LinkedSet;



/**
 * La classe qui mod�lise un graphe de d�pendances de r�gles (et de faits et de la requ�te)
 * Elle poss�de les m�thodes qui calculent ce graphe pour une base de connaissances donn�e
 *
 */
public class RuleDependencyGraph 
{

	private KnowledgeBase kb; //la base de connaissances r�f�renc�e
	private ArrayList<LinkedSet<Rule>> graphe; //tableau des listes de successeurs de chaque r�gle

//Les constructeurs de la classe	
	/**
	 * Constructeur vide
	 */
	public RuleDependencyGraph() 
	{
		kb = new KnowledgeBase();
		graphe = new ArrayList <LinkedSet<Rule>>();
	}
	
	/**
	 * Constructeur par copie
	 */
	public RuleDependencyGraph(RuleDependencyGraph copy)
	{
		kb = new KnowledgeBase(copy.kb);
		graphe = new ArrayList <LinkedSet<Rule>> (copy.graphe);
	}
	
	/**
	 * Constructeur � partir d'une base de connaissances
	 */
	public RuleDependencyGraph(KnowledgeBase bc)
	{
		kb = bc;
		graphe = new ArrayList <LinkedSet<Rule>>();
	}
	
//Les getters de la classe	
	public KnowledgeBase getKb() {
		return kb;
	}

	public ArrayList<LinkedSet<Rule>> getGraphe() {
		return graphe;
	}

//Les m�thodes qui caract�risent les fonctionnalit�es de la classe	
	/**
	 * Méthode de calcul du graphe de dépendances des règles
	 */
	public void calculeGDR()
	{
		LinkedSet<Rule> listeSuccesseurs;
		
		//calcul de règles dépendants des faits
		for (Atom fait : kb.getFB().getAtomList())
		{
			listeSuccesseurs = new LinkedSet<Rule> ();
			for (Rule r : kb.getRB()) {
				for (Atom a : r.getPremise()) {
					if (fait.unifiableA(a))
						listeSuccesseurs.add(r);
				}
			}
			graphe.add(listeSuccesseurs);
		}
		
		//calcul de règles dépendants des règles
		for (Rule r1 : kb.getRB())
		{
			listeSuccesseurs = new LinkedSet<Rule> ();
			for (Rule r2 : kb.getRB())
				for (Atom a : r2.getPremise())
					if (a.unifiableA(r1.getConclusion()))
						listeSuccesseurs.add(r2);
			graphe.add(listeSuccesseurs);			
		}
	}
	
	public RuleDependencyGraph calculeGDRAvecRequete(FactBase requete, KnowledgeBase k) {
		
		Rule q = new Rule();
		q.setName("Requ�te");
		q.setPremise(requete.getAtomList());
		q.setConclusion(new Atom("gagn�()"));
		k.getRB().add(q);
		RuleDependencyGraph avecRequete = new RuleDependencyGraph(k);
		avecRequete.calculeGDR();
		return avecRequete;
	}

	
//La m�thode toString de la classe 
	 
	public String toString()
	{
		String s = new String();
		s+="GRAPHE DE DÉPENDENCE DES FAITS ET DES RÈGLES :\n";
		
		Iterator<Atom> faitsIter = kb.getFB().getAtomList().iterator();
		Iterator<Rule> reglesIter = kb.getRB().iterator();
		Iterator<LinkedSet<Rule>> listeSuccesseursIter = graphe.iterator();
		Iterator<Rule> successeursIter;

		while (faitsIter.hasNext())	
		{
			s += "Fait : " + faitsIter.next() + "\nListe de successeurs : \n";
			successeursIter = listeSuccesseursIter.next().iterator();
			while (successeursIter.hasNext())
				s += "\t" + successeursIter.next() + "\n";
		}
		while (reglesIter.hasNext())
		{
			s += "\n" + reglesIter.next() + "\nListe de successeurs : \n";
			successeursIter = listeSuccesseursIter.next().iterator();
			while (successeursIter.hasNext())
				s += "\t" + successeursIter.next() + "\n";
		}
		return s;
	}

//Test de la classe	
	public static void main(String[] args) throws IOException 
	{
		KnowledgeBase bc = new KnowledgeBase("ex1.txt");
		RuleDependencyGraph ruleDependencyGraph = new RuleDependencyGraph(bc);
		ruleDependencyGraph.calculeGDR();
		System.out.println(bc + "\n" + ruleDependencyGraph);
	}

}
