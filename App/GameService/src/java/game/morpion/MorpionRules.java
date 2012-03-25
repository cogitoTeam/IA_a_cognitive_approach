/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game.morpion;

import game.Board;
import game.Board.Position;
import game.Game;
import game.Game.Player;
import game.Game.State;
import game.Rules;


class MorpionRules extends Rules
{
    /** SINGLETON **/
    
    private static MorpionRules instance = null;
    public static MorpionRules getInstance()
    {
        if(instance == null)
            instance = new MorpionRules();
        return instance;
    }
    private MorpionRules() { }
    
    
    
    /** IMPLEMENT INTERFACE **/
    
    // query
    @Override
    public Player getFirstPlayer() 
    {
        return Player.WHITE;
    }
    
    @Override
    public boolean isDraw(Board board) 
    {
        // return true if every cell is occupied
        return (board.get_n_pieces() == board.get_n_rows()*board.get_n_cols());
    }

    @Override
    public boolean hasWon(Board board, Player player) 
    {
        // local variables
        Position p = new Position(0, 0);
        int count;

        // check vertical '|' alignments
        for(p.col = 0; p.col < board.get_n_cols(); p.col++)
        {
            count = 0;
            for(p.row = 0; p.row < board.get_n_rows(); p.row++)
            {
                if(board.getCellOwner(p) != player)
                    break;
                else
                    count++;
            }
            if(count == board.get_n_rows())
                return true;
        }

        // check horizontal '--' alignments
        for(p.row = 0; p.row < board.get_n_rows(); p.row++)
        {
            count = 0;
            for(p.col = 0; p.col < board.get_n_rows(); p.col++)
            {
                if(board.getCellOwner(p) != player)
                    break;
                else
                    count++;
            }
            if(count == board.get_n_cols())
                return true;
        }
        

        // check for diagonal '\' alignments
        count = 0;
        for(int i = 0; i < board.get_n_rows(); i++)
        {
            p.row = p.col = i;
            if(board.getCellOwner(p) != player)
                break;
            else
                count++;
        }
        if(count == board.get_n_rows())
            return true;

        // check for diagonal '/' alignments
        count = 0;
        for(int i = 0; i < board.get_n_cols(); i++)
        {
            p.row = i;
            p.col = board.get_n_rows()-1-1;
            if(board.getCellOwner(p) != player)
                break;
            else
                count++;
        }
        if(count == board.get_n_cols())
            return true;
        
        // all possibilities have been exhausted
        return false;
    }

    @Override
    public boolean isLegalMove(Position p, Board board, Player player) 
    {
        return (board.getCell(p) == Board.Cell.EMPTY);
    }

    // modification
    @Override
    public Game.State performMove(Position p, Board board, Player player) 
    {
        // set the cell to the new colour
        board.setCellOwner(p, player);

        // check if success has occured
        if(hasWon(board, player))
            return (player == Player.WHITE) ? 
                    State.VICTORY_WHITE : State.VICTORY_BLACK;
        
        // check if draw has occured
        else if(isDraw(board))
                return State.DRAW;
        
        // otherwise report next player to move (cycle between the two)
        else
            return (player == Player.WHITE) ? 
                    State.TURN_BLACK : State.TURN_WHITE;
    }
    
    @Override
    public void reset(Board board) 
    {
        // simply clear the board, that's all there is to it ;)
        board.clear();
    }

}
