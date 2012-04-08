package ac.analysis;

import java.io.IOException;

import game.BoardMatrix;
import game.BoardMatrix.Position;
import game.BoardMatrix.Size;
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
   * @return
   */
  public Action analyse(Choices percept)
  {
    // TODO Auto-generated method stub
    BasicAnalysisEngine folConversion = new BasicAnalysisEngine(percept);
    folConversion.generateChoices();

    // your code

    return this._reasoning.stimulate();
  }

  public static void main(String[] args) throws IOException
  {
    Size s = new Size(8,8);
    BoardMatrix b = new BoardMatrix(null);
    
    Choices test = new Choices(b);
    test.getCurrentBoard();
    System.out.println(test.getCurrentBoard());
  }
}
