package ac.reasoning;

import org.apache.log4j.Logger;


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
  
  // ***************************************************************************
  // ATTRIBUTS
  // ***************************************************************************

  private ChoiceEngine        choice_engine;
  private IntrospectionEngine introspection_engine;

  // ***************************************************************************
  // PUBLIC METHODS
  // ***************************************************************************

  /**
   * Active the ChoiceEngine
   */
  public void stimulate()
  {
    logger.debug("stimulate");
    introspection_engine.stop();
    choice_engine.start();
    introspection_engine.start();
  }

}
