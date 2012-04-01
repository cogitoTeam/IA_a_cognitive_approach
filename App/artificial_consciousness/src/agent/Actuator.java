/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package agent;

import game.BoardMatrix.Position;
import game.Game;
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
    public boolean tryMove(int game_id, Player player, Position move)
    {
        // prepare the query to send to the server
        String s_query = "&game_id=" + game_id + "&row=" + move.row + "&col=" 
                        + move.col + "&player=" + player;
        
        // return true if the move succeeds, false otherwise
        return parseResult(getXML(s_query).getDocumentElement());
    }
    
    
    public boolean tryRestart(int game_id, Player player) 
    {
        // prepare the query to send to the server
        String s_query = "&game_id=" + game_id + "&restart";
        
        // return true if the restart succeeds
        getXML(s_query).getDocumentElement();
        return true;    /// FIXME -- always true
    }
    
    
    /* SUBROUTINES */
    
    private boolean parseResult(Node game_node)
    {
        // local variables
        NamedNodeMap attributes = game_node.getAttributes();
        String s_state = attributes.getNamedItem("state").getNodeValue();
        
        // parse the state to discover whether the move attempt was a success
        return (Game.State.valueOf(s_state) != Game.State.MOVE_FAILURE);
    }

}
