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
class IntrospectionEngine extends Thread
{

  @SuppressWarnings("unused")
  private static final Logger logger = Logger
      .getLogger(IntrospectionEngine.class);

  /* **************************************************************************
   * ATTRIBUTES
   * ************************************************************************ */

  @SuppressWarnings("unused")
  private ActiveMemory _memory;

  /* **************************************************************************
   * CONSTRUCTORS
   * ************************************************************************ */

  public IntrospectionEngine(ActiveMemory memory)
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
         
        //@todo implement this method and remove next line
        break;
      }
  }

  /* **************************************************************************
   * FRIENDLY METHODS
   * ************************************************************************ */
}
 