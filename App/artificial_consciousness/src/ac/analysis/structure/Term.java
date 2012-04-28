package ac.analysis.structure;

import java.io.Serializable;

/**
 * Represents a Term in first order logic
 * 
 */
public class Term implements Serializable, Comparable<Term>
{
  /**
   * 
   */
  private static final long serialVersionUID = 5174312039198169556L;
  private String label;
  private boolean constant;

  // ***************************************************************************
  // CONSTRUCTORS
  // ***************************************************************************

  /**
   * Constructor
   * 
   * @param n
   *          the label
   * @param c
   *          boolean indicating whether the term is constant or not
   */
  public Term(String n, boolean c)
  {
    label = n;
    constant = c;
  }

  /**
   * Constructor for a variable
   * 
   * @param n
   *          the label
   */
  public Term(String n)
  {
    label = n;
    constant = false;
  }

  // ***************************************************************************
  // METHODS
  // ***************************************************************************

  /**
   * 
   * Equals method
   * 
   * @param t
   *          the term to test
   * @return True if 't' is equal to this term, False otherwise
   * 
   * @Deprecated use equals because it is a method of the Object class.
   */
  public boolean equalsT(Term t)
  {
    return (t.constant == constant && t.label.equals(this.label));
  }

  /**
   *
   * Equals method
   * @param o 
   *          
   * @return True or False depending on the case 
   */
  @Override
  public boolean equals(Object o)
  {
    if (!o.getClass().equals(this.getClass()))
      return false;

    Term t = (Term) o;
    return t.constant == this.constant && t.label.equals(this.label);
  }

  public String toString()
  {
    if (constant)
      return "'" + label + "'";
    else
      return label;
  }

  @Override
  public int compareTo(Term t)
  {
    int val = this.label.compareTo(t.label);
    if (val == 0)
      val = ((Boolean) this.constant).compareTo(t.constant);

    return val;
  }

  // ***************************************************************************
  // GETTERS
  // ***************************************************************************

  /**
   * Indique si le terme est une constante
   * 
   * @return vrai si le terme est une constante, faux sinon
   */
  public boolean isConstant()
  {
    return constant;
  }

  /**
   * Indique si le terme est une variable
   * 
   * @return vrai si le terme est une variable, faux sinon
   */
  public boolean isVariable()
  {
    return !constant;
  }

  /**
   * Accesseur en lecture
   * 
   * @return le label du terme
   */
  public String getLabel()
  {
    return label;
  }

  // ***************************************************************************
  // SETTERS
  // ***************************************************************************

  /**
   * @param t
   */
  public void copy(Term t)
  {
    this.label = new String(t.label);
    this.constant = t.constant;
  }

  /**
   * @param is_constant
   */
  public void setConstant(boolean is_constant)
  {
    this.constant = is_constant;
  }

  /**
   * @param label
   */
  public void setLabel(String label)
  {
    this.label = label;
  }

}
