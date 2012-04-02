package ac.analysis;

import ac.memory.ActiveMemory;
import ac.reasoning.Reasoning;

public class Analysis
{
  
  private ActiveMemory _memory;
  private Reasoning _reasoning;
  
  public Analysis(ActiveMemory memory, Reasoning reasoning)
  {
    this._memory = memory;
    this._reasoning = reasoning;
  }

}
