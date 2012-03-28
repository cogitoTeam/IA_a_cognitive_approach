/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix;
import game.Rules;
import game.morpion.MorpionRules;
import game.reversi.ReversiRules;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


class BootstrapClient extends XMLClient
{
    /* ATTRIBUTES */
    
    private final Sensor sensor;
    private final Actuator actuator;
    private final int id;
    
    
    /* METHODS */
    
    // creation
    public BootstrapClient(String _s_server_url)
    {
        super(_s_server_url);

        // ask the server for a new game, complete with identifier
        Node game_node = getXML().getDocumentElement();
        NamedNodeMap attributes = game_node.getAttributes();
        
        // parse the game identifier from the reply
        String s_game_id = attributes.getNamedItem("id").getNodeValue();
        id = Integer.parseInt(s_game_id);
        
        // parse the rules
        String s_game_rules = attributes.getNamedItem("rules").getNodeValue();
        Rules rules;
        // morpion (naughts and crosses)
        if(s_game_rules.equals("morpion"))
            rules = MorpionRules.getInstance();
        // reversi (otherello)
        else if(s_game_rules.equals("reversi"))
            rules = ReversiRules.getInstance();
        // unknown game
        else
            rules = null;
            
        // parse the initial board
        
        /// TODO FINISH
        BoardMatrix board = null;
        
        // finallycreate the sensor and actuator
        sensor = new Sensor(_s_server_url, rules, board);
        actuator = new Actuator(_s_server_url);
    }
    
    // query
    
    public int getId()
    {
        return id;
    }
    
    public Sensor getSensor()
    {
        return sensor;
    }
    
    public Actuator getActuator()
    {
        return actuator;
    }
    
    /* SUBROUTINES */
}
