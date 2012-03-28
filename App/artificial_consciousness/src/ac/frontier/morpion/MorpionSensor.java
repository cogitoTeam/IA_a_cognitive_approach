/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier.morpion;

import ac.frontier.Sensor;
import game.BoardMatrix;
import game.morpion.MorpionBoardMatrix;
import javax.xml.parsers.ParserConfigurationException;

public class MorpionSensor extends Sensor
{
    /* IMPLEMENTATIONS */
    
    public MorpionSensor(String s_url) throws ParserConfigurationException
    {
        super(s_url);
    }
    
    @Override
    protected BoardMatrix createBoard() 
    {
        return new MorpionBoardMatrix();
    }

}