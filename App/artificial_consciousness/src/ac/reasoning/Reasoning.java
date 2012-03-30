package ac.reasoning;

import org.apache.log4j.Logger;

import frontier.Frontier;
import ac.memory.ActiveMemory;

/**
 * Cette classe représente le module de raisonnement.
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class Reasoning
{

  private static final Logger logger = Logger.getLogger(Reasoning.class);

  /* **************************************************************************
   * ATTRIBUTS
   * ************************************************************************ */

  private Frontier _frontier;
  private ActiveMemory _memory;

  private ChoiceEngine _choice_engine;
  private IntrospectionEngine _introspection_engine;

  /* **************************************************************************
   * CONSTRUCTORS
   * ************************************************************************ */

  /**
   * @param memory
   *          an instance of ActiveMemory
   */
  public Reasoning(ActiveMemory memory, Frontier frontier)
  {
    this._memory = memory;
    this._frontier = frontier;
    this._choice_engine = new ChoiceEngine(this._memory);
    this._introspection_engine = new IntrospectionEngine(this._memory);
  }

  /* **************************************************************************
   * PUBLIC METHODS
   * ************************************************************************ */

  /**
   * Active the ChoiceEngine
   */
  public void stimulate()
  {
    logger.debug("stimulate");
    _introspection_engine.stop();
    _choice_engine.start();
    _introspection_engine.start();
  }

}
