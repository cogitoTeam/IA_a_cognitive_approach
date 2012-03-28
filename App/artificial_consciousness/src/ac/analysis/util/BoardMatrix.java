/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package ac.analysis.util;


public abstract class BoardMatrix
{
    /* NESTING */
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
        @Override
        public String toString()
        {
            return "Size: n_rows=" + n_rows + " n_cols=" + n_cols;
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
        @Override
        public String toString()
        {
            return "Position: row=" + row + " col=" + col;
        }
    }
    

    /* ATTRIBUTES */
            
    private Size size;
    private Cell cells[][];
    
    
    /* METHODS */
    
    // creation
    public BoardMatrix(Size _size)
    {
        // create the matrix
        size = _size;
        cells = new Cell[_size.n_rows][_size.n_cols];
        
    }
    

    // modification

    public void clear()
    {
        // local variables
        Position p = new Position(0, 0);
        
        // set all cells empty
        for(p.row = 0; p.row < size.n_rows; p.row++)
            for(p.col = 0; p.col < size.n_cols; p.col++)
                cells[p.row][p.col] = Cell.EMPTY;
    }
    
    public Cell setCell(Position p, Cell new_value)
    {
        if(p.row < size.n_rows && p.col < size.n_cols)
        {
            Cell previous_value = cells[p.row][p.col];
            cells[p.row][p.col] = new_value;
            return previous_value;
        }
        else
            return Cell.OUT_OF_BOUNDS;
    }

    // query

    public BoardMatrix copy() throws InstantiationException, IllegalAccessException
    {
        // local variables
        BoardMatrix clone = this.getClass().newInstance();
        Position p = new Position(0, 0);

        // copy each cell
        for(p.row = 0; p.row < size.n_rows; p.row++)
            for(p.col = 0; p.col < size.n_cols; p.col++)
                clone.cells[p.row][p.col] = this.cells[p.row][p.col];

        // return the result
        return clone;
    }


    public Size get_size()
    {
        return size;
    }
    
    public int get_n_pieces()
    {
        // local variables
        Position p = new Position(0, 0);
        int count = 0;
        
        // count number of non-empty cells
        for(p.row = 0; p.row < size.n_rows; p.row++)
            for(p.col = 0; p.col < size.n_cols; p.col++)
                if(getCell(p) != Cell.EMPTY)
                    count++;
        
        // finished
        return count;
    }

    public Cell getCell(Position p)
    {
        if(p.row >= size.n_rows || p.col >= size.n_cols)
            return Cell.OUT_OF_BOUNDS;
        else
            return cells[p.row][p.col];
    }
}
