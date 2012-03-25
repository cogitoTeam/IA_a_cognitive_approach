/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.Game.Player;


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
    public void clear()
    {
        // local variables
        Position p = new Position(0, 0);
        
        // set all cells empty
        for(p.row = 0; p.row < get_n_rows(); p.row++)
            for(p.col = 0; p.col < get_n_cols(); p.col++)
                cells[p.row][p.col] = Cell.EMPTY;
    }
    
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
    
    public Cell setCellOwner(Position p, Game.Player new_owner)
    {
        Cell new_value = (new_owner == Player.WHITE) ? 
                            Cell.PIECE_WHITE : Cell.PIECE_BLACK;
        return setCell(p, new_value);
    }

    // query
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
    
    public Player getCellOwner(Position p)
    {
        Cell cell = getCell(p);
        switch(cell)
        {
            case PIECE_WHITE:
                return Player.WHITE;
                
            case PIECE_BLACK:
                return Player.BLACK;
                
            case OUT_OF_BOUNDS:
            case EMPTY:
            default:
                return null;
        }
    }
}
