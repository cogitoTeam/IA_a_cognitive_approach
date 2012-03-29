/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package frontier;

import game.BoardMatrix.Position;
import game.Game.Player;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


class Actuator extends XMLClient
{
    /* METHODS */
    
    // creation
    public Actuator(String _s_server_url)
    {
        super(_s_server_url);
    }
    
    // command
    boolean tryMove(int game_id, Player player, Position move)
    {
        // prepare the query to send to the server
        String s_query = "&game_id=" + game_id + "&row=" + move.row + "&col=" 
                        + move.col + "&player=" + player;
        
        System.out.println("sending action " + s_query);
        
        // return true if the move succeed, false otherwise
        return parseResult(getXML(s_query).getDocumentElement());
    }
    
    
    /* SUBROUTINES */
    
    private boolean parseResult(Node game_node)
    {
        // local variables
        NamedNodeMap attributes = game_node.getAttributes();
        String s_state = attributes.getNamedItem("state").getNodeValue();
        
        // parse the state to discover whether the move attempt was a success
        return (s_state != null && s_state.equals("MOVE_SUCCESS"));
    }
}
