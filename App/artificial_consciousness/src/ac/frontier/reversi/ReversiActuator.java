/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier.reversi;

import ac.frontier.Actuator;
import game.Rules;
import game.reversi.ReversiRules;


public class ReversiActuator extends Actuator
{
    /* IMPLEMENTATIONS */
    
    ReversiActuator(String _s_server_url)
    {
        super(_s_server_url);
    }
    
    @Override
    protected Rules getRules() 
    {
        return ReversiRules.getInstance();
    }
}
