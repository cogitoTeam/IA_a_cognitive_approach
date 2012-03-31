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
        // initialise attributes from boot
        sensor = bootstrap.getSensor();
        actuator = bootstrap.getActuator();
        game_id = bootstrap.getId();
        player = bootstrap.getPlayer();
        System.out.println("Playing game number " + game_id + " as " + player);
    }
    
    // query
    
    public boolean tryAction(Action action)
    {
        switch(action.getType())
        {
            case MOVE:
                Action.Move move = (Action.Move)action;
                return actuator.tryMove(game_id, player, move.getPosition());
            case RESTART:
                return actuator.tryRestart(game_id, player);
            default:
                return false;

        }
    }
    
    public Percept newPercept()
    {
        // to receive information via the external interface
        return sensor.perceive(game_id, player);
    }

}
