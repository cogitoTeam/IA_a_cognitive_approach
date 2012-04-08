package ac.analysis;

import game.BoardMatrix;
import game.BoardMatrix.Position;
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
public class BasicAnalysisEngine {
	private Choices input;

	/**
	 * Default constructor for the basic conceptual analyzer
	 * 
	 * @param percept
	 *            a list of available choices
	 */
	public BasicAnalysisEngine(Choices percept) {
		super();
		this.setInput(percept);
	}

	/**
	 * Gets the input
	 * 
	 * @return input the Percept
	 */
	public Choices getInput() {
		return input;
	}

	/**
	 * Sets the input
	 * 
	 * @param percept
	 *            a Percept
	 */
	public void setInput(Choices percept) {
		this.input = percept;
	}

	/**
	 * @param matrix
	 *            a BoardMatrix to_string
	 * @return a string which represents the FOL fact base corresponding to a
	 *         board matrix
	 */
	private CompleteBoardState convertMatrixtoCBS(BoardMatrix matrix) {
	  BoardMatrix board = input.getCurrentBoard();
    BoardMatrix.Position p = new Position(0, 0);
    for(p.row = 0; p.row < board.getSize().n_rows; p.row++)
      for(p.col = 0; p.col < board.getSize().n_cols; p.col++)
        board.getCell(p);
    
  //  percept.getOptions().get(0).getResult()
    
	  return null;
		
	}

	/**
	 * @return
	 */
	public Choices_FOL generateChoices() {
	  Choices_FOL output = new Choices_FOL();
	  
    return output;

	}
}
