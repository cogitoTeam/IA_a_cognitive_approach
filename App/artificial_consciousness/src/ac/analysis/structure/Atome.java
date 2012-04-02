package structure;

import java.util.ArrayList;

/**
 * Modélise un atome. Elle possède un label indiquant le nom de son prédicat 
 * et une liste de termes qui sont soit constantes, soit variables.
 *
 */

public class Atome
{
	private String label; //le prédicat de l'atome
	private ArrayList<Terme> listeTermes; //la liste de termes de cet atome

//Les constructeurs de la classe	
	/**
	 * Constructeur par copie
	 */
	public Atome (Atome copy)
	{
		label = new String(copy.getLabel());
		listeTermes = new ArrayList<Terme>(copy.getListeTermes());
	}
	/**
	 * Constructeur de la classe Atome
	 * Crée un atome, avec ou sans termes
	 * @param n l'atome, soit réduit à un nom de prédicat, soit de la forme
	 * prédicat(liste de termes), où les termes sont séparés par des points virgules
	 */
	public Atome (String s)
	{
		
   		listeTermes = new ArrayList<Terme>();
   		if (s.charAt(s.length()-1)!=')') // c'est donc un atome sans termes
   			label = s;
   		else
   		{
   		int cpt = 0;
   	   	String nomAtome = "";//pour construire le label de l'atome
   	   	String nomTerme = "";//pour construire le terme courant
		boolean constanteTerme;//le terme courant-il est une constante ou non
   		
   		while(s.charAt(cpt) != ')')
   		{
   			while(s.charAt(cpt) != '(')//On récupère le label de l'atome
   			{
   				nomAtome += s.charAt(cpt);
   				cpt++;
   			}
   			label = nomAtome;
   			cpt++;//Pour sauter le '('
   			while(s.charAt(cpt) != ')')//On va désormais s'intéresser aux termes de l'atome
   			{
				while(s.charAt(cpt) != ',' && s.charAt(cpt) != ')')//On récupère le label du terme
				{
					nomTerme += s.charAt(cpt);
					cpt++;					
				}
				if(nomTerme.charAt(0) == '\'')//On teste si le terme est une constante
				{
					constanteTerme = true;
					nomTerme = nomTerme.substring(1,nomTerme.length()-1);//Si c'est une constante alors on supprime les "'"
				}
				else constanteTerme = false;
				Terme t = new Terme(nomTerme,constanteTerme);//On crée un nouveau terme
				listeTermes.add(t);//On ajoute le terme créé s'il n'existait pas déjà
				nomTerme = "";
				if(s.charAt(cpt) == ',') cpt++;//Pour sauter le ','
   			}
 
   		}
   		}
	}
	
	/**
	 * Constructeur vide
	 */
	public Atome() {
		label = "";
		listeTermes = new ArrayList<Terme>();
	}
	
// Les getters et setters de la classe	
	public ArrayList<Terme> getListeTermes() {
		return listeTermes;
	}

	public void setListeTermes(ArrayList<Terme> listeTermes) {
		this.listeTermes = listeTermes;
	}

	public String getLabel()
	{
		return label;
	}
		
// Les méthodes qui caractérisent les fonctionnalitées de la classe	

	/**
	 * Ajoute le terme 't' à la liste de termes de l'atome, sans autre vérification
	 * @param t le terme à ajouter
	 */
	public void ajoutTerme(Terme t)
	{
		listeTermes.add(t);
	}
	/**
	 * Teste l'egalité des prédicats de deux atomes avec le label et l'arité
	 * de l'atome
	 * @param r l'atome à tester par rapport à l'atome courant
	 * @return vrai si les deux atomes ont même prédicat, faux sinon
	 */
	public boolean equalsP(Atome r)
	{
		return (this.label.equals(r.label) && listeTermes.size()==r.listeTermes.size());
	}

/**
	 * Teste l'egalité de deux atomes (même label et même liste de termes)
	 * @param r l'atome à tester par rapport à l'atome courant
	 * @return vrai si les deux atomes sont égaux, faux sinon
	 */
	public boolean equalsA(Atome r)
	{
		if (!equalsP(r)) return false;
		for (int i=0, counter=0; i < listeTermes.size(); i++) 
		{
			if (listeTermes.get(i).equalsT(r.listeTermes.get(i))) counter++;
			if (counter == listeTermes.size()) 
				return true;
		}
		return false; 
	}

