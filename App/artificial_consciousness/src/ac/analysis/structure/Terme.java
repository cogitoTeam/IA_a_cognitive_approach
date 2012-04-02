package structure;

/**
 * Modélise un terme : une variable ou une constante. Elle possède un attribut booléen qui
 * determine si ce terme est constante ou pas, et une méthode qui determine l'egalité entre 
 * deux termes.
 *
 */
public class Terme
{
	private String label;//le nom du terme (par exemple : x, 'toto')
	private boolean constante;//vrai si le terme courant est une constante, faux sinon (c'est une variable)

//Les constructeurs de la classe	
	/**
	 * Constructeur de la classe Terme
	 * @param n le label du terme
	 * @param c boolean qui indique si le terme est une constante ou pas (et alors c'est une variable)
	 */
	public Terme(String n, boolean c)
	{
		label = n;
		constante = c;	}
	
	/**
	 * Constructeur de la classe Terme pour créer une variable
	 * @param n le label du terme (qui doit être une variable)
	 */
	public Terme(String n)
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

// Les méthodes qui caractérisent les fonctionnalitées de la classe	
	
	/**
	 * Teste l'égalite du terme 't' et du terme courant (constante, label)
	 * @param t le terme à tester
	 * @return vrai si 't' et le terme courant sont égaux, faux sinon
	 */
	public boolean equalsT(Terme t)
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
	
//Méthode toString de la classe		
	/**
	 * Retourne la chaîne de caractères de ce terme
	 * @return la chaîne décrivant le terme 
	 */
	public String toString()
	{
		if(constante) return "'"+label+"'";
		else return label;
	}
	
	
}
