/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier.reversi;

import ac.frontier.Actuator;
import ac.frontier.Frontier;
import ac.frontier.Sensor;


public class ReversiFrontier extends Frontier
{
    /* IMPLEMENTATIONS */
    
    @Override
    protected Actuator createActuator()
    {
        return new ReversiActuator(Frontier.default_url);
    }

    @Override
    protected Sensor createSensor()
    {
        return new ReversiSensor(Frontier.default_url);
    }

}
