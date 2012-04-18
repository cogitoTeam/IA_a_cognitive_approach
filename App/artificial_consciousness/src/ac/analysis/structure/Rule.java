package ac.analysis.structure;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Mod�lise une r�gle de la forme "si hypoth�se alors conclusion: , o� l'hypoth�se est compos�e dune conjonction
 * d'atomes, et la conclusion est compos�e d'un seul atome. Elle poss�de aussi l'ensemble de termes 
 * apparaissant dans ses atomes pour pr�ciser que chaque terme n'y appara�t qu'une seule fois
 *
 */
public class Rule implements Serializable
{
	/**
   * 
   */
  private static final long serialVersionUID = 4373243435597065238L;
  private String name;
	private ArrayList<Atom> premise;//la liste des atomes hypoth�ses
	private Atom conclusion;//la conclusion de la r�gle
	private ArrayList<Term> terms;//l'ensemble des termes pr�sents dans la r�gle

  // ***************************************************************************
  // CONSTRUCTORS
  // ***************************************************************************

	
	/**
	 * Constructeur vide
	 */
	public Rule() 
	{
		super();
		this.name = "";
		this.premise = new ArrayList<Atom>();
		this.conclusion = new Atom();
		this.terms = new ArrayList<Term>();
	}

	/**
	 * 
	 * @param premise hypothèse de la règle.
	 * @param conclusion conclusion de la règle.
	 */
	public Rule(ArrayList<Atom> premise, Atom conclusion) 
	{
	  this.name = "";
    this.premise = premise;
    this.conclusion = conclusion;
    this.terms = new ArrayList<Term>();
    
    for(Atom a : premise)
      for(Term t : a.getTerms())
        this.addTerm(t);
    
    for(Term t : conclusion.getTerms())
      this.addTerm(t);
	}

	/**
	 * Constructeur
	 * @param regle la r�gle, sous forme textuelle ; cette forme est sous forme  
	 * "atome1;atome2;...atomek", o� les (k-1) premiers atomes forment l'hypoth�se,
	 * et le dernier forme la conclusion
	 */
	public Rule(String regle, String nomRegle)
	{
		Term t;
		name = nomRegle;
		premise = new ArrayList<Atom>();
		terms = new ArrayList<Term>();
		
		StringTokenizer st = new StringTokenizer(regle,";");
   		while(st.hasMoreTokens())
   		{
   			String s = st.nextToken(); // s repr�sente un atome
   			Atom a = new Atom(s);
   			premise.add(a);//ajout de a � la liste des atomes de l'hypoth�se (pour l'instant)
   			ArrayList<Term> terms = a.getTerms();
   			for (int i = 0; i < terms.size(); i ++)
   			{
   				t = addTerm(terms.get(i)); // ajout � la liste des termes
   				a.getTerms().set(i,t);
   				
   			}
   		}
   		// on a mis tous les atomes cr��s en hypoth�se
   		// reste � tranf�rer le dernier en conclusion
		conclusion = premise.remove(premise.size()-1);
	}
	
	
	// ***************************************************************************
  // GETTERS / SETTERS
  // ***************************************************************************

	
	/**
	 * Accesseur en lecture
	 * @return le nom de la r�gle
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Accesseur en lecture
	 * @return l'hypoth�se de la r�gle
	 */
	public ArrayList<Atom> getPremise() {
		return premise;
	}
	
	/**
	 * Accesseur en lecture
	 * @return la conclusion de la r�gle
	 */
	public Atom getConclusion()
	{
		return conclusion;
	}
	
	public void setName(String nom) {
		this.name = nom;
	}


	public void setPremise(ArrayList<Atom> hypothese) {
		this.premise = hypothese;
	}


	public void setConclusion(Atom conclusion) {
		this.conclusion = conclusion;
	}


