/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier.morpion;

import ac.frontier.Actuator;
import game.Rules;
import game.morpion.MorpionRules;


public class MorpionActuator extends Actuator
{
    /* IMPLEMENTATIONS */
    
    MorpionActuator(String _s_server_url) 
    {
        super(_s_server_url);
    }

    @Override
    protected Rules getRules() 
    {
        return MorpionRules.getInstance();
    }

}
