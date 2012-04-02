package structure;

import java.util.ArrayList;


/**
 * Modélise une substitution. Elle constiste d'une liste de CoupleTermes 
 * et permet , par exemple , de définir la substitution des variables d'une règle
 * par des constantes : chaque variable correspond à un CoupleTerme 
 * qui définit sa substitution par une constante.
 * 
 * Elle possède en particulier une méthode qui détermine si cette substitution 
 * est un homomorphisme
 * 
 */
public class Substitution 
{

	private ArrayList<CoupleTermes> listeCouples = null;

	/**
	 * Constructeur vide de la classe Substition
	 */
	public Substitution () 
	{
		listeCouples = new ArrayList<CoupleTermes>();		
	}

//Les constructeurs de la classe
	/**
	 * Constructeur de la classe Substition
	 * @param liste une liste de couples de termes
	 */
	public Substitution (ArrayList <CoupleTermes> ensembleCouples) 
	{
		listeCouples = ensembleCouples;			
	}

	/**
	 * Constructeur par copie de substitution
	 * @param from la substitution à copier
	 * @return to la substitution copiée
	 */
	public Substitution (Substitution from)
	{
		listeCouples = new ArrayList<CoupleTermes>(from.getListeCouples());
	}
	
// Les getters de la classe	
	public ArrayList<CoupleTermes> getListeCouples ()
	{
		return listeCouples;
	}

// Les méthodes qui caractérisent les fonctionnalitées de la classe	
	/**
	 * @return variables La liste des variables présents dans la substitution
	 */
	public ArrayList<Terme> getVariables ()
	{
		ArrayList<Terme> variables = new ArrayList<Terme> ();
		for (CoupleTermes c : listeCouples)
			variables.add(c.getX());
		return variables;
	}

	/**
	 * Ajoute un couple de termes à la substitution
	 * @param couple un couple de termes : (variable,constante)
	 */
	public void addCouple (CoupleTermes couple)
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
	 * Méthode qui remplace les termes d'une string selon la substitution définie
	 * @param s string dans laquelle on désire faire le remplacement
	 * @return t nouvelle string avec les termes remplacées
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
	 * Méthode qui détermine si cette substitution est un homomorphisme 
	 * entre deux ensembles d'atomes (donnés en paramètre)
	 * @param a1 L'ensemble des variables
	 * @param a2 L'ensemble des valeurs
	 */
	public boolean estHomomorphisme (ArrayList<Atome> a1, ArrayList<Atome> a2)
	{
		int counter = 0;
		Atome aSubstituer;
		for (Atome a : a1)
		{
			aSubstituer = a.appliquerSubstitution(this);
			for (Atome b : a2)
				if (aSubstituer.unifiableA(b))
					counter++;
		}
		return (counter == a1.size());//en particulier, si a1 = null, alors retourne vrai
	}
	
//Méthode toString de la classe	
	public String toString()
	{
		String s = "{";
		for (CoupleTermes c: listeCouples)
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
		ArrayList<CoupleTermes> couples=new ArrayList<CoupleTermes>();
		Terme x = new Terme ("x"), y = new Terme("y"), z = new Terme("z"), t = new Terme("t"), a = new Terme ("a",true), b = new Terme ("b",true), c = new Terme ("c",true);
		
		couples.add(new CoupleTermes(x,a));
		couples.add(new CoupleTermes(y,b));
		couples.add(new CoupleTermes(z,c));
		couples.add(new CoupleTermes(t,b));		
		
		s = new Substitution(couples);
		
		Atome a1 = new Atome("r(x,y,z)"), b1 = new Atome("s(z,t)"), c1 = new Atome("r('a','b','c')"),
				d = new Atome("s('b','a')"), e = new Atome("s('c','b')");
		ArrayList<Atome> var=new ArrayList<Atome>(), val=new ArrayList<Atome>();
		var.add(b1);
		var.add(a1);
		val.add(c1);
		val.add(d);
		val.add(e);
		
		if(s.estHomomorphisme(var, val))
			System.out.println(s + " est un homomorphisme");
	}

}
