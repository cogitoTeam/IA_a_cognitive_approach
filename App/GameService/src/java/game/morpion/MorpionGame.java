/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game.morpion;

import game.Game;


public class MorpionGame extends Game
{
    /** METHODS **/
    
    public MorpionGame(int id)
    {
        super(id, MorpionRules.getInstance(), new MorpionBoard());
    }
}
