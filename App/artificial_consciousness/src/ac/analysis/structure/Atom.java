package ac.analysis.structure;

import java.util.ArrayList;

/**
 * The <code>Atom</code> class represents an atomic formula in predicate logic.
 * It consists of:
 * <p>
 * <code>label</code> (the predicate's name) and <code>terms</code> (list of
 * elements of the class <code>Term</code>)
 * 
 * @author namratapatel
 */

public class Atom
{
  /* **************************************************************************
   * FIELD
   * ************************************************************************* */

  private String label;
  private ArrayList<Term> terms;

  /* *****************************************************************************
   * CONSTRUCTORS
   * **************************************************************************** */

  /**
   * Copy Constructor
   * 
   * @param copy
   */
  public Atom(Atom copy)
  {
    label = new String(copy.getLabel());
    terms = new ArrayList<Term>(copy.getTerms());
  }

  /**
   * Default Constructor :
   * <p>
   * Creates an atom from a well-formatted string
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

  /**
   * Empty Constructor
   */
  public Atom()
  {
    label = "";
    terms = new ArrayList<Term>();
  }

  /* *****************************************************************************
   * GETTERS AND SETTERS
   * **************************************************************************** */

  public ArrayList<Term> getTerms()
  {
    return terms;
  }

  public void setTerms(ArrayList<Term> listeTermes)
  {
    this.terms = listeTermes;
  }

  public String getLabel()
  {
    return label;
  }

  /* *****************************************************************************
   * METHODS
   * **************************************************************************** */

  /**
   * Adds term <code>t</code> to the atom
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
   * @return {@code True} if the two atoms are equivalent, {@code False}
   *         otherwise
   */
  public boolean equalsA(Atom r)
  {
    if (!equalsP(r))
      return false;
    for (int i = 0, counter = 0; i < terms.size(); i++)
      {
        if (terms.get(i).equalsT(r.terms.get(i)))
          counter++;
        if (counter == terms.size())
          return true;
      }
    return false;
  }

  /**
   * Checks if this {@code Atom} and the one specified are "unifiable"
   * 
   * @param a
   *          the atom to check this {@code Atom} against
   * @return {@code True} if the two atoms are "unifiable", {@code False}
   *         otherwise
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
   * M�thode qui remplace toutes les occurences d'un terme par un terme donn�
   * 
   * @param inList
   *          Liste dans laquelle on cherche
   * @param from
   *          Terme qui sera remplac�
   * @param to
   *          Terme de substitution
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
   * M�thode qui applique une substitution � l'atome courant:
   * 
   * @param s
   *          La substitution � appliquer
   * @return aSubstituer L'atome substitu�
   */
  public Atom applySubtitution(Substitution s)
  {
    Atom aSubstituer = new Atom(this);
    for (TermPair c : s.getListeCouples())
      aSubstituer.replace(c.getX(), c.getY(), aSubstituer.getTerms());
    return aSubstituer;
  }

  /**
   * M�thode qui rend l'ensemble des variables de deux atomes disjoints
   * 
   * @param a
   *          Atome a
   * @param b
   *          Atome b
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

  // La m�thode toString de la classe

  /**
   * Retourne la cha�ne de caract�res de cet atome
   * 
   * @return la cha�ne d�crivant l'atome (suivant l'�criture logique habituelle)
   */
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

  // juste pour une d�mo de la classe
  public static void main(String[] args)
  {
    Atom a = new Atom("r(x,y,z)");
    Atom b = new Atom("r(x,y,x)");
    if (a.unifiableA(b))
      System.out.println("a = " + a + " et b = " + b + " sont unifiables"); // appel
                                                                            // a.toString()
    else
      System.out.println("a = " + a + " et b = " + b
          + " ne sont pas unifiables"); // appel b.toString()

  }
}
