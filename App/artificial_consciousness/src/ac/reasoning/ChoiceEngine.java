package ac.reasoning;

import java.util.List;

import org.apache.log4j.Logger;

import book.Pair;

import ac.memory.ActiveMemory;
import ac.memory.MemoryException;
import ac.shared.CompleteBoardState;
import agent.Action;
import game.BoardMatrix.Position;


/**
 * Class ChoiceEngine Cette classe implémente la prise de décision
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
class ChoiceEngine
{

  @SuppressWarnings("unused")
  private static final Logger logger = Logger.getLogger(ChoiceEngine.class);

  /* **************************************************************************
   * ATTRIBUTS
   * ************************************************************************ */

  private ActiveMemory _memory;

  /* **************************************************************************
   * CONSTRUCTORS
   * ************************************************************************ */

  public ChoiceEngine(ActiveMemory memory)
  {
    this._memory = memory;
  }

  /* **************************************************************************
   * FRIENDLY METHODS
   * ************************************************************************ */

  Action start()
  {
    
    List<Pair<CompleteBoardState, Double>> list_bs = null;
    try
      {
        list_bs = this._memory
            .getGradedCompleteBoardState();
      }
    catch (MemoryException e)
      {
        e.printStackTrace();
        return new Action.Exit();
      }  

    /* ————————————————————————————————————————————————————————————————————————
     * Search the BoardState with better grade
     * 
     * @TODO outsource this procedure
     * —————————————————————————————————————————————————————————————————————— */
    Pair<CompleteBoardState, Double> better_bs = list_bs.get(0);
    double max_grade = better_bs.getSecond();

    for (Pair<CompleteBoardState, Double> bs : list_bs)
      if (bs.getSecond() > max_grade)
        {
          better_bs = bs;
          max_grade = bs.getSecond();
        }
    /* —————————————————————————————————————————————————————————————————————— */

    //@FIXME define the position
    return new Action.Move(new Position(0,0));
  }
}
