package ac.analysis.structure;

import java.io.Serializable;
import java.util.*;

/**
 * The {@code FactBase} class represents a fact base : a list of assertions.
 * <p>
 * It consists of :
 * <p>
 * {@code atomList} a list of elements of type {@link Atom} (the facts) and
 * <p>
 * {@code terms} a list of elements of type {@link Term} (all the terms in the
 * base)
 * 
 */
public class FactBase implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -8578199799701477962L;
  /* **************************************************************************
   * FIELD
   * ************************************************************************* */

  protected ArrayList<Atom> atomList;
  private ArrayList<Term> terms;

  /* **************************************************************************
   * CONSTRUCTORS
   * ************************************************************************* */
  /**
   * Empty Constructor for {@link FactBase}
   */
  public FactBase()
  {
    atomList = new ArrayList<Atom>();
    terms = new ArrayList<Term>();
  }

  /**
   * Copy Constructor
   * 
   * @param BF
   *          {@link FactBase}
   */
  public FactBase(FactBase BF)
  {
    atomList = new ArrayList<Atom>(BF.getAtomList());
    terms = new ArrayList<Term>(BF.getTermSet());
  }

  /**
   * Constructor :
   * <p>
   * Creates the fact base {@link FactBase} from a well-formed string, e.g.
   * 
   * <pre>
   * &quot;atom1;...;atomk&quot;
   * </pre>
   * 
   * @param theFactBase
   *          the atoms, passed in string form, as shown above
   */
  public FactBase(String theFactBase)
  {
    atomList = new ArrayList<Atom>();
    terms = new ArrayList<Term>();
    createFactBase(theFactBase);
  }

  /* **************************************************************************
   * GETTERS AND SETTERS
   * ************************************************************************* */

  /**
   * @return the list of {@code Atoms} in this {@code FactBase}
   */

  public ArrayList<Atom> getAtomList()
  {
    return atomList;
  }

  /**
   * @param anAtomList
   *          the list of {@code Atoms} to set
   */
  public void setAtomList(ArrayList<Atom> anAtomList)
  {
    this.atomList = anAtomList;
  }

  /**
   * @return the set of {@code Terms} in this {@code FactBase}
   */
  public ArrayList<Term> getTermSet()
  {
    return terms;
  }

  /**
   * @param termSet
   */
  public void setTermSet(ArrayList<Term> termSet)
  {
    this.terms = termSet;
  }

  /* **************************************************************************
   * METHODS
   * ************************************************************************* */

  /**
   * The method creates a Fact Base from a well-formatted String
   * 
   * @param factBase
   *          a string representing the fact base
   */
  private void createFactBase(String factBase)
  // Prerequisite: the string is supposed as well-formatted
  {
    Term t;
    StringTokenizer st = new StringTokenizer(factBase, ";");
    while (st.hasMoreTokens())
      {
        String s = st.nextToken();
        Atom a = new Atom(s);
        addAtom(a);
        ArrayList<Term> terms = a.getTerms();
        for (int i = 0; i < terms.size(); i++)
          {
            t = addTerm(terms.get(i));
            a.getTerms().set(i, t);
          }
      }
  }

  /**
   * Adds a list of new facts (passed as parameters) to the {@code FactBase}
   * 
   * @param facts
   *          the facts to be added (provided they do not already exist in the
   *          {@code FactBase})
   */
  public void addNewFacts(ArrayList<Atom> facts)
  {
    for (int i = 0; i < facts.size(); i++)
      addNewFact(facts.get(i));
  }

  /**
   * Adds a single new fact (passed as parameter) to the {@code FactBase}
   * 
   * @param fact
   *          the fact to be added (provided it does not already exist in the
   *          {@code FactBase})
   */
  public void addNewFact(Atom fact)
  {
    if (!atomExistsTest(fact))
      {
        atomList.add(fact);
        for (int j = 0; j < fact.getTerms().size(); j++)
          {
            Term t = new Term(fact.getTerms().get(j).getLabel(), fact
                .getTerms().get(j).isConstant());
            t = addTerm(t);
            atomList.get(atomList.size() - 1).getTerms().set(j, t);
          }
      }
  }

  /**
   * Adds a term to the list of terms in the {@code FactBase}
   * 
   * @param t
   *          the term to be added (provided it does not already exist in the
   *          term list of the {@code FactBase})
   * @return the term that has been added or the term that existed already in
   *         the term list of the {@code FactBase}
   * 
   */
  public Term addTerm(Term t)
  {
    int[] retour;

    retour = termDichotomicPosition(t);
    if (retour[0] != -1)
      terms.add(retour[1], t);
    return terms.get(retour[1]);
  }

  /**
   * Looks for the position at which to insert the {@code term} t
   * 
   * @param t
   *          the {@code term} to insert
   * @return the position at which to insert the {@code term} t
   */
  private int[] termDichotomicPosition(Term t)
  {
    int[] tableauReponses = new int[2];
    if (terms.size() > 0)
      return termRecursiveDichotomicPosition(t.getLabel(), 0, terms.size() - 1,
          tableauReponses);
    else
      {
        tableauReponses[0] = 0;
        tableauReponses[1] = 0;
        return tableauReponses;
      }
  }

  /**
   * Looks for the name between the indexes begin and end in the term list
   * 
   * @param name
   * @param begin
   * @param end
   * @param answerTable
   *          Its first cell is -1 if the term exists in the list. If not, its
   *          second cell shows the position at which to insert the term
   * @return the answerTable specifying the correct positions to insert (or not)
   *         the term
   */
  private int[] termRecursiveDichotomicPosition(String name, int begin,
      int end, int[] answerTable)
  {
    if (begin > end)
      {
        answerTable[0] = begin;
        answerTable[1] = begin;
        return answerTable;
      }
    int middle = (begin + end) / 2;
    int compare = terms.get(middle).getLabel().compareTo(name);
    if (compare == 0)
      {
        answerTable[0] = -1;
        answerTable[1] = middle;
        return answerTable;
      }
    if (compare > 0)
      return termRecursiveDichotomicPosition(name, begin, middle - 1,
          answerTable);
    return termRecursiveDichotomicPosition(name, middle + 1, end, answerTable);
  }

  /**
   * Adds an atom to the fact base (all terms in this atom should belong already
   * to the base)
   * 
   * @param a
   *          the atom to add
   * @return the position at which the atom has been added
   */
  private int addAtom(Atom a)
  {
    atomList.add(a);
    return atomList.size() - 1;
  }

  /**
   * Tests the existence of an atom in the fact base
   * 
   * @param a
   *          the atom to test
   * @return true if the atom exists, false otherwise
   */
  public boolean atomExistsTest(Atom a)
  {
    for (int i = 0; i < atomList.size(); i++)
      {
        if (atomList.get(i).equalsP(a))
          {
            int j = 0;
            for (j = 0; j < a.getTerms().size(); j++)
              {
                if (!a.getTerms().get(j)
                    .equalsT(atomList.get(i).getTerms().get(j)))
                  break;
              }
            if (j == a.getTerms().size())
              return true;
          }
      }
    return false;
  }

  /**
   * Tests the existence of a term in the base
   * 
   * @param t
   *          the term to test
   * @return true if the term exists in the base, false otherwise
   */
  public boolean termExistsTest(Term t)
  {
    for (int i = 0; i < terms.size(); i++)
      if (terms.get(i).equalsT(t))
        return true;
    return false;
  }

  public int hashCode()
  {
    String s = "";
    for (Atom a : atomList)
      {
        s += a.toString();
      }

    return s.hashCode();
  }

  public String toString()
  {
    String s = "Number of facts : " + atomList.size() + "\n";
    s += "List of facts : \n";
    for (int i = 0; i < atomList.size(); i++)
      {
        s += "\tFact " + (i + 1) + " : " + atomList.get(i) + "\n";
      }

    s += "List of terms : ";
      {
        for (int i = 0; i < terms.size(); i++)
          {
            s += terms.get(i);
            if (i < terms.size() - 1)
              s += " ; ";
          }
      }
    s += "\n";
    return s;
  }
}
