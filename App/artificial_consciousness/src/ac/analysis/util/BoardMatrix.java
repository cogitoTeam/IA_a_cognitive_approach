package ac.analysis.util;

/**
 * Classe BoardMatrix
 * 
 * @author wilbefast
 * @date 26 mars 2012
 * @version 0.1
 */
public abstract class BoardMatrix {
    /** NESTED DEFINITIONS **/

    /**
     * type that defines the different states of a matrix cell
     */
    public static enum Cell {
        /**
         * empty cell
         */
        EMPTY,
        /**
         * cell filled with a white piece
         */
        P_WHITE,
        /**
         * cell filled with a black piece
         */
        P_BLACK
    }

    /** ATTRIBUTES **/

    private Cell matrix[][];

    /**
     * METHODS Constructor
     * 
     * @param n_rows
     *            number of rows
     * @param n_cols
     *            number of columns
     * **/

    public BoardMatrix(int n_rows, int n_cols) {
        // create matrix
        matrix = new Cell[n_rows][n_cols];

        // all cells are initially empty
        for (int row = 0; row < n_rows; row++)
            for (int col = 0; col < n_cols; col++)
                matrix[row][col] = Cell.EMPTY;

    }

    // access
    /**
     * @return returns the number of rows
     */
    public int get_n_rows() {
        return matrix.length;
    }

    /**
     * @return returns the number of columns
     */
    public int get_n_cols() {
        return matrix[0].length;
    }

    /**
     * @param c
     *            the cell to replace in the matrix
     * @param row_c
     *            cell row coordinate
     * @param col_c
     *            cell column coordinate
     */
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
