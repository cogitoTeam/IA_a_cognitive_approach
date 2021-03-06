package ac.analysis.structure;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The <code>Atom</code> class represents an atomic formula in predicate logic.
 * It consists of:
 * <p>
 * <code>label</code> (the predicate's name) and <code>terms</code> (list of
 * elements of the class {@link Term})
 * 
 * @author namratapatel
 */

public class Atom implements Serializable, Comparable<Atom>
{
  /**
   * 
   */
  private static final long serialVersionUID = 655992608339297963L;
  /* **************************************************************************
   * FIELD
   * ************************************************************************* */
  private String label;
  private ArrayList<Term> terms;

  /* *****************************************************************************
   * CONSTRUCTORS
   * **************************************************************************** */

  /**
   * Empty Constructor for {@link Atom}
   */
  public Atom()
  {
    label = "";
    terms = new ArrayList<Term>();
  }

  /**
   * 
   * @param label
   *          the label of this atom.
   * @param termes
   *          the list of terms of this atom.
   */
  public Atom(String label, ArrayList<Term> termes)
  {
    this.label = label;
    this.terms = termes;
  }

  /**
   * Copy Constructor
   * 
   * @param copy
   *          {@link Atom}
   */
  public Atom(Atom copy)
  {
    label = new String(copy.getLabel());
    terms = new ArrayList<Term>(copy.getTerms());
  }

  /**
   * Constructor :
   * <p>
   * Creates an {@link Atom} from a well-formatted string
   * 
   * @param s
   *          a well-formatted string representing an atom, e.g.
   *          <p>
   *          <blockquote>
   * 
   * <pre>
   * near(x,y)
   */
  public Atom(String s)
  {

    terms = new ArrayList<Term>();
    if (s.charAt(s.length() - 1) != ')')
      label = s;
    else
      {
        int cpt = 0;
        String nomAtome = "";
        String nomTerme = "";
        boolean constanteTerme;

        while (s.charAt(cpt) != ')')
          {
            while (s.charAt(cpt) != '(')
              {
                nomAtome += s.charAt(cpt);
                cpt++;
              }
            label = nomAtome;
            cpt++;
            while (s.charAt(cpt) != ')')
              {
                while (s.charAt(cpt) != ',' && s.charAt(cpt) != ')')
                  {
                    nomTerme += s.charAt(cpt);
                    cpt++;
                  }
                if (nomTerme.charAt(0) == '\'')
                  {
                    constanteTerme = true;
                    nomTerme = nomTerme.substring(1, nomTerme.length() - 1);
                  }
                else
                  constanteTerme = false;
                Term t = new Term(nomTerme, constanteTerme);
                terms.add(t);
                nomTerme = "";
                if (s.charAt(cpt) == ',')
                  cpt++;
              }

          }
      }
  }

  /* *****************************************************************************
   * GETTERS AND SETTERS
   * **************************************************************************** */

  /**
   * @return
   *         {@code terms} : the list of terms in this {@code Atom}
   * 
   */
  public ArrayList<Term> getTerms()
  {
    return terms;
  }

  /**
   * @param termList
   *          The list of terms to set to this {@code Atom}
   */
  public void setTerms(ArrayList<Term> termList)
  {
    this.terms = termList;
  }

  /**
   * @return {@code label} : this {@code Atom}'s predicate name
   */
  public String getLabel()
  {
    return label;
  }

  /**
   * @param label
   *          The label to set to this {@code Atom}
   */
  public void setLabel(String label)
  {
    this.label = label;
  }

  /* *****************************************************************************
   * METHODS
   * **************************************************************************** */

  /**
   * Adds {@link Term} {@code t} to this {@code Atom}
   * 
   * @param t
   *          the term to add
   */
  public void addTerm(Term t)
  {
    terms.add(t);
  }

  /**
   * Compares this {@code Atom}'s predicate to that of the specified atom
   * 
   * @param r
   *          the atom to compare this {@code Atom} against
   * @return {@code True} if the two atoms have the same predicate,
   *         {@code False} otherwise
   */
  public boolean equalsP(Atom r)
  {
    return (this.label.equals(r.label) && terms.size() == r.terms.size());
  }

  /**
   * Compares this {@code Atom} to the one specified (same label and same list
   * of terms)
   * 
   * @param r
   *          the atom to compare this {@code Atom} against
   * @return {@code true} if the two atoms are equivalent, {@code false}
   *         otherwise
   */
  public boolean equalsA(Atom r)
  {
    if (!equalsP(r))
      return false;

    for (int i = 0; i < terms.size(); i++)
      if (!terms.get(i).equalsT(r.terms.get(i)))
        return false;

    return true;
  }

