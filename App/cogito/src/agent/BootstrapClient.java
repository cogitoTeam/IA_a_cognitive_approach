/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package agent;

import game.Game.Player;
import game.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;


class BootstrapClient extends XMLClient
{
    /* ATTRIBUTES */
    
    private final Sensor sensor;
    private final Actuator actuator;
    private final int id;
    private final Player player;
    
    
    /* METHODS */
    
    // creation
    public BootstrapClient(String _s_server_url)
    {
        // create XMLClient
        super(_s_server_url);

        // ask the server for a new game, complete with identifier
        Document doc = this.getXML();
        if(doc == null)
        {
            System.err.println("L'uri \""+_s_server_url+"\" est introuvable.");
            System.exit(1);
        }
            
        Element game_element = doc.getDocumentElement();
        NamedNodeMap attributes = game_element.getAttributes();
        
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
        BoardMatrix board = 
          new BoardMatrix(game_element.getElementsByTagName("board").item(0));
        
        // find out whether we're hosting or joining
        Game.State state = 
            Game.State.valueOf(attributes.getNamedItem("state").getNodeValue());
        boolean is_host = (state == Game.State.WAITING_FOR_PLAYER);
        
        // parse the current player
        attributes = game_element.getElementsByTagName("current_player")
                        .item(0).getAttributes();
        Player current_player = 
            Game.parsePlayer(attributes.getNamedItem("colour").getNodeValue());
        // the host always moves first, so we're the current player iff hosting
        player = (is_host) ? current_player : Game.otherPlayer(current_player);
        
        // finally create the sensor and actuator
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
    
    public Player getPlayer()
    {
        return player;
    }
    
    /* SUBROUTINES */
}
