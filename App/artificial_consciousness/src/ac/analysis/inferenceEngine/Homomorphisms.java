/**
 * 
 */
package ac.analysis.inferenceEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import ac.analysis.structure.*;

/**
 * La classe qui calcule et stocke les homomorphismes d'un ensemble d'atomes
 * dans un
 * autre ensemble d'atomes
 * 
 */
public class Homomorphisms
{
  private ArrayList<Atom> A1;
  private TreeSet<Atom> A2;

  private TreeSet<Term> domain_A2 = null;
  private ArrayList<Term> orderedVariables;

  private ArrayList<Substitution> S;

  /**
   * Constructeur de la classe Homomorphismes
   * 
   * @param ensembleVariables
   *          l'ensemble d'atomes � termes variables
   * @param ensembleValeurs
   *          l'ensemble d'atomes � termes constantes
   */
  public Homomorphisms(ArrayList<Atom> ensembleVariables,
      ArrayList<Atom> ensembleValeurs)
  {
    A1 = ensembleVariables; // l'ensemble de termes (variables)
    A2 = new TreeSet<>(ensembleValeurs); // l'ensemble de termes (constantes)
    orderedVariables = new ArrayList<Term>();
    S = new ArrayList<Substitution>(); // l'ensemble de homomorphismes
                                       // initialement vide
  }

  public Homomorphisms(ArrayList<Atom> requete, FactBase bf)
  {
    A1 = requete; // l'ensemble de termes (variables)
    A2 = new TreeSet<>(bf.getAtomList()); // l'ensemble de termes (constantes)
    orderedVariables = new ArrayList<Term>();
    S = new ArrayList<Substitution>();
  }

  public Homomorphisms(Query query, FactBase bf)
  {
    A1 = query.getAtomList();
    A2 = new TreeSet<>(bf.getAtomList());
    orderedVariables = new ArrayList<Term>();
    S = new ArrayList<Substitution>();
  }

  /**
   * M�thode qui retourne les termes de A2
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
   * M�thode qui retourne les variables de A1
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
   * M�thode (BacktrackToutesSolutions) qui g�n�re l'ensemble de homomorphismes
   * de A1 dans A2 et le stocke dans S
   */
  public ArrayList<Substitution> getHomomorphisms()
  {
    ArrayList<Atom>[] atom_rank = preprocessing();
    backtrackAllRec(new Substitution(), atom_rank, 0);
    return S;
  }

  /**
   * M�thode (Backtrack) qui recherche l'existence d'un homomorphisme de A1 dans
   * A2
   */
  public boolean existsHomomorphismTest(Substitution s)
  {
    ArrayList<Atom>[] atom_rank = preprocessing();
    return backtrackRec(s, atom_rank, s.getPairList().size());
  }

  /**
   * M�thode BacktrackRec le sous-algorithme de existeHomomorphisme
   */

  // @TODO Nam, can you correct this sentence
  /**
   * Search if exist an homomorphism with the given partial solution.
   * 
   * @param sol
   *          the partial solution
   * @return true if exist an homomorphism, false otherwise.
   */
  public boolean backtrackRec(Substitution sol, ArrayList<Atom>[] atom_rank, int rank)
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
              if (backtrackRec(solPrime, atom_rank, rank+1))
                return true;
          }
        return false;
      }
  }

  /**
   * M�thode qui teste si une substitution est un homomorphisme partiel
   * 
   * @param solPrime
   *          la substitution � consid�rer
   */
  private boolean isPartialHomomorphismTest(Substitution sol,
      ArrayList<Atom> atoms, TreeSet<Atom> a2)
  {
    int counter;
    ArrayList<Atom> A1Prime = new ArrayList<Atom>();
    ArrayList<Term> variables = sol.getVariables();

    // applications de la substitutions au atomes
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
   * M�thode de pr�traitement des variables de A1 qui calcule un ordre total sur
   * ces variables
   */
  private ArrayList<Atom>[] preprocessing()
  {
    // ordonne variables de A1 (donne rang � chacun), ordonne atomes de A1 selon
    // rang
    orderedVariables = getVariables();
    return getAtomRank(A1, orderedVariables);
  }

  private static ArrayList<Atom>[] getAtomRank(ArrayList<Atom> atomset,
      ArrayList<Term> vars)
  {
    int tmp, rank;

    // initialisation
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
   * M�thode qui retourne l'ensemble d'images possibles pour la variable donn�e
   * en param�tre
   * 
   * @param x
   *          une variable de A1
   * @return images l'ensemble de termes (constantes) qui sont les images
   *         possibles de x
   */
  private TreeSet<Term> computeCandidates(Term x)
  {
    return getDomain();
  }

  /**
   * M�thode qui retourne la prochaine variable non affect�e de A1
   * 
   */
  private Term chooseUnassignedVariable(Substitution sol)
  {
    for(Term t : orderedVariables)
      if(!sol.getVariables().contains(t))
        return t;
    
    return null;
  }

  // @TODO Nam, can you correct this sentence
  /**
   * Search all homomorphism with the given partial solution.
   * 
   * @param sol
   *          the partial solution
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

  public TreeSet<Atom> getA2()
  {
    return this.A2;
  }

  /* public static void main(String[] args) throws IOException
   * {
   * KnowledgeBase k = new KnowledgeBase("ex2.txt");
   * k.optimizedSaturation_FOL();
   * Query query = new Query("p('A',y);r(x,y,z)");
   * Homomorphisms h = new Homomorphisms (query,k.getFB());
   * System.out.println("A1 = " + query + "\nA2 = " + k.getFB() +
   * "\n\nExistent-ils d'homomorphismes de A1 dans A2 ?");
   * if(h.existsHomomorphismTest())
   * {
   * System.out.println(" Oui");
   * System.out.println("La liste de r�ponses est :");
   * System.out.println(h.getHomomorphisms());
   * }
   * else
   * System.out.println("Non");*
   * } */
}
