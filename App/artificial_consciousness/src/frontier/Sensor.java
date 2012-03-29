/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package frontier;

import game.BoardMatrix;
import game.BoardMatrix.Position;
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
    private final BoardMatrix board;
    
    /* METHODS */
    
    // creation
    public Sensor(String _s_server_url, Rules _rules, BoardMatrix _board)
    {
        super(_s_server_url);
        
        rules = _rules;
        board = _board;
    }
    
    // query
    public Percept parsePerceptXML(int game_id, Player player)
    {
        // get an XML document from the server
        Document doc = getXML("game_id="+game_id);
        
        // parse the current board
        Node board_node = 
                doc.getDocumentElement().getElementsByTagName("board").item(0);
        board.parseCells(board_node);
        
        // create the percept
        int value = rules.getValue(board, player);
        Percept percept = new Percept(board.copy(), value);
        
        // get legal moves
        List<Position> moves = rules.getLegalMoves(board, player);
        
        // generate options based on legal moves
        for(Position move : moves)
        {
            // generate and add each option
            BoardMatrix result = rules.getResultingBoard(board, player, move);
            percept.addOption(move, result);
        }
        
        // result the fruits of our labour !
        System.out.println("connection success");
        return percept;
    }
}
