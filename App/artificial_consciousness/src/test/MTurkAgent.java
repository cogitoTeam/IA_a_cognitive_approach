/*****************
 * @author william
 * @date 30-Mar-2012
 *****************/

package test;

import agent.Action;
import agent.Agent;
import agent.Percept;
import agent.Percept.Choices;
import agent.Percept.Defeat;
import agent.Percept.Draw;
import agent.Percept.Victory;
import game.BoardMatrix.Position;
import java.util.Scanner;


public class MTurkAgent extends Agent
{
    /* ATTRIBUTES */
    
    private Scanner console = new Scanner(System.in);

    
    
    /* IMPLEMENTATIONS */
    
    @Override
    protected void think() 
    {
        System.out.println("Waiting for other player...");
    }

    @Override
    protected Action choices_reaction(Choices percept) 
    {
        // ask user to make a choice
        System.out.println("It's your turn to make a move!");

        // display the board
        System.out.println(percept.getCurrentBoard().toConsole());
        
        // get order strings from console
        System.out.print("Choose a row: ");
        String row = console.nextLine();
        System.out.print("Choose a collumn: ");
        String col = console.nextLine();
        
        // parse order
        Position position 
            = new Position(Integer.parseInt(row), Integer.parseInt(col));
        
        // send order
        return new Action.Move(position);
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
    protected void action_result(boolean success, Action action) 
    {
        System.out.println(action + ((success) ? " successful!"  : " failed!"));
    }
    
    
    /* SUBROUTIES */
    
    private Action gameend_reaction(Percept.GameEnd percept)
    {
        // tell the user their score
        System.out.println("Your score: " + percept.getScore());
        // show the final board
        System.out.println(percept.getCurrentBoard().toConsole());
        
        // start a new game when the player is ready
        System.out.println("Press enter to restart");
        console.nextLine();
        return new Action.Restart();
    }

}
