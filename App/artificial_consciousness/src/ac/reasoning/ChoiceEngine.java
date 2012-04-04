package ac.reasoning;

import java.util.List;

import org.apache.log4j.Logger;

import ac.util.Pair;
import ac.memory.Memory;
import ac.memory.MemoryException;
import ac.shared.FOLObjects.Option;
import agent.Action;

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
    List<Pair<Option, Double>> options_list = null;

    try
      {
        options_list = this._memory.getGradedOptions();
      }
    catch (MemoryException e)
      {
        LOGGER.error(e.getMessage());
        return new Action.Exit();
      }

    return this.getBetterOption(options_list).getMove();
  }

  // ***************************************************************************
  // PRIATE METHODS
  // ***************************************************************************

  private Option getBetterOption(List<Pair<Option, Double>> options_list)
  {
    Option better_option = options_list.get(0).getFirst();
    double max_grade = options_list.get(0).getSecond();

    for (Pair<Option, Double> pair : options_list)
      if (pair.getSecond() > max_grade)
        {
          better_option = pair.getFirst();
          max_grade = pair.getSecond();
        }

    return better_option;
  }

}
