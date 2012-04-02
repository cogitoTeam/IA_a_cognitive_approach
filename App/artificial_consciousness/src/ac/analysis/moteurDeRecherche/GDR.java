/**
 * 
 */
package moteurDeRecherche;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import structure.Atome;
import structure.BaseFaits;
import structure.Regle;


/**
 * La classe qui modélise un graphe de dépendances de règles (et de faits et de la requête)
 * Elle possède les méthodes qui calculent ce graphe pour une base de connaissances donnée
 *
 */
public class GDR 
{

	private BaseConnaissances BC; //la base de connaissances référencée
	private ArrayList<ArrayList<Regle>> graphe; //tableau des listes de successeurs de chaque règle

//Les constructeurs de la classe	
	/**
	 * Constructeur vide
	 */
	public GDR() 
	{
		BC = new BaseConnaissances();
		graphe = new ArrayList <ArrayList<Regle>>();
	}
	
	/**
	 * Constructeur par copie
	 */
	public GDR(GDR copy)
	{
		BC = new BaseConnaissances(copy.BC);
		graphe = new ArrayList <ArrayList <Regle>> (copy.graphe);
	}
	
	/**
	 * Constructeur à partir d'une base de connaissances
	 */
	public GDR(BaseConnaissances bc)
	{
		BC = bc;
		graphe = new ArrayList <ArrayList<Regle>>();
	}
	
//Les getters de la classe	
	public BaseConnaissances getBC() {
		return BC;
	}

	public ArrayList<ArrayList<Regle>> getGraphe() {
		return graphe;
	}

//Les méthodes qui caractérisent les fonctionnalitées de la classe	
	/**
	 * Méthode de calcul du graphe de dépendances des règles
	 */
	public void calculeGDR()
	{
		ArrayList<Regle> listeSuccesseurs;
		
		//calcul de règles dépendants des faits
		for (Atome fait : BC.getBF().getListeAtomes())
		{
			listeSuccesseurs = new ArrayList <Regle> ();
			for (Regle r : BC.getBR()) {
				for (Atome a : r.getHypothese()) {
					if (fait.unifiableA(a))
						listeSuccesseurs.add(r);
				}
			}
			graphe.add(listeSuccesseurs);
		}
		
		//calcul de règles dépendants des règles
		for (Regle r1 : BC.getBR())
		{
			listeSuccesseurs = new ArrayList <Regle> ();
			for (Regle r2 : BC.getBR())
				for (Atome a : r2.getHypothese())
					if (a.unifiableA(r1.getConclusion()))
						listeSuccesseurs.add(r2);
			graphe.add(listeSuccesseurs);			
		}
	}
	
	public GDR calculeGDRAvecRequete(BaseFaits requete, BaseConnaissances k) {
		
		Regle q = new Regle();
		q.setNom("Requête");
		q.setHypothese(requete.getListeAtomes());
		q.setConclusion(new Atome("gagné()"));
		k.getBR().add(q);
		GDR avecRequete = new GDR(k);
		avecRequete.calculeGDR();
		return avecRequete;
	}

	
//La méthode toString de la classe 
	 
	public String toString()
	{
		String s = new String();
		s+="GRAPHE DE DÉPENDENCE DES FAITS ET DES RÈGLES :\n";
		
		Iterator<Atome> faitsIter = BC.getBF().getListeAtomes().iterator();
		Iterator<Regle> reglesIter = BC.getBR().iterator();
		Iterator<ArrayList<Regle>> listeSuccesseursIter = graphe.iterator();
		Iterator<Regle> successeursIter;

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
		BaseConnaissances bc = new BaseConnaissances("ex1.txt");
		GDR gdr = new GDR(bc);
		gdr.calculeGDR();
		System.out.println(bc + "\n" + gdr);
	}

}
