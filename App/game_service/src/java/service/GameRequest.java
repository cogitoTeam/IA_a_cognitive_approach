/*****************
 * @author william
 * @date 02-May-2012
 *****************/


package service;

import game.BoardMatrix;
import game.Game;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


public class GameRequest
{
    /* ATTRIBUTES */
    private final Integer game_id;
    private final boolean restart;
    private final Game.Player player;
    private final BoardMatrix.Position position;

    /* METHODS */
    
    // constructor
    
    /**
     * Parses an HttpServletRequest in order to extract the following:
     * <li>game_id (null if not present)</li>
     * <li>restart (true if present, false otherwise)</li>
     * <li>player (null if not present)</li>
     * <li>Position (null if not present)</li>
     * @param request 
     * The HTTP request to be parsed.
     */
    public GameRequest(HttpServletRequest request)
    {
        // Get all the parameters
        Map<String, String[]> parameters = request.getParameterMap();
        
        // the game identifier
        game_id = (parameters.containsKey("game_id")) 
                ? Integer.parseInt(parameters.get("game_id")[0])
                : null;
        
        // request to restart ?
        restart = parameters.containsKey("restart");
        
        // the player to make the move ?
        player = (parameters.containsKey("player"))
                ? Game.parsePlayer(parameters.get("player")[0])
                : null;
        
        // the position where the move is being made
        final Integer row = (parameters.containsKey("row")) 
                ? Integer.parseInt(parameters.get("row")[0])
                : -1;
        final Integer col = (parameters.containsKey("col")) 
                ? Integer.parseInt(parameters.get("col")[0])
                : -1;
        position = (row != -1 && col != -1 && player != null) 
                ? new BoardMatrix.Position(row, col)
                : null;
    }
    
    // query

    /** Which game is to be retrieved ?
     * @return the game_id
     */
    public Integer getID() 
    {
        return game_id;
    }

    /** Is this a request for the game to be restarted ?
     * @return the restart
     */
    public boolean isRestart() 
    {
        return restart;
    }

    /** Is this a request for a move to be made ?
    * @return true is a well-formed player, row and column was found in the 
    * request.
    */
    public boolean isMove()
    {
        return (player != null & position != null);
    }

    /** Which player is a move being requested for ?
     * @return the player
     */
    public Game.Player getPlayer() 
    {
        return player;
    }

    /** Where is the move is being made ?
     * @return the position
     */
    public BoardMatrix.Position getPosition() 
    {
        return position;
    }
}