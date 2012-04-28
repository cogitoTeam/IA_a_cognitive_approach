/**
 * 
 */
package ac.analysis.inferenceEngine;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import ac.analysis.structure.*;

/**
 * Calculates homomorphisms (projections) between two sets of
 * atoms
 * <p>
 * {@code A1} and {@code A2}
 * <p>
 * and stores them in as a list of {@link Substitution}
 */
public class Homomorphisms
{
  private ArrayList<Atom> A1;
  private TreeSet<Atom> A2;

  private TreeSet<Term> domain_A2 = null;
  private ArrayList<Term> orderedVariables;

  private ArrayList<Substitution> S;

  /**
   * Constructor
   * 
   * @param varSet
   *          Set of variables (atoms with variable terms)
   * @param valSet
   *          Set of values (atoms with constant terms)
   */
  public Homomorphisms(ArrayList<Atom> varSet, ArrayList<Atom> valSet)
  {
    A1 = varSet;
    A2 = new TreeSet<Atom>(valSet);
    orderedVariables = new ArrayList<Term>();
    S = new ArrayList<Substitution>();
  }

  /**
   * Constructor
   * 
   * @param query
   *          a list of atoms
   * @param fb
   *          a fact base
   */
  public Homomorphisms(ArrayList<Atom> query, FactBase fb)
  {
    A1 = query;
    A2 = new TreeSet<Atom>(fb.getAtomList());
    orderedVariables = new ArrayList<Term>();
    S = new ArrayList<Substitution>();
  }

  /**
   * Constructor
   * 
   * @param query
   *          a {@link Query}
   * @param fb
   *          a fact base
   */
  public Homomorphisms(Query query, FactBase fb)
  {
    A1 = query.getAtomList();
    A2 = new TreeSet<Atom>(fb.getAtomList());
    orderedVariables = new ArrayList<Term>();
    S = new ArrayList<Substitution>();
  }

  /**
   * @return the set of {@code Term}s (=constants) in A2
   */
  private TreeSet<Term> getDomain()
  {
    if (domain_A2 != null)
      return domain_A2;

    domain_A2 = new TreeSet<Term>();

    for (Atom a : A2)
      for (Term t : a.getTerms())
        domain_A2.add(t);

    return domain_A2;

  }

  /**
   * @return the set of {@code Term}s (=variables) in A1
   */
  private ArrayList<Term> getVariables()
  {
    ArrayList<Term> variables = new ArrayList<Term>();

    for (Atom a : A1)
      for (Term t : a.getTerms())
        if (t.isVariable())
          if (!variables.contains(t))
            variables.add(t);

    return variables;
  }

  /**
   * @return A2
   */
  public TreeSet<Atom> getA2()
  {
    return this.A2;
  }

  /**
   * Uses the BackTrackAll algorithm to generate all the homomorphisms from
   * A1 into A2 and stores them in S
   * 
   * @return S : the list of homomorphisms
   */
  public ArrayList<Substitution> getHomomorphisms()
  {
    ArrayList<Atom>[] atom_rank = preprocessing();
    backtrackAllRec(new Substitution(), atom_rank, 0);
    return S;
  }

  /**
   * Uses the recursive backtrack algorithm (c.f. {@code backTrackrec}) to test
   * if there exists a homomorphism between
   * A1 and A2
   * 
   * @param s
   *          the substitution to test
   * @return True if there exits a homomorphism, False otherwise
   */
  public boolean existsHomomorphismTest(Substitution s)
  {
    ArrayList<Atom>[] atom_rank = preprocessing();
    return backtrackRec(s, atom_rank, s.getPairList().size());
  }

  /**
   * Backtrack algorithm used in the homomorphism existence test (c.f.
   * {@code existsHomomorphismTest})
   * Looks for a homomorphism (if it exists) for a given partial solution.
   * 
   * @param sol
   *          the partial solution
   * @param atom_rank
   * @param rank
   * @return True if there exists a homomorphism, False otherwise.
   */
  public boolean backtrackRec(Substitution sol, ArrayList<Atom>[] atom_rank,
      int rank)
  {
    Term x;
    Set<Term> candidats;
    Substitution solPrime;
    if (sol.num_Pairs() == orderedVariables.size())
      return true;
    else
      {
        x = chooseUnassignedVariable(sol);
        candidats = computeCandidates(x);
        for (Term c : candidats)
          {
            solPrime = new Substitution(sol);
            solPrime.addPair(new TermPair(x, c));
            if (solPrime.isHomomorphismTest(atom_rank[rank], A2))
              if (backtrackRec(solPrime, atom_rank, rank + 1))
                return true;
          }
        return false;
      }
  }

