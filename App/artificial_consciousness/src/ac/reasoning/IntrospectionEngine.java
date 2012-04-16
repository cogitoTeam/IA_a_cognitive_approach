package ac.reasoning;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Class IntrospectionEngine Cette classe implémente le moteur d'introspection
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
class IntrospectionEngine extends Thread
{

  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger
      .getLogger(IntrospectionEngine.class);

  /* **************************************************************************
   * ATTRIBUTES
   * ************************************************************************ */

  @SuppressWarnings("unused")
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

  // peut retourner une liste d'extension
  private RelevantPartialBoardState extension(RelevantPartialBoardState rs1,
      CompleteBoardState cbs1, CompleteBoardState cbs2)
  {
    RelevantPartialBoardState new_rs;

    Homomorphisms homo_cbs;
    Homomorphisms homo_cbs1 = new Homomorphisms(rs1.getRule().getPremise(),
        cbs1.getBoardStateFacts().getAtomList());
    Homomorphisms homo_cbs2 = new Homomorphisms(rs1.getRule().getPremise(),
        cbs2.getBoardStateFacts().getAtomList());

    ArrayList<Substitution> substitution_list1 = homo_cbs1.getHomomorphisms();
    ArrayList<Substitution> substitution_list2 = homo_cbs2.getHomomorphisms();

    ArrayList<Term> vars1 = this.getVariables(cbs1.getBoardStateFacts()
        .getAtomList());

    ArrayList<Term> vars;

    for (Substitution sub1 : substitution_list1)
      {
        vars = sub1.getVariables();

        for (Term t : vars1)
          {
            vars.add(t);
            // ajouter tous les prédicats contenant t au RS
            new_rs = cbs1.getPart(vars);

            // tester si il existe un HOMO de RS dans cbs2 (en gardant sub2
            // comme
            // base)
            for (Substitution sub2 : substitution_list2)
              {
                homo_cbs = new Homomorphisms(new_rs.getRule().getPremise(),
                    cbs2.getBoardStateFacts().getAtomList());
                
                if (homo_cbs.backtrackRec(sub2))
                  break;
                  
                //never break
                vars.remove(t); //@TODO faire du récursif
              }
          }
      }

    return null; // @TODO always return null
  }

  /**
   * 
   * @return an list of Term
   */
  private ArrayList<Term> getVariables(ArrayList<Atom> atomList)
  {
    ArrayList<Term> variables = new ArrayList<Term>();

    for (Atom a : atomList)
      {
        for (Term t : a.getTerms())
          {
            boolean contient = false;
            if (t.isVariable())
              {
                for (Term i : variables)
                  {
                    if (t.equalsT(i))
                      contient = true;
                  }
                if (!contient)
                  variables.add(t);
              }
          }
      }
    return variables;
  }

}
