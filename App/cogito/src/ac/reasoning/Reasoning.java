package ac.reasoning;

import org.apache.log4j.Logger;

import ac.memory.Memory;
import ac.memory.MemoryException;
import agent.Action;

/**
 * Cette classe représente le module de raisonnement.
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class Reasoning
{

  private static final Logger LOGGER = Logger.getLogger(Reasoning.class);

  /* **************************************************************************
   * ATTRIBUTES
   * ************************************************************************ */

  private Memory _memory;

  private ChoiceEngine _choice_engine;
  private IntrospectionEngine _introspection_engine;

  /* **************************************************************************
   * CONSTRUCTORS
   * ************************************************************************ */

  /**
   * @param memory
   *          an instance of ActiveMemory
   * @throws MemoryException 
   */
  public Reasoning(Memory memory)
  {
    this._memory = memory;
    this._choice_engine = new ChoiceEngine(this._memory);
    this._introspection_engine = new IntrospectionEngine(this._memory);
  }

  /* **************************************************************************
   * PUBLIC METHODS
   * ************************************************************************ */

  /**
   * Active the ChoiceEngine
   * 
   * @return an Action
   */
  public Action stimulate()
  {
    if(LOGGER.isDebugEnabled())
      LOGGER.debug("stimulate");
    
    _introspection_engine.interrupt();
    Action action = _choice_engine.start();
    return action;
  }
  
  public void think()
  {
    //@todo replace run by start to start a new thread
    _introspection_engine.run();
  }

}
