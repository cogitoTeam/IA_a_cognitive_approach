package ac.analysis.structure;

import java.io.Serializable;

/**
 * Mod�lise un terme : une variable ou une constante. Elle poss�de un attribut bool�en qui
 * determine si ce terme est constante ou pas, et une m�thode qui determine l'egalit� entre 
 * deux termes.
 *
 */
public class Term implements Serializable, Comparable<Term>
{
	/**
   * 
   */
  private static final long serialVersionUID = 5174312039198169556L;
  private String label;//le nom du terme (par exemple : x, 'toto')
	private boolean constant;//vrai si le terme courant est une constante, faux sinon (c'est une variable)

//Les constructeurs de la classe	
	/**
	 * Constructeur de la classe Terme
	 * @param n le label du terme
	 * @param c boolean qui indique si le terme est une constante ou pas (et alors c'est une variable)
	 */
	public Term(String n, boolean c)
	{
		label = n;
		constant = c;	}
	
	/**
	 * Constructeur de la classe Terme pour cr�er une variable
	 * @param n le label du terme (qui doit �tre une variable)
	 */
	public Term(String n)
	{
		label = n;
		constant=false;
	}
	
//Les getters de la classe	
	/**
	 * Indique si le terme est une constante
	 * @return vrai si le terme est une constante, faux sinon
	 */
	public boolean isConstant()
	{
		return constant;
	}
	
	/**
	 * Indique si le terme est une variable
	 * @return vrai si le terme est une variable, faux sinon
	 */
	public boolean isVariable()
	{
		return !constant;
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
	 * 
	 * Teste l'�galite du terme 't' et du terme courant (constante, label)
	 * @param t le terme � tester
	 * @return vrai si 't' et le terme courant sont �gaux, faux sinon
	 * 
	 * @Deprecated use equals because is an method of Object class.
	 */
	@Deprecated
	public boolean equalsT(Term t)
	{
		return(t.constant==constant && t.label.equals(this.label));
	}
	
	/**
	* Teste l'égalite du terme 't' et du terme courant (constante, label)
	* @param o le terme à tester
	* @return vrai si 't' et le terme courant sont égaux, faux sinon.
	*/
  @Override
  public boolean equals(Object o)
  {
    if(!o.getClass().equals(this.getClass()))
      return false;
    
    Term t = (Term)o;
    return t.constant == this.constant && t.label.equals(this.label);
  }
	
	//M�thode toString de la classe		
	/**
	 * Retourne la cha�ne de caract�res de ce terme
	 * @return la cha�ne d�crivant le terme 
	 */
	public String toString()
	{
		if(constant) return "'"+label+"'";
		else return label;
	}

  @Override
  public int compareTo(Term t)
  {
    int val = this.label.compareTo(t.label);
    if(val == 0)
      val = ((Boolean)this.constant).compareTo(t.constant);
    
    return val;
  }
	
	
}
