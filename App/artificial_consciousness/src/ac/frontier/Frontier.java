/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix.Position;
import game.Game.Player;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;


public abstract class Frontier 
{ 
    /* CONSTANTS */
    protected static final String default_url = "http://localhost:8084/game_service/ws";
    
  
    /* ATTRIBUTES */
   
    private Actuator actuator;
    private Sensor sensor;
    
    
    
    /* METHODS */
    
    // creation
    public Frontier()
    {
        try 
        {
            // attempt to create the external interface
            actuator = createActuator();
            sensor = createSensor();
        } 
        catch (ParserConfigurationException ex) 
        {
            Logger.getLogger(Frontier.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    // query
    
    public boolean tryMove(Player player, Position p)
    {
        // to send information via the external interface
        return actuator.tryMove(p);
    }
    
    public List<Option> getOptions(Player player)
    {
        // to receive information via the external interface
        return sensor.getOptions(player);
    }
    
    

    /* INTERFACE / SUBROUTINES */
    
    protected abstract Actuator createActuator() 
            throws ParserConfigurationException;
    
    protected abstract Sensor createSensor()
            throws ParserConfigurationException;

}
