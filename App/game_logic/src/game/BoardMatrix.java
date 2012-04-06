/*****************
 * @author william
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

    public static Cell playerToPiece(Player p)
    {
        return (p == Player.WHITE) ? Cell.PIECE_WHITE : Cell.PIECE_BLACK;  
    }
    
    /* ATTRIBUTES */
            
    private final Size size;
    private final Cell cells[][];
    
    
    /* METHODS */
    
    // creation
    protected BoardMatrix(Size _size)
    {
        size = _size;
        // create the matrix
        cells = new Cell[_size.n_rows][_size.n_cols];
    }
    
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
    
    public Cell setCellOwner(Position p, Game.Player new_owner)
    {
        Cell new_value = (new_owner == Player.WHITE) ? 
                            Cell.PIECE_WHITE : Cell.PIECE_BLACK;
        return setCell(p, new_value);
    }

    // query

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


    public Size getSize()
    {
        return size;
    }
    
    public int get_n_cells()
    {
        return size.n_rows * size.n_cols;
    }
    
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
    
    public int count_pieces()
    {
        // total number of pieces = total cells - empty cells
        return get_n_cells() - count_player_pieces(null);
    }

    public Cell getCell(Position p)
    {
        if(p.row < 0 || p.col < 0 || p.row >= size.n_rows || p.col >= size.n_cols)
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


    /* OVERRIDES */
    
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

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 19 * hash + (this.size != null ? this.size.hashCode() : 0);
        hash = 19 * hash + Arrays.deepHashCode(this.cells);
        return hash;
    }
    
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
