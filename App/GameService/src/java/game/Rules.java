/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;

import java.util.List;


public abstract class Rules 
{
    /** ATTRIBUTES **/

    /** PUBLIC INTERFACE **/
 
    // query
    public abstract boolean isDraw(Board board);
    
    public abstract boolean hasWon(Board board, Game.Player player);
    
    public abstract List<Board.Position> getLegalMoves(Board board, 
                                                        Game.Player player);
    public abstract boolean isLegalMove(Board.Position p, Board board, 
                                        Game.Player player);
    public abstract void performMove(Board.Position p, Board board, 
                                    Game.Player player);
}