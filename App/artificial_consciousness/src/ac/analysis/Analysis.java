package ac.analysis;

import agent.Frontier;
import ac.memory.ActiveMemory;
import ac.reasoning.Reasoning;

public class Analysis
{
  
  private ActiveMemory _memory;
  private Reasoning _reasoning;
  private Frontier _frontier;
  
  public Analysis(ActiveMemory memory, Reasoning reasoning, Frontier frontier)
  {
    this._frontier = frontier;
    this._memory = memory;
    this._reasoning = reasoning;
  }

}
