package ac.analysis.structure;

import java.util.ArrayList;


/**
 * Mod�lise une substitution. Elle constiste d'une liste de CoupleTermes 
 * et permet , par exemple , de d�finir la substitution des variables d'une r�gle
 * par des constantes : chaque variable correspond � un CoupleTerme 
 * qui d�finit sa substitution par une constante.
 * 
 * Elle poss�de en particulier une m�thode qui d�termine si cette substitution 
 * est un homomorphisme
 * 
 */
public class Substitution 
{

	private ArrayList<TermPair> listeCouples = null;

	/**
	 * Constructeur vide de la classe Substition
	 */
	public Substitution () 
	{
		listeCouples = new ArrayList<TermPair>();		
	}

//Les constructeurs de la classe
	/**
	 * Constructeur de la classe Substition
	 * @param liste une liste de couples de termes
	 */
	public Substitution (ArrayList <TermPair> ensembleCouples) 
	{
		listeCouples = ensembleCouples;			
	}

	/**
	 * Constructeur par copie de substitution
	 * @param from la substitution � copier
	 * @return to la substitution copi�e
	 */
	public Substitution (Substitution from)
	{
		listeCouples = new ArrayList<TermPair>(from.getListeCouples());
	}
	
// Les getters de la classe	
	public ArrayList<TermPair> getListeCouples ()
	{
		return listeCouples;
	}

// Les m�thodes qui caract�risent les fonctionnalit�es de la classe	
	/**
	 * @return variables La liste des variables pr�sents dans la substitution
	 */
	public ArrayList<Term> getVariables ()
	{
		ArrayList<Term> variables = new ArrayList<Term> ();
		for (TermPair c : listeCouples)
			variables.add(c.getX());
		return variables;
	}

	/**
	 * Ajoute un couple de termes � la substitution
	 * @param couple un couple de termes : (variable,constante)
	 */
	public void addCouple (TermPair couple)
	{
		listeCouples.add(couple);
	}
	
	/**
	 * Retourne le nombre de couples dans la substitution
	 */
	public int nombreCouples () 
	{
			return listeCouples.size();
	}

	
	/**
	 * M�thode qui remplace les termes d'une string selon la substitution d�finie
	 * @param s string dans laquelle on d�sire faire le remplacement
	 * @return t nouvelle string avec les termes remplac�es
	 */
	public String replace (String s)
	{
		for (int i = 0; i < listeCouples.size(); i++)
		{
			s = listeCouples.get(i).replaceXY(s); 
		}
		return s;
	}
	
	/**
	 * M�thode qui d�termine si cette substitution est un homomorphisme 
	 * entre deux ensembles d'atomes (donn�s en param�tre)
	 * @param a1 L'ensemble des variables
	 * @param a2 L'ensemble des valeurs
	 */
	public boolean estHomomorphisme (ArrayList<Atom> a1, ArrayList<Atom> a2)
	{
		int counter = 0;
		Atom aSubstituer;
		for (Atom a : a1)
		{
			aSubstituer = a.applySubtitution(this);
			for (Atom b : a2)
				if (aSubstituer.unifiableA(b))
					counter++;
		}
		return (counter == a1.size());//en particulier, si a1 = null, alors retourne vrai
	}
	
//M�thode toString de la classe	
	public String toString()
	{
		String s = "{";
		for (TermPair c: listeCouples)
		{
			s += c;
		}
		s+="}";
	return s;
	}
	
//Test de la classe	
	public static void main(String[] args)
	{
		Substitution s;
		ArrayList<TermPair> couples=new ArrayList<TermPair>();
		Term x = new Term ("x"), y = new Term("y"), z = new Term("z"), t = new Term("t"), a = new Term ("a",true), b = new Term ("b",true), c = new Term ("c",true);
		
		couples.add(new TermPair(x,a));
		couples.add(new TermPair(y,b));
		couples.add(new TermPair(z,c));
		couples.add(new TermPair(t,b));		
		
		s = new Substitution(couples);
		
		Atom a1 = new Atom("r(x,y,z)"), b1 = new Atom("s(z,t)"), c1 = new Atom("r('a','b','c')"),
				d = new Atom("s('b','a')"), e = new Atom("s('c','b')");
		ArrayList<Atom> var=new ArrayList<Atom>(), val=new ArrayList<Atom>();
		var.add(b1);
		var.add(a1);
		val.add(c1);
		val.add(d);
		val.add(e);
		
		if(s.estHomomorphisme(var, val))
			System.out.println(s + " est un homomorphisme");
	}

}
