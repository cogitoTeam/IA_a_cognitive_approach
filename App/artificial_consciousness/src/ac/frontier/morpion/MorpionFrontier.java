/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier.morpion;

import ac.frontier.Actuator;
import ac.frontier.Frontier;
import ac.frontier.Sensor;
import javax.xml.parsers.ParserConfigurationException;


public class MorpionFrontier extends Frontier
{
    /* IMPLEMENTATIONS */
    
    @Override
    protected Actuator createActuator() throws ParserConfigurationException 
    {
        return new MorpionActuator(Frontier.default_url);
    }

    @Override
    protected Sensor createSensor() throws ParserConfigurationException 
    {
        return new MorpionSensor(Frontier.default_url);
    }

}
