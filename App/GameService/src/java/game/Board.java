/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;


public abstract class Board 
{
    /** NESTING **/
    public static enum Cell
    {
        EMPTY, PIECE_BLACK, PIECE_WHITE, OUT_OF_BOUNDS
    }
    
    public static class Size
    {
        public int n_rows;
        public int n_cols;
        public Size(int _n_rows, int _n_cols)
        {
            n_rows = _n_rows;
            n_cols = _n_cols;
        }
    }
    
    public static class Position
    {
        public int row;
        public int col;
        public Position(int _row, int _col)
        {
            row = _row;
            col = _col;
        }
    }
    
    /** CONTANTS **/
    

    /** ATTRIBUTES **/
            
    private Cell cells[][];
    
    
    /** METHODS **/
    
    // creation
    public Board(Size s)
    {
        // create the matrix
        cells = new Cell[s.n_rows][s.n_cols];
    }
    

    // modification
    public Cell setCell(Position p, Cell new_value)
    {
        if(p.row < get_n_rows() && p.col < get_n_cols())
        {
            Cell previous_value = cells[p.row][p.col];
            cells[p.row][p.col] = new_value;
            return previous_value;
        }
        else
            return Cell.OUT_OF_BOUNDS;
    }

    // access
    public int get_n_rows()
    {
        return cells.length;
    }

    public int get_n_cols()
    {
        return cells[0].length;
    }

    public Cell getCell(Position p)
    {
        if(p.row >= get_n_rows() || p.col >= get_n_cols())
            return Cell.OUT_OF_BOUNDS;
        else
            return cells[p.row][p.col];
    }
}
