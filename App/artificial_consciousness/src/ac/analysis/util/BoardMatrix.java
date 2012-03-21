/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/

package ac.analysis.util;

public abstract class BoardMatrix {
	/** NESTED DEFINITIONS **/

	public static enum Cell {
		EMPTY, P_WHITE, P_BLACK
	}

	/** ATTRIBUTES **/

	private Cell matrix[][];

	/** METHODS **/

	// creation
	public BoardMatrix(int n_rows, int n_cols) {
		// create matrix
		matrix = new Cell[n_rows][n_cols];

		// all cells are initially empty
		for (int row = 0; row < n_rows; row++)
			for (int col = 0; col < n_cols; col++)
				matrix[row][col] = Cell.EMPTY;

	}

	// access
	public int get_n_rows() {
		return matrix.length;
	}

	public int get_n_cols() {
		return matrix[0].length;
	}

	public void fill_cell(Cell c, int row_c, int col_c) {
		matrix[row_c][col_c] = c;
	}

	// to_String
	@Override
	public String toString() {
		String t = new String();
		for (int row = 0; row < get_n_rows(); row++) {
			for (int col = 0; col < get_n_cols(); col++)
				t += matrix[row][col].toString() + "\t";
			t += "\n";
		}
		return t;
	}

	/** TODO finish **/
}
