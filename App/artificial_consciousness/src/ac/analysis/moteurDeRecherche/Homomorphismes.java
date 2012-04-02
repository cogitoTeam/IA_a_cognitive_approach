/**
 * 
 */
package ac.analysis.moteurDeRecherche;

import java.io.IOException;
import java.util.ArrayList;

import ac.analysis.structure.*;



/**
 * La classe qui calcule et stocke les homomorphismes d'un ensemble d'atomes dans un 
 * autre ensemble d'atomes
 *
 */
public class Homomorphismes {
	private ArrayList<Atome> A1;
	private ArrayList<Atome> A2;
	
	private ArrayList<Terme> variablesOrdonnees;
	
	private ArrayList<Substitution> S;

	/**
	 * Constructeur de la classe Homomorphismes
	 * @param  ensembleVariables l'ensemble d'atomes � termes variables 
	 * @param ensembleValeurs l'ensemble d'atomes � termes constantes 
	 */
	public Homomorphismes (ArrayList<Atome> ensembleVariables, ArrayList<Atome> ensembleValeurs) 
	{
		A1 = ensembleVariables; //l'ensemble de termes (variables)
		A2 = ensembleValeurs; //l'ensemble de termes (constantes)
		variablesOrdonnees = new ArrayList<Terme>();
		S = new ArrayList<Substitution>(); //l'ensemble de homomorphismes initialement vide	
	}
	
	public Homomorphismes(ArrayList<Atome> requete, BaseFaits bf) 
	{
		A1 = requete; //l'ensemble de termes (variables)
		A2 = bf.getListeAtomes(); //l'ensemble de termes (constantes)
		variablesOrdonnees = new ArrayList<Terme>();
		S = new ArrayList<Substitution>();
	}

	public Homomorphismes(Requete requete, BaseFaits bf) {
		A1 = requete.getListeAtomes();
		A2 = bf.getListeAtomes();
		variablesOrdonnees = new ArrayList<Terme>();
		S = new ArrayList<Substitution>();
	}

	/**
	 * M�thode qui retourne les termes de A2
	 */
	private ArrayList<Terme> getDomaine()
	{
		ArrayList<Terme> termes = new ArrayList<Terme>();
		boolean contient;
		for (Atome a: A2)
		{
			for (Terme t:a.getListeTermes())
			{
				contient = false;
				for (Terme i:termes) {
					if(t.equalsT(i))
						contient = true;
				}
				if(!contient)
					termes.add(t);
			}
		}
		return termes;
	}
	
	/**
	 * M�thode qui retourne les variables de A1
	 */
	private ArrayList<Terme> getVariables()
	{
		ArrayList<Terme> variables = new ArrayList<Terme>();
		
		for (Atome a: A1)
		{
			for (Terme t:a.getListeTermes())
			{
				boolean contient=false;
				if (t.isVariable()) 
				{
					for (Terme i : variables) 
					{
						if (t.equalsT(i))
							contient = true;
					}
					if (!contient)
						variables.add(t);
				}
			}
		}
		return variables;
	}
	
	/**
	 * M�thode (BacktrackToutesSolutions) qui g�n�re l'ensemble de homomorphismes de A1 dans A2 et le stocke dans S
	 */
	public ArrayList<Substitution> getHomomorphismes() 
	{
		pretraitement();
		backtrackAllRec(new Substitution());
		return S;
	}
	
	/**
	 * M�thode (Backtrack) qui recherche l'existence d'un homomorphisme de A1 dans A2
	 */
	public boolean existeHomomorphisme ()
	{
		pretraitement();
		return backtrackRec(new Substitution ());
	}
	
	/**
	 * M�thode BacktrackRec le sous-algorithme de existeHomomorphisme
	 */
	private boolean backtrackRec(Substitution sol)
	{
		Terme x;
		ArrayList<Terme> candidats;
		Substitution solPrime;
		if (sol.nombreCouples() == variablesOrdonnees.size())
			return true;
		else
		{
			x = choisirVariableNonAffectee(sol);
			candidats = calculerCandidats(x);
			for (Terme c: candidats)
			{
				solPrime = new Substitution (sol);
				solPrime.addCouple(new CoupleTermes(x,c));
				if (estHomomorphismePartiel(solPrime))
					if(backtrackRec(solPrime))
						return true;
			}		
			return false;		
		}
	}
	
	/**
	 * M�thode qui teste si une substitution est un homomorphisme partiel
	 * @param solPrime la substitution � consid�rer
	 */
	private boolean estHomomorphismePartiel(Substitution sol) 
	{
		ArrayList<Atome> A1Prime = new ArrayList<Atome>();
		for (Atome a : A1)
		{
			int counter = 0;
			for (Terme t1 : a.getListeTermes())
				for (Terme t2 : sol.getVariables())
					if (t1.equalsT(t2)||t1.isConstante())
						counter++;
			if (counter >= a.getListeTermes().size())
				A1Prime.add(a);
		}
		return sol.estHomomorphisme(A1Prime, A2);
	}

	/**
	 * M�thode de pr�traitement des variables de A1 qui calcule un ordre total sur ces variables
	 */
	private void pretraitement()
	{
		//ordonne variables de A1 (donne rang � chacun), ordonne atomes de A1 selon rang		
		variablesOrdonnees = getVariables();
	}
	
	/**
	 * M�thode qui retourne l'ensemble d'images possibles pour la variable donn�e en param�tre
	 * @param x une variable de A1
	 * @return images l'ensemble de termes (constantes) qui sont les images possibles de x
	 */
	private ArrayList<Terme> calculerCandidats(Terme x)
	{
		return getDomaine();
	}
	
	/**
	 * M�thode qui retourne la prochaine variable non affect�e de A1
	 * 
	 */
	private Terme choisirVariableNonAffectee(Substitution sol)
	{
			return variablesOrdonnees.get(sol.nombreCouples());
	}
	
	/**
	 * M�thode BacktrackAllRec le sous-algorithme de getHomomorphismes
	 */
	private void backtrackAllRec(Substitution sol)
	{
		Terme x;
		ArrayList<Terme> candidats;
		Substitution solPrime;
		if (sol.nombreCouples() == variablesOrdonnees.size())
		{
			S.add(sol);
			return;
		}
		else
		{
			x = choisirVariableNonAffectee(sol);
			candidats = calculerCandidats(x);
			for (Terme c: candidats)
			{
				solPrime = new Substitution (sol);
				solPrime.addCouple(new CoupleTermes(x,c));
				if (estHomomorphismePartiel(solPrime))
					backtrackAllRec(solPrime);
			}		
		}
	}

	public static void main(String[] args) throws IOException
	{
		BaseConnaissances k = new BaseConnaissances("ex2.txt");
		k = k.saturationOrdre1Exploite();
		Requete requete = new Requete("p('A',y);r(x,y,z)");
		Homomorphismes h = new Homomorphismes (requete,k.getBF());
		System.out.println("A1 = " + requete + "\nA2 = " + k.getBF() + "\n\nExistent-ils d'homomorphismes de A1 dans A2 ?");
		if(h.existeHomomorphisme())
		{
			System.out.println(" Oui");
			System.out.println("La liste de r�ponses est :");
			System.out.println(h.getHomomorphismes());
		}
		else
			System.out.println("Non");
	}
}
