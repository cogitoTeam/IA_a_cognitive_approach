package ac.analysis;

import game.BoardMatrix;
import game.BoardMatrix.*;
import ac.analysis.structure.*;
import ac.shared.CompleteBoardState;
import ac.shared.FOLObjects.*;
import agent.Percept.Choices;
import agent.Percept.*;

/**
 * This class implements the basic conceptual analyzer : It converts a board
 * matrix into a board state described in first order logic
 * 
 * @author Namrata Patel
 * 
 */
public class BasicAnalysisEngine
{
  private Choices input;

  /**
   * Default constructor for the basic conceptual analyzer
   * 
   * @param percept
   *          a list of available choices
   */
  public BasicAnalysisEngine(Choices percept)
  {
    super();
    this.setInput(percept);
  }

  /**
   * Gets the input
   * 
   * @return input the Percept
   */
  public Choices getInput()
  {
    return input;
  }

  /**
   * Sets the input
   * 
   * @param percept
   *          a Percept
   */
  public void setInput(Choices percept)
  {
    this.input = percept;
  }

  /**
   * @param matrix
   *          a BoardMatrix
   * @return an instance of a {@link CompleteBoardState} class which represents
   *         the converted BoardMatrix
   */
  private CompleteBoardState convertMatrixtoCBS(BoardMatrix matrix)
  {
    BoardMatrix.Position p = new Position(0, 0);
    Cell c;
    String s;
    Atom a;
    CompleteBoardState cbs = new CompleteBoardState();
    for (p.row = 0; p.row < matrix.getSize().n_rows; p.row++)
      for (p.col = 0; p.col < matrix.getSize().n_cols; p.col++)
        {
          c = matrix.getCell(p);
          s = "is" + c + "(c_" + p.row + p.col + ")";
          a = new Atom(s);
          cbs.getBoardStateFacts().addNewFact(a);
        }

    // percept.getOptions().get(0).getResult()

    return null;

  }

  /**
   * @return the choices converted into FOL
   */
  public Choices_FOL generateChoices()
  {
    Choices_FOL output = new Choices_FOL();

    BoardMatrix board = input.getCurrentBoard();
    CompleteBoardState current_board = convertMatrixtoCBS(board);

    output.setCurrent_board(current_board);

    CompleteBoardState result;
    Option tmp;
    for (agent.Action.Option o : input.getOptions())
      {
        result = convertMatrixtoCBS(o.getResult());
        tmp = new Option(o.getAction(), result);
        output.addOption(tmp);
      }

    return output;

  }
}
