package structure;



import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Modélise une règle de la forme "si hypothèse alors conclusion: , où l'hypothèse est composée dune conjonction
 * d'atomes, et la conclusion est composée d'un seul atome. Elle possède aussi l'ensemble de termes 
 * apparaissant dans ses atomes pour préciser que chaque terme n'y apparaît qu'une seule fois
 *
 */
public class Regle
{
	private String nom;
	private ArrayList<Atome> hypothese;//la liste des atomes hypothèses
	private Atome conclusion;//la conclusion de la règle
	private ArrayList<Terme> ensembleTermes;//l'ensemble des termes présents dans la règle

//// Les constructeurs de la classe	
	
	/**
	 * Constructeur vide
	 */
	public Regle() {
		super();
		this.nom = "";
		this.hypothese = new ArrayList<Atome>();
		this.conclusion = new Atome();
		this.ensembleTermes = new ArrayList<Terme>();
	}


	/**
	 * Constructeur
	 * @param regle la règle, sous forme textuelle ; cette forme est sous forme  
	 * "atome1;atome2;...atomek", où les (k-1) premiers atomes forment l'hypothèse,
	 * et le dernier forme la conclusion
	 */
	public Regle(String regle, String nomRegle)
	{
		Terme t;
		nom = nomRegle;
		hypothese = new ArrayList<Atome>();
		ensembleTermes = new ArrayList<Terme>();
		
		StringTokenizer st = new StringTokenizer(regle,";");
   		while(st.hasMoreTokens())
   		{
   			String s = st.nextToken(); // s représente un atome
   			Atome a = new Atome(s);
   			hypothese.add(a);//ajout de a à la liste des atomes de l'hypothèse (pour l'instant)
   			ArrayList<Terme> termes = a.getListeTermes();
   			for (int i = 0; i < termes.size(); i ++)
   			{
   				t = ajouterTerme(termes.get(i)); // ajout à la liste des termes
   				a.getListeTermes().set(i,t);
   				
   			}
   		}
   		// on a mis tous les atomes créés en hypothèse
   		// reste à tranférer le dernier en conclusion
		conclusion = hypothese.remove(hypothese.size()-1);
	}
	
// Les getters et setters de la classe	
	
	/**
	 * Accesseur en lecture
	 * @return le nom de la règle
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Accesseur en lecture
	 * @return l'hypothèse de la règle
	 */
	public ArrayList<Atome> getHypothese() {
		return hypothese;
	}
	
	/**
	 * Accesseur en lecture
	 * @return la conclusion de la règle
	 */
	public Atome getConclusion()
	{
		return conclusion;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}


	public void setHypothese(ArrayList<Atome> hypothese) {
		this.hypothese = hypothese;
	}


	public void setConclusion(Atome conclusion) {
		this.conclusion = conclusion;
	}


	/**
	 * Accesseur en lecture
	 * @return l'ensemble de termes de la règle
	 */
	public ArrayList<Terme> getEnsembleTermes() {
		return ensembleTermes;
	}

// Les méthodes qui caractérisent les fonctionnalitées de la classe	
	
	/**
	 * Ajoute un terme à la liste des termes s'il n'existe pas déjà
	 * @param t le terme à potentiellement ajouter
	 * @return un sommet terme, soit t s'il a été inséré, soit le sommet terme qui existait déjà dans la liste des sommets termes
	 */
	private Terme ajouterTerme(Terme t)
	//SI : dans le cas où le terme t n'existe pas déjà dans la liste des sommets termes, on l'ajoute à la bonne place
	//et on lui donne comme voisin le sommet relation se trouvant à l'index "index" dans la liste des sommets relations
	//Sinon, on ajoute le sommet relation se trouvant à l'index "index" dans la liste des sommets relations au sommet terme déjà existant dans la liste des sommets termes
	{
		int[] retour;
		
		retour = positionDichoTerme(t);//on recherche la position où ajouter t
		if(retour[0]!= -1) ensembleTermes.add(retour[1],t);//Si t n'apparaissait pas auparavant, on l'ajoute à la liste des termes
		return ensembleTermes.get(retour[1]);//On retourne le terme, soit t s'il a été inséré, soit le terme qui existait déjà dans la liste des termes
	}


	/**
	 * Cherche la position où insérer le sommet terme 't'
	 * @param t le sommet terme à insérer 
	 * @return la position où doit être ajoutée le sommet terme
	 */
	private int[] positionDichoTerme(Terme t)
	//SE : si t se trouve dans la liste des termes, retourne son indice.
	//sinon retourne l'indice où il devrait être inséré
	//SI : appelle la méthode positionDichoRecursif en indiquant comme paramètre de recherche les
	//indices de début et de fin de la liste des termes (à savoir : 0 et ensembleTermes.size()-1)
	//tableauRéponses : la première cellule est à -1 si le terme apparaît déjà
	//					la seconde à la place où doit être inséré le terme
	{
		int[] tableauReponses = new int[2];
		if(ensembleTermes.size()>0) return positionDichoRecursifTerme(t.getLabel(),0,ensembleTermes.size()-1,tableauReponses);
		else
		{
			tableauReponses[0] = 0;
			tableauReponses[1] = 0;
			return tableauReponses;
		}
	}


	private int[] positionDichoRecursifTerme(String nom, int début, int fin, int[] tabReponses)
	//SE : recherche nom, de façon récursive, entre les indices début et fin de la liste des termes. début et fin 
	//doivent obligatoirement être positifs et inférieurs à la taille de la liste des termes.
	//tabReponses : la première cellule est à -1 si le terme apparaît déjà
	//		    la seconde à la place où doit être inséré le terme
	{
	  if (début>fin)
	  {
		  tabReponses[0] = début;
		  tabReponses[1] = début;
		  return tabReponses; // et on sort
	  }
	  int milieu = (début+fin)/2;
	  int compare = ensembleTermes.get(milieu).getLabel().compareTo(nom) ;
	  if (compare == 0)//Si le terme de nom "nom" existe déjà
	  {
		  tabReponses[0] = -1;
		  tabReponses[1] = milieu;
		  return tabReponses; // et on sort 
	  }
	  if (compare > 0) return positionDichoRecursifTerme(nom,début,milieu-1,tabReponses);
	  return positionDichoRecursifTerme(nom,milieu+1,fin,tabReponses);
	}


	/**
	 * Affiche les caractéristiques de la règle
	 */
	public void afficher()
	{	
		System.out.println(this); // this.toString()
	}

// La méthode toString de la classe	

	public String toString()
	{
		String s =  nom + " : ";
		for(int i=0;i<hypothese.size();i++)
		{
			s+=hypothese.get(i);	
			if(i<hypothese.size()-1) s+=",";
		}
		s+=" --> ";  
		s+=conclusion;
	return s;
	}
	
//Test de la classe	
	public static void main(String[] args)
	{
		Atome a = new Atome("mange(x,'Loup')");
		System.out.println(a); // appel a.toString()
		Atome b = new Atome("mange(x,y)");
		System.out.println(b); // appel b.toString()
		Atome c = new Atome("animal"); // on donne juste le nom du prédicat
		c.ajoutTerme(new Terme("x")); //puis on ajoute un terme
		System.out.println(c); // appel c.toString()
		Regle r = new Regle("carnivore(x);mange(x,'Viande')","Règle 1");
		System.out.println(r);
	}

}