  /**
   * Checks if a substitution (passed as parameter) is a partial homomorphism
   * 
   * @param sol
   *          the substitution
   * @param atoms
   * @param a2
   */
  private boolean isPartialHomomorphismTest(Substitution sol,
      ArrayList<Atom> atoms, TreeSet<Atom> a2)
  {
    int counter;
    ArrayList<Atom> A1Prime = new ArrayList<Atom>();
    ArrayList<Term> variables = sol.getVariables();

    for (Atom a : atoms)
      {
        counter = 0;

        for (Term t1 : a.getTerms())
          for (Term t2 : variables)
            if (t1.equalsT(t2) || t1.isConstant())
              counter++;

        if (counter >= a.getTerms().size())
          A1Prime.add(a);
      }
    return sol.isHomomorphismTest(A1Prime, a2);
  }

  /**
   * Processes the variables in the atom set A1 in order to put them in a
   * favourable order
   */
  private ArrayList<Atom>[] preprocessing()
  {
    orderedVariables = getVariables();
    return getAtomRank(A1, orderedVariables);
  }

  private static ArrayList<Atom>[] getAtomRank(ArrayList<Atom> atomset,
      ArrayList<Term> vars)
  {
    int tmp, rank;

    AtomSet[] atom_rank = new AtomSet[vars.size()];
    for (int i = 0; i < vars.size(); ++i)
      atom_rank[i] = new AtomSet();

    //
    for (Atom a : atomset)
      {
        rank = 0;
        for (Term t : a.getTerms())
          {
            tmp = vars.indexOf(t);
            if (rank < tmp)
              rank = tmp;
          }
        atom_rank[rank].add(a);
      }

    return atom_rank;
  }

  /**
   * Computes the candidates for a given variable (passed as parameter)
   * 
   * @param x
   *          the variable (in A1)
   * @return the list of possible candidates
   */
  private TreeSet<Term> computeCandidates(Term x)
  {
    return getDomain();
  }

  /**
   * Determines the next variable in a given substitution (passed as parameter)
   * which is to be assigned a value (in the order defined during
   * pre-processing)
   * 
   * @return the variable
   * @param sol
   *          the substitution
   */
  private Term chooseUnassignedVariable(Substitution sol)
  {
    for (Term t : orderedVariables)
      if (!sol.getVariables().contains(t))
        return t;

    return null;
  }

 
  /**
   * Finds all possible homomorphisms with the given partial solution.
   * 
   * @param sol
   *          the partial solution
   * @param atom_rank 
   * @param rank 
   */
  public void backtrackAllRec(Substitution sol, ArrayList<Atom>[] atom_rank,
      int rank)
  {
    Term x;
    Set<Term> candidats;
    Substitution solPrime;

    if (sol.num_Pairs() == orderedVariables.size())
      {
        S.add(sol);
        return;
      }
    else
      {
        x = orderedVariables.get(rank);
        candidats = computeCandidates(x);
        for (Term c : candidats)
          {
            solPrime = new Substitution(sol);
            solPrime.addPair(new TermPair(x, c));
            if (solPrime.isHomomorphismTest(atom_rank[rank], A2))
              backtrackAllRec(solPrime, atom_rank, rank + 1);
          }
      }
  }

  /**
   * @param sol
   * @param atom_rank
   * @param rank
   */
  public void backtrackAllRecVar(Substitution sol, ArrayList<Atom>[] atom_rank,
      int rank)
  {
    Term x;
    Set<Term> candidats;
    Substitution solPrime;

    if (sol.num_Pairs() == orderedVariables.size())
      {
        S.add(sol);
        return;
      }
    else
      {
        x = orderedVariables.get(rank);
        candidats = computeCandidates(x);
        for (Term c : candidats)
          {
            solPrime = new Substitution(sol);
            solPrime.addPair(new TermPair(x, c));
            if (isPartialHomomorphismTest(solPrime, atom_rank[rank], A2))
              backtrackAllRec(solPrime, atom_rank, rank + 1);
          }
      }
  }

  
}