	/**
	 * Accesseur en lecture
	 * @return l'ensemble de termes de la r�gle
	 */
	public ArrayList<Term> getTerms() {
		return terms;
	}

// Les m�thodes qui caract�risent les fonctionnalit�es de la classe	
	
	/**
	 * Ajoute un terme � la liste des termes s'il n'existe pas d�j�
	 * @param t le terme � potentiellement ajouter
	 * @return un sommet terme, soit t s'il a �t� ins�r�, soit le sommet terme qui existait d�j� dans la liste des sommets termes
	 */
	private Term addTerm(Term t)
	//SI : dans le cas o� le terme t n'existe pas d�j� dans la liste des sommets termes, on l'ajoute � la bonne place
	//et on lui donne comme voisin le sommet relation se trouvant � l'index "index" dans la liste des sommets relations
	//Sinon, on ajoute le sommet relation se trouvant � l'index "index" dans la liste des sommets relations au sommet terme d�j� existant dans la liste des sommets termes
	{
		int[] retour;
		
		retour = termDichotomicPosition(t);//on recherche la position o� ajouter t
		if(retour[0]!= -1) terms.add(retour[1],t);//Si t n'apparaissait pas auparavant, on l'ajoute � la liste des termes
		return terms.get(retour[1]);//On retourne le terme, soit t s'il a �t� ins�r�, soit le terme qui existait d�j� dans la liste des termes
	}


	/**
	 * Cherche la position o� ins�rer le sommet terme 't'
	 * @param t le sommet terme � ins�rer 
	 * @return la position o� doit �tre ajout�e le sommet terme
	 */
	private int[] termDichotomicPosition(Term t)
	//SE : si t se trouve dans la liste des termes, retourne son indice.
	//sinon retourne l'indice o� il devrait �tre ins�r�
	//SI : appelle la m�thode positionDichoRecursif en indiquant comme param�tre de recherche les
	//indices de d�but et de fin de la liste des termes (� savoir : 0 et ensembleTermes.size()-1)
	//tableauR�ponses : la premi�re cellule est � -1 si le terme appara�t d�j�
	//					la seconde � la place o� doit �tre ins�r� le terme
	{
		int[] tableauReponses = new int[2];
		if(terms.size()>0) return termRecursiveDichotomicPosition(t.getLabel(),0,terms.size()-1,tableauReponses);
		else
		{
			tableauReponses[0] = 0;
			tableauReponses[1] = 0;
			return tableauReponses;
		}
	}


	private int[] termRecursiveDichotomicPosition(String nom, int debut, int fin, int[] tabReponses)
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
	  int compare = terms.get(milieu).getLabel().compareTo(nom) ;
	  if (compare == 0)//Si le terme de nom "nom" existe d�j�
	  {
		  tabReponses[0] = -1;
		  tabReponses[1] = milieu;
		  return tabReponses; // et on sort 
	  }
	  if (compare > 0) return termRecursiveDichotomicPosition(nom,debut,milieu-1,tabReponses);
	  return termRecursiveDichotomicPosition(nom,milieu+1,fin,tabReponses);
	}


	

// La m�thode toString de la classe	

	public String toString()
	{
		String s =  name + " : ";
		for(int i=0;i<premise.size();i++)
		{
			s+=premise.get(i);	
			if(i<premise.size()-1) s+=",";
		}
		s+=" --> ";  
		s+=conclusion;
	return s;
	}
	
//Test de la classe	
	public static void main(String[] args)
	{
		Atom a = new Atom("mange(x,'Loup')");
		System.out.println(a); // appel a.toString()
		Atom b = new Atom("mange(x,y)");
		System.out.println(b); // appel b.toString()
		Atom c = new Atom("animal"); // on donne juste le nom du pr�dicat
		c.addTerm(new Term("x")); //puis on ajoute un terme
		System.out.println(c); // appel c.toString()
		Rule r = new Rule("carnivore(x);mange(x,'Viande')","R�gle 1");
		System.out.println(r);
	}

}


