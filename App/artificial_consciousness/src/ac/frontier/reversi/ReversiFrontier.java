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
        return new ReversiActuator(Frontier.DEFAULT_S_SERVER_URL);
    }

    @Override
    protected Sensor createSensor()
    {
        return new ReversiSensor(Frontier.DEFAULT_S_SERVER_URL);
    }

}
