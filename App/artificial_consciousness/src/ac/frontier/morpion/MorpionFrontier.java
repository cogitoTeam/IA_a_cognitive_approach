/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier.morpion;

import ac.frontier.Actuator;
import ac.frontier.Frontier;
import ac.frontier.Sensor;


public class MorpionFrontier extends Frontier
{
    /* IMPLEMENTATIONS */
    
    @Override
    protected Actuator createActuator()
    {
        return new MorpionActuator(Frontier.DEFAULT_S_SERVER_URL);
    }

    @Override
    protected Sensor createSensor()
    {
        return new MorpionSensor(Frontier.DEFAULT_S_SERVER_URL);
    }

}
