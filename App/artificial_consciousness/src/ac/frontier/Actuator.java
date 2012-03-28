/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix.Position;
import game.Rules;


public abstract class Actuator extends XMLClient
{
    /* METHODS */
    
    // creation
    public Actuator(String _base_url)
    {
        super(_base_url);
    }
    
    // command
    boolean tryMove(Position p)
    {
        String s_url = "";
        
        // return true if the move succeed, false otherwise
        getXML(s_url);
        
        return false;
    }
    
    /* INTERFACE */
    
    protected abstract Rules getRules();

}
