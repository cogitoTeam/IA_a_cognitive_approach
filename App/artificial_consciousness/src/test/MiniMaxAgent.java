/*****************
 * @author wdyce
 * @date   Mar 27, 2012
 *****************/

package test;

import agent.Action;
import agent.Agent;
import agent.Percept.Choices;
import agent.Percept.Defeat;
import agent.Percept.Draw;
import agent.Percept.Victory;
import game.BoardMatrix;
import game.Game.Player;
import game.Rules;

public class MiniMaxAgent extends Agent
{
    /* IMPLEMENTATIONS */

    @Override
    protected void think() 
    {
        // minimax doesn't think
    }

    @Override
    protected Action choices_reaction(Choices percept) 
    {
        // find the option with the highest utility
        int max_utility = Integer.MIN_VALUE;
        int max_i = -1;
        for(int i = 0; i < percept.getOptions().size(); i++)
        {
            int i_utility = 
                evaluate(getRules(), percept.getCurrentBoard(), getPlayer());
            System.out.println("option " + i + " has utility " + i_utility);
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
    protected Action victory_reaction(Victory percept) 
    {
        // restart the game
        return new Action.Restart();
    }

    @Override
    protected Action defeat_reaction(Defeat percept) 
    {
        // restart the game
        return new Action.Restart();
    }

    @Override
    protected Action draw_reaction(Draw percept) 
    {
        // restart the game
        return new Action.Restart();
    }
    
    @Override
    protected void action_result(boolean success, Action action) 
    {
        if(!success)
        {
            System.out.println(action + " failed!");
            sleep(2);
        }
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
