package ac.analysis.structure;

/**
 * Mod�lise une requ�te conjonctive : une liste d'atomes. 
 * La classe BaseFaits poss�dant toutes les fonctionnalit�es attendues par une requ�te,
 * cette classe en est une sp�cialisation : notamment, elle ne modifie que la m�thode toString
 *
 */
public class Requete extends BaseFaits {
//Les constructeurs 
	public Requete() {
	}

	public Requete(BaseFaits BF) {
		super(BF);
	}

	public Requete(String baseFaits) {
		super(baseFaits);
	}
// La m�thode toString de la classe	
	public String toString()
	{
		String s = "Nombre d'atomes : "+listeAtomes.size()+ "\n";
		s+="Liste des atomes : \n";
		for(int i=0;i<listeAtomes.size();i++)
		{
			s += "\tAtome " + (i+1) + " : " + listeAtomes.get(i) + "\n";			
		}
	 return s;
	}

}