	/**
	 * Méthode qui teste si deux atomes sont unifiables
	 * @param a,b les atomes à tester 
	 * @return vrai si les deux atomes sont unifiables, faux sinon
	 */
	public boolean unifiableA(Atome r)
	{
		Atome a = new Atome(this), b = new Atome(r);
		if (a.getListeTermes().size() != b.getListeTermes().size()||!b.equalsP(a)) return false;
		a = rendDisjoints(b); //rend l'ensemble de variables de l'atome courant et de l'atome b disjoints
		
		int indicator = 0; //cet indicateur sert à compter le nombre de termes égaux des atomes testés
		
		//Pour chaque terme t de l'atome a,
		for (int i = 0; i < a.getListeTermes().size(); i++)
		{
			//Si t est une variable,
			if (a.getListeTermes().get(i).isVariable())
			{
				//remplace toutes les occurences de t dans a et b par le terme correspondant de b (constante ou variable)
				replace(a.getListeTermes().get(i), b.getListeTermes().get(i), a.getListeTermes());
				replace(a.getListeTermes().get(i), b.getListeTermes().get(i), b.getListeTermes());
			}
			else //(si t est constant)
				//si le terme correspondand de b est une variable, fait l'inverse
				if (b.getListeTermes().get(i).isVariable())
				{
					replace(b.getListeTermes().get(i), a.getListeTermes().get(i), a.getListeTermes());
					replace(b.getListeTermes().get(i), a.getListeTermes().get(i), b.getListeTermes());
				}
		}
		//teste l'égalité des termes de a et de b après toutes les substitutions en incrémentant indicator
		for (int i = 0; i < a.getListeTermes().size(); i++)
		{
				if (a.getListeTermes().get(i).equalsT(b.getListeTermes().get(i)))
					indicator++;
				else
					if (a.getListeTermes().get(i).isVariable()||b.getListeTermes().get(i).isVariable())
						indicator++;
		}
		return (indicator == a.getListeTermes().size());
			
	}
	
	/**
	 * Méthode qui remplace toutes les occurences d'un terme par un terme donné
	 * @param inList Liste dans laquelle on cherche
	 * @param from Terme qui sera remplacé
	 * @param to Terme de substitution
	 */
	public void replace (Terme from, Terme to, ArrayList<Terme> inList)
	{
		for (int i = 0 ; i < inList.size(); i++)
		{
			if (inList.get(i).equalsT(from))
				inList.set(i, to);
		}	
	}
	
	/**
	 * Méthode qui applique une substitution à l'atome courant:
	 * @param s La substitution à appliquer
	 * @return aSubstituer L'atome substitué
	 */
	public Atome appliquerSubstitution (Substitution s)
	{
		Atome aSubstituer = new Atome(this);
		for (CoupleTermes c : s.getListeCouples())
			aSubstituer.replace(c.getX(), c.getY(), aSubstituer.getListeTermes());
		return aSubstituer;
	}
	
	/**
	 * Méthode qui rend l'ensemble des variables de deux atomes disjoints
	 * @param a Atome a
	 * @param b Atome b
	 */
	public Atome rendDisjoints(Atome b)
	{
		Atome a = new Atome(this);
		for (int i = 0; i < a.getListeTermes().size(); i++)
			{
				if (a.getListeTermes().get(i).isVariable())
					a.getListeTermes().set(i, new Terme(a.getListeTermes().get(i).getLabel() + "1", false));
				if (b.getListeTermes().get(i).isVariable())
					b.getListeTermes().set(i, new Terme(b.getListeTermes().get(i).getLabel() + "2", false));
			}	
		return a;
	}
	
	/**
	 * Affiche l'atome selon le format logique habituel
	 */
	public void afficherAvecTermes()
	{
		System.out.println(this); // appel de toString
	}

// La méthode toString de la classe	
	
	/**
	 * Retourne la chaîne de caractères de cet atome
	 * @return la chaîne décrivant l'atome (suivant l'écriture logique habituelle)
	 */
	public String toString()
	{
		String s = label + "(";
		for(int i=0;i<listeTermes.size();i++)
		{
			s+=listeTermes.get(i);
			if(i<listeTermes.size()-1) s+=",";
		}
		s+=")";
	return s;
	}
	
// juste pour une démo de la classe
	public static void main(String[] args)
	{
		Atome a = new Atome("r(x,y,z)");
		Atome b = new Atome("r(x,y,x)");
		if (a.unifiableA(b))
			System.out.println("a = " + a + " et b = " + b + " sont unifiables"); // appel a.toString()	
		else	
			System.out.println("a = " + a + " et b = " + b + " ne sont pas unifiables"); // appel b.toString()
		
	}
}

