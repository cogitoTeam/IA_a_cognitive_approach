/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import ac.frontier.morpion.MorpionFrontier;
import game.Game.Player;


public class TestFrontier 
{
    public static void main(String[] args)
    {
        Frontier frontier = new MorpionFrontier();
        
        frontier.getOptions(Player.WHITE);
    }
}
