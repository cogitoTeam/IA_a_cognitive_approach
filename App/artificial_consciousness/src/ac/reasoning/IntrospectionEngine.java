package ac.reasoning;

import java.util.List;

import org.apache.log4j.Logger;

import ac.memory.Memory;
import ac.memory.episodic.EpisodicMemoryException;
import ac.memory.episodic.Game;
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
  private static final Logger logger = Logger
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
        catch (EpisodicMemoryException e)
          {
            e.printStackTrace();
          }

        // @todo implement this method and remove next line
        break;
      }
  }

  /* **************************************************************************
   * PRIVATE METHODS
   * ************************************************************************ */

  private void searchNewRPBS() throws EpisodicMemoryException
  {
    // @todo get last won game
    List<Game> list_cbs = this._memory.getWonGames(2);

    CompleteBoardState cbs1 = list_cbs.get(0).getLastMove()
        .getCompleteBoardState();
    CompleteBoardState cbs2 = list_cbs.get(1).getLastMove()
        .getCompleteBoardState();

    /* 
     * @todo
     * for(pair<cbs,cbs> pair : //chaque pair possible)
     * {
     * for(rpbs : chaque RPBS commun pour la pair courante
     * {
     * RPBS new_rpbs = rpbs.extersion(cbs1,cbs25;
     * if(new_rpbs not already exist)
     * {
     * mem.add(new_rpbs);
     * mem.associate(new_rpbs,cbs1)
     * mem.associate(new_rpbs.cbs2)
     * }
     * }
     * }
     * * */

  }

}
