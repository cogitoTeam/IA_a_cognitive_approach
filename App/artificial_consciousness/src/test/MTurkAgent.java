/*****************
 * @author william
 * @date 30-Mar-2012
 *****************/

package test;

import agent.Action;
import agent.Percept;
import agent.Percept.Choices;
import game.BoardMatrix.Position;
import game.Game;
import java.util.Scanner;


public class MTurkAgent extends SwitchAgent
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
    protected void actionResult(boolean success, Action action) 
    {
        System.out.println(action + ((success) ? " successful!"  : " failed!"));
    }
    
    @Override
    protected Action choicesReaction(Choices percept) 
    {
        // ask user to make a choice
        System.out.println("It's your turn to make a move!");

        // display the board
        System.out.println(percept.getCurrentBoard().toConsole());
        
        // get order strings from console
        Position p = new Position(-1, -1);
        while(p.row == -1 || p.col == -1)
        {
            try
            {
                // got row
                System.out.print("Choose a row: ");
                String row = console.nextLine();
                p.row = Integer.parseInt(row);
                // get collumn
                System.out.print("Choose a collumn: ");
                String col = console.nextLine();
                p.col = Integer.parseInt(col);
            }
            catch(NumberFormatException ex)
            {
                System.out.println("That's not a number!");
                p.row = p.col = -1;
            }
        }
        // send order
        return new Action.Move(p);
    }
    
    @Override
    protected Action gameEndReaction(Percept.GameEnd percept)
    {
        // tell the player who won
        if(percept.getWinner() == getPlayer())
            System.out.print("You win!");
        else if(percept.getWinner() == Game.otherPlayer(getPlayer()))
            System.out.print("You lose!");
        else
            System.out.print("It's a draw...");
        
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
