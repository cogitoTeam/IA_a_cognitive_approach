/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.BoardMatrix.Position;
import game.BoardMatrix.Size;
import game.Game.Player;
import game.Game.State;


public class MorpionRules extends Rules
{
    /* CONSTANTS */
    private static final Size BOARD_SIZE = new Size(3, 3);
    
    /* SINGLETON */
    
    private static MorpionRules instance = null;
    public static MorpionRules getInstance()
    {
        if(instance == null)
            instance = new MorpionRules();
        return instance;
    }
    private MorpionRules() { }
    
    
    
    /* IMPLEMENT INTERFACE */
    
    // query
    @Override
    public Player getFirstPlayer() 
    {
        return Player.WHITE;
    }
    
    @Override
    public boolean isDraw(BoardMatrix board) 
    {
        // return true if every cell is occupied
        return (board.get_n_pieces() == board.get_n_cells());
    }

    @Override
    public boolean hasWon(BoardMatrix board, Player player) 
    {
        // local variables
        Position p = new Position(0, 0);
        int count;

        // check vertical '|' alignments
        for(p.col = 0; p.col < board.getSize().n_cols; p.col++)
        {
            count = 0;
            for(p.row = 0; p.row < board.getSize().n_rows; p.row++)
            {
                if(board.getCellOwner(p) != player)
                    break;
                else
                    count++;
            }
            if(count == board.getSize().n_rows)
                return true;
        }

        // check horizontal '--' alignments
        for(p.row = 0; p.row < board.getSize().n_rows; p.row++)
        {
            count = 0;
            for(p.col = 0; p.col < board.getSize().n_rows; p.col++)
            {
                if(board.getCellOwner(p) != player)
                    break;
                else
                    count++;
            }
            if(count == board.getSize().n_cols)
                return true;
        }
        

        // check for diagonal '\' alignments
        count = 0;
        for(int i = 0; i < board.getSize().n_rows; i++)
        {
            p.row = p.col = i;
            if(board.getCellOwner(p) != player)
                break;
            else
                count++;
        }
        if(count == board.getSize().n_rows)
            return true;

        // check for diagonal '/' alignments
        count = 0;
        for(int i = 0; i < board.getSize().n_cols; i++)
        {
            p.row = i;
            p.col = board.getSize().n_cols-1-i;
            if(board.getCellOwner(p) != player)
                break;
            else
                count++;
        }
        if(count == board.getSize().n_cols)
            return true;
        
        // all possibilities have been exhausted
        return false;
    }
    
    
    @Override
    public int getValue(BoardMatrix board, Player player) 
    {
        return hasWon(board, player) ? 1 : isDraw(board) ? 0 : -1;
    }

    @Override
    public boolean isLegalMove(Position p, BoardMatrix board, Player player) 
    {
        return (board.getCell(p) == BoardMatrix.Cell.EMPTY);
    }

    // modification
    @Override
    public Game.State performMove(Position p, BoardMatrix board, Player player) 
    {
        // check if the move is legal
        if(!isLegalMove(p, board, player))
            return State.MOVE_FAILURE;
        
        // set the cell to the new colour
        board.setCellOwner(p, player);

        // check if success has occured
        if(hasWon(board, player))
            return State.VICTORY;
        
        // check if draw has occured
        else if(isDraw(board))
            return State.DRAW;
        
        // otherwise report next player to move (cycle between the two)
        else
            return State.MOVE_SUCCESS;
    }
    
    @Override
    public void reset(BoardMatrix board) 
    {
        // simply clear the board, that's all there is to it ;)
        board.clear();
    }

    @Override
    public String toString() 
    {
        return "morpion";
    }

    @Override
    public BoardMatrix createBoard()
    {
        return new BoardMatrix(BOARD_SIZE);
    }

}
