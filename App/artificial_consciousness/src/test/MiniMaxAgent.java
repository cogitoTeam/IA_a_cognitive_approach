/*****************
 * @author wdyce
 * @date   Mar 27, 2012
 *****************/

package test;

import frontier.Action;
import frontier.Percept.Choices;
import frontier.Percept.Defeat;
import frontier.Percept.Draw;
import frontier.Percept.Victory;
import game.BoardMatrix;
import game.Game.Player;
import game.Rules;
import main.Agent;

public class MiniMaxAgent extends Agent
{
    /* CONSTANTS */
    /*private static final Player PMIN = Player.BLACK;
    private static final Player PMAX = Player.WHITE;
    * 
    */


    
    /* IMPLEMENTATIONS */

    @Override
    protected void think() 
    {
        // minimax doesn't think
    }

    @Override
    protected Action choices_reaction(Choices percept) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Action victory_reaction(Victory percept) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Action defeat_reaction(Defeat percept) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Action draw_reaction(Draw percept) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    protected void action_failed(Action action) 
    {
        // no fault tolerance implemented for this Agent
        state = State.ERROR;
    }
    
    
    
    /* SUBROUTINES */

    private int evaluate(Rules rules, BoardMatrix board, Player player)
    {
        return 0;
        /*List<Position> options = rules.getLegalMoves(board, player);

        // Is leaf node ?
        if(options.isEmpty())
            return (rules.hasWon(board, PMAX)) ? 1 
                    : ((rules.hasWon(board, PMIN)) ? -1 : 0);


        // Is min node ?
        if(player == PMIN)
        {
            int beta = Integer.MAX_VALUE;
            for(Position option : options)
            {
                
            }
        }
        else // player == PMAX
        {
            int alpha = Integer.MIN_VALUE;
        }*/

    }
}
