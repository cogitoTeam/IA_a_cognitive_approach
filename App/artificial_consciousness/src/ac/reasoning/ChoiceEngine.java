package ac.reasoning;

import java.util.List;

import org.apache.log4j.Logger;

import ac.util.Pair;
import ac.memory.Memory;
import ac.memory.MemoryException;
import ac.shared.FOLObjects.Option_FOL;
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
    List<Pair<Option_FOL, Double>> options_list = null;
    Option_FOL option;

    try
      {
        options_list = this._memory.getGradedOptions();
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occurred during getting options.", e);
        return new Action.Exit();
      }

    option = this.getBetterOption(options_list);

    try
      {
        this._memory.OptionChosen(option);
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occurred during storing a choice.", e);
      }

    return option.getMove();
  }

  // ***************************************************************************
  // PRIATE METHODS
  // ***************************************************************************

  private Option_FOL getBetterOption(List<Pair<Option_FOL, Double>> options_list)
  {
    Option_FOL better_option = options_list.get(0).getFirst();
    double max_grade = options_list.get(0).getSecond();

    for (Pair<Option_FOL, Double> pair : options_list)
      if (pair.getSecond() > max_grade)
        {
          better_option = pair.getFirst();
          max_grade = pair.getSecond();
        }

    return better_option;
  }

}
