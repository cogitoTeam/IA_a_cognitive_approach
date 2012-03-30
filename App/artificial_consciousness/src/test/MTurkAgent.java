/*****************
 * @author william
 * @date 30-Mar-2012
 *****************/

package test;

import frontier.Action;
import frontier.Percept;
import frontier.Percept.Choices;
import frontier.Percept.Defeat;
import frontier.Percept.Draw;
import frontier.Percept.Victory;
import java.io.Console;
import main.Agent;


public class MTurkAgent extends Agent
{
    /* ATTRIBUTES */
    
    Console console = System.console();
    
    
    /* IMPLEMENTATIONS */
    
    @Override
    protected void think() 
    {
        System.out.println("Waiting for other player to make a move...");
    }

    @Override
    protected Action choices_reaction(Choices percept) 
    {
        // ask user to make a choice
        System.out.println("It's your turn to make a move!");

        // get strings
        String row = console.readLine("Row: ");
        String col = console.readLine("Collumn: ");

        // TODO -- finish
        return null;
    }

    @Override
    protected Action victory_reaction(Victory percept)
    {
        System.out.print("You win!");
        return gameend_reaction(percept);
    }

    @Override
    protected Action defeat_reaction(Defeat percept) 
    {
        System.out.print("You lose!");
        return gameend_reaction(percept);
    }

    @Override
    protected Action draw_reaction(Draw percept)
    {
        System.out.print("It's a draw...");
        return gameend_reaction(percept);
    }

    @Override
    protected void action_failed(Action action) 
    {
        System.out.println("Invalid move!");
        // no fault tolerance implemented for this Agent
        state = State.ERROR;
    }
    
    
    /* SUBROUTIES */
    
    private Action gameend_reaction(Percept.GameEnd percept)
    {
        // tell the user their score
        System.out.println("Your score: " + percept.getScore());
        
        // start a new game when the player is ready
        console.readLine("Press enter to restart...");
        return new Action.Restart();
    }

}
