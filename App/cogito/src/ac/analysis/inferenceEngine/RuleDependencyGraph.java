/**
 * 
 */
package ac.analysis.inferenceEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ac.analysis.structure.*;
import ac.util.LinkedSet;



/**
 * Constructs a graph which indicates the dependency of rules and facts
 */
public class RuleDependencyGraph 
{

	private KnowledgeBase kb; 
	private ArrayList<LinkedSet<Rule>> graphe; 


	/**
	 * Empty constructor
	 */
	public RuleDependencyGraph() 
	{
		kb = new KnowledgeBase();
		graphe = new ArrayList <LinkedSet<Rule>>();
	}
	
	/**
	 * Copy constructor
	 * @param copy 
	 */
	public RuleDependencyGraph(RuleDependencyGraph copy)
	{
		kb = new KnowledgeBase(copy.kb);
		graphe = new ArrayList <LinkedSet<Rule>> (copy.graphe);
	}
	
	/**
	 * Constructor
	 * @param k a Knowledge Base 
	 */
	public RuleDependencyGraph(KnowledgeBase k)
	{
		kb = k;
		graphe = new ArrayList <LinkedSet<Rule>>();
	}
	
/**
 * @return the knowledge base
 */
	public KnowledgeBase getKb() {
		return kb;
	}

	/**
	 * @return the rule dependency graph
	 */
	public ArrayList<LinkedSet<Rule>> getGraphe() {
		return graphe;
	}


	/**
	 * Computes rule dependencies and stores them in a graph
	 */
	public void computeRDG()
	{
		LinkedSet<Rule> successorList;
		
		for (Atom fact : kb.getFB().getAtomList())
		{
			successorList = new LinkedSet<Rule> ();
			for (Rule r : kb.getRB()) {
				for (Atom a : r.getPremise()) {
					if (fact.unifiableA(a))
						successorList.add(r);
				}
			}
			graphe.add(successorList);
		}
		
		for (Rule r1 : kb.getRB())
		{
			successorList = new LinkedSet<Rule> ();
			for (Rule r2 : kb.getRB())
				for (Atom a : r2.getPremise())
					if (a.unifiableA(r1.getConclusion()))
						successorList.add(r2);
			graphe.add(successorList);			
		}
	}
	
 
	/**
	 * Computes dependencies including a query (passed as parameter)
	 * @param query
	 * @param k
	 * @return the graph
	 */
	public RuleDependencyGraph calculeGDRAvecRequete(FactBase query, KnowledgeBase k) {
		
		Rule q = new Rule();
		q.setName("Requ�te");
		q.setPremise(query.getAtomList());
		q.setConclusion(new Atom("gagn�()"));
		k.getRB().add(q);
		RuleDependencyGraph avecRequete = new RuleDependencyGraph(k);
		avecRequete.computeRDG();
		return avecRequete;
	}

	
	 
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

/**
 * @param args
 * @throws IOException
 */
//Test de la classe	
	public static void main(String[] args) throws IOException 
	{
		KnowledgeBase bc = new KnowledgeBase("ex1.txt");
		RuleDependencyGraph ruleDependencyGraph = new RuleDependencyGraph(bc);
		ruleDependencyGraph.computeRDG();
		System.out.println(bc + "\n" + ruleDependencyGraph);
	}

}