  /**
   * Makes this {@code Atom} and the one specified distinct, i.e
   * <p>
   * if the 2 atoms have variables, their variables are renamed so that they are
   * distinct
   * 
   * @param b
   *          the atom to make distinct with this {@code Atom} (it is modified
   *          in the method)
   * @return a copy of this {@code Atom} after its modification (leaving this
   *         {@code Atom} unmodified in the method)
   */
  public Atom makeDistinct(Atom b)
  {
    Atom a = new Atom(this);
    for (int i = 0; i < a.getTerms().size(); i++)
      {
        if (a.getTerms().get(i).isVariable())
          a.getTerms().set(i,
              new Term(a.getTerms().get(i).getLabel() + "1", false));
        if (b.getTerms().get(i).isVariable())
          b.getTerms().set(i,
              new Term(b.getTerms().get(i).getLabel() + "2", false));
      }
    return a;
  }

  /**
   * Checks if this {@code Atom} and the one specified are "unifiable"
   * 
   * @param r
   *          the atom to check this {@code Atom} against
   * @return {@code True} if the two atoms are identical by unification,
   *         {@code False} otherwise
   * 
   */
  public boolean unifiableA(Atom r)
  {
    Atom a = new Atom(this), b = new Atom(r);
    if (a.getTerms().size() != b.getTerms().size() || !b.equalsP(a))
      return false;
    a = makeDistinct(b);

    int indicator = 0;
    for (int i = 0; i < a.getTerms().size(); i++)
      {
        if (a.getTerms().get(i).isVariable())
          {
            replace(a.getTerms().get(i), b.getTerms().get(i), a.getTerms());
            replace(a.getTerms().get(i), b.getTerms().get(i), b.getTerms());
          }
        else if (b.getTerms().get(i).isVariable())
          {
            replace(b.getTerms().get(i), a.getTerms().get(i), a.getTerms());
            replace(b.getTerms().get(i), a.getTerms().get(i), b.getTerms());
          }
      }
    for (int i = 0; i < a.getTerms().size(); i++)
      {
        if (a.getTerms().get(i).equalsT(b.getTerms().get(i)))
          indicator++;
        else if (a.getTerms().get(i).isVariable()
            || b.getTerms().get(i).isVariable())
          indicator++;
      }
    return (indicator == a.getTerms().size());

  }

  /**
   * Replaces all occurrences of term {@code from} by term {@code to} in the
   * specified list of terms
   * 
   * @param inList
   *          The list that contains the term to be replaced
   * @param from
   *          the old term to be replaced
   * @param to
   *          the new term to replace with
   */
  public void replace(Term from, Term to, ArrayList<Term> inList)
  {
    for (int i = 0; i < inList.size(); i++)
      {
        if (inList.get(i).equalsT(from))
          inList.set(i, to);
      }
  }

  /**
   * Applies the specified {@link Substitution} to this {@code Atom}
   * 
   * @param s
   *          the substitution to apply to this {@code Atom}
   * @return a copy of this {@code Atom} after its modification (leaving this
   *         {@code Atom} unmodified in the method)
   */
  public Atom applySubtitution(Substitution s)
  {
    Atom substitute = new Atom(this);
    for (TermPair c : s.getPairList())
      substitute.replace(c.getX(), c.getY(), substitute.getTerms());
    return substitute;
  }

  public String toString()
  {
    String s = label + "(";
    for (int i = 0; i < terms.size(); i++)
      {
        s += terms.get(i);
        if (i < terms.size() - 1)
          s += ",";
      }
    s += ")";
    return s;
  }

  /**
   * @param t
   * @return the value of t.contains
   */
  public boolean contains(Term t)
  {
    return this.terms.contains(t);
  }

  /* *****************************************************************************
   * MAIN
   * **************************************************************************** */

  /**
   * Demo of class
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    Atom a = new Atom("r(x,y,z)");
    Atom b = new Atom("r(x,y,x)");
    if (a.unifiableA(b))
      System.out.println("a = " + a + " and b = " + b + " are unifiable");
    else
      System.out.println("a = " + a + " and b = " + b + " are not unifiable");

  }

  @Override
  public int compareTo(Atom o)
  {
    int val = this.label.compareTo(o.label);
    if (val == 0)
      {
        for (int i = 0; i < terms.size(); i++)
          {
            val = terms.get(i).compareTo(o.terms.get(i));
            if (val != 0)
              return val;
          }
      }
    return val;
  }
}
