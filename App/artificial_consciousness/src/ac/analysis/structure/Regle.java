package ac.analysis.structure;



import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Mod�lise une r�gle de la forme "si hypoth�se alors conclusion: , o� l'hypoth�se est compos�e dune conjonction
 * d'atomes, et la conclusion est compos�e d'un seul atome. Elle poss�de aussi l'ensemble de termes 
 * apparaissant dans ses atomes pour pr�ciser que chaque terme n'y appara�t qu'une seule fois
 *
 */
public class Regle
{
	private String nom;
	private ArrayList<Atome> hypothese;//la liste des atomes hypoth�ses
	private Atome conclusion;//la conclusion de la r�gle
	private ArrayList<Terme> ensembleTermes;//l'ensemble des termes pr�sents dans la r�gle

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
	 * @param regle la r�gle, sous forme textuelle ; cette forme est sous forme  
	 * "atome1;atome2;...atomek", o� les (k-1) premiers atomes forment l'hypoth�se,
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
   			String s = st.nextToken(); // s repr�sente un atome
   			Atome a = new Atome(s);
   			hypothese.add(a);//ajout de a � la liste des atomes de l'hypoth�se (pour l'instant)
   			ArrayList<Terme> termes = a.getListeTermes();
   			for (int i = 0; i < termes.size(); i ++)
   			{
   				t = ajouterTerme(termes.get(i)); // ajout � la liste des termes
   				a.getListeTermes().set(i,t);
   				
   			}
   		}
   		// on a mis tous les atomes cr��s en hypoth�se
   		// reste � tranf�rer le dernier en conclusion
		conclusion = hypothese.remove(hypothese.size()-1);
	}
	
// Les getters et setters de la classe	
	
	/**
	 * Accesseur en lecture
	 * @return le nom de la r�gle
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Accesseur en lecture
	 * @return l'hypoth�se de la r�gle
	 */
	public ArrayList<Atome> getHypothese() {
		return hypothese;
	}
	
	/**
	 * Accesseur en lecture
	 * @return la conclusion de la r�gle
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
	 * @return l'ensemble de termes de la r�gle
	 */
	public ArrayList<Terme> getEnsembleTermes() {
		return ensembleTermes;
	}

// Les m�thodes qui caract�risent les fonctionnalit�es de la classe	
	
	/**
	 * Ajoute un terme � la liste des termes s'il n'existe pas d�j�
	 * @param t le terme � potentiellement ajouter
	 * @return un sommet terme, soit t s'il a �t� ins�r�, soit le sommet terme qui existait d�j� dans la liste des sommets termes
	 */
	private Terme ajouterTerme(Terme t)
	//SI : dans le cas o� le terme t n'existe pas d�j� dans la liste des sommets termes, on l'ajoute � la bonne place
	//et on lui donne comme voisin le sommet relation se trouvant � l'index "index" dans la liste des sommets relations
	//Sinon, on ajoute le sommet relation se trouvant � l'index "index" dans la liste des sommets relations au sommet terme d�j� existant dans la liste des sommets termes
	{
		int[] retour;
		
		retour = positionDichoTerme(t);//on recherche la position o� ajouter t
		if(retour[0]!= -1) ensembleTermes.add(retour[1],t);//Si t n'apparaissait pas auparavant, on l'ajoute � la liste des termes
		return ensembleTermes.get(retour[1]);//On retourne le terme, soit t s'il a �t� ins�r�, soit le terme qui existait d�j� dans la liste des termes
	}


	/**
	 * Cherche la position o� ins�rer le sommet terme 't'
	 * @param t le sommet terme � ins�rer 
	 * @return la position o� doit �tre ajout�e le sommet terme
	 */
	private int[] positionDichoTerme(Terme t)
	//SE : si t se trouve dans la liste des termes, retourne son indice.
	//sinon retourne l'indice o� il devrait �tre ins�r�
	//SI : appelle la m�thode positionDichoRecursif en indiquant comme param�tre de recherche les
	//indices de d�but et de fin de la liste des termes (� savoir : 0 et ensembleTermes.size()-1)
	//tableauR�ponses : la premi�re cellule est � -1 si le terme appara�t d�j�
	//					la seconde � la place o� doit �tre ins�r� le terme
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


	private int[] positionDichoRecursifTerme(String nom, int debut, int fin, int[] tabReponses)
	//SE : recherche nom, de fa�on r�cursive, entre les indices d�but et fin de la liste des termes. d�but et fin 
	//doivent obligatoirement �tre positifs et inf�rieurs � la taille de la liste des termes.
	//tabReponses : la premi�re cellule est � -1 si le terme appara�t d�j�
	//		    la seconde � la place o� doit �tre ins�r� le terme
	{
	  if (debut>fin)
	  {
		  tabReponses[0] = debut;
		  tabReponses[1] = debut;
		  return tabReponses; // et on sort
	  }
	  int milieu = (debut+fin)/2;
	  int compare = ensembleTermes.get(milieu).getLabel().compareTo(nom) ;
	  if (compare == 0)//Si le terme de nom "nom" existe d�j�
	  {
		  tabReponses[0] = -1;
		  tabReponses[1] = milieu;
		  return tabReponses; // et on sort 
	  }
	  if (compare > 0) return positionDichoRecursifTerme(nom,debut,milieu-1,tabReponses);
	  return positionDichoRecursifTerme(nom,milieu+1,fin,tabReponses);
	}


	/**
	 * Affiche les caract�ristiques de la r�gle
	 */
	public void afficher()
	{	
		System.out.println(this); // this.toString()
	}

// La m�thode toString de la classe	

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
		Atome c = new Atome("animal"); // on donne juste le nom du pr�dicat
		c.ajoutTerme(new Terme("x")); //puis on ajoute un terme
		System.out.println(c); // appel c.toString()
		Regle r = new Regle("carnivore(x);mange(x,'Viande')","R�gle 1");
		System.out.println(r);
	}

}


