package ac.analysis.structure;

import java.util.*;

/**
 * The {@code FactBase} class represents a fact base : a list of assertions. <p> It constists of : <p>  the type {@link Atom}
 *
 */
public class FactBase
{
	protected ArrayList<Atom> atomList;//l'ensemble des faits (atomes)
	private ArrayList<Term> ensembleTermes;//l'ensemble des termes pr�sents
	
//Les constructeurs de la classe 
	/**
	 * Constructeur de la classe 'BaseFaits'
	 * cr�e une base de faits vide
	 */
	public FactBase()
	{
		atomList = new ArrayList<Atom>();
		ensembleTermes = new ArrayList<Term>();
	}
	
	/**
	 * Constructeur par copie de la classe 'BaseFaits'
	 */
	public FactBase(FactBase BF)
	{
		atomList = new ArrayList<Atom>(BF.getListeAtomes());
		ensembleTermes = new ArrayList<Term>(BF.getEnsembleTermes());
	}
	
	/**
	 * Constructeur de la classe 'BaseFaits'
	 * @param baseFaits les faits, pass�s sous la forme "atom1;...;atomk"
	 */
	public FactBase(String baseFaits)
	{
		atomList = new ArrayList<Atom>();
		ensembleTermes = new ArrayList<Term>();
		creerBaseFaits(baseFaits);	
	}
	
//Les getters et setters de la classe	
	public ArrayList<Atom> getListeAtomes() {
		return atomList;
	}

	public void setListeAtomes(ArrayList<Atom> listeAtomes) {
		this.atomList = listeAtomes;
	}

	public ArrayList<Term> getEnsembleTermes() {
		return ensembleTermes;
	}

	public void setEnsembleTermes(ArrayList<Term> ensembleTermes) {
		this.ensembleTermes = ensembleTermes;
	}

	
//Les m�thodes qui d�finissent les fonctionnalit�es de la classe

	private void creerBaseFaits(String baseFaits)
	//Pr�requis : le format de la base de faits est suppos� correct
   	{
		Term t;
   		StringTokenizer st = new StringTokenizer(baseFaits,";");
   		while(st.hasMoreTokens())
   		{
   			String s = st.nextToken(); // s repr�sente un atome
   			Atom a = new Atom(s);
   			ajouterAtome(a);//On ajoute un atome � la liste des atomes
   			ArrayList<Term> terms = a.getTerms();
   			for (int i = 0; i < terms.size(); i ++)
   			{
   				t = ajouterTerme(terms.get(i));
   				a.getTerms().set(i,t);
   			}
   		}
   	}
	
	/**
	 * Ajoute des faits � la base de faits s'ils n'apparaissaient pas d�j�
	 * @param faits les faits � ajouter (seuls ceux n'apparaissant pas dans la base seront ajout�s)
	 */
	public void ajouterNouveauxFaits(ArrayList<Atom> faits)
	// Spec Interne : la liste des termes apparaissant dans la base est �galement
	// mise � jour
	{
		for(int i=0;i<faits.size();i++)
			ajouterNouveauFait(faits.get(i));
	}
	
	/**
	 * Ajoute un fait � la base de faits s'il n'apparait pas d�j�
	 * @param fait le fait � ajouter (ne sera ajout� que s'il n'apparait pas d�j�)
	 */
	public void ajouterNouveauFait(Atom fait)
	// Spec Interne : la liste des termes apparaissant dans la base est �galement
	// mise � jour
	{
			if(!atomeExiste(fait))
			{
				atomList.add(fait);
				for(int j=0;j<fait.getTerms().size();j++)
				{
					Term t = new Term(fait.getTerms().get(j).getLabel(),fait.getTerms().get(j).isConstante());//On cr�e un nouveau terme
					t = ajouterTerme(t); // ceci ajoute le terme dans la liste des termes de la base de faits s'il n'existait pas d�j�
					atomList.get(atomList.size()-1).getTerms().set(j,t);
				}
			}
	}

	/**
	 * Ajoute un terme � la liste des termes s'il n'existe pas d�j�.
	 * @param t le terme � potentiellement ajouter
	 * @return un sommet terme, soit t s'il a �t� ins�r�, soit le sommet terme qui existait d�j� dans la liste des sommets termes
	 */
	public Term ajouterTerme(Term t)
	//SI : dans le cas o� le terme t n'existe pas d�j� dans la liste des sommets termes, on l'ajoute � la bonne place
	//et on lui donne comme voisin l'atome se trouvant � l'index "index" dans la liste des atomes
	//Sinon, on ajoute l'atome se trouvant � l'index "index" dans la liste des atomes au sommet terme d�j� existant dans la liste des sommets termes
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
	private int[] positionDichoTerme(Term t)
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
	//SE : recherche le nom, de fa�on r�cursive, entre les indices d�but et fin de la liste des termes. d�but et fin 
	//doivent obligatoirement �tre positifs et inf�rieurs � la taille de la liste des termes.
	//tabReponses : la premi�re cellule est � -1 si le terme appara�t d�j�
	//				la seconde � la place o� doit �tre ins�r� le terme
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
	 * Ajoute un atome � la base de faits sans autre v�rification
	 * tous les termes de l'atome doivent d�j� exister dans la base
	 * @param a l'atome � ajouter
	 * @return la position o� l'atome 'a' a �t� ajout� (s'il existait d�j� il est ajout� en double)
	 */
	private int ajouterAtome(Atom a)
	{
		atomList.add(a);
		return atomList.size()-1;
	}
	
	/**
	 * Teste l'existence d'un atome dans la base
	 * @param a l'atome dont on teste l'existence
	 * @return vrai si l'atome existe d�j�, faux sinon
	 */
	public boolean atomeExiste(Atom a)
	{
		for(int i=0;i<atomList.size();i++)
		{
			if(atomList.get(i).equalsP(a))
			{
				int j=0;
				for(j=0;j<a.getTerms().size();j++)
				{
					if(!a.getTerms().get(j).equalsT(atomList.get(i).getTerms().get(j)))
						break;
				}
				if(j==a.getTerms().size())
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Teste l'existence d'un terme dans la base
	 * @param t le terme dont on teste l'existence
	 * @return vrai si le terme existe d�j�, faux sinon
	 */
	public boolean termeExiste(Term t)
	{
		for(int i=0;i<ensembleTermes.size();i++)
			if(ensembleTermes.get(i).equalsT(t)) return true;
		return false;
	}
	
	/**
	 * Affiche une description textuelle de la base de faits
	 */
	public void afficher()
	{	
		System.out.println(this); //this.toString()
	}
//La m�thode toString de la classe	
	/**
	 * retourne une description de la base de faits
	 * @return description de la base de faits
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String s = "Nombre de faits : "+atomList.size()+ "\n";
		s+="Liste des faits : \n";
		for(int i=0;i<atomList.size();i++)
		{
			s += "\tFait " + (i+1) + " : " + atomList.get(i) + "\n";			
		}
	
		s+="Liste des termes : ";
		{
			for(int i=0;i<ensembleTermes.size();i++)
			{
				s+=ensembleTermes.get(i);
				if(i<ensembleTermes.size()-1) s+=" ; ";
			}
		}
		s+="\n";
	 return s;
	}
}
