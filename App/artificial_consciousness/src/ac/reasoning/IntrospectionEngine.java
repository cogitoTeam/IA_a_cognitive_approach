package ac.reasoning;

import org.apache.log4j.Logger;

import ac.memory.ActiveMemory;

/**
 * Class IntrospectionEngine Cette classe implémente le moteur d'introspection
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
class IntrospectionEngine
{

  @SuppressWarnings("unused")
  private static final Logger logger = Logger
                                         .getLogger(IntrospectionEngine.class);

  /* **************************************************************************
   * ATTRIBUTES
   * ************************************************************************ */

  @SuppressWarnings("unused")
  private ActiveMemory        _memory;

  /* **************************************************************************
   * CONSTRUCTORS
   * ************************************************************************ */

  public IntrospectionEngine(ActiveMemory memory)
  {
    this._memory = memory;
  }

  /* **************************************************************************
   * FRIENDLY METHODS
   * ************************************************************************ */

  void stop()
  {
    // TODO Auto-generated method stub

  }

  void start()
  {
    // TODO Auto-generated method stub

  }

}
