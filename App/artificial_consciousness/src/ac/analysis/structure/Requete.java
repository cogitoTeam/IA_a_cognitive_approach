package structure;

/**
 * Modélise une requête conjonctive : une liste d'atomes. 
 * La classe BaseFaits possédant toutes les fonctionnalitées attendues par une requête,
 * cette classe en est une spécialisation : notamment, elle ne modifie que la méthode toString
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
// La méthode toString de la classe	
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
