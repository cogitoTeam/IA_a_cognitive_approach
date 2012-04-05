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
    BoardMatrix board = percept.getCurrentBoard();
    BoardMatrix.Position p = new Position(0, 0);
    for(p.row = 0; p.row < board.getSize().n_rows; p.row++)
      for(p.col = 0; p.col < board.getSize().n_cols; p.col++)
        board.getCell(p);
    
  //  percept.getOptions().get(0).getResult()
    // TODO Auto-generated method stub

    // your code

    return this._reasoning.stimulate();
  }

}
