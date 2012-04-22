package ac.reasoning;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import ac.AC;
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
  private List<RelevantPartialBoardState> rpbs_list;

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
    if (LOGGER.isDebugEnabled())
      LOGGER.debug("I think…");

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
    rpbs_list = this._memory.getRelevantPartialBoardStates();

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
    long cbs1_id, cbs2_id;

    try
      {
        cbs1 = m1.getCompleteBoardState();
        cbs2 = m2.getCompleteBoardState();

        cbs1_id = cbs1.getId();
        cbs2_id = cbs2.getId();
        
        cbs1.generalise("w");
        cbs2.generalise("z");

        for (RelevantPartialBoardState rs1 : m1.getRelevantPartialBoardStates())
          for (RelevantPartialBoardState rs2 : m2
              .getRelevantPartialBoardStates())
            if (rs1.equals(rs2))
              {
                new_rpbs = this.extension(rs1, cbs1, cbs2);

                if (new_rpbs != null)
                  {
                    // @TODO add a boost
                    this._memory.putRelevantStructure(new_rpbs);
                    this._memory.addAssociation(cbs1_id, new_rpbs);
                    this._memory.addAssociation(cbs2_id, new_rpbs);
                  }
              }
      }
    catch (EpisodicMemoryException e)
      {
        if (LOGGER.isDebugEnabled())
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
        cbs1.getBoardStateFacts());
    Homomorphisms homo_cbs2 = new Homomorphisms(rs1.getRule().getPremise(),
        cbs2.getBoardStateFacts());

    ArrayList<Substitution> substitution_list1 = homo_cbs1.getHomomorphisms();
    ArrayList<Substitution> substitution_list2 = homo_cbs2.getHomomorphisms();

    TreeSet<Atom> atom_list = homo_cbs1.getA2(); // ensemble des faits de cbs1
    TreeSet<Term> all_vars = this.getVariables(atom_list); // ensemble des
                                                           // variable de cbs1

    // *********************************************************
    // list des termes associé aux termes qui sont contenu dans
    TreeMap<Term, LinkedList<TreeSet<Term>>> term_map = new TreeMap<>();
    for (Term t : all_vars)
      term_map.put(t, new LinkedList<TreeSet<Term>>());

    TreeSet<Term> tree;
    // pour l'ensemble des variables de cbs1
    for (Atom a : atom_list)
      {
        // creer un TreeSet de terms par atomes
        tree = new TreeSet<>();
        for (Term t : a.getTerms())
          tree.add(t);

        // lier ce TreeSet a chaque termes de celui-ci
        for (Term t : tree)
          term_map.get(t).add(tree);

      }
    // On peut donc retrouver la liste des terms lier a un termes t
    // par le fait d'appartenir au même atomes
    // *********************************************************

    for (Substitution sub1 : substitution_list1)
      {
        TreeMap<Term, LinkedList<TreeSet<Term>>> current_map = (TreeMap<Term, LinkedList<TreeSet<Term>>>) term_map
            .clone();

        // retrait de tous les atomes déjà utiliser dans la substitution
        ArrayList<Term> used_vars = sub1.getSubstitues();
        for (Term t : used_vars)
          for (TreeSet<Term> current_tree : current_map.get(t))
            current_tree.remove(t);

        // liste des variables appartenant a un atomes dont seul cette
        // variables n'est pas présente dans la substitution et dont au moins
        // une l'est
        ArraySet<Term> choices = getChoices(current_map, used_vars);

        Term t;
        while (choices.size() > 0)
          {
            t = choices.getRandom();
            choices.remove(t);
            used_vars.add(t);

            // créer un RS contenant tous les prédicats complétements
            // instanciés
            new_rs = cbs1.getPart(used_vars);
            new_rs = new_rs.clone();
            new_rs.renameVars(sub1);

            // tester si il existe un homomorphisme de RS dans cbs2 (en gardant
            // sub2 comme base)
            boolean haveHomomorphism = false;
            for (Substitution sub2 : substitution_list2)
              {
                homo_cbs = new Homomorphisms(new_rs.getRule().getPremise(),
                    cbs2.getBoardStateFacts());

                if (homo_cbs.existsHomomorphismTest(sub2))
                  {
                    if (!this.rpbs_list.contains(new_rs))
                      {
                        haveNewRPBS = true;
                        haveHomomorphism = true;
                        break;
                      }
                  }
              }

            if (haveHomomorphism)
              {
                new_rs.renameVars();
                if (LOGGER.isDebugEnabled())
                  LOGGER.debug("Add new rpbs: " + new_rs.getRule());
                
                return new_rs;
                /*
                 * for (TreeSet<Term> current_tree : current_map.get(t))
                 * current_tree.remove(t); */
              }
            else
              used_vars.remove(t);

            choices = getChoices(current_map, used_vars);
          }

      }

    return null;
  }

  private ArraySet<Term> getChoices(
      TreeMap<Term, LinkedList<TreeSet<Term>>> current_map,
      ArrayList<Term> used_vars)
  {
    ArraySet<Term> vars = new ArraySet<Term>();
    for (Term t : used_vars)
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
  private TreeSet<Term> getVariables(TreeSet<Atom> atomList)
  {
    TreeSet<Term> variables = new TreeSet<Term>();

    for (Atom a : atomList)
      for (Term t : a.getTerms())
        if (t.isVariable())
          variables.add(t);

    return variables;
  }

  // ***************************************************************************
  // TEST MAIN
  // ***************************************************************************

  public static void main(String[] args)
  {
    AC ac = new AC();
    ac.getReasoning().think();
  }

}
