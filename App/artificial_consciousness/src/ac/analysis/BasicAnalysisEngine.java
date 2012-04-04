package ac.analysis;

import ac.analysis.structure.*;
import ac.shared.*;
import agent.Percept;

/**
 * This class implements the basic conceptual analyzer : It converts a board
 * matrix into a board state described in first order logic
 * 
 * @author Namrata Patel
 * 
 */
public class BasicAnalysisEngine {
	private Percept input;

	/**
	 * Default constructor for the basic conceptual analyzer
	 * 
	 * @param input
	 *            a Percept
	 */
	public BasicAnalysisEngine(Percept input) {
		super();
		this.setInput(input);
	}

	/**
	 * Gets the input
	 * 
	 * @return input the Percept
	 */
	public Percept getInput() {
		return input;
	}

	/**
	 * Sets the input
	 * 
	 * @param input
	 *            a Percept
	 */
	public void setInput(Percept input) {
		this.input = input;
	}

	/**
	 * @param matrix
	 *            a BoardMatrix to_string
	 * @return a string which represents the FOL fact base corresponding to a
	 *         board matrix
	 */
	public String convertMatrixtoFOL(String matrix) {
		// To do task as defined
		String FOL = matrix;
		return FOL;

	}

	/**
	 * @return
	 */
	public CompleteBoardState generateCBS() {

	}
}
