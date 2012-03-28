/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier.reversi;

import ac.frontier.Actuator;
import ac.frontier.Frontier;
import ac.frontier.Sensor;
import javax.xml.parsers.ParserConfigurationException;


public class ReversiFrontier extends Frontier
{
    /* IMPLEMENTATIONS */
    
    @Override
    protected Actuator createActuator() throws ParserConfigurationException 
    {
        return new ReversiActuator(Frontier.default_url);
    }

    @Override
    protected Sensor createSensor() throws ParserConfigurationException
    {
        return new ReversiSensor(Frontier.default_url);
    }

}
