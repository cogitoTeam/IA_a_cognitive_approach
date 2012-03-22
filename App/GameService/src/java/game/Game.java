/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;


public abstract class Game 
{
    /** NESTING **/
    
    public static enum Player
    {
        WHITE,
        BLACK
    }
    
    public static enum State
    {
        CONTINUE,
        VICTORY_WHITE,
        VICTORY_BLACK,
        DRAW
    }
    
    /** ATTRIBUTES **/
    
    private final Rules rules;
    private final Board board;
    private Player current_player;
    
    /** METHODS **/
    
    // creation
    public Game(Rules _rules, Board _board)
    {
        rules = _rules;
        board = _board;
    }
    
    // main
    public void performMove(Position p)
    {
        // perform the move and change player
        current_turn = rules.performMove(row, col, board, current_turn);
        // redraw the game board
        board.redraw();

        // the game continues ?
        if(current_turn < n_players)
        {
            // ai turn ?
            if (!is_human[current_turn])
            {
                var ai_choice = ai.bestMove(board, current_turn);

                // check if the ai's move is legal
                if(rules.isLegalMove(ai_choice[0], ai_choice[1], board,
                                                        current_turn))
                    performMove(ai_choice[0], ai_choice[1]);
                else
                    alert("ai performed illegal move");
            }
        }
        else
        {
            // draw ?
            if(current_turn == typ.DRAW)
                alert("draw");

            // black victory ?
            else if(current_turn == typ.VICTORY_BLACK)
                alert("black wins");

            // white victory ?
            else if(current_turn == typ.VICTORY_WHITE)
                alert("white wins");

            // restart either way
            obj.restart();
        }
    }
    
    // modification
    public void restart()
    {
        rules.reset(board);
        current_player = rules.getFirstPlayer();
    }
    
}
