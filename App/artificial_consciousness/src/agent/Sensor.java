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

import ac.shared.GameEndStatus;


class Sensor extends XMLClient
{
    /* ATTRIBUTES */
    
    private final Rules rules;
    // the most recently seen board is stored in a kind of sensory memory
    private BoardMatrix board;
    
    /* METHODS */
    
    // creation
    public Sensor(String _s_server_url, Rules _rules, BoardMatrix _board)
    {
        super(_s_server_url);
        
        rules = _rules;
        board = _board;
    }
    
    // query
    
    
    Rules getRules() 
    {
        return rules;
    }
    
    public Percept perceive(int game_id, Player player)
    {
        // get an XML document from the server
        Document doc = getXML("game_id="+game_id);
        
        // parse the current state
        Game.State current_state = 
            Game.State.valueOf(doc.getDocumentElement().getAttribute("state"));
        // optimisation: don't bother parsing everything if nothing's changed
        if(current_state == Game.State.NO_CHANGE)
            return null;
        
        // parse the current player
        Node player_node = 
            doc.getDocumentElement().getElementsByTagName("current_player")
                .item(0);
        Player current_player = 
            Game.parsePlayer(player_node.getAttributes().getNamedItem("colour")
                .getNodeValue());
        
        // parse the current board
        Node board_node = 
            doc.getDocumentElement().getElementsByTagName("board").item(0);
        board.parseCells(board_node);
        int board_value = rules.getValue(board, player);
        
        // otherwise reason based on perceived state
        switch(current_state)
        {
            case VICTORY:
                // 'victory' and 'defeat' percepts
                return (player == current_player)
                    ? new Percept.GameEnd(GameEndStatus.VICTORY, board.copy(), board_value)
                    : new Percept.GameEnd(GameEndStatus.DEFEAT, board.copy(), board_value);
                
            case DRAW:
                // 'draw' percept
                return new Percept.GameEnd(GameEndStatus.DRAW, board.copy(), board_value);
                
            case WAITING_FOR_PLAYER:
                // 'waiting for player' percept
                return new Percept.WaitingForPlayer(board);
                
            case PLAYER_JOINED:
            case MOVE_FAILURE:
            case MOVE_SUCCESS:
                // 'choices' percept
                return (player == current_player) 
                    ? perceiveChoices(player)
                    : new Percept.OpponentTurn(board.copy());
                
            
            case NO_CHANGE:
            default:
                // we should never arrive at this case
                return null;
        }
    }
    
    
    /* SUBROUTINES */

    private Percept.Choices perceiveChoices(Player player)
    {
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
