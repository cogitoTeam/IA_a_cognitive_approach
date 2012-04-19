package ac.reasoning;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import ac.analysis.inferenceEngine.Homomorphisms;
import ac.analysis.structure.Atom;
import ac.analysis.structure.Substitution;
import ac.analysis.structure.Term;
import ac.memory.Memory;
import ac.memory.MemoryException;
import ac.memory.episodic.EpisodicMemoryException;
import ac.memory.episodic.Game;
import ac.memory.episodic.Move;
import ac.shared.CompleteBoardState;
import ac.shared.RelevantPartialBoardState;
import ac.util.ArraySet;

/**
 * Class IntrospectionEngine Cette classe implémente le moteur d'introspection
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
class IntrospectionEngine extends Thread
{

  private static final Logger LOGGER = Logger
      .getLogger(IntrospectionEngine.class);

  /* **************************************************************************
   * ATTRIBUTES
   * ************************************************************************ */

  private Memory _memory;

  /* **************************************************************************
   * CONSTRUCTORS
   * ************************************************************************ */

  public IntrospectionEngine(Memory memory)
  {
    this._memory = memory;
  }

  /* **************************************************************************
   * OVERRIDE METHODS
   * ************************************************************************ */

  @Override
  /* This method is executed in new thread when you call it by
   * IntrospectionEngine.start()
   * 
   * @see java.lang.Thread#run() */
  public void run()
  {
    while (!this.isInterrupted())
      {
        /* Warning : if you use this.sleep, you have to do this
         * 
         * try
         * {
         * this.sleep(1000);
         * }
         * catch (InterruptedException e)
         * {
         * this.interrupt();
         * }
         * * */

        try
          {
            this.searchNewRPBS();
          }
        catch (MemoryException e)
          {
            LOGGER.error("An error occured during introspection", e);
          }

        // @todo implement this method and remove next line
        break;
      }
  }

  /* **************************************************************************
   * PRIVATE METHODS
   * ************************************************************************ */

  private void searchNewRPBS() throws MemoryException
  {
    // @todo get last won game
    int nb_game = 10;
    List<Game> list_games = this._memory.getLastWonGames(nb_game);
    Move m1, m2;

    for (int i = 0; i < list_games.size(); i++)
      for (int j = i + 1; j < list_games.size(); j++)
        {
          m1 = list_games.get(i).getLastMove();
          m2 = list_games.get(j).getLastMove();

          while ((m1 = m1.getPreviousMove()) != null
              && (m2 = m2.getPreviousMove()) != null)
            {
              this.searchNewRPBS(m1, m2);
            }
        }

  }

  private void searchNewRPBS(Move m1, Move m2) throws MemoryException
  {
    RelevantPartialBoardState new_rpbs;
    CompleteBoardState cbs1, cbs2;

    try
      {
        cbs1 = m1.getCompleteBoardState();
        cbs2 = m2.getCompleteBoardState();

        // @TODO les cbs sont-ils composés de variables où de constante.

        for (RelevantPartialBoardState rs1 : m1.getRelevantPartialBoardStates())
          for (RelevantPartialBoardState rs2 : m2
              .getRelevantPartialBoardStates())
            if (rs1.equals(rs2))
              {
                new_rpbs = this.extension(rs1, cbs1, cbs2);
                // @TODO add a boost
                this._memory.putRelevantStructure(new_rpbs);
                this._memory.addAssociation(cbs1, new_rpbs);
                this._memory.addAssociation(cbs2, new_rpbs);
              }
      }
    catch (EpisodicMemoryException e)
      {
        LOGGER.debug(e.getMessage());
      }
  }

  private RelevantPartialBoardState extension(RelevantPartialBoardState rs1,
      CompleteBoardState cbs1, CompleteBoardState cbs2)
  {
    boolean haveNewRPBS = false;
    RelevantPartialBoardState new_rs = null;

    Homomorphisms homo_cbs;
    Homomorphisms homo_cbs1 = new Homomorphisms(rs1.getRule().getPremise(),
        cbs1.getBoardStateFacts().getAtomList());
    Homomorphisms homo_cbs2 = new Homomorphisms(rs1.getRule().getPremise(),
        cbs2.getBoardStateFacts().getAtomList());

    ArrayList<Substitution> substitution_list1 = homo_cbs1.getHomomorphisms();
    ArrayList<Substitution> substitution_list2 = homo_cbs2.getHomomorphisms();

    TreeMap<Term, LinkedList<TreeSet<Term>>> term_map = new TreeMap<>();

    ArrayList<Atom> atom_list = cbs1.getBoardStateFacts().getAtomList();
    ArrayList<Term> all_vars = this.getVariables(atom_list);

    for (Term t : all_vars)
      term_map.put(t, new LinkedList<TreeSet<Term>>());

    TreeSet<Term> tree ;
    for (Atom a : atom_list)
      {
        tree = new TreeSet<>();
        for (Term t : a.getTerms())
            tree.add(t);
          
        for (Term t : tree)
          term_map.get(t).add((TreeSet<Term>)tree.clone()); 
        
      }
    
    for (Substitution sub1 : substitution_list1)
      {
        TreeMap<Term, LinkedList<TreeSet<Term>>> current_map = (TreeMap<Term, LinkedList<TreeSet<Term>> >)term_map.clone();

        ArrayList<Term> used_vars = sub1.getVariables();
        for (Term t : sub1.getVariables())
          for (TreeSet<Term> current_tree : current_map.get(t))
            current_tree.remove(t);
        
        ArraySet<Term> choices = getChoices(current_map, all_vars);
        
        Term t;
        while(choices.size() > 0)
          {
            t = choices.getRandom();
                  
            used_vars.add(t);
            // créer un RS contenant tous les prédicats complétements
            // instanciés
            new_rs = cbs1.getPart(used_vars);
    
            // tester si il existe un homomorphisme de RS dans cbs2 (en gardant
            // sub2 comme base)
            boolean haveHomomorphism = false;
            for (Substitution sub2 : substitution_list2)
              {
                homo_cbs = new Homomorphisms(new_rs.getRule().getPremise(),
                    cbs2.getBoardStateFacts().getAtomList());
    
                if (homo_cbs.backtrackRec(sub2))
                  {
                    haveNewRPBS = true;
                    haveHomomorphism = true;
                    break;
                  }
              }
    
            if (!haveHomomorphism)
              used_vars.remove(t);
            else
              for (TreeSet<Term> current_tree : current_map.get(t))
                current_tree.remove(t);
            
            choices = getChoices(current_map, all_vars);
          }
          
      }
    
    if (!haveNewRPBS)
      new_rs = null;

    return new_rs;
  }

  private ArraySet<Term> getChoices(
      TreeMap<Term, LinkedList<TreeSet<Term>>> current_map,
      ArrayList<Term> all_vars)
  {
    ArraySet<Term> vars = new ArraySet<Term>();
    for (Term t : all_vars)
      for (TreeSet<Term> current_tree : current_map.get(t))
        if (current_tree.size() == 1)
          vars.add(current_tree.first());

    return vars;
  }


  /**
   * 
   * @param used_term
   * @return an list of Term
   */
  private ArrayList<Term> getVariables(ArrayList<Atom> atomList)
  {
    ArrayList<Term> variables = new ArrayList<Term>();

    for (Atom a : atomList)
      for (Term t : a.getTerms())
        {
          boolean contient = false;
          if (t.isVariable())
            {
              for (Term i : variables)
                {
                  if (t.equals(i))
                    contient = true;
                }
              if (!contient)
                variables.add(t);
            }
        }

    return variables;
  }

}
