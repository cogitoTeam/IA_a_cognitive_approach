/*****************
 * Class representing a game board in matrix form
 * @author william <wilbefast@gmail.com>
 * @date 21-Mar-2012
 *****************/


package game;

import game.Game.Player;
import java.util.Arrays;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class BoardMatrix
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
            return "n_rows=\"" + n_rows + "\" n_cols=\"" + n_cols + "\"";
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
        public void add(Direction delta)
        {
            row += delta.row;
            col += delta.col;
        }
        public boolean within(Size size)
        {
            return (row >= 0 && row < size.n_rows 
                    && col >= 0 && col < size.n_cols);
        }
        @Override
        public String toString()
        {
            return "row=\"" + row + "\" col=\"" + col + "\"";
        }
    }
    
    public static class Direction extends Position
    {
        public Direction(int _row, int _col)
        {
            super(_row, _col);
        }
    }
            
            
            
    
    /* CLASS NAMEPSACE FUNCTIONS */
    /** 
    * @param c
    * A BoardMatrix.Cell enumeration case (ex: Board.Cell.PIECE_WHITE).
    * @return 
    * The corresponding Game.Player enumeration case 
    * (ex: Game.Player.WHITE) or null if there is no such case.
    */
    public static Player pieceToPlayer(Cell c)
    {
        switch(c)
        {
            case PIECE_WHITE:
                return Player.WHITE;
            case PIECE_BLACK:
                return Player.BLACK;
            default:
                return null;
        }
    }

    /** 
    * @param p
    * A Game.Player enumeration case (ex: Game.Player.WHITE).
    * @return 
    * The corresponding BoardMatrix.Cell enumeration case 
    * (ex: Board.Cell.PIECE_WHITE) or null is there is no such case.
    */
    public static Cell playerToPiece(Player p)
    {
        switch(p)
        {
            case WHITE:
                return Cell.PIECE_WHITE;
            case BLACK:
                return Cell.PIECE_BLACK;
            default:
                return null;
        }
    }
    
    /* ATTRIBUTES */
            
    private final Size size;
    private final Cell cells[][];
    
    
    /* METHODS */
    
    // creation
    /** Create a board by manually specifying the size: use Rules.createBoard() 
     * to create a board of the appropriate size for the rules in question.
    * @param _size
    * Initialiser for BoardMatrix.size attribute. This is used to allocate
    * memory for the 2-dimensional array where representing the board state.
    */
    protected BoardMatrix(Size _size)
    {
        size = _size;
        // create the matrix
        cells = new Cell[_size.n_rows][_size.n_cols];
    }
    
    /** Create a board by parsing the 'board' node of an XML document: no error
     * protected is provided by the function so use it carefully!
    * @param board_node
    * The Document Object Model (DOM) node to be parsed in order to create the
    * BoardMatrix object.
    */
    public BoardMatrix(Node board_node)
    {
        // parse size
        size = new Size(0, 0);
        NamedNodeMap attributes = board_node.getAttributes();
        size.n_rows = 
            Integer.parseInt(attributes.getNamedItem("n_rows").getNodeValue());
        size.n_cols = 
            Integer.parseInt(attributes.getNamedItem("n_cols").getNodeValue());
        
        // create the matrix
        cells = new Cell[size.n_rows][size.n_cols];
        
        // update cells from DOM node
        parseCells(board_node);
    }
    

    // modification
    /** Modify (don't resize) the contents of a board by parsing the 'board' 
    * node of an XML document: no error protection is provided by the function 
    * so use it carefully!
    * @param board_node
    * The Document Object Model (DOM) node to be parsed in order to modify the
    * BoardMatrix object.
    */
    public final void parseCells(Node board_node)
    {
        // get the cell nodes from the document
        NodeList nl_cells = board_node.getChildNodes();
        
        // parse each cell
        for(int i = 0; i < nl_cells.getLength(); i++)
        {
            // local variables
            NamedNodeMap attributes = nl_cells.item(i).getAttributes();
            
            // parse position
            Position p = new Position(0, 0);
            p.row = Integer.parseInt(attributes.getNamedItem("row")
                                        .getNodeValue());
            p.col = Integer.parseInt(attributes.getNamedItem("col")
                                        .getNodeValue());
            
            // parse owner
            Player owner = null;
            Node n_owner = attributes.getNamedItem("owner");
            if(n_owner != null)
                owner = Game.parsePlayer(n_owner.getNodeValue());
            
            // finally set the cell's owner
            if(owner == null)
                setCell(p, BoardMatrix.Cell.EMPTY);
            else
                setCellOwner(p, owner);
        }
    }

    /** Clear the board: set all cells to Cell.EMPTY.
    */
    public void clear()
    {
        // local variables
        Position p = new Position(0, 0);
        
        // set all cells empty
        for(p.row = 0; p.row < size.n_rows; p.row++)
            for(p.col = 0; p.col < size.n_cols; p.col++)
                cells[p.row][p.col] = Cell.EMPTY;
    }
    
    /** Change the contents of a specific cell.
    * @param p
    * The position on the board to be modified.
    * @param new_value
    * The new contents of the cell.
    * @return 
    * The previous value of the cell, before it was modified, or 
    * Cell.OUT_OF_BOUNDS if the specified position was invalid.
    */
    public Cell setCell(Position p, Cell new_value)
    {
        if(p.row >= 0 && p.col >= 0 && 
            p.row < size.n_rows && p.col < size.n_cols)
        {
            Cell previous_value = cells[p.row][p.col];
            cells[p.row][p.col] = new_value;
            return previous_value;
        }
        else
            return Cell.OUT_OF_BOUNDS;
    }
    
    /** Change the owner of a specific cell to
    * @param p
    * The position on the board to be modified.
    * @param new_owner
    * The new owner of the cell.
    * @return
    * The previous owner of the cell or null if the specified position was 
    * invalid.
    */
    public Player setCellOwner(Position p, Game.Player new_owner)
    {
        return pieceToPlayer(setCell(p, playerToPiece(new_owner)));
    }

    // query

    /**
     * Create a copy of the message receiver (this).
     * @return 
     * A deep copy of the object, with the same size and contents. 
     */
    public BoardMatrix copy()
    {
        // local variables
        BoardMatrix clone = new BoardMatrix(this.size);
        Position p = new Position(0, 0);

        // copy each cell
        for(p.row = 0; p.row < size.n_rows; p.row++)
            for(p.col = 0; p.col < size.n_cols; p.col++)
                clone.cells[p.row][p.col] = this.cells[p.row][p.col];

        // return the result
        return clone;
    }

    /**
     * @return 
     * The size of the board, as an inner-class 'Size' structure with n_rows
     * and n_cols fields.
     */
    public Size getSize()
    {
        return size;
    }
    
    /**
     * @return 
     * The total number of cells, so the number of rows times the number of 
     * columns. 
     */
    public int get_n_cells()
    {
        return size.n_rows * size.n_cols;
    }
    
    /**
    * @param player
    * The player whose pieces we'll be counting, or null.
    * @return 
    * The total number of pieces owned by the specified player (the number of
    * empty cells if null is specified).
    */
    public int count_player_pieces(Player player)
    {
        // local variables
        Cell piece = (player == null) ? Cell.EMPTY : playerToPiece(player);
        Position p = new Position(0, 0);
        int count = 0;
        
        // count number of non-empty cells
        for(p.row = 0; p.row < size.n_rows; p.row++)
            for(p.col = 0; p.col < size.n_cols; p.col++)
                if(getCell(p) == piece)
                    count++;
        // finished
        return count;
    }
 
    /**
    * @return 
    * The total number of pieces on the board, so belonging to either player.
    */
    public int count_pieces()
    {
        // total number of pieces = total cells - empty cells
        return get_n_cells() - count_player_pieces(null);
    }

    /**
    * @param p
    * The position on the board we want to know the contents of.
    * @return 
    * The contents of the specified cell, or Cell.OUT_OF_BOUNDS is the position
    * given is invalid.
    */
    public Cell getCell(Position p)
    {
        if(p.row < 0 || p.col < 0 || p.row >= size.n_rows || p.col >= size.n_cols)
            return Cell.OUT_OF_BOUNDS;
        else
            return cells[p.row][p.col];
    }
    
    /**
    * @param p
    * The position on the board we want to know the owner of.
    * @return 
    * The player who owns the specified cell, or null if the position is invalid
    * or the cell has no owner.
    */
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


    /* OVERRIDES */
    
    /**
    * @param other
    * The object to compare this one with.
    * @return 
    * True if and only if the other object is a BoardMatrix with an identical
    * size and contents.
    */
    @Override
    public boolean equals(Object other)
    {
        // check types
        if(!(other instanceof BoardMatrix))
            return false;
        
        // check that sizes are identical
        BoardMatrix other_board = (BoardMatrix)other;
        if(!this.size.equals(other_board.size))
            return false;
        
        // check that all cells are identical
        Position p = new Position(0, 0);
        for(p.row = 0; p.row < size.n_rows; p.row++)
            for(p.col = 0; p.col < size.n_cols; p.col++)
                if(this.getCell(p) != other_board.getCell(p))
                    return false;
        
        return true;
    }

    /**
    * @return 
    * Hash function representative of the equals operator (size and cell 
    * contents must be identical).
    */
    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 19 * hash + (this.size != null ? this.size.hashCode() : 0);
        hash = 19 * hash + Arrays.deepHashCode(this.cells);
        return hash;
    }
    
    /**
    * @return 
    * An XML 'board' tag containing the board information (size, number of 
    * pieces and contents of each cell).
    */
    @Override
    public String toString()
    {
        // local variables
        String result = 
                "<board " + size + " n_pieces=\"" + count_pieces() + "\">";
        Position p = new Position(0, 0);
        
        // read the board positions
        for(p.row = 0; p.row < size.n_rows; p.row++)
            for(p.col = 0; p.col < size.n_cols; p.col++)
            {
                Player owner = getCellOwner(p);
                result += "<cell " + p;
                if(owner != null)
                    result += " owner=\"" + owner + "\"/>";
                else
                    result += "/>";
            }
        
        // finished
        return result+"</board>";
    }
    
    /**
    * @return 
    * A grid of characters representing the board in a more human-readable way.
    * <li>'*' = white piece</li>
    * <li>'@' = black piece</li>
    * <li>'-' = empty cell</li>
    */
    public String toConsole()
    {
        String result = "";
        Position p = new Position(0, 0);
        for(p.row = 0; p.row < size.n_rows; p.row++)
        {
            for(p.col = 0; p.col < size.n_cols; p.col++)
            {
                switch(getCell(p))
                {
                    case PIECE_WHITE:
                        result += '*';
                        break;
                    case PIECE_BLACK:
                        result += '@';
                        break;
                    case OUT_OF_BOUNDS:
                    case EMPTY:
                    default:
                        result += '-';
                        break;
                }
            }
            result += "\n";
        }
        
        return result;
    }
}
