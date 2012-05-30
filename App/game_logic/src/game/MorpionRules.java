/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import game.BoardMatrix.Position;
import game.BoardMatrix.Size;
import game.Game.Player;


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
    public int getMaxSearchDepth()
    {
      // we can permit ourselves to search the full possibility space
      return 9;
    }
    
    @Override
    public Size getBoardSize()
    {
        return BOARD_SIZE;
    }
    
    @Override
    public Player getFirstPlayer() 
    {
        return Player.WHITE;
    }
    
    @Override
    public boolean canMove(BoardMatrix board, Player player)
    {
        // return true if every cell is occupied
        return board.count_pieces() != board.get_n_cells();
    }
    
    @Override
    public boolean isDraw(BoardMatrix board) 
    {
        
        return (!canMove(board, Player.WHITE) 
                && !canMove(board, Player.BLACK)
                && !hasWon(board, Player.WHITE)
                && !hasWon(board, Player.BLACK));
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
    public float estimateValue(BoardMatrix board, Player player) 
    {
        return 0.5f; /// FIXME
    }
    
    @Override
    public int getScore(BoardMatrix board, Player player) 
    {
        return hasWon(board, player) ? 1 
              : hasWon(board, Game.otherPlayer(player)) ? -1 
              : 0;
    }

    @Override
    public boolean isLegalMove(Position p, BoardMatrix board, Player player) 
    {
        return (board.getCell(p) == BoardMatrix.Cell.EMPTY);
    }

    // modification
    @Override
    public void performMove(Position p, BoardMatrix board, Player player) 
    {    
        // set the cell to the new colour
        board.setCellOwner(p, player);
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

}
