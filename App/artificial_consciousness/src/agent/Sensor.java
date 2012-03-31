/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package agent;

import game.BoardMatrix;
import game.BoardMatrix.Position;
import game.Game;
import game.Game.Player;
import game.Rules;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


class Sensor extends XMLClient
{
    /* ATTRIBUTES */
    
    private final Rules rules;
    // the most recently seen board is stored in a kind of sensory memory
    private BoardMatrix board;
    private Player current_player;
    
    /* METHODS */
    
    // creation
    public Sensor(String _s_server_url, Rules _rules, BoardMatrix _board)
    {
        super(_s_server_url);
        
        rules = _rules;
        board = _board;
        current_player = _rules.getFirstPlayer();
    }
    
    // query
    
    public boolean renewXML(int game_id)
    {
        // get an XML document from the server
        Document doc = getXML("game_id="+game_id);
        
        // if no changes have occured, report nothing
        if(doc.getDocumentElement().getAttribute("state").equals("NO_CHANGE"))
            return false;
        
        // parse the current player
        Node player_node = 
            doc.getDocumentElement().getElementsByTagName("current_player")
                .item(0);
        current_player = 
            Game.parsePlayer(player_node.getAttributes().getNamedItem("colour")
                .getNodeValue());
        
        // parse the current board
        Node board_node = 
        doc.getDocumentElement().getElementsByTagName("board").item(0);
        board.parseCells(board_node);
        
        // success
        return true;
    }

    public Percept perceiveBoard(Player player)
    {
        // check first that it's our move
        if(player != current_player)
            return new Percept.OpponentTurn(board.copy());
        
        // create a 'choices' percept
        Percept.Choices percept = new Percept.Choices(board.copy());
        
        // get legal moves
        List<Position> moves = rules.getLegalMoves(board, player);
        
        // generate options based on legal moves
        for(Position move : moves)
        {
            // generate and add each option
            Action action = new Action.Move(move);
            BoardMatrix result = rules.getResultingBoard(board, player, move);
            percept.getOptions().add(new Action.Option(action, result));
        }
        
        // result the fruits of our labour !
        return percept;
    }
}
