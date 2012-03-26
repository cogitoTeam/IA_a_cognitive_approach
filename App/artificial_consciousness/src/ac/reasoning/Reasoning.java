package ac.reasoning;

/**
 * Cette classe représente le module de raisonnement.
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class Reasoning
{
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
    introspection_engine.stop();
    choice_engine.start();
    introspection_engine.start();
  }

}
