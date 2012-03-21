/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package ac.analysis.util;


public abstract class BoardMatrix
{
    /** NESTED DEFINITIONS **/

    public static enum Cell
    {
        EMPTY,
        PIECE_WHITE,
        PIECE_BLACK
    }

    /** ATTRIBUTES **/

    private Cell matrix[][];


    /** METHODS **/

    // creation
    public BoardMatrix(int n_rows, int n_cols)
    {
        // create matrix
        matrix = new Cell[n_rows][n_cols];
        
        // all cells are initially empty
        for(int row = 0; row < n_rows; row++)
            for(int col = 0; col < n_cols; col++)
                matrix[row][col] = Cell.EMPTY;

    }
    
    // access
    public int get_n_rows()
    {
        return matrix.length;
    }
    
    public int get_n_cols()
    {
        return matrix[0].length;
    }
    
    /** TODO finish **/
}
