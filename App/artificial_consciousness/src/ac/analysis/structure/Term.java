package ac.analysis.structure;

/**
 * Mod�lise un terme : une variable ou une constante. Elle poss�de un attribut bool�en qui
 * determine si ce terme est constante ou pas, et une m�thode qui determine l'egalit� entre 
 * deux termes.
 *
 */
public class Term
{
	private String label;//le nom du terme (par exemple : x, 'toto')
	private boolean constante;//vrai si le terme courant est une constante, faux sinon (c'est une variable)

//Les constructeurs de la classe	
	/**
	 * Constructeur de la classe Terme
	 * @param n le label du terme
	 * @param c boolean qui indique si le terme est une constante ou pas (et alors c'est une variable)
	 */
	public Term(String n, boolean c)
	{
		label = n;
		constante = c;	}
	
	/**
	 * Constructeur de la classe Terme pour cr�er une variable
	 * @param n le label du terme (qui doit �tre une variable)
	 */
	public Term(String n)
	{
		label = n;
		constante=false;
	}
	
//Les getters de la classe	
	/**
	 * Indique si le terme est une constante
	 * @return vrai si le terme est une constante, faux sinon
	 */
	public boolean isConstante()
	{
		return constante;
	}
	
	/**
	 * Indique si le terme est une variable
	 * @return vrai si le terme est une variable, faux sinon
	 */
	public boolean isVariable()
	{
		return !constante;
	}
	

	/**
	 * Accesseur en lecture
	 * @return le label du terme
	 */
	public String getLabel()
	{
		return label;
	}

// Les m�thodes qui caract�risent les fonctionnalit�es de la classe	
	
	/**
	 * Teste l'�galite du terme 't' et du terme courant (constante, label)
	 * @param t le terme � tester
	 * @return vrai si 't' et le terme courant sont �gaux, faux sinon
	 */
	public boolean equalsT(Term t)
	{
		return(t.constante==constante && t.label.equals(this.label));
	}
	
	/**
	 * Affiche le terme
	 */
	public void afficher()
	{
		System.out.print(this); // appel de toString
	}
	
//M�thode toString de la classe		
	/**
	 * Retourne la cha�ne de caract�res de ce terme
	 * @return la cha�ne d�crivant le terme 
	 */
	public String toString()
	{
		if(constante) return "'"+label+"'";
		else return label;
	}
	
	
}
