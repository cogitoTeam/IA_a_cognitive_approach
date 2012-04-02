package ac;

import ac.analysis.Analysis;
import agent.Action;
import agent.Percept.Choices;
import agent.Percept.Defeat;
import agent.Percept.Draw;
import agent.Percept.Victory;
import ac.memory.ActiveMemory;
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

  public AC()
  {
    this._memory = new ActiveMemory();
    this._reasoning = new Reasoning(this._memory);
    this._analysis = new Analysis(this._memory, this._reasoning);
    
  }
  
  /* **************************************************************************
   * METHODS
   * ************************************************************************ */

  
  @Override
  protected void think()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected Action choices_reaction(Choices percept)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Action victory_reaction(Victory percept)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Action defeat_reaction(Defeat percept)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Action draw_reaction(Draw percept)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void action_result(boolean success, Action action)
  {
    // TODO Auto-generated method stub
    
  }

  /* **************************************************************************
   * GETTERS
   * ************************************************************************ */

  public ActiveMemory getMemory()
  {
    return _memory;
  }

  public Reasoning getReasoning()
  {
    return _reasoning;
  }

}
