package ac.analysis;

import game.BoardMatrix;
import game.BoardMatrix.Position;
import ac.memory.ActiveMemory;
import ac.reasoning.Reasoning;
import agent.Action;
import agent.Percept.Choices;

public class Analysis
{

  private ActiveMemory _memory;
  private Reasoning _reasoning;

  public Analysis(ActiveMemory memory, Reasoning reasoning)
  {
    this._memory = memory;
    this._reasoning = reasoning;
  }

  /**
   * This is the input method from environment in case of a choice.
   * 
   * @param percept
   */
  public Action analyse(Choices percept)
  {
    // TODO Auto-generated method stub
    BasicAnalysisEngine folConversion = new BasicAnalysisEngine(percept);
    folConversion.generateChoices();

    // your code

    return this._reasoning.stimulate();
  }

}
