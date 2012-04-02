/**
 * 
 */
package ac.analysis.moteurDeRecherche;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ac.analysis.structure.*;



/**
 * La classe qui mod�lise un graphe de d�pendances de r�gles (et de faits et de la requ�te)
 * Elle poss�de les m�thodes qui calculent ce graphe pour une base de connaissances donn�e
 *
 */
public class GDR 
{

	private BaseConnaissances BC; //la base de connaissances r�f�renc�e
	private ArrayList<ArrayList<Regle>> graphe; //tableau des listes de successeurs de chaque r�gle

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
	 * Constructeur � partir d'une base de connaissances
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

//Les m�thodes qui caract�risent les fonctionnalit�es de la classe	
	/**
	 * M�thode de calcul du graphe de d�pendances des r�gles
	 */
	public void calculeGDR()
	{
		ArrayList<Regle> listeSuccesseurs;
		
		//calcul de r�gles d�pendants des faits
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
		
		//calcul de r�gles d�pendants des r�gles
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
		q.setNom("Requ�te");
		q.setHypothese(requete.getListeAtomes());
		q.setConclusion(new Atome("gagn�()"));
		k.getBR().add(q);
		GDR avecRequete = new GDR(k);
		avecRequete.calculeGDR();
		return avecRequete;
	}

	
//La m�thode toString de la classe 
	 
	public String toString()
	{
		String s = new String();
		s+="GRAPHE DE D�PENDENCE DES FAITS ET DES R�GLES :\n";
		
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
