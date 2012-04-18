package ac.reasoning;

import java.util.List;

import org.apache.log4j.Logger;

import ac.util.Pair;
import ac.memory.Memory;
import ac.memory.MemoryException;
import ac.shared.FOLObjects.Option_FOL;
import agent.Action;
import agent.Action.Move;

/**
 * Class ChoiceEngine Cette classe implémente la prise de décision
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
class ChoiceEngine
{

  private static final Logger LOGGER = Logger.getLogger(ChoiceEngine.class);

  // **************************************************************************
  // ATTRIBUTES
  // ************************************************************************ */

  private Memory _memory;

  // **************************************************************************
  // CONSTRUCTORS
  // ************************************************************************ */

  public ChoiceEngine(Memory memory)
  {
    this._memory = memory;
  }

  // **************************************************************************
  // FRIENDLY METHODS
  // ************************************************************************ */

  Action start()
  {

    List<Pair<Option_FOL, Double>> options_list = null;
    Option_FOL option;
	Action move = null;

    try
      {
        options_list = this._memory.getGradedOptions();
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occurred during getting options.", e);
        return new Action.Exit();
      }
    
    if(options_list.size() == 0)
      {
        LOGGER.debug("Pas de choix en mémoire");
        return new Action.Skip();
      }


    option = this.getBetterOption(options_list);
    
    if (option != null)
      {
        try
          {
            this._memory.OptionChosen(option);
          }
        catch (MemoryException e)
          {
            LOGGER.error("An error occurred during storing a choice.", e);
          }
        
        move = option.getMove();
      }
    else
      LOGGER.debug("Better option null");


    return move;
  }

  // ***************************************************************************
  // PRIATE METHODS
  // ***************************************************************************

  private Option_FOL getBetterOption(List<Pair<Option_FOL, Double>> options_list)
  {
    Option_FOL better_option = null;

    if (options_list.size() > 0)
      {
        better_option = options_list.get(0).getFirst();
        double max_grade = options_list.get(0).getSecond();

        for (Pair<Option_FOL, Double> pair : options_list)
          if (pair.getSecond() > max_grade)
            {
              better_option = pair.getFirst();
              max_grade = pair.getSecond();
            }
      }

    return better_option;
  }

}
