/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier.morpion;

import ac.frontier.Sensor;
import game.BoardMatrix;
import game.Rules;
import game.morpion.MorpionBoardMatrix;
import game.morpion.MorpionRules;

public class MorpionSensor extends Sensor
{
    /* IMPLEMENTATIONS */
    
    public MorpionSensor(String _s_server_url)
    {
        super(_s_server_url);
    }
    
    @Override
    protected BoardMatrix createBoard() 
    {
        return new MorpionBoardMatrix();
    }

    @Override
    protected Rules getRules() 
    {
        return MorpionRules.getInstance();
    }

}
