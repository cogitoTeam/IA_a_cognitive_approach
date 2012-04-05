/**
 * 
 */
package ac.analysis.inferenceEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ac.analysis.structure.*;



/**
 * La classe qui mod�lise un graphe de d�pendances de r�gles (et de faits et de la requ�te)
 * Elle poss�de les m�thodes qui calculent ce graphe pour une base de connaissances donn�e
 *
 */
public class RuleDependencyGraph 
{

	private KnowledgeBase BC; //la base de connaissances r�f�renc�e
	private ArrayList<ArrayList<Rule>> graphe; //tableau des listes de successeurs de chaque r�gle

//Les constructeurs de la classe	
	/**
	 * Constructeur vide
	 */
	public RuleDependencyGraph() 
	{
		BC = new KnowledgeBase();
		graphe = new ArrayList <ArrayList<Rule>>();
	}
	
	/**
	 * Constructeur par copie
	 */
	public RuleDependencyGraph(RuleDependencyGraph copy)
	{
		BC = new KnowledgeBase(copy.BC);
		graphe = new ArrayList <ArrayList <Rule>> (copy.graphe);
	}
	
	/**
	 * Constructeur � partir d'une base de connaissances
	 */
	public RuleDependencyGraph(KnowledgeBase bc)
	{
		BC = bc;
		graphe = new ArrayList <ArrayList<Rule>>();
	}
	
//Les getters de la classe	
	public KnowledgeBase getBC() {
		return BC;
	}

	public ArrayList<ArrayList<Rule>> getGraphe() {
		return graphe;
	}

//Les m�thodes qui caract�risent les fonctionnalit�es de la classe	
	/**
	 * M�thode de calcul du graphe de d�pendances des r�gles
	 */
	public void calculeGDR()
	{
		ArrayList<Rule> listeSuccesseurs;
		
		//calcul de r�gles d�pendants des faits
		for (Atom fait : BC.getBF().getListeAtomes())
		{
			listeSuccesseurs = new ArrayList <Rule> ();
			for (Rule r : BC.getBR()) {
				for (Atom a : r.getHypothese()) {
					if (fait.unifiableA(a))
						listeSuccesseurs.add(r);
				}
			}
			graphe.add(listeSuccesseurs);
		}
		
		//calcul de r�gles d�pendants des r�gles
		for (Rule r1 : BC.getBR())
		{
			listeSuccesseurs = new ArrayList <Rule> ();
			for (Rule r2 : BC.getBR())
				for (Atom a : r2.getHypothese())
					if (a.unifiableA(r1.getConclusion()))
						listeSuccesseurs.add(r2);
			graphe.add(listeSuccesseurs);			
		}
	}
	
	public RuleDependencyGraph calculeGDRAvecRequete(FactBase requete, KnowledgeBase k) {
		
		Rule q = new Rule();
		q.setNom("Requ�te");
		q.setHypothese(requete.getListeAtomes());
		q.setConclusion(new Atom("gagn�()"));
		k.getBR().add(q);
		RuleDependencyGraph avecRequete = new RuleDependencyGraph(k);
		avecRequete.calculeGDR();
		return avecRequete;
	}

	
//La m�thode toString de la classe 
	 
	public String toString()
	{
		String s = new String();
		s+="GRAPHE DE D�PENDENCE DES FAITS ET DES R�GLES :\n";
		
		Iterator<Atom> faitsIter = BC.getBF().getListeAtomes().iterator();
		Iterator<Rule> reglesIter = BC.getBR().iterator();
		Iterator<ArrayList<Rule>> listeSuccesseursIter = graphe.iterator();
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
