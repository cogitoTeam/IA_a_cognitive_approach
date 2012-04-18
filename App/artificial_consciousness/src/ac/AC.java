package ac;

import java.io.IOException;

import org.apache.log4j.Logger;

import ac.analysis.Analysis;
import agent.Action;
import agent.Percept;
import ac.memory.MemoryException;
import ac.memory.Neo4jActiveMemory;
import ac.memory.episodic.Neo4jEpisodicMemory;
import ac.memory.semantic.Neo4jSemanticMemory;
import ac.reasoning.Reasoning;
import agent.Agent;

/**
 * 
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class AC extends Agent
{

  private static final Logger LOGGER = Logger.getLogger(AC.class);

  /* **************************************************************************
   * ATTRIBUTS
   * ************************************************************************ */

  private Analysis _analysis;
  private Neo4jActiveMemory _memory;
  private Reasoning _reasoning;

  /* **************************************************************************
   * CONSTRUCTOR
   * ************************************************************************ */

  /**
   * Default constructor
   */
  public AC()
  {
    this._memory = new Neo4jActiveMemory(new Neo4jEpisodicMemory(),
        new Neo4jSemanticMemory());
    this._reasoning = new Reasoning(this._memory);
    this._analysis = new Analysis(this._memory, this._reasoning);

  }

  /* **************************************************************************
   * METHODS
   * ************************************************************************ */

  @Override
  protected void think()
  {
    // nothing to do
  }

  @Override
  protected Action perceptReaction(Percept percept)
  {
    Action action = null;
    
    LOGGER.debug("percept reçu "+percept.getClass());
    if(percept.getClass() == Percept.Choices.class)
      {
        LOGGER.debug("percept de types choices");
        try
          {
            action = this._analysis.analyse((Percept.Choices)percept);
          }
        catch (Exception e)
          {
            LOGGER.error("erreur lors de l'analyse", e);
          }          
      }

    return null;
  }

  @Override
  protected void actionResult(boolean success, Action action)
  {
    // TODO Auto-generated method stub

  }

  /* **************************************************************************
   * GETTERS
   * ************************************************************************ */

  /**
   * @return the memory module
   */
  public Neo4jActiveMemory getMemory()
  {
    return _memory;
  }

  /**
   * @return the reasoning module
   */
  public Reasoning getReasoning()
  {
    return _reasoning;
  }

  /**
   * @return the analysis module
   */
  public Analysis getAnalysis()
  {
    return _analysis;
  }

}
