package ac.analysis.searchEngine;

 

import java.util.ArrayList;

import ac.analysis.structure.*;

/**
* La classe qui calcule et stocke les substitutions d'un ensemble de termes variables dans un 
* autre ensemble de termes constantes
*/
public class Substitutions 
{

	private ArrayList<Term> T1;
	private ArrayList<Term> T2;
	
	public ArrayList<Substitution> S;

	/**
	 * Constructeur de la classe Substitutions
	 * @param variables les termes variables 
	 * @param constantes les termes constantes 
	 */
	public Substitutions (ArrayList<Term> variables, ArrayList<Term> constantes) 
	{
		T1 = variables; //l'ensemble de termes (variables)
		T2 = constantes; //l'ensemble de termes (constantes)
		S = new ArrayList<Substitution>(); //l'ensemble de substitutions initialement vide	
	}
	
	/**
	 * M�thode r�cursive qui g�n�re l'ensemble de substitutions de T1 dans T2 et le stocke dans S
	 */
	public void getSubstitutions(Substitution s) 
	{
		if (s != null && s.nombreCouples() == T1.size())
			S.add(s); //si nombre de couples = nombre de variables, ajoute substitution � S
		else 
			for (int i = 0; i < T2.size(); i++) //pour toutes les constantes
			{
				//g�n�re le couple (prochaine variable, constante) 
				TermPair couple = new TermPair(T1.get(s.nombreCouples()), T2.get(i));
				Substitution temp = new Substitution(s);//copie de s
				temp.addCouple(couple); //ajoute le couple � la substitution
				getSubstitutions(temp); //appel r�cursive
			}			
		return;
	}
}