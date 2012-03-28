/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.Game.Player;
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
        @Override
        public String toString()
        {
            return "row=\"" + row + "\" col=\"" + col + "\"";
        }
    }
    
    /* CONTANTS */
    

    /* ATTRIBUTES */
            
    private Cell cells[][];
    
    
    /* METHODS */
    
    // creation
    protected BoardMatrix(Size s)
    {
        // create the matrix
        cells = new Cell[s.n_rows][s.n_cols];
    }
    
    public BoardMatrix(Node board_node)
    {
        // parse size
        Size s = new Size(0, 0);
        NamedNodeMap attributes = board_node.getAttributes();
        s.n_rows = 
            Integer.parseInt(attributes.getNamedItem("n_rows").getNodeValue());
        s.n_cols = 
            Integer.parseInt(attributes.getNamedItem("n_cols").getNodeValue());
        
        // create the matrix
        cells = new Cell[s.n_rows][s.n_cols];
        
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

    public BoardMatrix copy() throws InstantiationException, IllegalAccessException
    {
        // local variables
        BoardMatrix clone = this.getClass().newInstance();
        Position p = new Position(0, 0);

        // copy each cell
        for(p.row = 0; p.row < get_n_rows(); p.row++)
            for(p.col = 0; p.col < get_n_cols(); p.col++)
                clone.cells[p.row][p.col] = this.cells[p.row][p.col];

        // return the result
        return clone;
    }


    public int get_n_rows()
    {
        return cells.length;
    }

    public int get_n_cols()
    {
        return cells[0].length;
    }
    
    public int get_n_pieces()
    {
        // local variables
        Position p = new Position(0, 0);
        int count = 0;
        
        // count number of non-empty cells
        for(p.row = 0; p.row < get_n_rows(); p.row++)
            for(p.col = 0; p.col < get_n_cols(); p.col++)
                if(getCell(p) != BoardMatrix.Cell.EMPTY)
                    count++;
        
        // finished
        return count;
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


    /* OVERRIDES */
    @Override
    public String toString()
    {
        // local variables
        String result = "<board n_rows=\"" + get_n_rows() 
                        + "\" n_cols=\"" + get_n_cols() 
                        + "\" n_pieces=\"" + get_n_pieces() + "\">";
        Position p = new Position(0, 0);
        
        // read the board positions
        for(p.row = 0; p.row < get_n_rows(); p.row++)
            for(p.col = 0; p.col < get_n_cols(); p.col++)
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
}
