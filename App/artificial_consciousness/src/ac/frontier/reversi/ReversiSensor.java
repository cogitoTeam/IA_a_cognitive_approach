/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier.reversi;

import ac.frontier.Sensor;
import game.BoardMatrix;
import game.Rules;
import game.reversi.ReversiBoardMatrix;
import game.reversi.ReversiRules;


public class ReversiSensor extends Sensor
{
    /* IMPLEMENTATIONS */
    
    public ReversiSensor(String s_url)
    {
        super(s_url);
    }
    
    @Override
    protected BoardMatrix createBoard() 
    {
        return new ReversiBoardMatrix();
    }

    @Override
    protected Rules getRules() 
    {
        return ReversiRules.getInstance();
    }

}
