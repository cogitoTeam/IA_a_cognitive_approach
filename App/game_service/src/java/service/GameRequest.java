/*****************
 * @author william
 * @date 02-May-2012
 *****************/


package service;

import game.BoardMatrix;
import game.Game;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


class GameRequest
{
    // attributes
    private final Integer game_id;
    private final boolean restart;
    private final Game.Player player;
    private final BoardMatrix.Position position;

    // methods
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
}