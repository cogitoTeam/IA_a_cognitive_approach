/*****************
 * @author william
 * @date 30-Mar-2012
 *****************/


package test;

import frontier.*;
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
    protected Action choose_reaction(Percept percept) 
    {
        // take your turn
        if(percept instanceof MyTurnPercept)
            return myturn_reaction((MyTurnPercept)percept);
        
        // the game has ended
        else if(percept instanceof GameEndPercept)
            return gameend_reaction((GameEndPercept)percept);
        
        // ... 
        
        // unknown
        else
        {
            System.out.println("Received unknown percept!");
            return null;
        }
    }

    @Override
    protected void action_failed(Action action) 
    {
        System.out.println("Invalid move!");
    }
    
    
    /* SUBROUTIES */
    
    private Action myturn_reaction(MyTurnPercept percept) 
    {
        // ask user to make a choice
        System.out.println("It's your turn to make a move!");

        // get strings
        String row = console.readLine("Row: ");
        String col = console.readLine("Collumn: ");

        // TODO -- finish
        return null;
    }
    
    private Action gameend_reaction(GameEndPercept percept)
    {
        // tell the user that the game has ended
        switch(percept.getResult())
        {
            case WIN:
                System.out.print("You win!");
                break;
            case LOSE:
                System.out.print("You lose!");
                break;
            default:
            case DRAW:
                System.out.print("It's a draw...");
                break;
        }
        
        // tell the user their score
        System.out.println("Your score: " + percept.getScore());
        
        // start a new game when the player is ready
        console.readLine("Press enter to restart...");
        return new RestartAction();
    }

}
