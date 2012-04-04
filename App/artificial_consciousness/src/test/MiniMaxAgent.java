/*****************
 * @author wdyce
 * @date   Mar 27, 2012
 *****************/

package test;

import agent.Action;
import agent.Percept.Choices;
import agent.Percept.Defeat;
import agent.Percept.Draw;
import agent.Percept.Victory;
import game.BoardMatrix;
import game.Game;
import game.Game.Player;
import game.Rules;
import java.util.List;

public class MiniMaxAgent extends SwitchAgent
{
    /* IMPLEMENTATIONS */

    @Override
    protected void think() 
    {
        // minimax doesn't think
    }

    @Override
    protected Action choicesReaction(Choices percept) 
    {
        // local variables
        Player me = getPlayer();
        // find the option with the highest utility
        int max_utility = Integer.MIN_VALUE;
        int max_i = -1;
        for(int i = 0; i < percept.getOptions().size(); i++)
        {
            int i_utility = 
                evaluate(getRules(), percept.getOptions().get(i).getResult(),me);
            if(i_utility > max_utility)
            {
                max_i = i;
                max_utility = i_utility;
            }
        }
        
        // perform this action
        return percept.getOptions().get(max_i).getAction();
    }

    @Override
    protected Action victoryReaction(Victory percept) 
    {
        // restart the game
        return new Action.Restart();
    }

    @Override
    protected Action defeatReaction(Defeat percept) 
    {
        // restart the game
        return new Action.Restart();
    }

    @Override
    protected Action drawReaction(Draw percept) 
    {
        // restart the game
        return new Action.Restart();
    }
    
    @Override
    protected void actionResult(boolean success, Action action) 
    {
        if(!success)
        {
            System.out.println(action + " failed!");
            sleep(2);
        }
    }
    
    
    /* SUBROUTINES */
    
    private int evaluate(Rules rules, BoardMatrix board, Player current)
    {
        // maximise my own gain, not the other player's
        Player me = getPlayer(), other = Game.otherPlayer(current);
        boolean max = (current == me);
        
        // Is leaf node ?
        if(rules.hasWon(board, me))
            return 1;
        else if(rules.hasWon(board, other))
            return -1;
        else if(rules.isDraw(board))
            return 0;
        
        // Otherwise there should be possible moves to play...
        List<BoardMatrix> children = rules.getChildBoards(board, current);

        // Is max node ?
        if(max)
        {
            int beta = Integer.MAX_VALUE;
            for(BoardMatrix child : children)
            {
                int beta_prime = evaluate(rules, child, other);
                if(beta_prime < beta)
                    beta = beta_prime;
            }
            return beta;
        }
        
        // Is min node ?
        else
        {
            int alpha = Integer.MIN_VALUE;
            for(BoardMatrix child : children)
            {
                int alpha_prime = evaluate(rules, child, other);
                if(alpha_prime > alpha)
                    alpha = alpha_prime;
            }
            return alpha;
        }
    }
}
