package ac;

import ac.analysis.Analysis;
import agent.Action;
import agent.Percept;
import ac.memory.ActiveMemory;
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

  /* **************************************************************************
   * ATTRIBUTS
   * ************************************************************************ */

  private Analysis _analysis;
  private ActiveMemory _memory;
  private Reasoning _reasoning;

  /* **************************************************************************
   * CONSTRUCTOR
   * ************************************************************************ */

  /**
   * Default constructor
   */
  public AC()
  {
    this._memory = new ActiveMemory(new Neo4jEpisodicMemory(),
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
    // TODO Auto-generated method stub
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
  public ActiveMemory getMemory()
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
