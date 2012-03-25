/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game.morpion;

import game.Game;


public class MorpionGame extends Game
{
    /** METHODS **/
    
    public MorpionGame()
    {
        super(MorpionRules.getInstance(), new MorpionBoard());
    }
}
