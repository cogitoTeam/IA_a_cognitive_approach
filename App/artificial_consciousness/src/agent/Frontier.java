/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package agent;

import game.Game.Player;


class Frontier 
{ 
    /* CONSTANTS */
    protected static final String DEFAULT_S_SERVER_URL = 
                                "http://localhost:8084/game_service/ws";
    
  
    /* ATTRIBUTES */
   
    private Actuator actuator;
    private Sensor sensor;
    private int game_id;
    private Player player;
    
    
    
    /* METHODS */
    
    // creation
    public Frontier()
    {
        // boot up the interface
        BootstrapClient bootstrap = new BootstrapClient(DEFAULT_S_SERVER_URL);
        game_id = bootstrap.getId();
        sensor = bootstrap.getSensor();
        actuator = bootstrap.getActuator();
        player = bootstrap.getPlayer();
    }
    
    // query
    
    public boolean tryAction(Action action)
    {
        if(action instanceof Action.Move)
        {
            // to send move via the external interface
            Action.Move move = (Action.Move)action;
            return actuator.tryMove(game_id, player, move.getPosition());
        }
        
        // TODO other types of actions
        
        
        // unknown action type
        else
            return false;  
    }
    
    public Percept newPercept()
    {
        // to receive information via the external interface
        if(sensor.renewXML(game_id))
            return sensor.perceiveBoard(player);
        else
            return null;
    }
    
    public Percept oldPercept()
    {
        return sensor.perceiveBoard(player);
    }

}
