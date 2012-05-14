package ac.analysis.inferenceEngine;

import java.util.ArrayList;

import ac.analysis.structure.*;

/**
 * Calculates and stores possible substitutions between two sets of {@link Term}
 * : one a set of variables, the other a set of constants
 */
public class Substitutions
{

  private ArrayList<Term> T1;
  private ArrayList<Term> T2;
  /**
   * List of possible substitutions
   */
  public ArrayList<Substitution> S;

  /**
   * Constructor
   * 
   * @param variables
   * @param constants
   */
  public Substitutions(ArrayList<Term> variables, ArrayList<Term> constants)
  {
    T1 = variables; 
    T2 = constants; 
    S = new ArrayList<Substitution>(); 
  }

  /**
   * Computes the possible substitutions of
   * @param s a given substitution
   */
  public void computeSubstitutions(Substitution s)
  {
    if (s != null && s.num_Pairs() == T1.size())
      S.add(s); 
    else
      for (int i = 0; i < T2.size(); i++) 
        {
          TermPair pair = new TermPair(T1.get(s.num_Pairs()), T2.get(i));
          Substitution temp = new Substitution(s);
          temp.addPair(pair); 
          computeSubstitutions(temp);
        }
    return;
  }
}
