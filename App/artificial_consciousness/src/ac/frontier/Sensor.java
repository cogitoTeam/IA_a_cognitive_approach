/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix;
import game.BoardMatrix.Position;
import game.Game.Player;
import game.Rules;
import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Document;


class Sensor extends XMLClient
{
    /* ATTRIBUTES */
    
    private final Rules rules;
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
    public List<Option> getOptions(int game_id, Player player)
    {
        // get an XML document from the server
        Document doc = getXML("game_id="+game_id);
        
        // parse the current board
        board.parseCells(doc.getDocumentElement().getElementsByTagName("board")
                                                                    .item(0));
        
        // get legal moves
        List<Position> moves = rules.getLegalMoves(board, player);
        
        // generate options based on legal moves
        List<Option> options = new LinkedList<Option>();
        for(Position move : moves)
            // ad each option
            options.add(new Option(move, rules
                    .getResultingBoard(board, player, move)));
        
        // result the fruits of our labour !
        return options;
    }
}
