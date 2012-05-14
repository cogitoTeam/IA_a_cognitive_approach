package ac.analysis.structure;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Represents a substitution
 */
public class Substitution
{

  private ArrayList<TermPair> pairList = null;
  private ArrayList<Term> variables = null;

  /**
   * Empty constructor
   */
  public Substitution()
  {
    pairList = new ArrayList<TermPair>();
    variables = new ArrayList<Term>();

  }

  /**
   * Constructor
   * @param termPairList
   *          list of term pairs
   */
  public Substitution(ArrayList<TermPair> termPairList)
  {
    pairList = termPairList;
    variables = new ArrayList<Term>();
    for (TermPair c : pairList)
      variables.add(c.getX());
  }

  /**
   * Copy constructor 
   * @param copy 
   * 
   */
  public Substitution(Substitution copy)
  {
    pairList = new ArrayList<TermPair>(copy.getPairList());
    variables = new ArrayList<Term>();
    for (TermPair c : pairList)
      variables.add(c.getX());
  }

  /**
   * @return list of pairs
   */
  public ArrayList<TermPair> getPairList()
  {
    return pairList;
  }


  /**
   * @return variables in the substitution
   */
  public ArrayList<Term> getVariables()
  {
    return variables;
  }
  
  /**
   * @return list of substitutes
   */
  public ArrayList<Term> getSubstitutes()
  {
    ArrayList<Term> list = new ArrayList<Term>();
    for(TermPair p : this.pairList)
      list.add(p.getY());
    
    return list;
  }

  /**
   * 
   * @param pair
   *          term pair : (variable,constant)
   */
  public void addPair(TermPair pair)
  {
    pairList.add(pair);
    variables.add(pair.getX());
  }

  /**
   * 
   * @return the number of pairs in the substitution
   */
  public int num_Pairs()
  {
    return pairList.size();
  }

  /**
   * Applies a substitution
   * 
   * @param s
   *          source string 
   * @return the new string
   */
  public String replace(String s)
  {
    for (int i = 0; i < pairList.size(); i++)
      {
        s = pairList.get(i).replaceXY(s);
      }
    return s;
  }

  /**
   * Determines if this substitution is a homomorphism
   * 
   * @param a1
   *          set of variables
   * @param a2
   *          set of values
   * @return True is it is a homomorphism, False otherwise
   */
  public boolean isHomomorphismTest(ArrayList<Atom> a1, TreeSet<Atom> a2)
  {
    Atom aSubstituer;
    for (Atom a : a1)
      {
        aSubstituer = a.applySubtitution(this);
        if (!a2.contains(aSubstituer))
          return false;

      }
    return true;
  }

  public String toString()
  {
    String s = "{";
    for (TermPair c : pairList)
      {
        s += c;
      }
    s += "}";
    return s;
  }

  // Test de la classe
  /**
   * @param args
   */
  public static void main(String[] args)
  {
    ArrayList<TermPair> couples = new ArrayList<TermPair>();
    Term x = new Term("x"), y = new Term("y"), z = new Term("z"), t = new Term(
        "t"), a = new Term("a", true), b = new Term("b", true), c = new Term(
        "c", true);

    couples.add(new TermPair(x, a));
    couples.add(new TermPair(y, b));
    couples.add(new TermPair(z, c));
    couples.add(new TermPair(t, b));

    Atom a1 = new Atom("r(x,y,z)"), b1 = new Atom("s(z,t)"), c1 = new Atom(
        "r('a','b','c')"), d = new Atom("s('b','a')"), e = new Atom(
        "s('c','b')");
    ArrayList<Atom> var = new ArrayList<Atom>(), val = new ArrayList<Atom>();
    var.add(b1);
    var.add(a1);
    val.add(c1);
    val.add(d);
    val.add(e);

  }

}
