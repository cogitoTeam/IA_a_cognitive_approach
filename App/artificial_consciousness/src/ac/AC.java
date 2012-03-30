package ac;

import ac.analysis.Analysis;
import frontier.Action;
import frontier.Frontier;
import frontier.Percept;
import ac.memory.ActiveMemory;
import ac.reasoning.Reasoning;
import main.Agent;

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
  private Frontier _frontier;

  /* **************************************************************************
   * CONSTRUCTOR
   * ************************************************************************ */

  public AC()
  {
    this._memory = new ActiveMemory();
    this._frontier = new Frontier();
    this._reasoning = new Reasoning(this._memory, this._frontier);
    this._analysis = new Analysis(this._memory, this._reasoning, this._frontier);
    
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

  public Frontier getFrontier()
  {
    return _frontier;
  }

@Override
protected void think() {
	// TODO Auto-generated method stub
	
}

@Override
protected Action choose_reaction(Percept percept) {
	// TODO Auto-generated method stub
	return null;
}

@Override
protected void action_failed(Action action) {
	// TODO Auto-generated method stub
	
}

}
